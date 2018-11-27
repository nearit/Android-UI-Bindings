package com.nearit.ui_bindings;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nearit.ui_bindings.content.ContentDetailIntentBuilder;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.recipes.models.ReactionBundle;
import it.near.sdk.trackings.TrackingInfo;
import it.near.sdk.utils.ContentsListener;
import it.near.sdk.utils.NearUtils;

/**
 * @author Federico Boschini
 */

class NearItUIIntentManager implements ContentsListener {

    private static final String TAG = "NearITUIIntentManager";

    public static boolean openLinksInWebView = false;

    private final Context context;
    private boolean intentManaged = false;
    private NearItLaunchMode launchMode = NearItLaunchMode.STANDARD;

    NearItUIIntentManager(Context context) {
        this.context = context;
    }

    boolean manageIntent(@Nullable Intent intent, NearItLaunchMode launchMode) {
        this.launchMode = launchMode;
        intentManaged = false;
        if (intent != null && intent.getExtras() != null && NearUtils.carriesNearItContent(intent)) {
            NearUtils.parseContents(intent, this);
        }
        return intentManaged;
    }

    boolean manageContent(@Nullable ReactionBundle reaction, TrackingInfo trackingInfo, NearItLaunchMode launchMode) {
        this.launchMode = launchMode;
        intentManaged = false;
        if (reaction != null) {
            NearUtils.parseContents(reaction, trackingInfo, this);
        }
        return intentManaged;
    }

    @Override
    public void gotContentNotification(Content content, TrackingInfo trackingInfo) {
        Log.d(TAG, "content notification received");
        ContentDetailIntentBuilder builder = NearITUIBindings.getInstance(context).contentIntentBuilder(content, trackingInfo, launchMode);
        if (openLinksInWebView) builder.openLinksInWebView();
        context.startActivity(builder.build());
        intentManaged = true;
    }

    @Override
    public void gotCouponNotification(Coupon coupon, TrackingInfo trackingInfo) {
        Log.d(TAG, "coupon notification received");
        context.startActivity(NearITUIBindings.getInstance(context)
                .couponIntentBuilder(coupon, launchMode).build());
        intentManaged = true;
    }

    @Override
    public void gotFeedbackNotification(Feedback feedback, TrackingInfo trackingInfo) {
        Log.d(TAG, "feedback notification received");
        context.startActivity(NearITUIBindings.getInstance(context)
                .feedbackIntentBuilder(feedback, launchMode).build());
        intentManaged = true;
    }

    @Override
    public void gotCustomJSONNotification(CustomJSON customJSON, TrackingInfo trackingInfo) {
        //  Not yet implemented
        Log.d(TAG, "customJson notification received");
        intentManaged = false;
    }

    @Override
    public void gotSimpleNotification(SimpleNotification simpleNotification, TrackingInfo trackingInfo) {
        //  Not yet implemented
        Log.d(TAG, "simple notification received");
        intentManaged = true;
    }
}
