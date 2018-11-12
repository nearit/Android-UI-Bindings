package com.nearit.ui_bindings.coupon.detail;

import android.content.Context;
import android.content.Intent;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */

public class NearItCouponDetailActivitySingleInstance extends BaseCouponDetailActivity {

    public static Intent createIntent(Context context, Coupon coupon, CouponDetailExtraParams params) {
        Intent intent = new Intent(context, NearItCouponDetailActivitySingleInstance.class);
        return BaseCouponDetailActivity.addExtras(intent, coupon, params);
    }

}
