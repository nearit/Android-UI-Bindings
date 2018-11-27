package com.nearit.ui_bindings.coupon.list;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;

/**
 * @author Federico Boschini
 */
public class BaseCouponListActivity extends AppCompatActivity {

    @SuppressWarnings("unused")
    private final static String TAG = "NearItCouponListActiv";
    private boolean isEnableTapToClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nearit_ui_activity_coupon_list);

        CouponListExtraParams extras = null;
        Intent intent = getIntent();
        if (intent != null &&
                intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            extras = CouponListExtraParams.fromIntent(intent);
            isEnableTapToClose = extras.isEnableTapOutsideToClose();
            String activityTitle = extras.getActivityTitle();
            if (activityTitle != null) {
                setTitle(activityTitle);
            }
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.coupons_fragment_container, NearItCouponListFragment.newInstance(extras))
                .commit();

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

    public static Intent addExtras(Intent intent, CouponListExtraParams params) {
        return intent.putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

}
