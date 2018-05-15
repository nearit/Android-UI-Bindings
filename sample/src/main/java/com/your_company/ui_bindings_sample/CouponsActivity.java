package com.your_company.ui_bindings_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nearit.ui_bindings.NearITUIBindings;
import com.your_company.ui_bindings_sample.factories.CouponFactory;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */

public class CouponsActivity extends AppCompatActivity {

    CouponFactory couponFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);
        couponFactory = new CouponFactory();
    }

    /*  COUPON LIST EXAMPLES    */

    public void onDefaultClicked(View view) {
        //  start an activity that shows a list of REAL coupons, with jagged borders
        startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                .createCouponListIntentBuilder()
                .jaggedBorders()
                .build());
    }

    public void onValidClicked(View view) {
        startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                .createCouponListIntentBuilder()
                .validCoupons()
                .build());
    }

    public void onInactiveClicked(View view) {
        startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                .createCouponListIntentBuilder()
                .inactiveCoupons()
                .build());
    }

    public void onExpiredClicked(View view) {
        startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                .createCouponListIntentBuilder()
                .expiredCoupons()
                .build());
    }

    public void onRedeemedClicked(View view) {
        startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                .createCouponListIntentBuilder()
                .redeemedCoupons()
                .build());
    }

    public void onValidAndInactiveClicked(View view) {
        startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                .createCouponListIntentBuilder()
                .validCoupons()
                .inactiveCoupons()
                .build());
    }

    public void onValidAndExpiredClicked(View view) {
        startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                .createCouponListIntentBuilder()
                .validCoupons()
                .expiredCoupons()
                .build());
    }

    public void onValidAndRedeemedClicked(View view) {
        startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                .createCouponListIntentBuilder()
                .validCoupons()
                .redeemedCoupons()
                .build());
    }

    public void onInactiveAndExpiredClicked(View view) {
        startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                .createCouponListIntentBuilder()
                .inactiveCoupons()
                .expiredCoupons()
                .build());
    }

    public void onInactiveAndRedeemedClicked(View view) {
        startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                .createCouponListIntentBuilder()
                .inactiveCoupons()
                .redeemedCoupons()
                .build());
    }

    public void onExpiredAndRedeemedClicked(View view) {
        startActivity(NearITUIBindings.getInstance(CouponsActivity.this)
                .createCouponListIntentBuilder()
                .expiredCoupons()
                .redeemedCoupons()
                .build());
    }

    /*  COUPON DETAIL EXAMPLES    */

    public void onValidDetailClicked(View view) {
        //  In a real scenario the coupon is provided by the NearIT SDK
        Coupon validCoupon = couponFactory.getValidCoupon();
        startActivity(
                //  Basic example with a valid coupon
                NearITUIBindings.getInstance(CouponsActivity.this)
                        .createCouponDetailIntentBuilder(validCoupon)
                        .build());
    }

    public void onInactiveDetailClicked(View view) {
        //  In a real scenario the coupon is provided by the NearIT SDK
        Coupon validCoupon = couponFactory.getInactiveCoupon();
        startActivity(
                //  Basic example with a not yet valid coupon
                NearITUIBindings.getInstance(CouponsActivity.this)
                        .createCouponDetailIntentBuilder(validCoupon)
                        .build());
    }

    public void onExpiredDetailClicked(View view) {
        Coupon expiredCoupon = couponFactory.getExpiredCoupon();
        startActivity(
                //  Basic example with an expired coupon
                //  + enable tap outside the dialog to close it
                NearITUIBindings.getInstance(CouponsActivity.this)
                        .createCouponDetailIntentBuilder(expiredCoupon)
                        .enableTapOutsideToClose()
                        .build());
    }

    public void onCustomIconDetailClicked(View view) {
        //  In a real scenario the coupon is provided by the NearIT SDK
        Coupon validCoupon = couponFactory.getValidCoupon();
        startActivity(
                //  Change the default icon placeholder
                NearITUIBindings.getInstance(CouponsActivity.this)
                        .createCouponDetailIntentBuilder(validCoupon)
                        .setIconPlaceholderResourceId(R.drawable.my_custom_placeholder)
                        .build());
    }

    public void onPlainCouponClicked(View view) {
        //  start a plain activity that shows the fragment fullscreen
        //  please check it
        startActivity(new Intent(CouponsActivity.this, CouponPlainActivity.class));
    }
}
