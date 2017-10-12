package com.your_company.ui_bindings_kotlin_sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nearit.ui_bindings.NearITUIBindings
import com.your_company.ui_bindings_kotlin_sample.factories.CouponFactory

/**
 * Created by Federico Boschini on 12/10/17.
 */
class CouponPlainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plain_coupon)

        val coupon = CouponFactory.getValidCoupon()

        val couponFragment = NearITUIBindings.getInstance(this)
                .createCouponDetailFragmentBuilder(coupon)
                .build()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, couponFragment)
                .commit()

    }

}