package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */

public class NearItCouponDetailActivity extends BaseCouponDetailActivity {

    public static Intent createIntent(Context context, Coupon coupon, CouponDetailExtraParams params) {
        Intent intent = new Intent(context, NearItCouponDetailActivity.class);
        return BaseCouponDetailActivity.addExtras(intent, coupon, params);
    }
}
