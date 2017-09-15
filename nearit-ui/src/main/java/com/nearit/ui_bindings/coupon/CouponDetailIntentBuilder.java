package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class CouponDetailIntentBuilder {
    private Context mContext;
    private int mIconDrawable;
    private int mSeparatorDrawable;
    private boolean mNoSeparator = false;
    private boolean mEnableTapOutsideToClose = false;

    public CouponDetailIntentBuilder(Context context) {
        mContext = context;
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

    public Intent build(Coupon coupon) {
        return NearItCouponDetailActivity.createIntent(mContext, coupon, getParams());
    }

    private CouponDetailExtraParams getParams() {
        return new CouponDetailExtraParams(
                mIconDrawable,
                mSeparatorDrawable,
                mNoSeparator,
                mEnableTapOutsideToClose);
    }

}
