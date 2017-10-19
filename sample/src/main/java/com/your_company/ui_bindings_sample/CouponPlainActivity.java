package com.your_company.ui_bindings_sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nearit.ui_bindings.NearITUIBindings;
import com.your_company.ui_bindings_sample.factories.CouponFactory;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 13/09/17.
 */

public class CouponPlainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_coupon);

        CouponFactory couponFactory = new CouponFactory();

        //  In a real scenario the coupon is provided by the NearIT SDK
        Coupon validCoupon = couponFactory.getValidCoupon();

        Fragment couponFragment = NearITUIBindings.getInstance(this)
                .createCouponDetailFragmentBuilder(validCoupon)
                .disableAutoMaxBrightness()
                .build();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, couponFragment).commit();

    }
}
