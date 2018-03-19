package com.your_company.ui_bindings_kotlin_sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.nearit.ui_bindings.NearITUIBindings

/**
* @author Federico Boschini
*/

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

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

        val handled: Boolean = NearITUIBindings.onNewIntent(this, intent)

        if(!handled) {
            //  intent was not carrying an in-app content
        }
    }

    fun openPermission(view: View) {
        startActivity(Intent(this@MainActivity, PermissionsActivity::class.java))
    }

    fun openCoupons(view: View) {
        startActivity(Intent(this@MainActivity, CouponsActivity::class.java))
    }

    fun openFeedbacks(view: View) {
        startActivity(Intent(this@MainActivity, FeedbackActivity::class.java))
    }

    fun openContent(view: View) {
        startActivity(Intent(this@MainActivity, ContentActivity::class.java))
    }

    fun openInbox(view: View) {
        startActivity(Intent(this@MainActivity, InboxActivity::class.java))
    }
}