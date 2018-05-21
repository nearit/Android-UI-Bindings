package com.nearit.ui_bindings.coupon;

/**
 * @author Federico Boschini
 */

public class CouponListFragmentBuilder {
    private int mIconDrawable;
    private int mSeparatorDrawable;
    private int mNoCouponsLayout;
    private boolean mEnableNetErrorDialog = false;
    private boolean mJaggedBorders = false;
    private boolean mNoSeparator;
    private boolean mNoIcon;
    private boolean mDefaultList = true;
    private boolean mValid = false;
    private boolean mExpired = false;
    private boolean mInactive = false;
    private boolean mRedeemed = false;

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

    /**
     * Sets no icon
     */
    public CouponListFragmentBuilder setNoIcon() {
        mNoIcon = true;
        return this;
    }

    public CouponListFragmentBuilder jaggedBorders() {
        mJaggedBorders = true;
        return this;
    }

    public CouponListFragmentBuilder validCoupons() {
        mDefaultList = false;
        mValid = true;
        return this;
    }

    public CouponListFragmentBuilder expiredCoupons() {
        mDefaultList = false;
        mExpired = true;
        return this;
    }

    public CouponListFragmentBuilder inactiveCoupons() {
        mDefaultList = false;
        mInactive = true;
        return this;
    }

    public CouponListFragmentBuilder redeemedCoupons() {
        mDefaultList = false;
        mRedeemed = true;
        return this;
    }

    public CouponListFragmentBuilder enableNetErrorDialog() {
         mEnableNetErrorDialog = true;
        return this;
    }

    public CouponListFragmentBuilder setNoCouponsLayout(int layout) {
        mNoCouponsLayout = layout;
        return this;
    }

    public NearItCouponListFragment build() {
        return NearItCouponListFragment.newInstance(getParams());
    }

    private CouponListExtraParams getParams() {
        return new CouponListExtraParams(
                mIconDrawable,
                mSeparatorDrawable,
                mNoCouponsLayout,
                mEnableNetErrorDialog,
                mJaggedBorders,
                mNoSeparator,
                mNoIcon,
                mDefaultList,
                mValid,
                mExpired,
                mInactive,
                mRedeemed);
    }

}
