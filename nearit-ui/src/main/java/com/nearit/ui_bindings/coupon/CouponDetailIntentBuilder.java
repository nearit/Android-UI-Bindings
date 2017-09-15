package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class CouponDetailIntentBuilder {
    private Context mContext;
    private int mIconDrawable;
    private int mSeparatorDrawable;
    private boolean mNoSeparator = false;
    private boolean mEnableTapOutsideToClose = false;
    private Coupon mCoupon;

    public CouponDetailIntentBuilder(Context context, Coupon coupon) {
        mContext = context;
        mCoupon = coupon;
    }

    /**
     * Sets a custom icon as a placeholder for coupons without icon
     */
    public CouponDetailIntentBuilder setIconPlaceholderResourceId(int icon) {
        mIconDrawable = icon;
        return this;
    }

    /**
     * Sets a custom separator
     */
    public CouponDetailIntentBuilder setSeparatorResourceId(int separator) {
        mSeparatorDrawable = separator;
        return this;
    }

    /**
     * Sets no separator
     */
    public CouponDetailIntentBuilder setNoSeparator() {
        mNoSeparator = true;
        return this;
    }

    /**
     * Enables or disables tap outside the dialog to close
     * <p>
     * <p> default is false
     */
    public CouponDetailIntentBuilder enableTapOutsideToClose() {
        mEnableTapOutsideToClose = true;
        return this;
    }

    public Intent build() {
        return NearItCouponDetailActivity.createIntent(mContext, mCoupon, getParams());
    }

    private CouponDetailExtraParams getParams() {
        return new CouponDetailExtraParams(
                mIconDrawable,
                mSeparatorDrawable,
                mNoSeparator,
                mEnableTapOutsideToClose);
    }

}
