package com.nearit.ui_bindings.permissions;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;

import com.nearit.ui_bindings.ExtraConstants;

/**
 * @author Federico Boschini
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class PermissionsRequestExtraParams implements Parcelable {

    private final boolean enableTapToClose;
    private final boolean autoStartRadar;
    private final boolean invisibleLayoutMode;
    private boolean noBeacon;
    private boolean noNotifications;
    private final boolean nonBlockingBeacon;
    private int headerResourceId;
    private int bluetoothResourceId;
    private int locationResourceId;
    private int notificationsResourceId;
    private int sadFaceResourceId;
    private int worriedFaceResourceId;
    private int happyFaceResourceId;
    private final boolean noHeader;
    private final boolean showNotificationsButton;
    private final String explanation;

    PermissionsRequestExtraParams(
            boolean enableTapToClose,
            boolean autoStartRadar,
            boolean invisibleLayoutMode,
            boolean noBeacon,
            boolean noNotifications,
            boolean nonBlockingBeacon,
            int headerResourceId,
            int bluetoothResourceId,
            int locationResourceId,
            int notificationsResourceId,
            int sadFaceResourceId,
            int worriedFaceResourceId,
            int happyFaceResourceId,
            boolean noHeader,
            boolean showNotificationsButton,
            String explanation) {
        this.enableTapToClose = enableTapToClose;
        this.autoStartRadar = autoStartRadar;
        this.invisibleLayoutMode = invisibleLayoutMode;
        this.noBeacon = noBeacon;
        this.noNotifications = noNotifications;
        this.nonBlockingBeacon = nonBlockingBeacon;
        this.headerResourceId = headerResourceId;
        this.bluetoothResourceId = bluetoothResourceId;
        this.locationResourceId = locationResourceId;
        this.notificationsResourceId = notificationsResourceId;
        this.sadFaceResourceId = sadFaceResourceId;
        this.worriedFaceResourceId = worriedFaceResourceId;
        this.happyFaceResourceId = happyFaceResourceId;
        this.noHeader = noHeader;
        this.showNotificationsButton = showNotificationsButton;
        this.explanation = explanation;
    }

    /**
     * Extract PermissionsRequestExtraParams from an Intent.
     */
    public static PermissionsRequestExtraParams fromIntent(Intent intent) {
        return intent.getParcelableExtra(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Extract PermissionsRequestExtraParams from a Bundle.
     */
    @SuppressWarnings("unused")
    public static PermissionsRequestExtraParams fromBundle(Bundle bundle) {
        return bundle.getParcelable(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Create a bundle containing this PermissionsRequestExtraParams object as {@link
     * ExtraConstants#EXTRA_FLOW_PARAMS}.
     */
    @SuppressWarnings("unused")
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtraConstants.EXTRA_FLOW_PARAMS, this);
        return bundle;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(enableTapToClose ? 1 : 0);
        dest.writeInt(autoStartRadar ? 1 : 0);
        dest.writeInt(invisibleLayoutMode ? 1 : 0);
        dest.writeInt(noBeacon ? 1 : 0);
        dest.writeInt(noNotifications ? 1 : 0);
        dest.writeInt(nonBlockingBeacon ? 1 : 0);
        dest.writeInt(headerResourceId);
        dest.writeInt(bluetoothResourceId);
        dest.writeInt(locationResourceId);
        dest.writeInt(notificationsResourceId);
        dest.writeInt(sadFaceResourceId);
        dest.writeInt(worriedFaceResourceId);
        dest.writeInt(happyFaceResourceId);
        dest.writeInt(noHeader ? 1 : 0);
        dest.writeInt(showNotificationsButton ? 1 : 0);
        dest.writeString(explanation);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PermissionsRequestExtraParams> CREATOR = new Creator<PermissionsRequestExtraParams>() {
        @Override
        public PermissionsRequestExtraParams createFromParcel(Parcel in) {
            boolean enableTapToClose = in.readInt() != 0;
            boolean autoStartRadar = in.readInt() != 0;
            boolean invisibleLayoutMode = in.readInt() != 0;
            boolean noBeacon = in.readInt() != 0;
            boolean noNotifications = in.readInt() != 0;
            boolean nonBlockingBeacon = in.readInt() != 0;
            int headerResourceId = in.readInt();
            int bluetoothResourceId = in.readInt();
            int locationResourceId = in.readInt();
            int notificationsResourceId = in.readInt();
            int sadFaceResourceId = in.readInt();
            int worriedResourceId = in.readInt();
            int happyResourceId = in.readInt();
            boolean noHeader = in.readInt() != 0;
            boolean showNotificationsButton = in.readInt() != 0;
            String explanation = in.readString();

            return new PermissionsRequestExtraParams(
                    enableTapToClose,
                    autoStartRadar,
                    invisibleLayoutMode,
                    noBeacon,
                    noNotifications,
                    nonBlockingBeacon,
                    headerResourceId,
                    bluetoothResourceId,
                    locationResourceId,
                    notificationsResourceId,
                    sadFaceResourceId,
                    worriedResourceId,
                    happyResourceId,
                    noHeader,
                    showNotificationsButton,
                    explanation);
        }

        @Override
        public PermissionsRequestExtraParams[] newArray(int size) {
            return new PermissionsRequestExtraParams[size];
        }
    };

    boolean isEnableTapToClose() {
        return enableTapToClose;
    }

    public boolean isAutoStartRadar() {
        return autoStartRadar;
    }

    boolean isInvisibleLayoutMode() {
        return invisibleLayoutMode;
    }

    public boolean isNoBeacon() {
        return noBeacon;
    }

    public boolean isNoNotifications() {
        return noNotifications;
    }

    public boolean isNonBlockingBeacon() {
        return nonBlockingBeacon;
    }

    int getHeaderResourceId() {
        return headerResourceId;
    }

    boolean isNoHeader() {
        return noHeader;
    }

    public boolean isShowNotificationsButton() {
        return showNotificationsButton;
    }

    public int getBluetoothResourceId() {
        return bluetoothResourceId;
    }

    public int getLocationResourceId() {
        return locationResourceId;
    }

    public int getNotificationsResourceId() {
        return notificationsResourceId;
    }

    public int getSadFaceResourceId() {
        return sadFaceResourceId;
    }

    public int getWorriedFaceResourceId() {
        return worriedFaceResourceId;
    }

    public int getHappyFaceResourceId() {
        return happyFaceResourceId;
    }

    public void setNoBeacon(boolean noBeacon) {
        this.noBeacon = noBeacon;
    }

    public void setNoNotifications(boolean noNotifications) {
        this.noNotifications = noNotifications;
    }

    public String getExplanation() {
        return explanation;
    }
}
