package com.nearit.ui_bindings.permissions.invisible;

import com.nearit.ui_bindings.permissions.PermissionsManager;
import com.nearit.ui_bindings.permissions.PermissionsRequestExtraParams;
import com.nearit.ui_bindings.permissions.State;

import it.near.sdk.NearItManager;

import static com.nearit.ui_bindings.utils.PermissionsUtils.LOCATION_PERMISSION_GRANTED;
import static com.nearit.ui_bindings.utils.PermissionsUtils.LOCATION_PERMISSION_ONLY_IN_USE;

/**
 * @author Federico Boschini
 */
public class NearItInvisiblePresenterImpl implements InvisiblePermissionsContract.InvisiblePermissionsPresenter {

    @SuppressWarnings("unused")
    private static final String TAG = "InvisiblePresenter";

    static final int NEAR_BLUETOOTH_SETTINGS_CODE = 4000;
    static final int NEAR_LOCATION_SETTINGS_CODE = 5000;
    static final int NEAR_PERMISSION_REQUEST_FINE_LOCATION = 6000;

    private InvisiblePermissionsContract.InvisiblePermissionsView view;
    private PermissionsRequestExtraParams params;
    private PermissionsManager permissionsManager;
    private State state;
    private NearItManager nearItManager;

    private boolean flightModeDialogLaunched = false;
    private boolean dontAskAgainDialogLaunched = false;
    private boolean notificationsDialogLaunched = false;

    public NearItInvisiblePresenterImpl(
            InvisiblePermissionsContract.InvisiblePermissionsView view,
            PermissionsRequestExtraParams params,
            PermissionsManager permissionsManager,
            State state,
            NearItManager nearItManager
    ) {
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
            view.requestBluetooth();
        } else {
            if (!permissionsManager.areNotificationsEnabled() && !params.isNoNotifications()) {
                view.showNotificationsDialog();
            } else {
                finalCheck();
            }
        }
    }

    @Override
    public void handleLocationPermissionGranted() {
        if (permissionsManager.isFlightModeOn()) {
            view.showAirplaneDialog();
            flightModeDialogLaunched = true;
        }
    }

    @Override
    public void handleLocationPermissionDenied() {
        // Location Permission denied
        finalCheck();
    }

    @Override
    public void handleLocationServicesDenied() {
        // Location Services dialog canceled
        finalCheck();
    }

    @Override
    public void handleBluetoothGranted() {
        if (checkLocation()) {
            if (permissionsManager.areNotificationsEnabled() || params.isNoNotifications()) {
                finalCheck();
            } else {
                notificationsDialogLaunched = true;
                view.showNotificationsDialog();
            }
        } else {
            if (permissionsManager.isLocationPermissionGranted() == LOCATION_PERMISSION_GRANTED) {
                askLocationServices();
            } else {
                askLocationPermission();
            }
        }
    }

    @Override
    public void handleBluetoothDenied() {
        // Bluetooth dialog canceled
        finalCheck();
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

    @SuppressWarnings("MissingPermission")
    private void onPermissionsReady() {
        if (params.isAutoStartRadar()) {
            nearItManager.startRadar();
        }
        view.finishWithOkResult();
    }

    private void askLocationPermission() {

        int isPermissionGranted = permissionsManager.isLocationPermissionGranted();

        switch (isPermissionGranted) {
            case LOCATION_PERMISSION_ONLY_IN_USE:
            case LOCATION_PERMISSION_GRANTED:
                askLocationServices();
                break;
            default:
                boolean alreadyAsked;
                alreadyAsked = state.getLocationPermissionAsked();
                if (alreadyAsked && !view.shouldShowRequestPermissionRationale()) {
                    view.showDontAskAgainDialog();
                    dontAskAgainDialogLaunched = true;
                } else {
                    state.setLocationPermissionAsked();
                    view.requestLocationPermission();
                }
        }
    }

    private void askLocationServices() {
        if (!permissionsManager.areLocationServicesOn()) {
            state.setLocationAsked();
            view.requestLocationServices(permissionsManager.isBleAvailable() && !params.isNoBeacon());
        } else {
            if (permissionsManager.isBleAvailable() && !params.isNoBeacon() && !permissionsManager.isBluetoothOn()) {
                onLocationServicesOn();
            } else {
                if (!permissionsManager.areNotificationsEnabled() && !params.isNoNotifications()) {
                    state.setNotificationsAsked();
                    view.showNotificationsDialog();
                } else {
                    finalCheck();
                }
            }
        }
    }

    private boolean checkLocation() {
        // TODO: maybe return false if LOCATION_PERMISSION_ONLY_IN_USE ?
        return permissionsManager.areLocationServicesOn() &&
                (permissionsManager.isLocationPermissionGranted() == LOCATION_PERMISSION_GRANTED
                        || permissionsManager.isLocationPermissionGranted() == LOCATION_PERMISSION_ONLY_IN_USE);
    }

}
