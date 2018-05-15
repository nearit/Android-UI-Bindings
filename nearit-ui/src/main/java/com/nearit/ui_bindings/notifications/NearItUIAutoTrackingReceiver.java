package com.nearit.ui_bindings.notifications;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.nearit.ui_bindings.NearITUIBindings;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.recipes.models.Recipe;
import it.near.sdk.trackings.TrackingInfo;
import it.near.sdk.utils.ContentsListener;
import it.near.sdk.utils.NearItIntentConstants;
import it.near.sdk.utils.NearUtils;

/**
 * @author Federico Boschini
 */

public class NearItUIAutoTrackingReceiver extends WakefulBroadcastReceiver implements ContentsListener {

    private Context context;
    private static final String SHOULD_AUTODISMISS_IF_APP_IS_FOREGROUND = "should_autodismiss_if_app_is_foreground";
    private boolean shouldAutoDismiss;
    private Intent launcherIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        TrackingInfo trackingInfo = intent.getParcelableExtra(NearItIntentConstants.TRACKING_INFO);
        NearItManager.getInstance().sendTracking(trackingInfo, Recipe.OPENED);

        shouldAutoDismiss = intent.getBooleanExtra(SHOULD_AUTODISMISS_IF_APP_IS_FOREGROUND, false);

        this.context = context;

        launcherIntent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());

        if (intent.getExtras() != null) {
            launcherIntent.putExtras(intent.getExtras());
        }

        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        NearUtils.parseContents(intent, this);
    }

    @Override
    public void gotCouponNotification(Coupon coupon, TrackingInfo trackingInfo) {
        context.startActivity(NearITUIBindings.getInstance(context).createCouponDetailIntentBuilder(coupon).build().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS));
    }

    @Override
    public void gotFeedbackNotification(Feedback feedback, TrackingInfo trackingInfo) {
        context.startActivity(NearITUIBindings.getInstance(context).createFeedbackIntentBuilder(feedback).build().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS));
    }

    @Override
    public void gotContentNotification(Content content, TrackingInfo trackingInfo) {
        context.startActivity(NearITUIBindings.getInstance(context).createContentDetailIntentBuilder(content, trackingInfo).build().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS));
    }

    @Override
    public void gotSimpleNotification(SimpleNotification simpleNotification, TrackingInfo trackingInfo) {
        if (shouldAutoDismiss) {
            //  it means that we got here from a background notification
            context.startActivity(launcherIntent);
        }
    }

    @Override
    public void gotCustomJSONNotification(CustomJSON customJSON, TrackingInfo trackingInfo) {
        if (shouldAutoDismiss) {
            //  it means that we got here from a background notification
            context.startActivity(launcherIntent);
        }
    }
}
