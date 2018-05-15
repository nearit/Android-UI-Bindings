package com.nearit.ui_bindings;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.nearit.ui_bindings.content.ContentDetailFragmentBuilder;
import com.nearit.ui_bindings.content.ContentDetailIntentBuilder;
import com.nearit.ui_bindings.coupon.CouponDetailFragmentBuilder;
import com.nearit.ui_bindings.coupon.CouponDetailIntentBuilder;
import com.nearit.ui_bindings.coupon.CouponListFragmentBuilder;
import com.nearit.ui_bindings.coupon.CouponListIntentBuilder;
import com.nearit.ui_bindings.feedback.FeedbackFragmentBuilder;
import com.nearit.ui_bindings.feedback.FeedbackIntentBuilder;
import com.nearit.ui_bindings.inbox.InboxListFragmentBuilder;
import com.nearit.ui_bindings.inbox.InboxListIntentBuilder;
import com.nearit.ui_bindings.notifications.NearItUIProximityListener;
import com.nearit.ui_bindings.permissions.PermissionsRequestIntentBuilder;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.recipes.models.ReactionBundle;
import it.near.sdk.trackings.TrackingInfo;

import static com.nearit.ui_bindings.coupon.CouponDetailIntentBuilder.DEFAULT_LAUNCH_MODE;

public class NearITUIBindings {

    private final Context mContext;

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
        return new CouponDetailIntentBuilder(mContext, coupon, DEFAULT_LAUNCH_MODE);
    }

    public CouponDetailIntentBuilder createCouponDetailIntentBuilder(Coupon coupon, boolean singleInstance) {
        return new CouponDetailIntentBuilder(mContext, coupon, singleInstance);
    }

    public CouponDetailFragmentBuilder createCouponDetailFragmentBuilder(Coupon coupon) {
        return new CouponDetailFragmentBuilder(coupon);
    }

    public FeedbackIntentBuilder createFeedbackIntentBuilder(Feedback feedback) {
        return new FeedbackIntentBuilder(mContext, feedback);
    }

    public FeedbackFragmentBuilder createFeedbackFragmentBuilder(Feedback feedback) {
        return new FeedbackFragmentBuilder(feedback);
    }

    /**
     * @deprecated use {@link #createContentDetailFragmentBuilder(Content, TrackingInfo)} instead to get extra trackings.
     */
    @Deprecated
    public ContentDetailIntentBuilder createContentDetailIntentBuilder(Content content) {
        return this.createContentDetailIntentBuilder(content, null);
    }

    public ContentDetailIntentBuilder createContentDetailIntentBuilder(Content content, @Nullable TrackingInfo trackingInfo) {
        return new ContentDetailIntentBuilder(mContext, content, trackingInfo);
    }

    /**
     * @deprecated use {{@link #createContentDetailFragmentBuilder(Content, TrackingInfo)}} instead, to get extra trackings
     */
    @Deprecated
    public ContentDetailFragmentBuilder createContentDetailFragmentBuilder(Content content) {
        return this.createContentDetailFragmentBuilder(content, null);
    }

    public ContentDetailFragmentBuilder createContentDetailFragmentBuilder(Content content, @Nullable TrackingInfo trackingInfo) {
        return new ContentDetailFragmentBuilder(content, trackingInfo);
    }

    public CouponListIntentBuilder createCouponListIntentBuilder() {
        return new CouponListIntentBuilder(mContext);
    }

    public CouponListFragmentBuilder createCouponListFragmentBuilder() {
        return new CouponListFragmentBuilder();
    }

    public InboxListIntentBuilder createInboxListIntentBuilder() {
        return new InboxListIntentBuilder(mContext);
    }

    public InboxListFragmentBuilder createInboxListFragmentBuilder() {
        return new InboxListFragmentBuilder();
    }

    public static void enableAutomaticForegroundNotifications(Context context) {
        new NearItUIProximityListener(context);
    }

    public static boolean onNewIntent(Context context, Intent intent) {
        return new NearItUIIntentManager(context).manageIntent(intent);
    }

    public static boolean onNewContent(Context context, ReactionBundle reactionBundle, TrackingInfo trackingInfo) {
        return new NearItUIIntentManager(context).manageContent(reactionBundle, trackingInfo);
    }
}