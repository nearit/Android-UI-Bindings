package com.nearit.ui_bindings.permissions;

import android.content.Context;
import android.content.Intent;

import com.nearit.ui_bindings.permissions.invisible.NearItInvisiblePermissionsActivity;

/**
 * @author Federico Boschini
 */

public class PermissionsRequestIntentBuilder {
    private final Context context;
    private boolean enableTapOutsideToClose = false;
    private boolean radarAutoStart = false;
    private boolean invisibleLayoutMode = false;
    private boolean noBeacon = false;
    private boolean nonBlockingBeacon = false;
    private int headerResourceId;
    private int bluetoothResourceId;
    private int locationResourceId;
    private int notificationsResourceId;
    private int sadFaceResourceId;
    private int worriedFaceResourceId;
    private int happyFaceResourceId;
    private boolean noHeader = false;
    private boolean showNotificationsButton = false;

    public PermissionsRequestIntentBuilder(Context context) {
        this.context = context;
    }

    /**
     * Enables or disables tap outside the dialog to close
     * <p>
     * <p> default is false
     */
    public PermissionsRequestIntentBuilder enableTapOutsideToClose() {
        enableTapOutsideToClose = true;
        return this;
    }

    /**
     * Enables or disables automatic start of the radar if all permissions are given
     * <p>
     * <p> Automatic radar start default is false
     */
    public PermissionsRequestIntentBuilder automaticRadarStart() {
        radarAutoStart = true;
        return this;
    }

    /**
     * Enables or disables invisible permissions layout mode
     * <p>
     * <p> Default: false
     */
    public PermissionsRequestIntentBuilder invisibleLayoutMode() {
        invisibleLayoutMode = true;
        return this;
    }

    /**
     * Sets BLE requirement
     * <p>
     * <p> Default: false = bluetooth is required
     */
    public PermissionsRequestIntentBuilder noBeacon() {
        noBeacon = true;
        return this;
    }

    /**
     * Sets BLE requirement as non blocking
     * <p>
     * <p> Default: false = bluetooth is required and blocking
     */
    public PermissionsRequestIntentBuilder nonBlockingBeacon() {
        nonBlockingBeacon = true;
        return this;
    }

    /**
     *  Sets a custom header
     */
    public PermissionsRequestIntentBuilder setHeaderResourceId(int headerResourceId) {
        this.headerResourceId = headerResourceId;
        return this;
    }

    /**
     *  Sets a custom bluetoothIcon
     */
    public PermissionsRequestIntentBuilder setBluetoothResourceId(int bluetoothResourceId) {
        this.bluetoothResourceId = bluetoothResourceId;
        return this;
    }

    /**
     *  Sets a custom locationIcon
     */
    public PermissionsRequestIntentBuilder setLocationResourceId(int locationResourceId) {
        this.locationResourceId = locationResourceId;
        return this;
    }

    /**
     *  Sets a custom notificationsIcon
     */
    public PermissionsRequestIntentBuilder setNotificationsResourceId(int notificationsResourceId) {
        this.notificationsResourceId = notificationsResourceId;
        return this;
    }

    /**
     *  Sets a custom sadFaceResourceId
     */
    public PermissionsRequestIntentBuilder setSadFaceResourceId(int sadFaceResourceId) {
        this.sadFaceResourceId = sadFaceResourceId;
        return this;
    }

    /**
     *  Sets a custom happyFaceResourceId
     */
    public PermissionsRequestIntentBuilder setHappyFaceResourceId(int happyFaceResourceId) {
        this.happyFaceResourceId = happyFaceResourceId;
        return this;
    }

    /**
     *  Sets a custom worriedFaceResourceId
     */
    public PermissionsRequestIntentBuilder setWorriedFaceResourceId(int worriedFaceResourceId) {
        this.worriedFaceResourceId = worriedFaceResourceId;
        return this;
    }

    /**
     *  Sets no header
     */
    public PermissionsRequestIntentBuilder setNoHeader() {
        this.noHeader = true;
        return this;
    }

    /**
     *  Enables the notifications "permission".
     *  If this is not called, notifications button will show only if "permission" is missing
     */
    @SuppressWarnings("unused")
    public PermissionsRequestIntentBuilder showNotificationsButton() {
        showNotificationsButton = true;
        return this;
    }

    public Intent build() {
        if (invisibleLayoutMode) {
            return NearItInvisiblePermissionsActivity.createIntent(context, getParams());
        } else {
            return NearItPermissionsActivity.createIntent(context, getParams());
        }
    }

    private PermissionsRequestExtraParams getParams() {
        return new PermissionsRequestExtraParams(
                enableTapOutsideToClose,
                radarAutoStart,
                invisibleLayoutMode,
                noBeacon,
                nonBlockingBeacon,
                headerResourceId,
                bluetoothResourceId,
                locationResourceId,
                notificationsResourceId,
                sadFaceResourceId,
                worriedFaceResourceId,
                happyFaceResourceId,
                noHeader,
                showNotificationsButton);
    }

}
