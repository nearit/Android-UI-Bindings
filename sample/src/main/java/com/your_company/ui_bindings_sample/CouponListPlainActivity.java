package com.your_company.ui_bindings_sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nearit.ui_bindings.NearITUIBindings;

/**
 * @author Federico Boschini
 */

public class CouponListPlainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_coupon_list);

        Fragment couponListFragment = NearITUIBindings.getInstance(this)
                .couponListFragmentBuilder()
                .build();

        getSupportFragmentManager().beginTransaction().replace(R.id.coupon_list_fragment_container, couponListFragment).commit();

    }
}
