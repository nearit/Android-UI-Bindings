package com.nearit.ui_bindings.permissions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.nearit.ui_bindings.utils.VersionManager;

import it.near.sdk.NearItManager;

import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_BLUETOOTH_SETTINGS_CODE;
import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_LOCATION_SETTINGS_CODE;
import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_PERMISSION_REQUEST_FINE_LOCATION;

/**
 * @author Federico Boschini
 */
public class NearItInvisiblePresenterImpl implements InvisiblePermissionsContract.InvisiblePermissionsPresenter {

    private InvisiblePermissionsContract.InvisiblePermissionsView view;
    private PermissionsRequestExtraParams params;
    private PermissionsManager permissionsManager;
    private State state;
    private VersionManager versionManager;
    private NearItManager nearItManager;

    private boolean flightModeDialogLaunched = false;
    private boolean dontAskAgainDialogLaunched = false;
    private boolean notificationsDialogLaunched = false;

    NearItInvisiblePresenterImpl(
            InvisiblePermissionsContract.InvisiblePermissionsView view,
            PermissionsRequestExtraParams params,
            PermissionsManager permissionsManager,
            State state,
            VersionManager versionManager,
            NearItManager nearItManager
    ) {
        this.view = view;
        this.params = params;
        this.permissionsManager = permissionsManager;
        this.state = state;
        this.versionManager = versionManager;
        this.nearItManager = nearItManager;
        init();
    }

    private void init() {
        view.injectPresenter(this);
    }

    @Override
    public void start() {
        if (flightModeDialogLaunched || dontAskAgainDialogLaunched || notificationsDialogLaunched) {
            view.recreate();
        }

        if (!permissionsManager.isBleAvailable()) {
            params.setNoBeacon(true);
        }

        if (permissionsManager.isFlightModeOn()) {
            view.showAirplaneDialog();
            flightModeDialogLaunched = true;
        } else {
            boolean allPermissionsGiven =
                    permissionsManager.isBluetoothOn()
                            && checkLocation()
                            && permissionsManager.areNotificationsEnabled();

            if (allPermissionsGiven) {
                view.finishWithOkResult();
            } else {
                askLocationPermission();
            }
        }
    }

    @Override
    public void onLocationServicesOn() {
        if (!params.isNoBeacon() && permissionsManager.isBleAvailable() && !permissionsManager.isBluetoothOn()) {
            view.turnOnBluetooth();
        } else {
            if (!permissionsManager.areNotificationsEnabled()) {
                view.showNotificationsDialog();
            } else {
                finalCheck();
            }
        }
    }

    @Override
    public void handlePermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == NEAR_PERMISSION_REQUEST_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!permissionsManager.isFlightModeOn()) {
                    view.turnOnLocationServices(!params.isNoBeacon() && permissionsManager.isBleAvailable());
                } else {
                    view.showAirplaneDialog();
                    flightModeDialogLaunched = true;
                }
            } else {
                //  DENIED
                finalCheck();
            }
        }
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEAR_LOCATION_SETTINGS_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                //  CANCELED
                finalCheck();
            }
        }

        if (requestCode == NEAR_BLUETOOTH_SETTINGS_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (checkLocation()) {
                    if (permissionsManager.areNotificationsEnabled()) {
                        finalCheck();
                    } else {
                        notificationsDialogLaunched = true;
                        view.showNotificationsDialog();
                    }
                } else {
                    askLocationPermission();
                }
            } else {
                //  CANCELED
                finalCheck();
            }
        }
    }

    @Override
    public void onDialogClosed() {
        finalCheck();
    }

    @Override
    public void stop() {

    }

    /**
     * Checks one last time that everything is ok
     */
    private void finalCheck() {
        if (checkLocation()) {
            if (permissionsManager.isBluetoothOn() || params.isNoBeacon() || params.isNonBlockingBeacon() || !permissionsManager.isBleAvailable()) {
                onPermissionsReady();
            } else {
                view.finishWithKoResult();
            }
        } else {
            view.finishWithKoResult();
        }
    }

    @SuppressLint("MissingPermission")
    private void onPermissionsReady() {
        if (params.isAutoStartRadar()) {
            nearItManager.startRadar();
        }
        view.finishWithOkResult();
    }

    private void askLocationPermission() {
        if (versionManager.atLeastMarshmallow()) {
            boolean isPermissionGranted = permissionsManager.isLocationPermissionGranted();

            if (isPermissionGranted) {
                askLocationServices();
            } else {
                boolean alreadyAsked;
                alreadyAsked = state.getLocationPermissionAsked();
                if (alreadyAsked && !view.shouldShowRequestPermissionRationale()) {
                    view.showDontAskAgainDialog();
                    dontAskAgainDialogLaunched = true;
                } else {
                    state.setLocationPermissionAsked();
                    state.setLocationAsked();
                    view.requestLocationPermission();
                }
            }
        } else {
            askLocationServices();
        }
    }

    private void askLocationServices() {
        if (!permissionsManager.areLocationServicesOn()) {
            state.setLocationAsked();
            view.turnOnLocationServices(permissionsManager.isBleAvailable() && !params.isNoBeacon());
        } else {
            if (permissionsManager.isBleAvailable() && !params.isNoBeacon() && !permissionsManager.isBluetoothOn()) {
                onLocationServicesOn();
            } else {
                if (!permissionsManager.areNotificationsEnabled()) {
                    state.setNotificationsAsked();
                    view.showNotificationsDialog();
                } else {
                    finalCheck();
                }
            }
        }
    }

    private boolean checkLocation() {
        return permissionsManager.areLocationServicesOn() &&
                permissionsManager.isLocationPermissionGranted();
    }

    public static NearItInvisiblePresenterImpl obtain(InvisiblePermissionsContract.InvisiblePermissionsView view, PermissionsRequestExtraParams params, Context context) {
        return new NearItInvisiblePresenterImpl(view, params, PermissionsManager.obtain(context), State.obtain(context), VersionManager.obtain(context), NearItManager.getInstance());
    }
}
