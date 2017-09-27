package com.nearit.ui_bindings.notifications;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import it.near.sdk.recipes.background.NearBackgroundJobIntentService;
import it.near.sdk.utils.AppVisibilityDetector;
import it.near.sdk.utils.NearUtils;

/**
 * Created by Federico Boschini on 25/09/17.
 */

public class NearItUIIntentService extends NearBackgroundJobIntentService {

    private static final String TAG = "NearItUIIntentService";

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (NearUtils.carriesNearItContent(intent)) {
            if (AppVisibilityDetector.sIsForeground) {
                NearItUINotificationFactory.sendHeadsUpNotificationForBackgroundEvent(this, intent);
            } else {
                NearItUINotificationFactory.sendSimpleNotification(this, intent);
            }
        }
    }

    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "Current work stopped by OS");
        return super.onStopCurrentWork();
    }
}

//public class NearItUIIntentService extends IntentService {
//
//    public NearItUIIntentService() {
//        super(NearItUIIntentService.class.getName());
//    }
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        if (intent != null && NearUtils.carriesNearItContent(intent)) {
//            if (AppVisibilityDetector.sIsForeground) {
//                NearItUINotificationFactory.sendHeadsUpNotificationForBackgroundEvent(this, intent);
//            } else {
//                NearItUINotificationFactory.sendSimpleNotification(this, intent);
//            }
//            NearItUIBroadcastReceiver.completeWakefulIntent(intent);
//        }
//    }
//
//}
