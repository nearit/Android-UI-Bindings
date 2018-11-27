package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;

import com.nearit.ui_bindings.NearItLaunchMode;
import com.nearit.ui_bindings.coupon.detail.CouponDetailExtraParams;
import com.nearit.ui_bindings.coupon.detail.NearItCouponDetailActivity;
import com.nearit.ui_bindings.coupon.detail.NearItCouponDetailActivitySingleInstance;
import com.nearit.ui_bindings.coupon.detail.NearItCouponDetailActivitySingleTask;
import com.nearit.ui_bindings.coupon.detail.NearItCouponDetailActivitySingleTop;

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

    public static boolean openLinksInWebView = false;

    private final NearItLaunchMode mLaunchMode;
    private int mFlags;

    public CouponDetailIntentBuilder(Context context, Coupon coupon, NearItLaunchMode launchMode) {
        mContext = context;
        mCoupon = coupon;
        mLaunchMode = launchMode;
    }

    public CouponDetailIntentBuilder(Context context, Coupon coupon, NearItLaunchMode launchMode, int flags) {
        this(context, coupon, launchMode);
        mFlags = flags;
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

    public CouponDetailIntentBuilder openLinksInWebView() {
        openLinksInWebView = true;
        return this;
    }

    public Intent build() {
        Intent intent;
        switch (mLaunchMode) {
            case SINGLE_INSTANCE:
                intent = NearItCouponDetailActivitySingleInstance.createIntent(mContext, mCoupon, getParams());
                break;
            case SINGLE_TOP:
                intent = NearItCouponDetailActivitySingleTop.createIntent(mContext, mCoupon, getParams());
                break;
            case SINGLE_TASK:
                intent = NearItCouponDetailActivitySingleTask.createIntent(mContext, mCoupon, getParams());
                break;
            default:
            case STANDARD:
                intent = NearItCouponDetailActivity.createIntent(mContext, mCoupon, getParams());
        }

        intent.addFlags(mFlags);
        return intent;
    }

    private CouponDetailExtraParams getParams() {
        return new CouponDetailExtraParams(
                mIconDrawable,
                mSeparatorDrawable,
                mNoSeparator,
                mNoWakeLock,
                openLinksInWebView,
                mEnableTapOutsideToClose);
    }

}
