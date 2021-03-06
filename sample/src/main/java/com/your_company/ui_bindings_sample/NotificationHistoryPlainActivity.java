package com.your_company.ui_bindings_sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nearit.ui_bindings.NearITUIBindings;

/**
 * @author Federico Boschini
 */

public class NotificationHistoryPlainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_notification_history);

        Fragment notificationHistoryFragment = NearITUIBindings.getInstance(this)
                .notificationHistoryFragmentBuilder()
                .build();

        getSupportFragmentManager().beginTransaction().replace(R.id.notifications_fragment_container, notificationHistoryFragment).commit();
    }

}
