package com.nearit.ui_bindings.permissions;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;

import com.nearit.ui_bindings.ExtraConstants;

/**
 * Created by Federico Boschini on 29/08/17.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class PermissionsRequestExtraParams implements Parcelable {

    private final boolean enableTapToClose;
    private final boolean autoStartRadar;
    private final boolean invisibleLayoutMode;
    private final boolean noBeacon;
    private final boolean nonBlockingBeacon;

    PermissionsRequestExtraParams(
            boolean enableTapToClose,
            boolean autoStartRadar,
            boolean invisibleLayoutMode,
            boolean noBeacon,
            boolean nonBlockingBeacon) {
        this.enableTapToClose = enableTapToClose;
        this.autoStartRadar = autoStartRadar;
        this.invisibleLayoutMode = invisibleLayoutMode;
        this.noBeacon = noBeacon;
        this.nonBlockingBeacon = nonBlockingBeacon;
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
    public static PermissionsRequestExtraParams fromBundle(Bundle bundle) {
        return bundle.getParcelable(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Create a bundle containing this PermissionsRequestExtraParams object as {@link
     * ExtraConstants#EXTRA_FLOW_PARAMS}.
     */
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
        dest.writeInt(nonBlockingBeacon ? 1 : 0);
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
            boolean nonBlockingBeacon = in.readInt() != 0;

            return new PermissionsRequestExtraParams(
                    enableTapToClose,
                    autoStartRadar,
                    invisibleLayoutMode,
                    noBeacon,
                    nonBlockingBeacon);
        }

        @Override
        public PermissionsRequestExtraParams[] newArray(int size) {
            return new PermissionsRequestExtraParams[size];
        }
    };

    public boolean isEnableTapToClose() {
        return enableTapToClose;
    }

    public boolean isAutoStartRadar() {
        return autoStartRadar;
    }

    public boolean isInvisibleLayoutMode() {
        return invisibleLayoutMode;
    }

    public boolean isNoBeacon() {
        return noBeacon;
    }

    public boolean isNonBlockingBeacon() {
        return nonBlockingBeacon;
    }
}