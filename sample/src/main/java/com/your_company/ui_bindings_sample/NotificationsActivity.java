package com.your_company.ui_bindings_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nearit.ui_bindings.NearITUIBindings;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
    }

    public void openNotificationHistory(View view) {
        startActivity(NearITUIBindings.getInstance(this)
                .notificationHistoryIntentBuilder()
                .includeCoupons()
                .build()
        );
    }

    public void openNotificationsInActivity(View view) {
        startActivity(new Intent(this, NotificationHistoryPlainActivity.class));
    }
}
