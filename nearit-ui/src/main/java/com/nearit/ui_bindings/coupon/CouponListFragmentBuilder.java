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
    private boolean mValidOnly = false;
    private boolean mExpiredOnly = false;
    private boolean mInactiveOnly = false;
    private boolean mRedeemedOnly = false;
    private boolean mIncludeRedeemed = false;

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

    public CouponListFragmentBuilder onlyValidCoupons() {
        mValidOnly = true;
        return this;
    }

    public CouponListFragmentBuilder onlyExpiredCoupons() {
        mExpiredOnly = true;
        return this;
    }

    public CouponListFragmentBuilder onlyInactiveCoupons() {
        mInactiveOnly = true;
        return this;
    }

    public CouponListFragmentBuilder onlyRedeemedCoupons() {
        mRedeemedOnly = true;
        return this;
    }

    public CouponListFragmentBuilder includeRedeemed() {
        mIncludeRedeemed = true;
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
                mValidOnly,
                mExpiredOnly,
                mInactiveOnly,
                mRedeemedOnly,
                mIncludeRedeemed);
    }

}
