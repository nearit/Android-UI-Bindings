package com.nearit.ui_bindings.permissions;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.nearit.ui_bindings.utils.SpManager;
import com.nearit.ui_bindings.utils.VersionManager;

/**
 * @author Federico Boschini
 */
public class PermissionsPresenterImpl implements PermissionsContract.PermissionsPresenter {

    static final int NEAR_BLUETOOTH_SETTINGS_CODE = 4000;
    static final int NEAR_LOCATION_SETTINGS_CODE = 5000;
    static final int NEAR_PERMISSION_REQUEST_FINE_LOCATION = 6000;

    private PermissionsManager permissionsManager;
    private PermissionsRequestExtraParams params;
    private PermissionsContract.PermissionsView view;
    private SpManager spManager;
    private VersionManager versionManager;

    PermissionsPresenterImpl(PermissionsContract.PermissionsView view, PermissionsRequestExtraParams params, PermissionsManager permissionsManager, SpManager spManager, VersionManager versionManager) {
        this.view = view;
        this.params = params;
        this.permissionsManager = permissionsManager;
        this.spManager = spManager;
        this.versionManager = versionManager;
        init();
    }

    private void init() {
        view.injectPresenter(this);
    }

    @Override
    public void start() {
        checkPermissions();

        if (!permissionsManager.isBleAvailable()) {
            params.setNoBeacon(true);
            view.hideBluetoothButton();
        }

        if (params.isNoHeader()) {
            view.hideHeader();
        } else if (params.getHeaderDrawable() != 0) {
            view.setHeader(params.getHeaderDrawable());
        }

        if (params.isNoBeacon()) {
            view.hideBluetoothButton();
        }
    }

    @Override
    public void onLocationTapped() {
        askLocationPermission();
    }

    @Override
    public void onBluetoothTapped() {
        view.turnOnBluetooth();
    }

    @Override
    public void onNotificationsTapped() {
        if (!permissionsManager.areNotificationsEnabled()) view.showNotificationsDialog();
    }

    private void askLocationPermission() {
        if (versionManager.atLeastMarshmallow()) {

            boolean isPermissionGranted = permissionsManager.isLocationPermissionGranted();

            if (isPermissionGranted) {
                view.turnOnLocationServices(params.isInvisibleLayoutMode() && !params.isNoBeacon());
            } else {
                boolean alreadyAsked;
                alreadyAsked = spManager.locationPermissionAlreadyAsked();
                if (alreadyAsked && !view.shouldShowRequestPermissionRationale()) {
                    view.showDontAskAgainDialog();
                } else {
                    spManager.setLocationPermissionAsked();
                    view.requestLocationPermission();
                }
            }
        } else {
            view.turnOnLocationServices(params.isInvisibleLayoutMode() && !params.isNoBeacon());
        }
    }

    @Override
    public void onLocationServicesOn() {
        checkPermissions();
    }

    @Override
    public void finalCheck() {
        if (checkLocation()) {
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
                    view.turnOnLocationServices(params.isInvisibleLayoutMode() && !params.isNoBeacon());
                } else {
                    view.showAirplaneDialog();
                }
            }
        }
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        checkPermissions();
        if (requestCode == NEAR_LOCATION_SETTINGS_CODE) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }

        if (requestCode == NEAR_BLUETOOTH_SETTINGS_CODE) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void checkPermissions() {
        if (permissionsManager.isBluetoothOn()) {
            view.setBluetoothButtonChecked();
        } else {
            view.setBluetoothButtonUnchecked();
        }

        if (checkLocation()) {
            view.setLocationButtonChecked();
        } else {
            view.setLocationButtonUnchecked();
        }

        if (permissionsManager.areNotificationsEnabled()) {
            view.setNotificationsButtonChecked();
        } else {
            view.setNotificationsButtonUnchecked();
        }
    }

    private boolean checkLocation() {
        return permissionsManager.areLocationServicesOn() &&
                permissionsManager.isLocationPermissionGranted();
    }

}
