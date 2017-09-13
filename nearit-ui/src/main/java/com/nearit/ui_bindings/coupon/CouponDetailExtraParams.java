package com.nearit.ui_bindings.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearit.ui_bindings.ExtraConstants;

/**
 * Created by Federico Boschini on 06/09/17.
 */

class CouponDetailExtraParams implements Parcelable {

    private final int iconDrawable;
    private final int separatorDrawable;
    private final boolean enableTapOutsideToClose;

    CouponDetailExtraParams(int iconDrawable, int separatorDrawable, boolean enableTapOutsideToClose) {
        this.iconDrawable = iconDrawable;
        this.separatorDrawable = separatorDrawable;
        this.enableTapOutsideToClose = enableTapOutsideToClose;
    }

    public CouponDetailExtraParams(int iconDrawable, int separatorDrawable) {
        this.iconDrawable = iconDrawable;
        this.separatorDrawable = separatorDrawable;
        this.enableTapOutsideToClose = false;
    }

    /**
     * Extract CouponDetailExtraParams from an Intent.
     */
    public static CouponDetailExtraParams fromIntent(Intent intent) {
        return intent.getParcelableExtra(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Extract CouponDetailExtraParams from a Bundle.
     */
    public static CouponDetailExtraParams fromBundle(Bundle bundle) {
        return bundle.getParcelable(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Create a bundle containing this CouponDetailExtraParams object as {@link
     * ExtraConstants#EXTRA_FLOW_PARAMS}.
     */
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtraConstants.EXTRA_FLOW_PARAMS, this);
        return bundle;
    }

    public static final Creator<CouponDetailExtraParams> CREATOR = new Creator<CouponDetailExtraParams>() {
        @Override
        public CouponDetailExtraParams createFromParcel(Parcel in) {
            int iconDrawable = in.readInt();
            int separatorDrawable = in.readInt();
            boolean enableTapToClose = in.readInt() !=0;
            return new CouponDetailExtraParams(
                    iconDrawable,
                    separatorDrawable,
                    enableTapToClose
            );
        }

        @Override
        public CouponDetailExtraParams[] newArray(int size) {
            return new CouponDetailExtraParams[size];
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
