package com.your_company.ui_bindings_kotlin_sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.nearit.ui_bindings.NearITUIBindings
import it.near.sdk.utils.NearUtils

/**
* @author Federico Boschini
*/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (NearUtils.carriesNearItContent(intent)) {
            val handled = NearITUIBindings.onNewIntent(this, intent)
            if (!handled) {
                // it was a customJson
            }
        } else {
            //  intent was not carrying an in-app content
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun openPermission(view: View) {
        startActivity(Intent(this@MainActivity, PermissionsActivity::class.java))
    }

    @Suppress("UNUSED_PARAMETER")
    fun openCoupons(view: View) {
        startActivity(Intent(this@MainActivity, CouponsActivity::class.java))
    }

    @Suppress("UNUSED_PARAMETER")
    fun openFeedback(view: View) {
        startActivity(Intent(this@MainActivity, FeedbackActivity::class.java))
    }

    @Suppress("UNUSED_PARAMETER")
    fun openContent(view: View) {
        startActivity(Intent(this@MainActivity, ContentActivity::class.java))
    }

    @Suppress("UNUSED_PARAMETER")
    fun openNotifications(view: View) {
        startActivity(Intent(this@MainActivity, NotificationsActivity::class.java))
    }


    companion object {
        @Suppress("unused")
        const val TAG = "MainActivity"
    }
}