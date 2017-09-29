package com.nearit.ui_bindings.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearit.ui_bindings.ExtraConstants;

/**
 * Created by Federico Boschini on 06/09/17.
 */

class CouponListExtraParams implements Parcelable {

    private final int iconDrawable;
    private final int separatorDrawable;
    private final boolean noSeparator;
    private final boolean enableTapOutsideToClose;

    CouponListExtraParams(int iconDrawable, int separatorDrawable, boolean noSeparator, boolean enableTapOutsideToClose) {
        this.iconDrawable = iconDrawable;
        this.separatorDrawable = separatorDrawable;
        this.noSeparator = noSeparator;
        this.enableTapOutsideToClose = enableTapOutsideToClose;
    }

    /**
     * Extract CouponDetailExtraParams from an Intent.
     */
    static CouponListExtraParams fromIntent(Intent intent) {
        return intent.getParcelableExtra(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Extract CouponDetailExtraParams from a Bundle.
     */
    public static CouponListExtraParams fromBundle(Bundle bundle) {
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

    public static final Creator<CouponListExtraParams> CREATOR = new Creator<CouponListExtraParams>() {
        @Override
        public CouponListExtraParams createFromParcel(Parcel in) {
            int iconDrawable = in.readInt();
            int separatorDrawable = in.readInt();
            boolean noSeparator = in.readInt() !=0;
            boolean enableTapToClose = in.readInt() !=0;
            return new CouponListExtraParams(
                    iconDrawable,
                    separatorDrawable,
                    noSeparator,
                    enableTapToClose
            );
        }

        @Override
        public CouponListExtraParams[] newArray(int size) {
            return new CouponListExtraParams[size];
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
        dest.writeInt(noSeparator ? 1 : 0);
        dest.writeInt(enableTapOutsideToClose ? 1 : 0);
    }

    int getIconDrawable() {
        return iconDrawable;
    }

    int getSeparatorDrawable() {
        return separatorDrawable;
    }

    boolean isEnableTapOutsideToClose() {
        return enableTapOutsideToClose;
    }

    boolean isNoSeparator() {
        return noSeparator;
    }
}
