package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class CouponDetailIntentBuilder {
    private Context mContext;

    public CouponDetailIntentBuilder(Context context) {
        mContext = context;
    }

    public Intent build() {
        return NearItCouponDetailActivity.createIntent(mContext, getParams());
    }

    private CouponDetailIntentExtras getParams() {
        return new CouponDetailIntentExtras();
    }
}
