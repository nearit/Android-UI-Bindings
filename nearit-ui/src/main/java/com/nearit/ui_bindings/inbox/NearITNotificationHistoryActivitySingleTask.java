package com.nearit.ui_bindings.inbox;

import android.content.Context;
import android.content.Intent;

/**
 * @author Federico Boschini
 */
public class NearITNotificationHistoryActivitySingleTask extends BaseNotificationHistoryActivity {

    public static Intent createIntent(Context context, NotificationHistoryExtraParams params) {
        Intent intent = new Intent(context, NearITNotificationHistoryActivitySingleTask.class);
        return BaseNotificationHistoryActivity.addExtras(intent, params);
    }

}
