package com.nearit.ui_bindings_sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nearit.ui_bindings.NearITUIBindings;

import java.util.ArrayList;

import it.near.sdk.reactions.couponplugin.model.Claim;
import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 13/09/17.
 */

public class CouponPlainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_coupon);

        final Coupon validCoupon = new Coupon();
        Claim validCouponClaim = new Claim();
        validCouponClaim.serial_number = "0123456789";
        validCoupon.name = "Long name for a coupon";
        validCoupon.description = "Long coupon description, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        validCoupon.value = "Value qwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        validCoupon.redeemable_from = "2017-09-01T00:00:00.000Z";
        validCoupon.expires_at = "2017-10-20T23:59:59.999Z";
        validCoupon.claims = new ArrayList<>();
        validCoupon.claims.add(validCouponClaim);

        Fragment couponFragment = NearITUIBindings.getInstance(this)
                .createCouponDetailFragmentBuilder(validCoupon)
                .build();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, couponFragment).commit();

    }
}
