package com.nearit.ui_bindings.headsup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;

import com.nearit.ui_bindings.R;

import java.util.Calendar;

import it.near.sdk.NearItManager;
import it.near.sdk.geopolis.beacons.ranging.ProximityListener;
import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.recipes.models.Recipe;
import it.near.sdk.trackings.TrackingInfo;
import it.near.sdk.utils.CoreContentsListener;
import it.near.sdk.utils.NearItIntentConstants;
import it.near.sdk.utils.NearUtils;

/**
 * Created by Federico Boschini on 22/09/17.
 */

public class NearItUIProximityListener implements ProximityListener, CoreContentsListener {

    private static final int LIGHTS_ON_MILLIS = 500;
    private static final int LIGHTS_OFF_MILLIS = 500;
    private static final long[] VIBRATE_PATTERN = new long[]{100L, 200L, 100L, 500L};

    private static final int DEFAULT_GEO_NOTIFICATION_ICON = R.drawable.icon_geo_default_24dp;

//    private int customIcon = 0;

    private Context mContext;

    public NearItUIProximityListener(Context context) {
        mContext = context;
        init();
    }

    private void init() {
//        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NearItManager.getInstance().addProximityListener(this);
    }

    @Override
    public void foregroundEvent(Parcelable content, TrackingInfo trackingInfo) {
        NearUtils.parseCoreContents(content, trackingInfo, this);
    }



    @Override
    public void gotSimpleNotification(SimpleNotification simpleNotification, TrackingInfo trackingInfo) {
        sendNotifiedTracking(trackingInfo);

        String notifTitle = mContext.getApplicationInfo().loadLabel(mContext.getPackageManager()).toString();
        String notifText = simpleNotification.notificationMessage;
        Intent simpleIntent = new Intent();
        simpleIntent.putExtra(NearItIntentConstants.TRACKING_INFO, trackingInfo);

        sendNotification(mContext, notifTitle, notifText, getAutoTrackingTargetIntent(simpleIntent));
    }

    @Override
    public void gotCouponNotification(Coupon coupon, TrackingInfo trackingInfo) {
        sendNotifiedTracking(trackingInfo);

        String notifTitle = mContext.getApplicationInfo().loadLabel(mContext.getPackageManager()).toString();
        String notifText = coupon.notificationMessage;
        Intent couponIntent = new Intent();
        couponIntent.putExtra(NearItIntentConstants.CONTENT, coupon);
        couponIntent.putExtra(NearItIntentConstants.TRACKING_INFO, trackingInfo);

        sendNotification(mContext, notifTitle, notifText, getAutoTrackingTargetIntent(couponIntent));
    }

    @Override
    public void gotFeedbackNotification(Feedback feedback, TrackingInfo trackingInfo) {
        sendNotifiedTracking(trackingInfo);

        String notifTitle = mContext.getApplicationInfo().loadLabel(mContext.getPackageManager()).toString();
        String notifText = feedback.notificationMessage;
        Intent feedbackIntent = new Intent();
        feedbackIntent.putExtra(NearItIntentConstants.CONTENT, feedback);
        feedbackIntent.putExtra(NearItIntentConstants.TRACKING_INFO, trackingInfo);
        sendNotification(mContext, notifTitle, notifText, getAutoTrackingTargetIntent(feedbackIntent));
    }

    @Override
    public void gotContentNotification(Content content, TrackingInfo trackingInfo) {
        //  Not yet implemented
    }

    @Override
    public void gotCustomJSONNotification(CustomJSON customJSON, TrackingInfo trackingInfo) {
        //  No reaction
    }


    private Intent getAutoTrackingTargetIntent(Intent intent) {
        return new Intent(mContext, NearItUIAutoTrackingReceiver.class)
                .putExtras(intent.getExtras());
    }

    private void sendNotifiedTracking(TrackingInfo trackingInfo) {
        NearItManager.getInstance().sendTracking(trackingInfo, Recipe.NOTIFIED_STATUS);
    }

    private int uniqueNotificationCode() {
        return (int) Calendar.getInstance().getTimeInMillis();
    }

    private void sendNotification(Context context, String title, String contentText, Intent intent) {
        NotificationCompat.Builder notificationBuilder = getBuilder(context, title, contentText, intent);
        Notification notification = notificationBuilder.build();
        showNotification(context, uniqueNotificationCode(), notification);
    }

    private static NotificationCompat.Builder getBuilder(Context context,
                                                         String title,
                                                         String contentText,
                                                         Intent resultIntent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentText(contentText)
                .setContentIntent(getPendingIntent(context, resultIntent))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(DEFAULT_GEO_NOTIFICATION_ICON)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(contentText))
                .setLights(Color.RED, LIGHTS_ON_MILLIS, LIGHTS_OFF_MILLIS)
                .setSound(getSoundNotificationUri())
                .setAutoCancel(true)
                .setVibrate(VIBRATE_PATTERN);

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

    private static void showNotification(Context context, final int code, Notification notification) {
        final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(code, notification);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                mNotificationManager.cancel(code);
            }
        }, 5000);
    }
}
