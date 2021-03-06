package com.nearit.ui_bindings.permissions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import it.near.sdk.NearItManager;

import static com.nearit.ui_bindings.permissions.views.PermissionButton.NO_ICON;

/**
 * @author Federico Boschini
 */
public class PermissionsPresenterImpl implements PermissionsContract.PermissionsPresenter {

    @SuppressWarnings("unused")
    private static final String TAG = "PermissionsPresenter";

    static final int NEAR_BLUETOOTH_SETTINGS_CODE = 4000;
    static final int NEAR_LOCATION_SETTINGS_CODE = 5000;
    static final int NEAR_PERMISSION_REQUEST_FINE_LOCATION = 6000;

    private PermissionsManager permissionsManager;
    private PermissionsRequestExtraParams params;
    private PermissionsContract.PermissionsView view;
    private State state;
    private NearItManager nearItManager;

    PermissionsPresenterImpl(PermissionsContract.PermissionsView view, PermissionsRequestExtraParams params, PermissionsManager permissionsManager, State state, NearItManager nearItManager) {
        this.view = view;
        this.params = params;
        this.permissionsManager = permissionsManager;
        this.state = state;
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
            view.hideBluetoothButton();
        }

        if (params.isNoHeader()) {
            view.hideHeader();
        } else if (params.getHeaderResourceId() != NO_ICON) {
            view.setHeader(params.getHeaderResourceId());
        }

        if (params.getBluetoothResourceId() != NO_ICON) {
            view.setBluetoothIcon(params.getBluetoothResourceId());
        }
        if (params.getLocationResourceId() != NO_ICON) {
            view.setLocationIcon(params.getLocationResourceId());
        }
        if (params.getNotificationsResourceId() != NO_ICON) {
            view.setNotificationsIcon(params.getNotificationsResourceId());
        }
        if (params.getSadFaceResourceId() != NO_ICON) {
            view.setSadFaceIcon(params.getSadFaceResourceId());
        }
        if (params.getWorriedFaceResourceId() != NO_ICON) {
            view.setWorriedFaceIcon(params.getWorriedFaceResourceId());
        }
        if (params.getHappyFaceResourceId() != NO_ICON) {
            view.setHappyIcon(params.getHappyFaceResourceId());
        }

        if (params.isNoBeacon()) {
            view.hideBluetoothButton();
        }

        if (params.isShowNotificationsButton() || !permissionsManager.areNotificationsEnabled()) {
            view.showNotificationsButton();
        } else {
            view.hideNotificationsButton();
        }

        checkPermissions();

    }

    @Override
    public void onLocationTapped() {
        state.setLocationAsked();
        askLocationPermission();
    }

    @Override
    public void onBluetoothTapped() {
        state.setBluetoothAsked();
        view.turnOnBluetooth();
    }

    @Override
    public void onNotificationsTapped() {
        if (!permissionsManager.areNotificationsEnabled()) {
            state.setNotificationsAsked();
            view.showNotificationsDialog();
        }
    }

    private void askLocationPermission() {

        boolean isPermissionGranted = permissionsManager.isLocationPermissionGranted();

        if (isPermissionGranted) {
            if (!permissionsManager.isFlightModeOn()) {
                state.setLocationAsked();
                view.turnOnLocationServices(params.isInvisibleLayoutMode() && !params.isNoBeacon());
            } else {
                view.showAirplaneDialog();
            }
        } else {
            boolean alreadyAsked;
            alreadyAsked = state.getLocationPermissionAsked();
            if (alreadyAsked && !view.shouldShowRequestPermissionRationale()) {
                view.showDontAskAgainDialog();
            } else {
                state.setLocationPermissionAsked();
                view.requestLocationPermission();
            }
        }
    }

    @Override
    public void onLocationServicesOn() {
        checkPermissions();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void finalCheck() {
        if (checkLocation()) {
            if (params.isAutoStartRadar()) {
                nearItManager.startRadar();
            }
            if ((permissionsManager.isBluetoothOn()
                    || params.isNoBeacon()
                    || params.isNonBlockingBeacon()
                    || !permissionsManager.isBleAvailable())
                    && permissionsManager.areNotificationsEnabled()) {
                view.finishWithOkResult();
            }
        }
        view.finishWithKoResult();
    }

    @Override
    public void handlePermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == NEAR_PERMISSION_REQUEST_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!permissionsManager.isFlightModeOn()) {
                    state.setLocationAsked();
                    view.turnOnLocationServices(params.isInvisibleLayoutMode() && !params.isNoBeacon());
                } else {
                    view.showAirplaneDialog();
                }
            } else {
                checkPermissions();
            }
        }
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        checkPermissions();
    }

    @Override
    public void onDialogCanceled() {
        checkPermissions();
    }

    @Override
    public void stop() {

    }

    @Override
    public void checkPermissions() {
        if (state.getBluetoothAsked() || state.getLocationAsked() || state.getNotificationsAsked()) {
            view.refreshCloseText();
        }
        if (permissionsManager.isBluetoothOn()) {
            view.setBluetoothButtonHappy();
        } else {
            if (state.getBluetoothAsked()) {
                view.setBluetoothButtonSad();
            } else {
                view.resetBluetoothButton();
            }
        }

        if (permissionsManager.isLocationPermissionGranted() && permissionsManager.areLocationServicesOn()) {
            view.setLocationButtonHappy();
        } else {
            if (state.getLocationAsked()) {
                if (permissionsManager.isLocationPermissionGranted()) {
                    if (!permissionsManager.areLocationServicesOn()) {
                        view.setLocationButtonWorried();
                    }
                } else {
                    view.setLocationButtonSad();
                }
            } else {
                view.resetLocationButton();
            }
        }

        if (permissionsManager.areNotificationsEnabled()) {
            view.setNotificationsButtonHappy();
        } else {
            view.showNotificationsButton();
            if (state.getNotificationsAsked()) {
                view.setNotificationsButtonSad();
            } else {
                view.resetNotificationsButton();
            }
        }
    }

    private boolean checkLocation() {
        return permissionsManager.areLocationServicesOn() &&
                permissionsManager.isLocationPermissionGranted();
    }

}
