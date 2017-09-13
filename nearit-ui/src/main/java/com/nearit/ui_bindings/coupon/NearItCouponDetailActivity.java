package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class NearItCouponDetailActivity extends AppCompatActivity {

    private final static String TAG = "NearItCouponDetailActiv";

    private CouponDetailIntentExtras extras;

    @Nullable
    LinearLayout closeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nearit_ui_activity_coupon_detail);

        closeButton = (LinearLayout) findViewById(R.id.close_icon);
        if (closeButton != null) {
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        Intent intent = getIntent();
        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            extras = CouponDetailIntentExtras.fromIntent(intent);
        }

        Coupon coupon = getIntent().getParcelableExtra(ExtraConstants.COUPON_EXTRA);

        NearItCouponDetailFragment fragment = NearItCouponDetailFragment.newInstance(coupon, extras);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }

    public static Intent createIntent(Context context, Coupon coupon, CouponDetailIntentExtras params) {
        return new Intent(context, NearItCouponDetailActivity.class)
                .putExtra(ExtraConstants.COUPON_EXTRA, coupon)
                .putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

}
