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

    public boolean isBleAvailable() {
        return PermissionsUtils.isBleAvailable(context);
    }

    public boolean areNotificationsEnabled() {
        return PermissionsUtils.areNotificationsEnabled(context);
    }

    public boolean isLocationPermissionGranted() {
        return PermissionsUtils.checkLocationPermission(context);
    }

    public boolean areLocationServicesOn() {
        return PermissionsUtils.checkLocationServices(context);
    }

    public boolean isBluetoothOn() {
        return PermissionsUtils.checkBluetooth(context);
    }

    public boolean isFlightModeOn() {
        return PermissionsUtils.isAirplaneModeOn(context);
    }

    public static PermissionsManager obtain(Context context) {
        return new PermissionsManager(context);
    }
}
