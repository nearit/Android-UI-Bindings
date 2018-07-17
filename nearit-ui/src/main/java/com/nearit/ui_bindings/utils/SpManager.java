package com.nearit.ui_bindings.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Federico Boschini
 */
public class SpManager {

    private static final String NEAR_PERMISSION_ASKED = "nearit_ui_permission_asked";

    private SharedPreferences sharedPreferences;

    private SpManager(Context context) {
        sharedPreferences = context.getSharedPreferences("nearit_ui", Context.MODE_PRIVATE);
    }

    public boolean locationPermissionAlreadyAsked() {
        return sharedPreferences.getBoolean(NEAR_PERMISSION_ASKED, false);
    }

    public void setLocationPermissionAsked() {
        sharedPreferences.edit().putBoolean(NEAR_PERMISSION_ASKED, true).apply();
    }

    public static SpManager obtain(Context context) {
        return new SpManager(context);
    }

}
