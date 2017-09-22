package com.nearit.ui_bindings.permissions;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by Federico Boschini on 29/08/17.
 */
public class PermissionsRequestIntentBuilder {
    private Context mContext;
    private boolean mEnableTapOutsideToClose = false;
    private boolean mRadarAutoStart = false;
    private boolean mInvisibleLayoutMode = false;
    private boolean mNoBeacon = false;
    private boolean mNonBlockingBeacon = false;
    private int mHeaderDrawable;
    private boolean mNoHeader = false;

    public PermissionsRequestIntentBuilder(Context context) {
        mContext = context;
    }

    /**
     * Enables or disables tap outside the dialog to close
     * <p>
     * <p> default is false
     */
    public PermissionsRequestIntentBuilder enableTapOutsideToClose() {
        mEnableTapOutsideToClose = true;
        return this;
    }

    /**
     * Enables or disables automatic start of the radar if all permissions are given
     * <p>
     * <p> Automatic radar start default is false
     */
    public PermissionsRequestIntentBuilder automaticRadarStart() {
        mRadarAutoStart = true;
        return this;
    }

    /**
     * Enables or disables invisible permissions layout mode
     * <p>
     * <p> Default: false
     */
    public PermissionsRequestIntentBuilder invisibleLayoutMode() {
        mInvisibleLayoutMode = true;
        return this;
    }

    /**
     * Sets BLE requirement
     * <p>
     * <p> Default: false = bluetooth is required
     */
    public PermissionsRequestIntentBuilder noBeacon() {
        mNoBeacon = true;
        return this;
    }

    /**
     * Sets BLE requirement as non blocking
     * <p>
     * <p> Default: false = bluetooth is required and blocking
     */
    public PermissionsRequestIntentBuilder nonBlockingBeacon() {
        mNonBlockingBeacon = true;
        return this;
    }

    /**
     *  Sets a custom header
     */
    public PermissionsRequestIntentBuilder setHeaderResourceId(int header) {
        mHeaderDrawable = header;
        return this;
    }

    /**
     *  Sets no header
     */
    public PermissionsRequestIntentBuilder setNoHeader() {
        mNoHeader = true;
        return this;
    }

    public Intent build() {
        return NearItPermissionsActivity.createIntent(mContext, getParams());
    }

    private PermissionsRequestExtraParams getParams() {
        return new PermissionsRequestExtraParams(
                mEnableTapOutsideToClose,
                mRadarAutoStart,
                mInvisibleLayoutMode,
                mNoBeacon,
                mNonBlockingBeacon,
                mHeaderDrawable,
                mNoHeader);
    }

}
