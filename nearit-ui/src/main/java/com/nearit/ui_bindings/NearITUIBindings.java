package com.nearit.ui_bindings;

import android.content.Context;

import com.nearit.ui_bindings.content.ContentDetailFragmentBuilder;
import com.nearit.ui_bindings.content.ContentDetailIntentBuilder;
import com.nearit.ui_bindings.coupon.CouponDetailFragmentBuilder;
import com.nearit.ui_bindings.coupon.CouponDetailIntentBuilder;
import com.nearit.ui_bindings.feedback.FeedbackFragmentBuilder;
import com.nearit.ui_bindings.feedback.FeedbackIntentBuilder;
import com.nearit.ui_bindings.notifications.NearItUIProximityListener;
import com.nearit.ui_bindings.permissions.PermissionsRequestIntentBuilder;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;

public class NearITUIBindings {

    private Context mContext;

    private NearITUIBindings(Context context) {
        mContext = context;
    }

    public static NearITUIBindings getInstance(Context context) {
        return new NearITUIBindings(context);
    }

    public PermissionsRequestIntentBuilder createPermissionRequestIntentBuilder() {
        return new PermissionsRequestIntentBuilder(mContext);
    }

    public CouponDetailIntentBuilder createCouponDetailIntentBuilder(Coupon coupon) {
        return new CouponDetailIntentBuilder(mContext, coupon);
    }

    public CouponDetailFragmentBuilder createCouponDetailFragmentBuilder(Coupon coupon) {
        return new CouponDetailFragmentBuilder(mContext, coupon);
    }

    public FeedbackIntentBuilder createFeedbackIntentBuilder(Feedback feedback) {
        return new FeedbackIntentBuilder(mContext, feedback);
    }

    public FeedbackFragmentBuilder createFeedbackFragmentBuilder(Feedback feedback) {
        return new FeedbackFragmentBuilder(mContext, feedback);
    }

    public ContentDetailIntentBuilder createContentDetailIntentBuilder(Content content) {
        return new ContentDetailIntentBuilder(mContext, content);
    }

    public ContentDetailFragmentBuilder createContentDetailFragmentBuilder(Content content) {
        return new ContentDetailFragmentBuilder(mContext, content);
    }

    public static void enableAutomaticForegroundNotifications(Context context) {
        new NearItUIProximityListener(context);
    }

}