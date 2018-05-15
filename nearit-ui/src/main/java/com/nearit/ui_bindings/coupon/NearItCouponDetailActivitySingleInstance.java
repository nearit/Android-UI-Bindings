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

public class NearItCouponDetailActivitySingleInstance extends AppCompatActivity {

    private final static String TAG = "NearItCouponDetailActiv";

    private CouponDetailExtraParams extras;
    private boolean isEnableTapToClose = false;

    @Nullable
    LinearLayout closeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nearit_ui_activity_coupon_detail);

        closeButton = findViewById(R.id.close_icon);
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
            extras = CouponDetailExtraParams.fromIntent(intent);
            isEnableTapToClose = extras.isEnableTapOutsideToClose();
        }

        Coupon coupon = getIntent().getParcelableExtra(ExtraConstants.COUPON_EXTRA);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, NearItCouponDetailFragment.newInstance(coupon, extras))
                .commit();

    }

    public static Intent createIntent(Context context, Coupon coupon, CouponDetailExtraParams params) {
        return new Intent(context, NearItCouponDetailActivitySingleInstance.class)
                .putExtra(ExtraConstants.COUPON_EXTRA, coupon)
                .putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
            if (!isEnableTapToClose) {
                return true;
            }
            finish();
        }
        return super.dispatchTouchEvent(ev);
    }

}
