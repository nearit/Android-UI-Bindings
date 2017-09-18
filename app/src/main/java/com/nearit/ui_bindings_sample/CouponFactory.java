package com.nearit.ui_bindings_sample;

import java.util.ArrayList;

import it.near.sdk.reactions.couponplugin.model.Claim;
import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 18/09/17.
 */

class CouponFactory {

    private Coupon coupon = new Coupon();

    CouponFactory() {
        Claim claim = new Claim();
        claim.serial_number = "0123456789";
        coupon.name = "Long name for a coupon";
        coupon.description = "Long coupon description, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        coupon.value = "Value qwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!";
        coupon.claims = new ArrayList<>();
        coupon.claims.add(claim);
    }

    Coupon getValidCoupon() {
        coupon.redeemable_from = "2017-09-01T00:00:00.000Z";
        coupon.expires_at = "2099-10-30T23:59:59.999Z";
        return coupon;
    }

    Coupon getInactiveCoupon() {
        coupon.redeemable_from = "2099-09-01T00:00:00.000Z";
        coupon.expires_at = "2099-10-30T23:59:59.999Z";
        return coupon;
    }

    Coupon getExpiredCoupon() {
        coupon.redeemable_from = "2017-09-01T00:00:00.000Z";
        coupon.expires_at = "2017-09-02T23:59:59.999Z";
        return coupon;
    }

}
