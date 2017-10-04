package com.nearit.ui_bindings.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.nearit.ui_bindings.R;

import java.util.Calendar;

import it.near.sdk.GlobalConfig;
import it.near.sdk.NearItManager;
import it.near.sdk.recipes.models.ReactionBundle;
import it.near.sdk.recipes.models.Recipe;
import it.near.sdk.trackings.TrackingInfo;
import it.near.sdk.utils.NearItIntentConstants;
import it.near.sdk.utils.NearNotification;

/**
 * Created by Federico Boschini on 26/09/17.
 */

class NearItUINotificationFactory {

    private static final int LIGHTS_ON_MILLIS = 500;
    private static final int LIGHTS_OFF_MILLIS = 500;
    private static final long[] VIBRATE_PATTERN = new long[]{100L, 200L, 100L, 500L};
    private static final int DEFAULT_GEO_NOTIFICATION_ICON = R.drawable.icon_geo_default_24dp;
    private static final int DEFAULT_PUSH_NOTIFICATION_ICON = R.drawable.icon_push_default_24dp;
    private static final int NEARIT_UI_RANGING_NOTIFICATION_CODE = 6699;
    private static final String FROM_INTENT_SERVICE = "auto_tracking_from_intent_service";
    private static final String NOTIFICATION_CHANNEL_ID = "NearUINotifications";


    static void sendSimpleNotification(Context context, @NonNull Intent intent) {

        ReactionBundle content = intent.getParcelableExtra(NearItIntentConstants.CONTENT);
        String contentText = content.notificationMessage;

        String title = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();

        sendNotifiedTracking(intent);

        NearNotification.send(context,
                imgResFromIntent(intent),
                title,
                contentText,
                getAutoTrackingTargetIntent(intent, context),
                uniqueNotificationCode()
        );
    }

    static void sendHeadsUpNotificationForBackgroundEvent(Context context, Intent intent) {
        sendHeadsUpNotification(context, intent, false);
    }

    static void sendHeadsUpNotification(Context context, Intent intent, boolean autoDismiss) {
        ReactionBundle content = intent.getParcelableExtra(NearItIntentConstants.CONTENT);
        String contentText = content.notificationMessage;
        String title = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buildChannel(context);
        }

        NotificationCompat.Builder notificationBuilder = getBuilder(context, title, contentText, getAutoTrackingTargetIntent(intent, context), autoDismiss);
        Notification notification = notificationBuilder.build();

        int notificationCode;
        if (autoDismiss) {
            notificationCode = NEARIT_UI_RANGING_NOTIFICATION_CODE;
        } else notificationCode = uniqueNotificationCode();

        showNotification(context, notificationCode, notification, autoDismiss);
    }

    @RequiresApi(
            api = 26
    )
    private static void buildChannel(Context context) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
        notificationChannel.setDescription("Channel description");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(VIBRATE_PATTERN);
        mNotificationManager.createNotificationChannel(notificationChannel);
    }

    private static NotificationCompat.Builder getBuilder(Context context,
                                                         String title,
                                                         String contentText,
                                                         Intent resultIntent,
                                                         boolean autoDismiss) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentText(contentText)
                .setContentIntent(getPendingIntent(context, resultIntent))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(imgResFromIntent(resultIntent))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(contentText))
                .setLights(Color.RED, LIGHTS_ON_MILLIS, LIGHTS_OFF_MILLIS)
                .setSound(getSoundNotificationUri())
                .setAutoCancel(true)
                .setVibrate(VIBRATE_PATTERN);

        if (autoDismiss) {
            builder.setTimeoutAfter(5000);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
        }

        return builderWithTitlePreNougat(builder, title);
    }

    private static PendingIntent getPendingIntent(Context context, Intent resultIntent) {
        return PendingIntent.getBroadcast(
                context,
                (int) (System.currentTimeMillis() % Integer.MAX_VALUE),
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private static NotificationCompat.Builder builderWithTitlePreNougat(NotificationCompat.Builder builder, String title) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            return builder.setContentTitle(title);
        else
            return builder;
    }

    private static Uri getSoundNotificationUri() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    private static void showNotification(Context context, final int code, Notification notification, boolean autoDismiss) {
        final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(code, notification);

        if(autoDismiss) {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                public void run() {
                    mNotificationManager.cancel(code);
                }
            }, 5000);
        }
    }

    private static int imgResFromIntent(@NonNull Intent intent) {
        GlobalConfig globalConfig = NearItManager.getInstance().globalConfig;
        if (intent.hasExtra("action")) {
            if (intent.getStringExtra("action").equals(NearItManager.PUSH_MESSAGE_ACTION)) {
                return fetchPushNotification(globalConfig);
            } else if (intent.getStringExtra("action").equals(NearItManager.GEO_MESSAGE_ACTION)) {
                return fetchProximityNotification(globalConfig);
            }
        }
        return fetchProximityNotification(globalConfig);
    }

    private static int fetchProximityNotification(GlobalConfig globalConfig) {
        int imgRes = globalConfig.getProximityNotificationIcon();
        if (imgRes != GlobalConfig.DEFAULT_EMPTY_NOTIFICATION) {
            return imgRes;
        } else {
            return DEFAULT_GEO_NOTIFICATION_ICON;
        }
    }

    private static int fetchPushNotification(GlobalConfig globalConfig) {
        int imgRes = globalConfig.getPushNotificationIcon();
        if (imgRes != GlobalConfig.DEFAULT_EMPTY_NOTIFICATION) {
            return imgRes;
        } else {
            return DEFAULT_PUSH_NOTIFICATION_ICON;
        }
    }

    private static Intent getAutoTrackingTargetIntent(Intent intent, Context context) {
        return new Intent(context, NearItUIAutoTrackingReceiver.class)
                .putExtras(intent.getExtras())
                .putExtra(FROM_INTENT_SERVICE, true);
    }

    private static void sendNotifiedTracking(@NonNull Intent intent) {
        TrackingInfo trackingInfo = intent.getParcelableExtra(NearItIntentConstants.TRACKING_INFO);
        NearItManager.getInstance().sendTracking(trackingInfo, Recipe.NOTIFIED_STATUS);
    }

    private static int uniqueNotificationCode() {
        return (int) Calendar.getInstance().getTimeInMillis();
    }

}
