package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * @author Federico Boschini
 */

public class CouponListIntentBuilder {
    private final Context mContext;
    private int mIconDrawable;
    private int mSeparatorDrawable;
    private int mNoCouponLayout = 0;
    private boolean mEnableNetErrorDialog = false;
    private boolean mJaggedBorders = false;
    private boolean mNoSeparator = false;
    private boolean mNoIcon = false;
    private boolean mEnableTapOutsideToClose = false;
    private boolean mDefaultList = true;
    private boolean mValid = false;
    private boolean mExpired = false;
    private boolean mInactive = false;
    private boolean mRedeemed = false;
    @Nullable
    private String mActivityTitle = null;

    public CouponListIntentBuilder(Context context) {
        mContext = context;
    }


    public CouponListIntentBuilder setTitle(String title) {
        mActivityTitle = title;
        return this;
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
     * Enable jagged (vintage movie ticket style) coupon preview layout
     */
    public CouponListIntentBuilder jaggedBorders() {
        mJaggedBorders = true;
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

    public CouponListIntentBuilder validCoupons() {
        mDefaultList = false;
        mValid = true;
        return this;
    }

    public CouponListIntentBuilder expiredCoupons() {
        mDefaultList = false;
        mExpired = true;
        return this;
    }

    public CouponListIntentBuilder inactiveCoupons() {
        mDefaultList = false;
        mInactive = true;
        return this;
    }

    public CouponListIntentBuilder redeemedCoupons() {
        mDefaultList = false;
        mRedeemed = true;
        return this;
    }

    public CouponListIntentBuilder enableNetErrorDialog() {
        mEnableNetErrorDialog = true;
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
                mEnableNetErrorDialog,
                mJaggedBorders,
                mNoSeparator,
                mNoIcon,
                mDefaultList,
                mValid,
                mExpired,
                mInactive,
                mRedeemed,
                mEnableTapOutsideToClose,
                mActivityTitle);
    }

}
