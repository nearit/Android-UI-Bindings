package com.nearit.ui_bindings.utils;

import android.content.Context;
import android.os.Build;

/**
 * @author Federico Boschini
 */
public class VersionManager {

    private Context context;

    private VersionManager(Context context) {
        this.context = context;
    }

    public boolean atLeastMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static VersionManager obtain(Context context) {
        return new VersionManager(context);
    }

}
