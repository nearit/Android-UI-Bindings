package com.nearit.ui_bindings.coupon;

import com.nearit.ui_bindings.coupon.detail.CouponDetailExtraParams;
import com.nearit.ui_bindings.coupon.detail.NearItCouponDetailFragment;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */

public class CouponDetailFragmentBuilder {
    private int mIconDrawable;
    private int mSeparatorDrawable;
    private boolean mNoSeparator;
    private boolean mNoWakeLock;
    private final Coupon mCoupon;

    public CouponDetailFragmentBuilder(Coupon coupon) {
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

    /**
     *  Disable wake-lock and max brightness
     */
    public CouponDetailFragmentBuilder disableAutoMaxBrightness() {
        mNoWakeLock = true;
        return this;
    }

    public NearItCouponDetailFragment build() {
        return NearItCouponDetailFragment.newInstance(mCoupon, getParams());
    }

    private CouponDetailExtraParams getParams() {
        return new CouponDetailExtraParams(
                mIconDrawable,
                mSeparatorDrawable,
                mNoSeparator,
                mNoWakeLock
        );

    }

}
