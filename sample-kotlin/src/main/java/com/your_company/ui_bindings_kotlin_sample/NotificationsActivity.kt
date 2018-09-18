package com.your_company.ui_bindings_kotlin_sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.nearit.ui_bindings.NearITUIBindings

class NotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
    }

    fun openNotifications(view: View) {
        startActivity(NearITUIBindings.getInstance(this@NotificationsActivity)
                .notificationHistoryIntentBuilder()
                .build())
    }

    fun openNotificationsInActivity(view: View) {
        startActivity(Intent(this@NotificationsActivity, NotificationsPlainActivity::class.java))
    }
}