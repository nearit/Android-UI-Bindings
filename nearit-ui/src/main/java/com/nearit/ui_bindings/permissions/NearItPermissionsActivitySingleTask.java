package com.nearit.ui_bindings.permissions;

import android.content.Context;
import android.content.Intent;

import com.nearit.ui_bindings.inbox.NearITNotificationHistoryActivitySingleInstance;

/**
 * @author Federico Boschini
 */
public class NearItPermissionsActivitySingleTask extends BasePermissionsActivity {

    public static Intent createIntent(Context context, PermissionsRequestExtraParams params) {
        Intent intent = new Intent(context, NearItPermissionsActivitySingleTask.class);
        return BasePermissionsActivity.addExtras(intent, params);
    }

}