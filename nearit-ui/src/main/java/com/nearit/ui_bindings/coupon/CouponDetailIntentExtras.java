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

    CouponDetailIntentExtras() {
    }

    /**
     * Extract CouponDetailIntentExtras from an Intent.
     */
    public static CouponDetailIntentBuilder fromIntent(Intent intent) {
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
            return new CouponDetailIntentExtras();
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

    }
}
