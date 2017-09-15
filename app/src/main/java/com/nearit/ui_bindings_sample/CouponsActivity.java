package com.nearit.ui_bindings_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nearit.ui_bindings.NearITUIBindings;

import java.util.ArrayList;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.couponplugin.CouponListener;
import it.near.sdk.reactions.couponplugin.model.Claim;
import it.near.sdk.reactions.couponplugin.model.Coupon;

public class CouponsActivity extends AppCompatActivity {

    List<Coupon> coupons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

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

        Button valid = (Button) findViewById(R.id.valid_coupon);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        //  Basic example with a valid coupon
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder()
                                .build(validCoupon));
            }
        });

        final Coupon inactiveCoupon = new Coupon();
        Claim inactiveCouponClaim = new Claim();
        inactiveCouponClaim.serial_number = "0123456789";
        inactiveCoupon.name = "Long name for a coupon";
        inactiveCoupon.description = "Long coupon description, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        inactiveCoupon.value = "Value qwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        inactiveCoupon.redeemable_from = "2017-10-01T00:00:00.000Z";
        inactiveCoupon.expires_at = "2017-10-20T23:59:59.999Z";
        inactiveCoupon.claims = new ArrayList<>();
        inactiveCoupon.claims.add(inactiveCouponClaim);

        Button inactive = (Button) findViewById(R.id.inactive_coupon);
        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        //  Basic example with a coupon not yet valid
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder()
                                .build(inactiveCoupon));
            }
        });

        final Coupon expiredCoupon = new Coupon();
        Claim expiredCouponClaim = new Claim();
        expiredCouponClaim.serial_number = "0123456789";
        expiredCoupon.name = "Long name for a coupon";
        expiredCoupon.description = "Long coupon description, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        expiredCoupon.value = "Value qwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        expiredCoupon.expires_at = "2017-08-20T23:59:59.999Z";
        expiredCoupon.redeemable_from = "2017-08-01T00:00:00.000Z";
        expiredCoupon.claims = new ArrayList<>();
        expiredCoupon.claims.add(expiredCouponClaim);

        Button expired = (Button) findViewById(R.id.expired_coupon);
        expired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        //  Basic example with an expired coupon
                        //  + enable tap outside the dialog to close it
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder()
                                .enableTapOutsideToClose()
                                .build(expiredCoupon));
            }
        });

        final Coupon customIconCoupon = new Coupon();
        Claim customIconCouponClaim = new Claim();
        customIconCouponClaim.serial_number = "0123456789";
        customIconCoupon.name = "Long name for a coupon";
        customIconCoupon.description = "Long coupon description, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        customIconCoupon.value = "Value qwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        customIconCoupon.redeemable_from = "2017-09-01T00:00:00.000Z";
        customIconCoupon.expires_at = "2017-10-20T23:59:59.999Z";
        customIconCoupon.claims = new ArrayList<>();
        customIconCoupon.claims.add(customIconCouponClaim);

        Button customIcon = (Button) findViewById(R.id.custom_icon_coupon);
        customIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        //  Change the default icon placeholder
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder()
                                .setIconPlaceholderResourceId(R.drawable.shop_circle)
                                .build(customIconCoupon));
            }
        });

        final Button realCoupon = (Button) findViewById(R.id.real_coupon);
        realCoupon.setEnabled(false);
        NearItManager.getInstance().getCoupons(new CouponListener() {
            @Override
            public void onCouponsDownloaded(List<Coupon> list) {
                coupons = list;
                realCoupon.setEnabled(true);
                realCoupon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!coupons.isEmpty()){
                            startActivity(
                                    //  Example with a real NearIT coupon
                                    //  this is enabled only if there is a real coupon
                                    //  for the installation
                                    NearITUIBindings.getInstance(CouponsActivity.this)
                                            .createCouponDetailIntentBuilder()
                                            .setNoSeparator()
                                            .build(coupons.get(0))
                            );
                        } else Toast.makeText(CouponsActivity.this, "You have no real coupon", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCouponDownloadError(String s) {

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
