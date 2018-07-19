package com.nearit.ui_bindings.permissions;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Federico Boschini
 */
public class State {

    private static final String NEAR_LOCATION_ALREADY_ASKED = "nearit_ui_location_permission_already_asked";
    private static final String NEAR_LOCATION_ASKED = "nearit_ui_location_permission_asked";
    private static final String NEAR_BLUETOOTH_ASKED = "nearit_ui_bluetooth_asked";
    private static final String NEAR_NOTIFICATIONS_ASKED = "nearit_ui_notifications_asked";

    private SharedPreferences sharedPreferences;

    private State(Context context) {
        this.sharedPreferences = context.getSharedPreferences("nearit_ui", Context.MODE_PRIVATE);
    }

    public boolean getLocationPermissionAsked() {
        return sharedPreferences.getBoolean(NEAR_LOCATION_ALREADY_ASKED, false);
    }

    public void setLocationPermissionAsked() {
        sharedPreferences.edit().putBoolean(NEAR_LOCATION_ALREADY_ASKED, true).apply();
    }

    public boolean getLocationAsked() {
        return sharedPreferences.getBoolean(NEAR_LOCATION_ASKED, false);
    }

    public void setLocationAsked() {
        sharedPreferences.edit().putBoolean(NEAR_LOCATION_ASKED, true).apply();
    }

    public boolean getBluetoothAsked() {
        return sharedPreferences.getBoolean(NEAR_BLUETOOTH_ASKED, false);
    }

    public void setBluetoothAsked() {
        sharedPreferences.edit().putBoolean(NEAR_BLUETOOTH_ASKED, true).apply();
    }

    public boolean getNotificationsAsked() {
        return sharedPreferences.getBoolean(NEAR_NOTIFICATIONS_ASKED, false);
    }

    public void setNotificationsAsked() {
        sharedPreferences.edit().putBoolean(NEAR_NOTIFICATIONS_ASKED, true).apply();
    }

    public static State obtain(Context context) {
        return new State(context);
    }
}
