package com.nearit.ui_bindings.permissions;

import android.content.Context;
import android.content.Intent;

import com.nearit.ui_bindings.PermissionsActivity;

/**
 * Created by Federico Boschini on 29/08/17.
 */
public class PermissionsRequestIntentBuilder {
    private Context mContext;
    private boolean mEnableTapOutsideToClose = false;
    private boolean mRadarAutoStart = false;
    private boolean mInvisibleLayoutMode = false;
    private boolean mNoBLE = false;
    private boolean mNonBlockingBLE = true;

    public PermissionsRequestIntentBuilder(Context context) {
        mContext = context;
    }

    /**
     * Enables or disables tap outside the dialog to close
     * <p>
     * <p> default is false
     */
    public PermissionsRequestIntentBuilder setEnableTapOutsideToClose(boolean enabled) {
        mEnableTapOutsideToClose = enabled;
        return this;
    }

    /**
     * Enables or disables automatic start of the radar if all permissions are given
     * <p>
     * <p> Automatic radar start default is false
     */
    public PermissionsRequestIntentBuilder setAutomaticRadarStart(boolean enabled) {
        mRadarAutoStart = enabled;
        return this;
    }

    /**
     * Enables or disables invisible permissions layout mode
     * <p>
     * <p> Default: false
     */
    public PermissionsRequestIntentBuilder setInvisibleLayoutMode(boolean enabled) {
        mInvisibleLayoutMode = enabled;
        return this;
    }

    /**
     * Sets BLE requirement
     * <p>
     * <p> Default: true = bluetooth is NOT required
     */
    public PermissionsRequestIntentBuilder setNoBLE(boolean enabled) {
        mNoBLE = enabled;
        return this;
    }

    /**
     * Sets BLE requirement as non blocking
     * <p>
     * <p> Default: true = bluetooth is required but non blocking
     */
    public PermissionsRequestIntentBuilder setNonBlockingBLE(boolean enabled) {
        mNonBlockingBLE = enabled;
        return this;
    }

    public Intent build() {
        return PermissionsActivity.createIntent(mContext, getParams());
    }

    private PermissionsRequestIntentExtras getParams() {
        return new PermissionsRequestIntentExtras(
                mEnableTapOutsideToClose,
                mRadarAutoStart,
                mInvisibleLayoutMode,
                mNoBLE,
                mNonBlockingBLE);
    }

}
