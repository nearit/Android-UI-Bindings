package com.nearit.ui_bindings.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nearit.ui_bindings.R;

import java.util.Calendar;

import it.near.sdk.GlobalConfig;
import it.near.sdk.NearItManager;
import it.near.sdk.recipes.models.ReactionBundle;
import it.near.sdk.recipes.models.Recipe;
import it.near.sdk.trackings.TrackingInfo;
import it.near.sdk.utils.NearItIntentConstants;
import it.near.sdk.utils.NearNotification;
import it.near.sdk.utils.NearUtils;

/**
 * Created by Federico Boschini on 25/09/17.
 */

public class NearItUIIntentService extends IntentService {

    private static final int DEFAULT_GEO_NOTIFICATION_ICON = R.drawable.icon_geo_default_24dp;
    private static final int DEFAULT_PUSH_NOTIFICATION_ICON = R.drawable.icon_push_default_24dp;
    private static final String FROM_INTENT_SERVICE = "auto_tracking_from_intent_service";

    public NearItUIIntentService() {
        super(NearItUIIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && NearUtils.carriesNearItContent(intent)) {
            sendSimpleNotification(intent);
            NearItUIBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    protected void sendSimpleNotification(@NonNull Intent intent) {
        ReactionBundle content = intent.getParcelableExtra(NearItIntentConstants.CONTENT);
        String notifText = content.notificationMessage;

        String notifTitle = getApplicationInfo().loadLabel(getPackageManager()).toString();

        sendNotifiedTracking(intent);

        NearNotification.send(this,
                imgResFromIntent(intent),
                notifTitle,
                notifText,
                getAutoTrackingTargetIntent(intent),
                uniqueNotificationCode()
        );
    }

    private int imgResFromIntent(@NonNull Intent intent) {
        GlobalConfig globalConfig = NearItManager.getInstance().globalConfig;
        if (intent.getAction().equals(NearItManager.PUSH_MESSAGE_ACTION)) {
            return fetchPushNotification(globalConfig);
        } else if (intent.getAction().equals(NearItManager.GEO_MESSAGE_ACTION)) {
            return fetchProximityNotification(globalConfig);
        } else
            return fetchProximityNotification(globalConfig);
    }

    private int fetchProximityNotification(GlobalConfig globalConfig) {
        int imgRes = globalConfig.getProximityNotificationIcon();
        if (imgRes != GlobalConfig.DEFAULT_EMPTY_NOTIFICATION) {
            return imgRes;
        } else {
            return DEFAULT_GEO_NOTIFICATION_ICON;
        }
    }

    private int fetchPushNotification(GlobalConfig globalConfig) {
        int imgRes = globalConfig.getPushNotificationIcon();
        if (imgRes != GlobalConfig.DEFAULT_EMPTY_NOTIFICATION) {
            return imgRes;
        } else {
            return DEFAULT_PUSH_NOTIFICATION_ICON;
        }
    }

    private Intent getAutoTrackingTargetIntent(Intent intent) {
        return new Intent(this, NearItUIAutoTrackingReceiver.class)
                .putExtras(intent.getExtras())
                .putExtra(FROM_INTENT_SERVICE, true);
    }

    protected void sendNotifiedTracking(@NonNull Intent intent) {
        TrackingInfo trackingInfo = intent.getParcelableExtra(NearItIntentConstants.TRACKING_INFO);
        NearItManager.getInstance().sendTracking(trackingInfo, Recipe.NOTIFIED_STATUS);
    }

    private int uniqueNotificationCode() {
        return (int) Calendar.getInstance().getTimeInMillis();
    }

}
