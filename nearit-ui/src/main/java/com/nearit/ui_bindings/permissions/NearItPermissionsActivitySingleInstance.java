package com.nearit.ui_bindings.permissions;

import android.content.Context;
import android.content.Intent;

/**
 * @author Federico Boschini
 */
public class NearItPermissionsActivitySingleInstance extends BasePermissionsActivity {

    public static Intent createIntent(Context context, PermissionsRequestExtraParams params) {
        Intent intent = new Intent(context, NearItPermissionsActivitySingleInstance.class);
        return BasePermissionsActivity.addExtras(intent, params);
    }

}
