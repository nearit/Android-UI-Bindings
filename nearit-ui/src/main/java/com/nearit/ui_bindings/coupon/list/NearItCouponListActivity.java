package com.nearit.ui_bindings.coupon.list;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.nearit.ui_bindings.ExtraConstants;

/**
 * @author Federico Boschini
 */

public class NearItCouponListActivity extends BaseCouponListActivity {

    @NonNull
    public static Intent createIntent(Context context, CouponListExtraParams params) {
        return new Intent(context, NearItCouponListActivity.class)
                .putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }
}
