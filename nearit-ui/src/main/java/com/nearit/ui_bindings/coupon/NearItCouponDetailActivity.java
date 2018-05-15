package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;

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
