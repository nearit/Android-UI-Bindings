package com.your_company.ui_bindings_sample;

import android.app.Application;

import com.nearit.ui_bindings.NearITUIBindings;

/**
 * @author Federico Boschini
 */

public class NearItUISample extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //  this library will handle foreground notifications automatically and show the right dialog on tap
        NearITUIBindings.enableAutomaticForegroundNotifications(this);
    }
}
