package com.your_company.ui_bindings_sample.factories;

import java.util.ArrayList;

import it.near.sdk.reactions.couponplugin.model.Claim;
import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */

public class CouponFactory {

    private Coupon coupon = new Coupon();

    public CouponFactory() {
        Claim claim = new Claim();
        claim.serial_number = "0123456789";
        coupon.name = "Long name for a coupon";
        coupon.description = "Long coupon description, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        coupon.value = "Value qwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        coupon.claims = new ArrayList<>();
        coupon.claims.add(claim);
    }

    public Coupon getValidCoupon() {
        coupon.redeemable_from = "2017-09-01T00:00:00.000Z";
        coupon.expires_at = "2099-10-30T23:59:59.999Z";
        return coupon;
    }

    public Coupon getInactiveCoupon() {
        coupon.redeemable_from = "2099-09-01T00:00:00.000Z";
        coupon.expires_at = "2099-10-30T23:59:59.999Z";
        return coupon;
    }

    public Coupon getExpiredCoupon() {
        coupon.redeemable_from = "2017-09-01T00:00:00.000Z";
        coupon.expires_at = "2017-09-02T23:59:59.999Z";
        return coupon;
    }

}
