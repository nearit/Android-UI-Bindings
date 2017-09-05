package com.nearit.ui_bindings_sample;

import android.app.Application;

import com.nearit.ui_bindings.NearITUIBindings;

import it.near.sdk.NearItManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // NearItManager.init(this, getString(R.string.near_api_key));

        NearITUIBindings.init();
    }
}
