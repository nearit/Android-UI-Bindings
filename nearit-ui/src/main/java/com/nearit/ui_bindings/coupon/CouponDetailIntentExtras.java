package com.nearit.ui_bindings.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.permissions.PermissionsRequestIntentExtras;

/**
 * Created by Federico Boschini on 06/09/17.
 */

class CouponDetailIntentExtras implements Parcelable {

    private final int iconDrawable;
    private final int separatorDrawable;
    private final boolean enableTapOutsideToClose;

    CouponDetailIntentExtras(int iconDrawable, int separatorDrawable, boolean enableTapOutsideToClose) {
        this.iconDrawable = iconDrawable;
        this.separatorDrawable = separatorDrawable;
        this.enableTapOutsideToClose = enableTapOutsideToClose;
    }

    /**
     * Extract CouponDetailIntentExtras from an Intent.
     */
    public static CouponDetailIntentExtras fromIntent(Intent intent) {
        return intent.getParcelableExtra(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Extract CouponDetailIntentExtras from a Bundle.
     */
    public static CouponDetailIntentExtras fromBundle(Bundle bundle) {
        return bundle.getParcelable(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Create a bundle containing this CouponDetailIntentExtras object as {@link
     * ExtraConstants#EXTRA_FLOW_PARAMS}.
     */
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtraConstants.EXTRA_FLOW_PARAMS, this);
        return bundle;
    }

    public static final Creator<CouponDetailIntentExtras> CREATOR = new Creator<CouponDetailIntentExtras>() {
        @Override
        public CouponDetailIntentExtras createFromParcel(Parcel in) {
            int iconDrawable = in.readInt();
            int separatorDrawable = in.readInt();
            boolean enableTapToClose = in.readInt() !=0;
            return new CouponDetailIntentExtras(
                    iconDrawable,
                    separatorDrawable,
                    enableTapToClose
            );
        }

        @Override
        public CouponDetailIntentExtras[] newArray(int size) {
            return new CouponDetailIntentExtras[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iconDrawable);
        dest.writeInt(separatorDrawable);
        dest.writeInt(enableTapOutsideToClose ? 1 : 0);
    }

    public int getIconDrawable() {
        return iconDrawable;
    }

    public int getSeparatorDrawable() {
        return separatorDrawable;
    }

    public boolean isEnableTapOutsideToClose() {
        return enableTapOutsideToClose;
    }
}
