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
        return this.permissionsIntentBuilder(NearItLaunchMode.STANDARD);
    }

    public PermissionsRequestIntentBuilder permissionsIntentBuilder(NearItLaunchMode launchMode) {
        return new PermissionsRequestIntentBuilder(mContext, launchMode);
    }

    public PermissionsRequestIntentBuilder permissionsIntentBuilder(NearItLaunchMode launchMode, int flags) {
        return new PermissionsRequestIntentBuilder(mContext, launchMode, flags);
    }





    /*      COUPON     */

    /**
     * @deprecated use {@link #couponIntentBuilder(Coupon)} instead
     */
    @Deprecated
    public CouponDetailIntentBuilder createCouponDetailIntentBuilder(Coupon coupon) {
        return this.couponIntentBuilder(coupon, NearItLaunchMode.STANDARD);
    }

    /**
     * @deprecated use {@link #couponIntentBuilder(Coupon, NearItLaunchMode)}
     *             with {@link NearItLaunchMode#SINGLE_INSTANCE} instead
     */
    @Deprecated
    public CouponDetailIntentBuilder couponIntentBuilder(Coupon coupon, boolean singleInstance) {
        return this.couponIntentBuilder(coupon, singleInstance ? NearItLaunchMode.SINGLE_INSTANCE : NearItLaunchMode.STANDARD);
    }

    /**
     * Entry point to get an intent to show a Coupon
     * @param coupon: a NearIT {@link Coupon}
     * @return the builder
     */
    public CouponDetailIntentBuilder couponIntentBuilder(Coupon coupon) {
        return this.couponIntentBuilder(coupon, NearItLaunchMode.STANDARD);
    }

    /**
     * Entry point to get an intent to show a Coupon, choosing the preferred launchMode
     * @param coupon: a NearIT {@link Coupon}
     * @param launchMode: the launchMode for the activity
     * @return the builder
     */
    public CouponDetailIntentBuilder couponIntentBuilder(Coupon coupon, NearItLaunchMode launchMode) {
        return new CouponDetailIntentBuilder(mContext, coupon, launchMode);
    }

    /**
     * Entry point to get an intent to show a Coupon, choosing the preferred launchMode
     * @param coupon: a NearIT {@link Coupon}
     * @param launchMode: the launchMode for the activity
     * @param flags: bitwise {@link Intent} flags
     * @return the builder
     */
    public CouponDetailIntentBuilder couponIntentBuilder(Coupon coupon, NearItLaunchMode launchMode, int flags) {
        return new CouponDetailIntentBuilder(mContext, coupon, launchMode, flags);
    }


    /**
     * @deprecated use {@link #couponFragmentBuilder(Coupon)} instead
     */
    @Deprecated
    public CouponDetailFragmentBuilder createCouponDetailFragmentBuilder(Coupon coupon) {
        return this.couponFragmentBuilder(coupon);
    }

    /**
     * Entry point to get a (Support) Fragment to show a Coupon
     * @param coupon: a NearIT {@link Coupon}
     * @return the builder
     */
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

    /**
     * Entry point to get an intent to show the list of Coupons
     * @return the builder
     */
    public CouponListIntentBuilder couponListIntentBuilder() {
        return this.couponListIntentBuilder(NearItLaunchMode.STANDARD);
    }

    /**
     * Entry point to get an intent to show the list of Coupons, choosing the preferred launchMode
     * @param launchMode: the launchMode for the activity
     * @return the builder
     */
    public CouponListIntentBuilder couponListIntentBuilder(NearItLaunchMode launchMode) {
        return new CouponListIntentBuilder(mContext, launchMode);
    }

    /**
     * Entry point to get an intent to show the list of Coupons, choosing the preferred launchMode and flags for the Intent
     * @param launchMode: the launchMode for the activity
     * @param flags: bitwise {@link Intent} flags
     * @return the builder
     */
    public CouponListIntentBuilder couponListIntentBuilder(NearItLaunchMode launchMode, int flags) {
        return new CouponListIntentBuilder(mContext, launchMode, flags);
    }

    /**
     * @deprecated use {@link #couponListFragmentBuilder()} instead
     */
    @Deprecated
    public CouponListFragmentBuilder createCouponListFragmentBuilder() {
        return this.couponListFragmentBuilder();
    }

    /**
     * Entry point to get a (Support) Fragment to show the list of Coupons
     * @return the builder
     */
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

    /**
     *  @deprecated use {@link #feedbackIntentBuilder(Feedback, NearItLaunchMode)}
     *              with {@link NearItLaunchMode#SINGLE_INSTANCE} instead
     */
    @Deprecated
    public FeedbackIntentBuilder feedbackIntentBuilder(Feedback feedback, boolean singleInstance) {
        return this.feedbackIntentBuilder(feedback, singleInstance ? NearItLaunchMode.SINGLE_INSTANCE : NearItLaunchMode.STANDARD);
    }

    /**
     * Entry point to get an intent to show a Feedback
     * @param feedback: a NearIT {@link Feedback}
     * @return the builder
     */
    public FeedbackIntentBuilder feedbackIntentBuilder(Feedback feedback) {
        return this.feedbackIntentBuilder(feedback, NearItLaunchMode.STANDARD);
    }

    /**
     * Entry point to get an intent to show a Feedback, choosing the preferred launchMode
     * @param feedback: a NearIT {@link Feedback}
     * @param launchMode: the launchMode for the activity
     * @return the builder
     */
    public FeedbackIntentBuilder feedbackIntentBuilder(Feedback feedback, NearItLaunchMode launchMode) {
        return new FeedbackIntentBuilder(mContext, feedback, launchMode);
    }

    /**
     * Entry point to get an intent to show a Feedback, choosing the preferred launchMode and flags for the Intent
     * @param feedback: a NearIT {@link Feedback}
     * @param launchMode: the launchMode for the activity
     * @param flags: bitwise {@link Intent} flags
     * @return the builder
     */
    public FeedbackIntentBuilder feedbackIntentBuilder(Feedback feedback, NearItLaunchMode launchMode, int flags) {
        return new FeedbackIntentBuilder(mContext, feedback, launchMode, flags);
    }


    /**
     * @deprecated use {@link #feedbackFragmentBuilder(Feedback)} instead
     */
    @Deprecated
    public FeedbackFragmentBuilder createFeedbackFragmentBuilder(Feedback feedback) {
        return this.feedbackFragmentBuilder(feedback);
    }

    /**
     * Entry point to get a (Support) Fragment to show a Feedback
     * @param feedback: a NearIT {@link Feedback}
     * @return the builder
     */
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
        return this.contentIntentBuilder(content, trackingInfo, NearItLaunchMode.STANDARD);
    }

    /**
     *  @deprecated use {@link #contentIntentBuilder(Content, TrackingInfo, NearItLaunchMode)}
     *              with {@link NearItLaunchMode#SINGLE_INSTANCE} instead
     */
    @Deprecated
    public ContentDetailIntentBuilder contentIntentBuilder(Content content, @Nullable TrackingInfo trackingInfo, boolean singleInstance) {
        return this.contentIntentBuilder(content, trackingInfo, singleInstance ? NearItLaunchMode.SINGLE_INSTANCE : NearItLaunchMode.STANDARD);
    }

    /**
     * Entry point to get an intent to show a Content
     * @param content: a NearIT {@link Content}
     * @return the builder
     */
    public ContentDetailIntentBuilder contentIntentBuilder(Content content, @Nullable TrackingInfo trackingInfo) {
        return this.contentIntentBuilder(content, trackingInfo, NearItLaunchMode.STANDARD);
    }

    /**
     * Entry point to get an intent to show a Content, choosing the preferred launchMode
     * @param content: a NearIT {@link Content}
     * @param launchMode: the launchMode for the activity
     * @return the builder
     */
    public ContentDetailIntentBuilder contentIntentBuilder(Content content, @Nullable TrackingInfo trackingInfo, NearItLaunchMode launchMode) {
        return new ContentDetailIntentBuilder(mContext, content, trackingInfo, launchMode);
    }

    /**
     * Entry point to get an intent to show a Content, choosing the preferred launchMode and flags for the Intent
     * @param content: a NearIT {@link Content}
     * @param launchMode: the launchMode for the activity
     * @param flags: bitwise {@link Intent} flags
     * @return the builder
     */
    public ContentDetailIntentBuilder contentIntentBuilder(Content content, @Nullable TrackingInfo trackingInfo, NearItLaunchMode launchMode, int flags) {
        return new ContentDetailIntentBuilder(mContext, content, trackingInfo, launchMode, flags);
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

    /**
     * Entry point to get a (Support) Fragment to show a Content
     * @param content: a NearIT {@link Content}
     * @return the builder
     */
    public ContentDetailFragmentBuilder contentFragmentBuilder(Content content, @Nullable TrackingInfo trackingInfo) {
        return new ContentDetailFragmentBuilder(content, trackingInfo);
    }


    /*      Notification History       */

    /**
     * Entry point to get an intent to show the Notification History, choosing the preferred launchMode and flags for the Intent
     * @return the builder
     */
    public NotificationHistoryIntentBuilder notificationHistoryIntentBuilder() {
        return this.notificationHistoryIntentBuilder(NearItLaunchMode.STANDARD);
    }

    /**
     * Entry point to get an intent to show the Notification History, choosing the preferred launchMode
     * @param launchMode: the launchMode for the activity
     * @return the builder
     */
    public NotificationHistoryIntentBuilder notificationHistoryIntentBuilder(NearItLaunchMode launchMode) {
        return new NotificationHistoryIntentBuilder(mContext, launchMode);
    }

    /**
     * Entry point to get an intent to show the Notification History, choosing the preferred launchMode and flags for the Intent
     * @param launchMode: the launchMode for the activity
     * @param flags: bitwise {@link Intent} flags
     * @return the builder
     */
    public NotificationHistoryIntentBuilder notificationHistoryIntentBuilder(NearItLaunchMode launchMode, int flags) {
        return new NotificationHistoryIntentBuilder(mContext, launchMode, flags);
    }


    /**
     * Entry point to get a (Support) Fragment to show the Notification History
     * @return the builder
     */
    public NotificationHistoryFragmentBuilder notificationHistoryFragmentBuilder() {
        return new NotificationHistoryFragmentBuilder();
    }



    public static void enableAutomaticForegroundNotifications(Context context) {
        new NearItUIProximityListener(context);
    }

    /**
     * Automatically handle a new Intent to show a NearIT content
     * @param context: a valid {@link Context}
     * @param intent: an {@link Intent}
     * @return true if the intent has been handled,
     * false otherwise (not a {@link Content}, {@link Feedback} or {@link Coupon} or the intent was not carrying a NearIT content)
     */
    public static boolean onNewIntent(Context context, Intent intent) {
        return onNewIntent(context, intent, NearItLaunchMode.STANDARD);
    }

    /**
     * Automatically handle a new Intent to show a NearIT content, choosing the preferred launchMode for the activity
     * @param context: a valid {@link Context}
     * @param intent: an {@link Intent}
     * @param launchMode: the preferred launchMode
     * @return true if the intent has been handled,
     * false otherwise (not a {@link Content}, {@link Feedback} or {@link Coupon} or the intent was not carrying a NearIT content)
     */
    public static boolean onNewIntent(Context context, Intent intent, NearItLaunchMode launchMode) {
        return new NearItUIIntentManager(context).manageIntent(intent, launchMode);
    }

    /**
     * Automatically handle a NearIT content
     * @param context: a valid {@link Context}
     * @param reactionBundle: a {@link ReactionBundle}
     * @param trackingInfo: {@link TrackingInfo} for the content
     * @return true if the intent has been handled, false otherwise
     */
    public static boolean onNewContent(Context context, ReactionBundle reactionBundle, TrackingInfo trackingInfo) {
        return onNewContent(context, reactionBundle, trackingInfo, NearItLaunchMode.STANDARD);
    }

    /**
     * Automatically handle a NearIT content
     * @param context: a valid {@link Context}
     * @param reactionBundle: a {@link ReactionBundle}
     * @param trackingInfo: {@link TrackingInfo} for the content
     * @return true if the intent has been handled, false otherwise
     */
    public static boolean onNewContent(Context context, ReactionBundle reactionBundle, TrackingInfo trackingInfo, NearItLaunchMode launchMode) {
        return new NearItUIIntentManager(context).manageContent(reactionBundle, trackingInfo, launchMode);
    }

    /**
     * Call this method to enable link opening in webViews (a.k.a. customTabs)
     */
    public static void openLinksInWebView() {
        NearItUIIntentManager.openLinksInWebView = true;
        ContentDetailIntentBuilder.openLinksInWebView = true;
        ContentDetailFragmentBuilder.openLinksInWebView = true;
        CouponDetailIntentBuilder.openLinksInWebView = true;
        CouponDetailFragmentBuilder.openLinksInWebView = true;
    }


    public static NearITUIBindings getInstance(Context context) {
        return new NearITUIBindings(context);
    }
}