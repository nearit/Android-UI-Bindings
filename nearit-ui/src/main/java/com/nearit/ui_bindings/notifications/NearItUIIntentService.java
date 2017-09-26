package com.nearit.ui_bindings.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import it.near.sdk.utils.AppVisibilityDetector;
import it.near.sdk.utils.NearUtils;

/**
 * Created by Federico Boschini on 25/09/17.
 */

public class NearItUIIntentService extends IntentService {

    public NearItUIIntentService() {
        super(NearItUIIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && NearUtils.carriesNearItContent(intent)) {
            if (AppVisibilityDetector.sIsForeground) {
                NearItUINotificationFactory.sendHeadsUpNotification(this, intent);
            } else {
                NearItUINotificationFactory.sendSimpleNotification(this, intent);
            }
            NearItUIBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

}
