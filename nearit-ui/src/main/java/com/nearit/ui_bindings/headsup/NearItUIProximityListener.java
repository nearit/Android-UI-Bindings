package com.nearit.ui_bindings.headsup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;

import it.near.sdk.NearItManager;
import it.near.sdk.geopolis.beacons.ranging.ProximityListener;
import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.trackings.TrackingInfo;
import it.near.sdk.utils.CoreContentsListener;
import it.near.sdk.utils.NearUtils;

/**
 * Created by Federico Boschini on 22/09/17.
 */

public class NearItUIProximityListener implements ProximityListener, CoreContentsListener {

    private NotificationManager mNotificationManager;
    //  make it a param ?
    private boolean useHeadsUpNotification = true;

    private int customIcon = 0;

    private Context mContext;
    private static final int NEARITUI_NOTIFICATION_ID = 9999;

    public NearItUIProximityListener(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NearItManager.getInstance().addProximityListener(this);
    }

    @Override
    public void foregroundEvent(Parcelable content, TrackingInfo trackingInfo) {
        NearUtils.parseCoreContents(content, trackingInfo, this);
    }


    @Override
    public void gotSimpleNotification(SimpleNotification simpleNotification, TrackingInfo trackingInfo) {
        mNotificationManager.notify(NEARITUI_NOTIFICATION_ID, createNotification(
                useHeadsUpNotification, mContext));
    }

    @Override
    public void gotContentNotification(Content content, TrackingInfo trackingInfo) {

    }

    @Override
    public void gotCouponNotification(Coupon coupon, TrackingInfo trackingInfo) {

    }

    @Override
    public void gotCustomJSONNotification(CustomJSON customJSON, TrackingInfo trackingInfo) {

    }

    @Override
    public void gotFeedbackNotification(Feedback feedback, TrackingInfo trackingInfo) {

    }

    /**
     * Creates a new notification depending on the argument.
     *
     * @param makeHeadsUpNotification A boolean value to indicating whether a notification will be
     *                                created as a heads-up notification or not.
     *                                <ul>
     *                                <li>true : Creates a heads-up notification.</li>
     *                                <li>false : Creates a non-heads-up notification.</li>
     *                                </ul>
     * @return A Notification instance.
     */
    private Notification createNotification(boolean makeHeadsUpNotification, Context context) {
        Notification.Builder notificationBuilder = new Notification.Builder(context);

//                .setSmallIcon(R.drawable.ic_launcher_notification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_DEFAULT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setCategory(Notification.CATEGORY_MESSAGE);
        }

        notificationBuilder.setContentTitle("Sample Notification")
                .setContentText("This is a normal notification.");

        if (makeHeadsUpNotification) {
            Intent push = new Intent();
            push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            push.setClass(context, NearItUIProximityListener.class);

            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                    push, PendingIntent.FLAG_CANCEL_CURRENT);
            notificationBuilder
                    .setContentText("Heads-Up Notification on Android L or above.")
                    .setFullScreenIntent(fullScreenPendingIntent, true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return notificationBuilder.build();
        } else {
            return notificationBuilder.getNotification();
        }
    }
}
