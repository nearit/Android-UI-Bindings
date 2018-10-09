package com.your_company.ui_bindings_kotlin_sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nearit.ui_bindings.NearITUIBindings

class NotificationsPlainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plain_notifications)

        val notificationsFragment = NearITUIBindings.getInstance(this)
                .notificationHistoryFragmentBuilder()
                .build()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.notifications_fragment_container, notificationsFragment)
                .commit()
    }
}