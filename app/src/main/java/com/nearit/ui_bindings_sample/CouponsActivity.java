package com.nearit.ui_bindings_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nearit.ui_bindings.NearITUIBindings;

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
                        NearITUIBindings.getInstance(CouponsActivity.this)
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
                        NearITUIBindings.getInstance(CouponsActivity.this)
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
                        NearITUIBindings.getInstance(CouponsActivity.this)
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
                        NearITUIBindings.getInstance(CouponsActivity.this)
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



        Button couponList = (Button) findViewById(R.id.coupon_list);
        couponList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  start an activity that shows a list of REAL coupons
                startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                        .createCouponListIntentBuilder()
                        .build()
                );
            }
        });

        Button validCouponList = (Button) findViewById(R.id.valid_coupon_list);
        validCouponList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  start an activity that shows a list of REAL valid coupons ONLY
                startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                        .createCouponListIntentBuilder()
                        .onlyValidCoupons()
                        .build()
                );
            }
        });

        Button expiredCouponList = (Button) findViewById(R.id.expired_coupon_list);
        expiredCouponList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  start an activity that shows a list of REAL expired coupons ONLY
                startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                        .createCouponListIntentBuilder()
                        .onlyExpiredCoupons()
                        .build()
                );
            }
        });

        Button inactiveCouponList = (Button) findViewById(R.id.inactive_coupon_list);
        inactiveCouponList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  start an activity that shows a list of REAL inactive coupons ONLY
                startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                        .createCouponListIntentBuilder()
                        .onlyInactiveCoupons()
                        .build()
                );
            }
        });

        Button redeemedCouponList = (Button) findViewById(R.id.redeemed_coupon_list);
        redeemedCouponList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  start an activity that shows a list of REAL already redeemed coupons ONLY
                startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                        .createCouponListIntentBuilder()
                        .onlyRedeemedCoupons()
                        .build()
                );
            }
        });

        Button completeList = (Button) findViewById(R.id.include_redeemed_coupon_list);
        completeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  start an activity that shows the complete list of REAL coupons (redeemed included)
                startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                        .createCouponListIntentBuilder()
                        .includeRedeemed()
                        .build()
                );
            }
        });

        Button listInActivity = (Button) findViewById(R.id.coupon_list_in_activity);
        listInActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  start a plain activity that shows the fragment fullscreen
                //  please check it
                startActivity(new Intent(CouponsActivity.this, CouponListPlainActivity.class));
            }
        });

    }
}
