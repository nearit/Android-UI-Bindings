package com.nearit.ui_bindings_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nearit.ui_bindings.NearITUIBindings;

import java.util.ArrayList;

import it.near.sdk.reactions.couponplugin.model.Claim;
import it.near.sdk.reactions.couponplugin.model.Coupon;

public class CouponsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        final Coupon validCoupon = new Coupon();
        validCoupon.name = "Nome di questo coupon lun";
        validCoupon.description = "Descrizione del coupon, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        validCoupon.value = "Valoreqwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        validCoupon.redeemable_from = "2017-09-01T00:00:00.000Z";
        validCoupon.expires_at = "2017-10-20T23:59:59.999Z";
        Claim validCouponClaim = new Claim();
        validCouponClaim.serial_number = "0123456789";
        validCoupon.claims = new ArrayList<>();
        validCoupon.claims.add(validCouponClaim);

        Button valid = (Button) findViewById(R.id.valid_coupon);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder()
                                .build(validCoupon));
            }
        });

        final Coupon inactiveCoupon = new Coupon();
        inactiveCoupon.name = "Nome di questo coupon lun";
        inactiveCoupon.description = "Descrizione del coupon, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        inactiveCoupon.value = "Valoreqwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        inactiveCoupon.redeemable_from = "2017-10-01T00:00:00.000Z";
        inactiveCoupon.expires_at = "2017-10-20T23:59:59.999Z";
        Claim inactiveCouponClaim = new Claim();
        inactiveCouponClaim.serial_number = "0123456789";
        inactiveCoupon.claims = new ArrayList<>();
        inactiveCoupon.claims.add(inactiveCouponClaim);

        Button inactive = (Button) findViewById(R.id.inactive_coupon);
        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder()
                                .build(inactiveCoupon));
            }
        });

        final Coupon expiredCoupon = new Coupon();
        expiredCoupon.name = "Nome di questo coupon lun";
        expiredCoupon.description = "Descrizione del coupon, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        expiredCoupon.value = "Valoreqwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        expiredCoupon.expires_at = "2017-08-20T23:59:59.999Z";
        expiredCoupon.redeemable_from = "2017-08-01T00:00:00.000Z";
        Claim expiredCouponClaim = new Claim();
        expiredCouponClaim.serial_number = "0123456789";
        expiredCoupon.claims = new ArrayList<>();
        expiredCoupon.claims.add(expiredCouponClaim);

        Button expired = (Button) findViewById(R.id.expired_coupon);
        expired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder()
                                .build(expiredCoupon));
            }
        });

//        final Coupon longTextCoupon = new Coupon();
//        longTextCoupon.name = "Nome di questo coupon lun";
//        longTextCoupon.description = "Descrizione del coupon, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
//        longTextCoupon.value = "Valoreqwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
//        longTextCoupon.redeemable_from = "2017-09-01T00:00:00.000Z";
//        longTextCoupon.expires_at = "2017-10-20T23:59:59.999Z";
//        Claim longTextCouponClaim = new Claim();
//        longTextCouponClaim.serial_number = "0123456789";
//        longTextCoupon.claims = new ArrayList<>();
//        longTextCoupon.claims.add(longTextCouponClaim);
//
//        Button longText = (Button) findViewById(R.id.long_text_coupon);
//        longText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(
//                        NearITUIBindings.getInstance(getApplicationContext())
//                                .createCouponDetailIntentBuilder()
//                                .build(longTextCoupon));
//            }
//        });

    }
}
