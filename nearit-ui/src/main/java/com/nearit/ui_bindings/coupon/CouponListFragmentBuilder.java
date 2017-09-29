package com.nearit.ui_bindings.coupon;

import android.content.Context;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class CouponListFragmentBuilder {
    private int mIconDrawable;
    private int mSeparatorDrawable;
    private boolean mNoSeparator;

    public CouponListFragmentBuilder() {
    }

    /**
     * Sets a custom icon as a placeholder for coupons without icon
     */
    public CouponListFragmentBuilder setIconPlaceholderResourceId(int icon) {
        mIconDrawable = icon;
        return this;
    }

    /**
     * Sets a custom separator
     */
    public CouponListFragmentBuilder setSeparatorResourceId(int separator) {
        mSeparatorDrawable = separator;
        return this;
    }

    /**
     * Sets no separator
     */
    public CouponListFragmentBuilder setNoSeparator() {
        mNoSeparator = true;
        return this;
    }

    public NearItCouponListFragment build() {
        return NearItCouponListFragment.newInstance(getParams());
    }

    private CouponDetailExtraParams getParams() {
        return new CouponDetailExtraParams(
                mIconDrawable,
                mSeparatorDrawable,
                mNoSeparator);
    }

}
