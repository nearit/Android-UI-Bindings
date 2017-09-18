package com.nearit.ui_bindings_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nearit.ui_bindings.NearITUIBindings;

import java.util.ArrayList;
import java.util.List;

import it.near.sdk.reactions.couponplugin.model.Claim;
import it.near.sdk.reactions.couponplugin.model.Coupon;

public class CouponsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        final CouponFactory couponFactory = new CouponFactory();

        Button valid = (Button) findViewById(R.id.valid_coupon);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  In a real scenario the coupon is provided by the NearIT SDK
                Coupon validCoupon = couponFactory.getValidCoupon();
                startActivity(
                        //  Basic example with a valid coupon
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder(validCoupon)
                                .build());
            }
        });

        Button inactive = (Button) findViewById(R.id.inactive_coupon);
        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  In a real scenario the coupon is provided by the NearIT SDK
                Coupon inactiveCoupon = couponFactory.getInactiveCoupon();
                startActivity(
                        //  Basic example with a coupon not yet valid
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder(inactiveCoupon)
                                .build());
            }
        });

        Button expired = (Button) findViewById(R.id.expired_coupon);
        expired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  In a real scenario the coupon is provided by the NearIT SDK
                Coupon expiredCoupon = couponFactory.getExpiredCoupon();
                startActivity(
                        //  Basic example with an expired coupon
                        //  + enable tap outside the dialog to close it
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder(expiredCoupon)
                                .enableTapOutsideToClose()
                                .build());
            }
        });

        Button customIcon = (Button) findViewById(R.id.custom_icon_coupon);
        customIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  In a real scenario the coupon is provided by the NearIT SDK
                Coupon validCoupon = couponFactory.getValidCoupon();
                startActivity(
                        //  Change the default icon placeholder
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder(validCoupon)
                                .setIconPlaceholderResourceId(R.drawable.my_custom_placeholder)
                                .build());
            }
        });

        Button plainActivity = (Button) findViewById(R.id.plain_coupon_activity);
        plainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  start a plain activity that shows the fragment fullscreen
                //  please check it
                startActivity(new Intent(CouponsActivity.this, CouponPlainActivity.class));
            }
        });

    }
}
