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
import com.nearit.ui_bindings.inbox.NotificationHistoryFragmentBuilder;
import com.nearit.ui_bindings.inbox.NotificationHistoryIntentBuilder;
import com.nearit.ui_bindings.notifications.NearItUIProximityListener;
import com.nearit.ui_bindings.permissions.PermissionsRequestIntentBuilder;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.recipes.models.ReactionBundle;
import it.near.sdk.trackings.TrackingInfo;

public class NearITUIBindings {

    private final static boolean SINGLE_INSTANCE_DEFAULT = false;

    private final Context mContext;

    private NearITUIBindings(Context context) {
        mContext = context;
    }


    /*      PERMISSIONS     */

    /**
     * @deprecated use {@link #permissionsIntentBuilder()} instead.
     */
    @Deprecated
    public PermissionsRequestIntentBuilder createPermissionRequestIntentBuilder() {
        return this.permissionsIntentBuilder();
    }

    public PermissionsRequestIntentBuilder permissionsIntentBuilder() {
        return new PermissionsRequestIntentBuilder(mContext);
    }



    /*      COUPON     */

    /**
     * @deprecated use {@link #couponIntentBuilder(Coupon)} instead
     */
    @Deprecated
    public CouponDetailIntentBuilder createCouponDetailIntentBuilder(Coupon coupon) {
        return this.couponIntentBuilder(coupon);
    }

    public CouponDetailIntentBuilder couponIntentBuilder(Coupon coupon) {
        return this.couponIntentBuilder(coupon, SINGLE_INSTANCE_DEFAULT);
    }

    public CouponDetailIntentBuilder couponIntentBuilder(Coupon coupon, boolean singleInstance) {
        return new CouponDetailIntentBuilder(mContext, coupon, singleInstance);
    }

    /**
     * @deprecated use {@link #couponFragmentBuilder(Coupon)} instead
     */
    @Deprecated
    public CouponDetailFragmentBuilder createCouponDetailFragmentBuilder(Coupon coupon) {
        return this.couponFragmentBuilder(coupon);
    }

    public CouponDetailFragmentBuilder couponFragmentBuilder(Coupon coupon) {
        return new CouponDetailFragmentBuilder(coupon);
    }

    /**
     * @deprecated use {@link #couponListIntentBuilder()} instead
     */
    @Deprecated
    public CouponListIntentBuilder createCouponListIntentBuilder() {
        return this.couponListIntentBuilder();
    }

    public CouponListIntentBuilder couponListIntentBuilder() {
        return new CouponListIntentBuilder(mContext);
    }

    /**
     * @deprecated use {@link #couponListFragmentBuilder()} instead
     */
    @Deprecated
    public CouponListFragmentBuilder createCouponListFragmentBuilder() {
        return this.couponListFragmentBuilder();
    }

    public CouponListFragmentBuilder couponListFragmentBuilder() {
        return new CouponListFragmentBuilder();
    }


    /*      FEEDBACK    */

    /**
     * @deprecated use {@link #feedbackIntentBuilder(Feedback)} instead
     */
    @Deprecated
    public FeedbackIntentBuilder createFeedbackIntentBuilder(Feedback feedback) {
        return this.feedbackIntentBuilder(feedback);
    }

    public FeedbackIntentBuilder feedbackIntentBuilder(Feedback feedback) {
        return this.feedbackIntentBuilder(feedback, SINGLE_INSTANCE_DEFAULT);
    }

    public FeedbackIntentBuilder feedbackIntentBuilder(Feedback feedback, boolean singleInstance) {
        return new FeedbackIntentBuilder(mContext, feedback, singleInstance);
    }

    /**
     * @deprecated use {@link #feedbackFragmentBuilder(Feedback)} instead
     */
    @Deprecated
    public FeedbackFragmentBuilder createFeedbackFragmentBuilder(Feedback feedback) {
        return this.feedbackFragmentBuilder(feedback);
    }

    public FeedbackFragmentBuilder feedbackFragmentBuilder(Feedback feedback) {
        return new FeedbackFragmentBuilder(feedback);
    }



    /*      CONTENT     */

    /**
     * @deprecated use {@link #contentIntentBuilder(Content, TrackingInfo)} instead to get extra trackings.
     */
    @Deprecated
    public ContentDetailIntentBuilder createContentDetailIntentBuilder(Content content) {
        return this.contentIntentBuilder(content, null);
    }

    /**
     * @deprecated use {@link #contentIntentBuilder(Content, TrackingInfo)} instead
     */
    @Deprecated
    public ContentDetailIntentBuilder createContentDetailIntentBuilder(Content content, @Nullable TrackingInfo trackingInfo) {
        return this.contentIntentBuilder(content, trackingInfo, SINGLE_INSTANCE_DEFAULT);
    }

    public ContentDetailIntentBuilder contentIntentBuilder(Content content, @Nullable TrackingInfo trackingInfo) {
        return this.contentIntentBuilder(content, trackingInfo, SINGLE_INSTANCE_DEFAULT);
    }

    public ContentDetailIntentBuilder contentIntentBuilder(Content content, @Nullable TrackingInfo trackingInfo, boolean singleInstance) {
        return new ContentDetailIntentBuilder(mContext, content, trackingInfo, singleInstance);
    }

    /**
     * @deprecated use {@link #contentFragmentBuilder(Content, TrackingInfo)} instead, to get extra trackings
     */
    @Deprecated
    public ContentDetailFragmentBuilder createContentDetailFragmentBuilder(Content content) {
        return this.contentFragmentBuilder(content, null);
    }

    /**
     * @deprecated use {@link #contentFragmentBuilder(Content, TrackingInfo)} instead
     */
    @Deprecated
    public ContentDetailFragmentBuilder createContentDetailFragmentBuilder(Content content, @Nullable TrackingInfo trackingInfo) {
        return this.contentFragmentBuilder(content, trackingInfo);
    }

    public ContentDetailFragmentBuilder contentFragmentBuilder(Content content, @Nullable TrackingInfo trackingInfo) {
        return new ContentDetailFragmentBuilder(content, trackingInfo);
    }


    /*      Notification History       */

    public NotificationHistoryIntentBuilder notificationHistoryIntentBuilder() {
        return new NotificationHistoryIntentBuilder(mContext);
    }

    public NotificationHistoryFragmentBuilder notificationHistoryFragmentBuilder() {
        return new NotificationHistoryFragmentBuilder();
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

    public static void openLinksInWebView() {
        NearItUIIntentManager.openLinksInWebView = true;
        ContentDetailIntentBuilder.openLinksInWebView = true;
        ContentDetailFragmentBuilder.openLinksInWebView = true;
    }


    public static NearITUIBindings getInstance(Context context) {
        return new NearITUIBindings(context);
    }
}