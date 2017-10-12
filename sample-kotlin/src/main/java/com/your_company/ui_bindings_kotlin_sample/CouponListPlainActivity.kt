package com.your_company.ui_bindings_kotlin_sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nearit.ui_bindings.NearITUIBindings

/**
 * Created by Federico Boschini on 12/10/17.
 */
class CouponListPlainActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plain_coupon_list)

        val couponListFragment = NearITUIBindings.getInstance(this)
                .createCouponListFragmentBuilder()
                .build()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.coupon_list_fragment_container, couponListFragment)
                .commit()

    }

}