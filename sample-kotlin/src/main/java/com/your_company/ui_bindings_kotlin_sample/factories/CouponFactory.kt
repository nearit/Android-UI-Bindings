package com.your_company.ui_bindings_kotlin_sample.factories

import it.near.sdk.reactions.couponplugin.model.Claim
import it.near.sdk.reactions.couponplugin.model.Coupon

/**
 * Created by Federico Boschini on 12/10/17.
 */
object CouponFactory {

    private val coupon: Coupon = Coupon()

    init {
        val claim: Claim = Claim()
        claim.serial_number = "0123456789"
        coupon.name = "Long name for a coupon"
        coupon.description = "Long coupon description, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali."
        coupon.value = "Value qwertyuioplkjhgfdsazxcvbnmpoiuytrewqasdfghj!"
        coupon.claims = listOf(claim)
    }

    fun getValidCoupon(): Coupon {
        coupon.redeemable_from = "2017-09-01T00:00:00.000Z"
        coupon.expires_at = "2099-10-30T23:59:59.999Z"
        return coupon
    }

    fun getInactiveCoupon(): Coupon {
        coupon.redeemable_from = "2099-09-01T00:00:00.000Z"
        coupon.expires_at = "2099-10-30T23:59:59.999Z"
        return coupon
    }

    fun getExpiredCoupon(): Coupon {
        coupon.redeemable_from = "2017-09-01T00:00:00.000Z"
        coupon.expires_at = "2017-09-02T23:59:59.999Z"
        return coupon
    }

}