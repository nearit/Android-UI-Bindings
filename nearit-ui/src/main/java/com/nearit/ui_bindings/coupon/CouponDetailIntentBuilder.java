package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import it.near.sdk.reactions.couponplugin.model.Claim;
import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class CouponDetailIntentBuilder {
    private Context mContext;

    public CouponDetailIntentBuilder(Context context) {
        mContext = context;
    }

    public Intent build(Coupon coupon) {
        return NearItCouponDetailActivity.createIntent(mContext, coupon);
    }

}
