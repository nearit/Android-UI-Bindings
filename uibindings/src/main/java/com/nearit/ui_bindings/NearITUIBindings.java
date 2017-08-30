package com.nearit.ui_bindings;

import android.content.Context;
import com.nearit.ui_bindings.permissions.PermissionsRequestIntentBuilder;
import it.near.sdk.NearItManager;

public class NearITUIBindings {

    private Context mContext;

    public static void init(Context context) {
        NearItManager.getInstance();
    }

    private NearITUIBindings(Context context) {
        mContext = context;
    }

    public static NearITUIBindings getInstance(Context context) {
        return new NearITUIBindings(context);
    }

    public PermissionsRequestIntentBuilder createPermissionRequestIntentBuilder() {
        return new PermissionsRequestIntentBuilder(mContext);
    }

}