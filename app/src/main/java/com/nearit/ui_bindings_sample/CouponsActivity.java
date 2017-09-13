package com.nearit.ui_bindings_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
        validCoupon.name = "Nome di questo coupon lun";
        validCoupon.description = "Descrizione del coupon, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        validCoupon.value = "Valoreqwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        validCoupon.redeemable_from = "2017-09-01T00:00:00.000Z";
        validCoupon.expires_at = "2017-10-20T23:59:59.999Z";
        validCoupon.claims = new ArrayList<>();
        validCoupon.claims.add(validCouponClaim);
//        Image icon = new Image();
//        icon.imageMap = new HashMap<>();
//        icon.imageMap.put("icon", new HashMap<>().put("url","https://prod-nearit-media.s3.amazonaws.com/uploads/image/image/482fb24f-695d-4564-9902-cdf286fd27ff/square_300_file.png"));
//        validCoupon.icon = icon;

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
        Claim inactiveCouponClaim = new Claim();
        inactiveCouponClaim.serial_number = "0123456789";
        inactiveCoupon.name = "Nome di questo coupon lun";
        inactiveCoupon.description = "Descrizione del coupon, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        inactiveCoupon.value = "Valoreqwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        inactiveCoupon.redeemable_from = "2017-10-01T00:00:00.000Z";
        inactiveCoupon.expires_at = "2017-10-20T23:59:59.999Z";
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
        Claim expiredCouponClaim = new Claim();
        expiredCouponClaim.serial_number = "0123456789";
        expiredCoupon.name = "Nome di questo coupon lun";
        expiredCoupon.description = "Descrizione del coupon, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        expiredCoupon.value = "Valoreqwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        expiredCoupon.expires_at = "2017-08-20T23:59:59.999Z";
        expiredCoupon.redeemable_from = "2017-08-01T00:00:00.000Z";
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

        final Coupon customIconCoupon = new Coupon();
        Claim customIconCouponClaim = new Claim();
        customIconCouponClaim.serial_number = "0123456789";
        customIconCoupon.name = "Nome di questo coupon lun";
        customIconCoupon.description = "Descrizione del coupon, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        customIconCoupon.value = "Valoreqwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        customIconCoupon.redeemable_from = "2017-09-01T00:00:00.000Z";
        customIconCoupon.expires_at = "2017-10-20T23:59:59.999Z";
        customIconCoupon.claims = new ArrayList<>();
        customIconCoupon.claims.add(customIconCouponClaim);

        Button customIcon = (Button) findViewById(R.id.custom_icon_coupon);
        customIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder()
                                .setIconPlaceholderResourceId(R.drawable.shop_circle)
                                .build(customIconCoupon));
            }
        });


        NearItManager.getInstance().getCoupons(new CouponListener() {
            @Override
            public void onCouponsDownloaded(List<Coupon> list) {
                coupons = list;
            }

            @Override
            public void onCouponDownloadError(String s) {

            }
        });

        Button realRedeemed = (Button) findViewById(R.id.real_redeemed_coupon);
        realRedeemed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        NearITUIBindings.getInstance(CouponsActivity.this)
                                .createCouponDetailIntentBuilder()
                                .enableTapOutsideToClose()
                                .build(coupons.get(0))
                );
            }
        });

    }
}
