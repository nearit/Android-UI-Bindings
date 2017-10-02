package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;

/**
 * Created by Federico Boschini on 28/09/17.
 */

public class NearItCouponListActivity extends AppCompatActivity {

    private final static String TAG = "NearItCouponListActiv";

    private CouponListExtraParams extras;
    private boolean isEnableTapToClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nearit_ui_activity_coupon_list);

        Intent intent = getIntent();
        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            extras = CouponListExtraParams.fromIntent(intent);
            isEnableTapToClose = extras.isEnableTapOutsideToClose();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.coupons_fragment_container, NearItCouponListFragment.newInstance(extras))
                .commit();

    }

    public static Intent createIntent(Context context, CouponListExtraParams params) {
        return new Intent(context, NearItCouponListActivity.class)
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
