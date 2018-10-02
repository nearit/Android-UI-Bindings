package com.your_company.ui_bindings_kotlin_sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.nearit.ui_bindings.NearITUIBindings

class InboxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)
    }

    fun openInbox(view: View) {
        startActivity(NearITUIBindings.getInstance(this@InboxActivity)
                .inboxIntentBuilder()
                .setTitle("You got mail")
                .build())
    }

    fun openInboxInActivity(view: View) {
        startActivity(Intent(this@InboxActivity, InboxPlainActivity::class.java))
    }
}