package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class CouponListIntentBuilder {
    private Context mContext;
    private int mIconDrawable;
    private int mSeparatorDrawable;
    private boolean mNoSeparator = false;
    private boolean mEnableTapOutsideToClose = false;

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
     * Enables or disables tap outside the dialog to close
     * <p>
     * <p> default is false
     */
    public CouponListIntentBuilder enableTapOutsideToClose() {
        mEnableTapOutsideToClose = true;
        return this;
    }

    public Intent build() {
        return NearItCouponListActivity.createIntent(mContext, getParams());
    }

    private CouponListExtraParams getParams() {
        return new CouponListExtraParams(
                mIconDrawable,
                mSeparatorDrawable,
                mNoSeparator,
                mEnableTapOutsideToClose);
    }

}
