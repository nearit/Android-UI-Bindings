package com.your_company.ui_bindings_kotlin_sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.nearit.ui_bindings.NearITUIBindings
import com.your_company.ui_bindings_kotlin_sample.factories.CouponFactory

/**
* @author Federico Boschini
*/

class CouponsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupons)
    }

    /*  COUPON LIST EXAMPLES    */

    fun onDefaultClicked(view: View) {
        //  start an activity that shows a list of REAL coupons, with jagged borders
        startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                .couponListIntentBuilder()
                .jaggedBorders()
                .build())
    }

    fun onValidClicked(view: View) {
        startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                .couponListIntentBuilder()
                .validCoupons()
                .build())
    }

    fun onInactiveClicked(view: View) {
        startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                .couponListIntentBuilder()
                .inactiveCoupons()
                .build())
    }

    fun onExpiredClicked(view: View) {
        startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                .couponListIntentBuilder()
                .expiredCoupons()
                .build())
    }

    fun onRedeemedClicked(view: View) {
        startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                .couponListIntentBuilder()
                .redeemedCoupons()
                .build())
    }

    fun onValidAndInactiveClicked(view: View) {
        startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                .couponListIntentBuilder()
                .validCoupons()
                .inactiveCoupons()
                .build())
    }

    fun onValidAndExpiredClicked(view: View) {
        startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                .couponListIntentBuilder()
                .validCoupons()
                .expiredCoupons()
                .build())
    }

    fun onValidAndRedeemedClicked(view: View) {
        startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                .couponListIntentBuilder()
                .validCoupons()
                .redeemedCoupons()
                .build())
    }

    fun onInactiveAndExpiredClicked(view: View) {
        startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                .couponListIntentBuilder()
                .inactiveCoupons()
                .expiredCoupons()
                .build())
    }

    fun onInactiveAndRedeemedClicked(view: View) {
        startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                .couponListIntentBuilder()
                .inactiveCoupons()
                .redeemedCoupons()
                .build())
    }

    fun onExpiredAndRedeemedClicked(view: View) {
        startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                .couponListIntentBuilder()
                .expiredCoupons()
                .redeemedCoupons()
                .build())
    }

    /*  COUPON DETAIL EXAMPLES    */

    fun onValidDetailClicked(view: View) {
        //  In a real scenario the coupon is provided by the NearIT SDK
        val validCoupon = CouponFactory.getValidCoupon()
        startActivity(
                //  Basic example with a valid coupon
                NearITUIBindings.getInstance(this@CouponsActivity)
                        .couponIntentBuilder(validCoupon)
                        .build())
    }

    fun onInactiveDetailClicked(view: View) {
        //  In a real scenario the coupon is provided by the NearIT SDK
        val validCoupon = CouponFactory.getInactiveCoupon()
        startActivity(
                //  Basic example with a not yet valid coupon
                NearITUIBindings.getInstance(this@CouponsActivity)
                        .couponIntentBuilder(validCoupon)
                        .build())
    }

    fun onExpiredDetailClicked(view: View) {
        val expiredCoupon = CouponFactory.getExpiredCoupon()
        startActivity(
                //  Basic example with an expired coupon
                //  + enable tap outside the dialog to close it
                NearITUIBindings.getInstance(this@CouponsActivity)
                        .couponIntentBuilder(expiredCoupon)
                        .enableTapOutsideToClose()
                        .build())
    }

    fun onCustomIconDetailClicked(view: View) {
        //  In a real scenario the coupon is provided by the NearIT SDK
        val validCoupon = CouponFactory.getValidCoupon()
        startActivity(
                //  Change the default icon placeholder
                NearITUIBindings.getInstance(this@CouponsActivity)
                        .couponIntentBuilder(validCoupon)
                        .setIconPlaceholderResourceId(R.drawable.my_custom_placeholder)
                        .build())
    }

    fun onPlainCouponClicked(view: View) {
        //  start a plain activity that shows the fragment fullscreen
        //  please check it
        startActivity(Intent(this@CouponsActivity, CouponPlainActivity::class.java))
    }
}