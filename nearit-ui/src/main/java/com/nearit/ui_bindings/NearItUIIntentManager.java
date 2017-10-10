package com.nearit.ui_bindings;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.trackings.TrackingInfo;
import it.near.sdk.utils.CoreContentsListener;
import it.near.sdk.utils.NearUtils;

/**
 * Created by Federico Boschini on 09/10/17.
 */

class NearItUIIntentManager implements CoreContentsListener {

    private static final String TAG = "NearITUIIntentManager";

    private Context context;
    private Intent launcherIntent;
    private boolean intentManaged = false;

    NearItUIIntentManager(Context context) {
        this.context = context;
    }

    boolean manageIntent(@Nullable Intent intent) {
        if(intent != null && intent.getExtras() != null) {

            launcherIntent = context.getPackageManager()
                    .getLaunchIntentForPackage(context.getPackageName())
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtras(intent.getExtras());

            if (NearUtils.carriesNearItContent(launcherIntent)) {
                NearUtils.parseCoreContents(intent, this);
            }
        }
        return intentManaged;
    }

    @Override
    public void gotContentNotification(Content content, TrackingInfo trackingInfo) {
        Log.d(TAG, "content notification received");
        context.startActivity(NearITUIBindings.getInstance(context).createContentDetailIntentBuilder(content).build());
        intentManaged = true;
    }

    @Override
    public void gotCouponNotification(Coupon coupon, TrackingInfo trackingInfo) {
        Log.d(TAG, "coupon notification received");
        context.startActivity(NearITUIBindings.getInstance(context).createCouponDetailIntentBuilder(coupon).build());
        intentManaged = true;
    }

    @Override
    public void gotFeedbackNotification(Feedback feedback, TrackingInfo trackingInfo) {
        Log.d(TAG, "feedback notification received");
        context.startActivity(NearITUIBindings.getInstance(context).createFeedbackIntentBuilder(feedback).build());
        intentManaged = true;
    }

    @Override
    public void gotCustomJSONNotification(CustomJSON customJSON, TrackingInfo trackingInfo) {
        //  Not yet implemented
        Log.d(TAG, "customJson notification received");
    }

    @Override
    public void gotSimpleNotification(SimpleNotification simpleNotification, TrackingInfo trackingInfo) {
        //  Not yet implemented
        Log.d(TAG, "simple notification received");
    }
}
