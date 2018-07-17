package com.nearit.ui_bindings.permissions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.nearit.ui_bindings.utils.SpManager;
import com.nearit.ui_bindings.utils.VersionManager;

import it.near.sdk.NearItManager;

/**
 * @author Federico Boschini
 */
public class NearItInvisiblePresenterImpl implements InvisiblePermissionsContract.InvisiblePermissionsPresenter {

    static final int NEAR_BLUETOOTH_SETTINGS_CODE = 4000;
    static final int NEAR_LOCATION_SETTINGS_CODE = 5000;
    static final int NEAR_PERMISSION_REQUEST_FINE_LOCATION = 6000;

    private InvisiblePermissionsContract.InvisiblePermissionsView view;
    private PermissionsRequestExtraParams params;
    private PermissionsManager permissionsManager;
    private SpManager spManager;
    private VersionManager versionManager;
    private NearItManager nearItManager;

    private boolean flightModeDialogLaunched = false;
    private boolean dontAskAgainDialogLaunched = false;
    private boolean notificationsDialogLaunched = false;

    private NearItInvisiblePresenterImpl(
            InvisiblePermissionsContract.InvisiblePermissionsView view,
            PermissionsRequestExtraParams params,
            PermissionsManager permissionsManager,
            SpManager spManager,
            VersionManager versionManager,
            NearItManager nearItManager
    ) {
        this.view = view;
        this.params = params;
        this.permissionsManager = permissionsManager;
        this.spManager = spManager;
        this.versionManager = versionManager;
        this.nearItManager = nearItManager;
        init();
    }

    private void init() {
        view.injectPresenter(this);
    }

    @Override
    public void start() {
        if (!permissionsManager.isBleAvailable()) {
            params.setNoBeacon(true);
        }

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

    @Override
    public void onLocationServicesOn() {
        if (!params.isNoBeacon() && permissionsManager.isBleAvailable() && !permissionsManager.isBluetoothOn()) {
            view.turnOnBluetooth();
        } else {
            finalCheck();
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
            if (resultCode == Activity.RESULT_OK) {
                // ???
            } else {
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
        if (permissionsManager.isFlightModeOn()) {
            view.finishWithKoResult();
        }
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
                if (!permissionsManager.areLocationServicesOn()) {
                    view.turnOnLocationServices(permissionsManager.isBleAvailable() && !params.isNoBeacon());
                } else {
                    if (!permissionsManager.isBluetoothOn() && !params.isNoBeacon() && permissionsManager.isBleAvailable()) {
                        onLocationServicesOn();
                    } else {
                        if (!permissionsManager.areNotificationsEnabled()) {
                            view.showNotificationsDialog();
                        } else {
                            finalCheck();
                        }
                    }
                }
            } else {
                boolean alreadyAsked;
                alreadyAsked = spManager.locationPermissionAlreadyAsked();
                if (alreadyAsked && !view.shouldShowRequestPermissionRationale()) {
                    view.showDontAskAgainDialog();
                    dontAskAgainDialogLaunched = true;
                } else {
                    spManager.setLocationPermissionAsked();
                    view.requestLocationPermission();
                }
            }
        } else {
            if (!permissionsManager.areLocationServicesOn()) {
                view.turnOnLocationServices(permissionsManager.isBleAvailable() && !params.isNoBeacon());
            } else {
                if (permissionsManager.isBleAvailable() && !params.isNoBeacon() && !permissionsManager.isBluetoothOn()) {
                    onLocationServicesOn();
                } else {
                    if (!permissionsManager.areNotificationsEnabled()) {
                        view.showNotificationsDialog();
                    }
                }
            }
        }
    }

    private boolean checkLocation() {
        return permissionsManager.areLocationServicesOn() &&
                permissionsManager.isLocationPermissionGranted();
    }

    public static NearItInvisiblePresenterImpl obtain(InvisiblePermissionsContract.InvisiblePermissionsView view, PermissionsRequestExtraParams params, Context context) {
        return new NearItInvisiblePresenterImpl(view, params, PermissionsManager.obtain(context), SpManager.obtain(context), VersionManager.obtain(context), NearItManager.getInstance());
    }
}
