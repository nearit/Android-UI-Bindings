package com.your_company.ui_bindings_kotlin_sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.nearit.ui_bindings.NearITUIBindings
import com.your_company.ui_bindings_kotlin_sample.factories.CouponFactory
import kotlinx.android.synthetic.main.activity_coupons.*

/**
* @author Federico Boschini
*/

class CouponsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupons)

        /*valid_coupon.setOnClickListener {
            //  In a real scenario the coupon is provided by the NearIT SDK
            val validCoupon = CouponFactory.getValidCoupon()
            startActivity(
                    //  Basic example with a valid coupon
                    NearITUIBindings.getInstance(this@CouponsActivity)
                            .createCouponDetailIntentBuilder(validCoupon)
                            .build()
            )
        }

        inactive_coupon.setOnClickListener {
            //  In a real scenario the coupon is provided by the NearIT SDK
            val inactiveCoupon = CouponFactory.getInactiveCoupon()
            startActivity(
                    //  Basic example with a coupon not yet valid
                    NearITUIBindings.getInstance(this@CouponsActivity)
                            .createCouponDetailIntentBuilder(inactiveCoupon)
                            .build()
            )
        }

        expired_coupon.setOnClickListener {
            //  In a real scenario the coupon is provided by the NearIT SDK
            val expiredCoupon = CouponFactory.getExpiredCoupon()
            startActivity(
                    //  Basic example with an expired coupon
                    //  + enable tap outside the dialog to close it
                    NearITUIBindings.getInstance(this@CouponsActivity)
                            .createCouponDetailIntentBuilder(expiredCoupon)
                            .build()
            )
        }

        custom_icon_coupon.setOnClickListener {
            //  In a real scenario the coupon is provided by the NearIT SDK
            val validCoupon = CouponFactory.getValidCoupon()
            startActivity(
                    //  Change the default icon placeholder
                    NearITUIBindings.getInstance(this@CouponsActivity)
                            .createCouponDetailIntentBuilder(validCoupon)
                            .setIconPlaceholderResourceId(R.drawable.my_custom_placeholder)
                            .build()
            )
        }

        val plainActivity = findViewById<View>(R.id.plain_coupon_activity) as Button
        plainActivity.setOnClickListener {
            //  start a plain activity that shows the fragment fullscreen
            //  please check it
            startActivity(Intent(this@CouponsActivity, CouponPlainActivity::class.java))
        }



        coupon_list.setOnClickListener {
            //  start an activity that shows a list of REAL coupons, with jagged borders
            startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                    .createCouponListIntentBuilder()
                    .jaggedBorders()
                    .build()
            )
        }

        valid_coupon_list.setOnClickListener {
            //  start an activity that shows a list of REAL valid coupons ONLY
            startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                    .createCouponListIntentBuilder()
                    .validCoupons()
                    .build()
            )
        }

        expired_coupon_list.setOnClickListener {
            //  start an activity that shows a list of REAL expired coupons ONLY
            startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                    .createCouponListIntentBuilder()
                    .expiredCoupons()
                    .build()
            )
        }

        inactive_coupon_list.setOnClickListener {
            //  start an activity that shows a list of REAL inactive coupons ONLY
            startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                    .createCouponListIntentBuilder()
                    .inactiveCoupons()
                    .build()
            )
        }

        redeemed_coupon_list.setOnClickListener {
            //  start an activity that shows a list of REAL already redeemed coupons ONLY
            startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                    .createCouponListIntentBuilder()
                    .redeemedCoupons()
                    .build()
            )
        }

        include_redeemed_coupon_list.setOnClickListener {
            //  start an activity that shows the complete list of REAL coupons (redeemed included)
            startActivity(NearITUIBindings.getInstance(this@CouponsActivity)
                    .createCouponListIntentBuilder()
                    .includeRedeemed()
                    .build()
            )
        }

        coupon_list_in_activity.setOnClickListener {
            //  start a plain activity that shows the fragment fullscreen
            //  please check it
            startActivity(Intent(this@CouponsActivity, CouponListPlainActivity::class.java))
        }*/
    }

}