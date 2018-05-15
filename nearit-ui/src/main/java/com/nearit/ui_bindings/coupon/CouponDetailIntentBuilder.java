package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */

public class CouponDetailIntentBuilder {

    private final Context mContext;
    private int mIconDrawable;
    private int mSeparatorDrawable;
    private boolean mNoSeparator = false;
    private boolean mNoWakeLock = false;
    private boolean mEnableTapOutsideToClose = false;
    private final Coupon mCoupon;

    private boolean mSingleInstance;

    public CouponDetailIntentBuilder(Context context, Coupon coupon, boolean singleInstance) {
        mContext = context;
        mCoupon = coupon;
        mSingleInstance = singleInstance;
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

    /**
     * Disable wake-lock and max brightness
     */
    public CouponDetailIntentBuilder disableAutoMaxBrightness() {
        mNoWakeLock = true;
        return this;
    }

    public Intent build() {
        if (mSingleInstance) return NearItCouponDetailActivitySingleInstance.createIntent(mContext, mCoupon, getParams());
        else return NearItCouponDetailActivity.createIntent(mContext, mCoupon, getParams());
    }

    private CouponDetailExtraParams getParams() {
        return new CouponDetailExtraParams(
                mIconDrawable,
                mSeparatorDrawable,
                mNoSeparator,
                mNoWakeLock,
                mEnableTapOutsideToClose);
    }

}
