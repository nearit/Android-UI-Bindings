package com.nearit.ui_bindings.permissions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Federico Boschini
 */
public class NearItPermissionsActivity extends AppCompatActivity {

    public static Intent createIntent(Context context, PermissionsRequestExtraParams params) {
        Intent intent = new Intent(context, NearItPermissionsActivity.class);
        return BasePermissionsActivity.addExtras(intent, params);
    }
}
