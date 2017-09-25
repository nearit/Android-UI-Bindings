package com.nearit.ui_bindings.notifications;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Federico Boschini on 25/09/17.
 */

public class NearItUIBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),
                NearItUIIntentService.class.getName());

        startWakefulService(context, (intent.setComponent(comp)));
    }
}
