package com.nearit.ui_bindings.inbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;

public class NearITNotificationHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nearit_ui_activity_notification_history);

        NotificationHistoryExtraParams extras = null;
        Intent intent = getIntent();
        if (intent != null &&
                intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            extras = intent.getParcelableExtra(ExtraConstants.EXTRA_FLOW_PARAMS);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notifications_fragment_container,
                        NearITNotificationHistoryFragment.newInstance(extras))
                .commit();
    }

    @NonNull
    public static Intent createIntent(Context context, NotificationHistoryExtraParams extras) {
        return new Intent(context, NearITNotificationHistoryActivity.class)
                .putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, extras);
    }
}
