package com.nearit.ui_bindings.coupon;

import android.content.Context;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class CouponDetailFragmentBuilder {
    private Context mContext;
    private int mIconDrawable;
    private int mSeparatorDrawable;
    private boolean mNoSeparator;
    private Coupon mCoupon;

    public CouponDetailFragmentBuilder(Context context, Coupon coupon) {
        mContext = context;
        mCoupon = coupon;
    }

    /**
     * Sets a custom icon as a placeholder for coupons without icon
     */
    public CouponDetailFragmentBuilder setIconPlaceholderResourceId(int icon) {
        mIconDrawable = icon;
        return this;
    }

    /**
     * Sets a custom separator
     */
    public CouponDetailFragmentBuilder setSeparatorResourceId(int separator) {
        mSeparatorDrawable = separator;
        return this;
    }

    /**
     * Sets no separator
     */
    public CouponDetailFragmentBuilder setNoSeparator() {
        mNoSeparator = true;
        return this;
    }

    public NearItCouponDetailFragment build() {
        return NearItCouponDetailFragment.newInstance(mCoupon, getParams());
    }

    private CouponDetailExtraParams getParams() {
        return new CouponDetailExtraParams(
                mIconDrawable,
                mSeparatorDrawable,
                mNoSeparator);
    }

}
