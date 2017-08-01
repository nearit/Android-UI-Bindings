package com.nearit.ui_bindings;

import android.content.Context;

import it.near.sdk.NearItManager;

public class NearITUIBindings {

    public static void init(Context context) {
        NearItManager.getInstance(context);
    }

}
