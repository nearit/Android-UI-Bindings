package com.nearit.ui_bindings.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.recipes.foreground.ProximityListener;
import it.near.sdk.recipes.models.Recipe;
import it.near.sdk.trackings.TrackingInfo;
import it.near.sdk.utils.ContentsListener;
import it.near.sdk.utils.NearItIntentConstants;
import it.near.sdk.utils.NearUtils;

/**
 * @author Federico Boschini
 */

public class NearItUIProximityListener implements ProximityListener, ContentsListener {

//    private int customIcon = 0;

    private Context mContext;

    public NearItUIProximityListener(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        NearItManager.getInstance().addProximityListener(this);
    }

    @Override
    public void foregroundEvent(Parcelable content, TrackingInfo trackingInfo) {
        NearUtils.parseContents(content, trackingInfo, this);
    }

    @Override
    public void gotSimpleNotification(SimpleNotification simpleNotification, TrackingInfo trackingInfo) {
        sendNotifiedTracking(trackingInfo);

        Intent simpleIntent = new Intent();
        simpleIntent.putExtra(NearItIntentConstants.CONTENT, simpleNotification);
        simpleIntent.putExtra(NearItIntentConstants.TRACKING_INFO, trackingInfo);

        NearItUINotificationFactory.sendHeadsUpNotification(mContext, getAutoTrackingTargetIntent(simpleIntent), true);
    }

    @Override
    public void gotCouponNotification(Coupon coupon, TrackingInfo trackingInfo) {
        sendNotifiedTracking(trackingInfo);

        Intent couponIntent = new Intent();
        couponIntent.putExtra(NearItIntentConstants.CONTENT, coupon);
        couponIntent.putExtra(NearItIntentConstants.TRACKING_INFO, trackingInfo);

        NearItUINotificationFactory.sendHeadsUpNotification(mContext, getAutoTrackingTargetIntent(couponIntent), true);
    }

    @Override
    public void gotFeedbackNotification(Feedback feedback, TrackingInfo trackingInfo) {
        sendNotifiedTracking(trackingInfo);

        Intent feedbackIntent = new Intent();
        feedbackIntent.putExtra(NearItIntentConstants.CONTENT, feedback);
        feedbackIntent.putExtra(NearItIntentConstants.TRACKING_INFO, trackingInfo);

        NearItUINotificationFactory.sendHeadsUpNotification(mContext, getAutoTrackingTargetIntent(feedbackIntent), true);
    }

    @Override
    public void gotContentNotification(Content content, TrackingInfo trackingInfo) {
        sendNotifiedTracking(trackingInfo);

        Intent contentIntent = new Intent();
        contentIntent.putExtra(NearItIntentConstants.CONTENT, content);
        contentIntent.putExtra(NearItIntentConstants.TRACKING_INFO, trackingInfo);

        NearItUINotificationFactory.sendHeadsUpNotification(mContext, getAutoTrackingTargetIntent(contentIntent), true);
    }

    @Override
    public void gotCustomJSONNotification(CustomJSON customJSON, TrackingInfo trackingInfo) {
        //  No reaction
    }

    private Intent getAutoTrackingTargetIntent(Intent intent) {
        Intent target = new Intent(mContext, NearItUIAutoTrackingReceiver.class);
        if (intent != null && intent.getExtras() != null) {
            target.putExtras(intent.getExtras());
        }
        return target;
    }

    private void sendNotifiedTracking(TrackingInfo trackingInfo) {
        NearItManager.getInstance().sendTracking(trackingInfo, Recipe.RECEIVED);
    }

}
