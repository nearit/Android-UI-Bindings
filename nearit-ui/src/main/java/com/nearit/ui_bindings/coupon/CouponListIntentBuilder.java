package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;

/**
 * @author Federico Boschini
 */

public class CouponListIntentBuilder {
    private Context mContext;
    private int mIconDrawable;
    private int mSeparatorDrawable;
    private int mNoCouponLayout;
    private boolean mNoSeparator = false;
    private boolean mNoIcon = false;
    private boolean mEnableTapOutsideToClose = false;
    private boolean mValidOnly = false;
    private boolean mExpiredOnly = false;
    private boolean mInactiveOnly = false;
    private boolean mRedeemedOnly = false;
    private boolean mIncludeRedeemed = false;

    public CouponListIntentBuilder(Context context) {
        mContext = context;
    }

    /**
     * Sets a custom icon as a placeholder for coupons without icon
     */
    public CouponListIntentBuilder setIconPlaceholderResourceId(int icon) {
        mIconDrawable = icon;
        return this;
    }

    /**
     * Sets a custom separator
     */
    public CouponListIntentBuilder setSeparatorResourceId(int separator) {
        mSeparatorDrawable = separator;
        return this;
    }

    /**
     * Sets no separator
     */
    public CouponListIntentBuilder setNoSeparator() {
        mNoSeparator = true;
        return this;
    }

    /**
     * Sets no icon
     */
    public CouponListIntentBuilder setNoIcon() {
        mNoIcon = true;
        return this;
    }

    /**
     * Enables or disables tap outside the dialog to close
     * <p>
     * <p> default is false
     */
    public CouponListIntentBuilder enableTapOutsideToClose() {
        mEnableTapOutsideToClose = true;
        return this;
    }

    public CouponListIntentBuilder onlyValidCoupons() {
        mValidOnly = true;
        return this;
    }

    public CouponListIntentBuilder onlyExpiredCoupons() {
        mExpiredOnly = true;
        return this;
    }

    public CouponListIntentBuilder onlyInactiveCoupons() {
        mInactiveOnly = true;
        return this;
    }

    public CouponListIntentBuilder onlyRedeemedCoupons() {
        mRedeemedOnly = true;
        return this;
    }

    public CouponListIntentBuilder includeRedeemed() {
        mIncludeRedeemed = true;
        return this;
    }

    public CouponListIntentBuilder setNoCouponLayout(int layout) {
        mNoCouponLayout = layout;
        return this;
    }
    public Intent build() {
        return NearItCouponListActivity.createIntent(mContext, getParams());
    }

    private CouponListExtraParams getParams() {
        return new CouponListExtraParams(
                mIconDrawable,
                mSeparatorDrawable,
                mNoCouponLayout,
                mNoSeparator,
                mNoIcon,
                mEnableTapOutsideToClose,
                mValidOnly,
                mExpiredOnly,
                mInactiveOnly,
                mRedeemedOnly,
                mIncludeRedeemed);
    }

}
