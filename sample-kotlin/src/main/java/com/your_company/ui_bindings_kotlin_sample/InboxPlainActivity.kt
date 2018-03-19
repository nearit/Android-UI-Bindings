package com.your_company.ui_bindings_kotlin_sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nearit.ui_bindings.NearITUIBindings

class InboxPlainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plain_inbox)

        val inboxFragment = NearITUIBindings.getInstance(this)
                .createInboxListFragmentBuilder()
                .build()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.inbox_fragment_container, inboxFragment)
                .commit()
    }
}