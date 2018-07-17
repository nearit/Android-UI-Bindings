package com.nearit.ui_bindings.permissions;

import android.content.Context;

import com.nearit.ui_bindings.utils.PermissionsUtils;

/**
 * @author Federico Boschini
 */
public class PermissionsManager {

    private Context context;

    private PermissionsManager(Context context) {
        this.context = context;
    }

    boolean isBleAvailable() {
        return PermissionsUtils.isBleAvailable(context);
    }

    boolean areNotificationsEnabled() {
        return PermissionsUtils.areNotificationsEnabled(context);
    }

    boolean isLocationPermissionGranted() {
        return PermissionsUtils.checkLocationPermission(context);
    }

    boolean areLocationServicesOn() {
        return PermissionsUtils.checkLocationServices(context);
    }

    boolean isBluetoothOn() {
        return PermissionsUtils.checkBluetooth(context);
    }

    boolean isFlightModeOn() {
        return PermissionsUtils.isAirplaneModeOn(context);
    }

    public static PermissionsManager obtain(Context context) {
        return new PermissionsManager(context);
    }
}
