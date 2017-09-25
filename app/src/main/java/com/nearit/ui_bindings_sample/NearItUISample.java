package com.nearit.ui_bindings_sample;

import android.app.Application;

import com.nearit.ui_bindings.NearITUIBindings;

/**
 * Created by Federico Boschini on 22/09/17.
 */

public class NearItUISample extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NearITUIBindings.enableAutomaticForegroundNotifications(this);
    }
}
