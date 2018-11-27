package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.nearit.ui_bindings.NearItLaunchMode;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.coupon.list.CouponListExtraParams;
import com.nearit.ui_bindings.coupon.list.NearItCouponListActivity;
import com.nearit.ui_bindings.coupon.list.NearItCouponListActivitySingleInstance;
import com.nearit.ui_bindings.coupon.list.NearItCouponListActivitySingleTask;
import com.nearit.ui_bindings.coupon.list.NearItCouponListActivitySingleTop;

/**
 * @author Federico Boschini
 */

public class CouponListIntentBuilder {
    private final Context mContext;
    private int mIconPlaceholderResId = R.drawable.ic_nearit_ui_coupon_icon_placeholder;
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

    private NearItLaunchMode mLaunchMode;
    private int mFlags;

    public CouponListIntentBuilder(Context context, NearItLaunchMode launchMode) {
        mContext = context;
        mLaunchMode = launchMode;
    }

    public CouponListIntentBuilder(Context context, NearItLaunchMode launchMode, int flags) {
        this(context, launchMode);
        mFlags = flags;
    }


    public CouponListIntentBuilder setTitle(String title) {
        mActivityTitle = title;
        return this;
    }

    /**
     * Sets a custom icon as a placeholder for coupons without icon
     */
    public CouponListIntentBuilder setIconPlaceholderResourceId(int icon) {
        mIconPlaceholderResId = icon;
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
        Intent intent;
        switch (mLaunchMode) {
            case SINGLE_INSTANCE:
                intent = NearItCouponListActivitySingleInstance.createIntent(mContext, getParams());
                break;
            case SINGLE_TOP:
                intent = NearItCouponListActivitySingleTop.createIntent(mContext, getParams());
                break;
            case SINGLE_TASK:
                intent = NearItCouponListActivitySingleTask.createIntent(mContext, getParams());
                break;
            case STANDARD:
            default:
                intent = NearItCouponListActivity.createIntent(mContext, getParams());
        }

        intent.addFlags(mFlags);
        return intent;
    }

    private CouponListExtraParams getParams() {
        return new CouponListExtraParams(
                mIconPlaceholderResId,
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
