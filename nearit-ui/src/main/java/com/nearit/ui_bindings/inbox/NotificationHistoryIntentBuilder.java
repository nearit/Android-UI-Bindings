package com.nearit.ui_bindings.inbox;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nearit.ui_bindings.NearItLaunchMode;

public class NotificationHistoryIntentBuilder {
    private final Context context;
    private int mEmptyListLayout = 0;
    private boolean includeCustomJSON = false;
    private boolean includeFeedbacks = true;
    private boolean includeCoupons = false;
    @Nullable
    private String activityTitle = null;

    private final NearItLaunchMode launchMode;
    private int flags;

    public NotificationHistoryIntentBuilder(Context context, NearItLaunchMode launchMode) {
        this.context = context;
        this.launchMode = launchMode;
    }

    public NotificationHistoryIntentBuilder(Context context, NearItLaunchMode launchMode, int flags) {
        this(context, launchMode);
        this.flags = flags;
    }

    public NotificationHistoryIntentBuilder setEmptyListLayout(@LayoutRes int res) {
        this.mEmptyListLayout = res;
        return this;
    }

    public NotificationHistoryIntentBuilder includeCustomJSON() {
        this.includeCustomJSON = true;
        return this;
    }

    public NotificationHistoryIntentBuilder noFeedbacks() {
        this.includeFeedbacks = false;
        return this;
    }

    public NotificationHistoryIntentBuilder includeCoupons() {
        this.includeCoupons = true;
        return this;
    }

    public NotificationHistoryIntentBuilder setTitle(String title) {
        this.activityTitle = title;
        return this;
    }

    public Intent build() {
        Intent intent;
        switch (launchMode) {
            case SINGLE_INSTANCE:
                intent = NearITNotificationHistoryActivitySingleInstance.createIntent(context, getParams());
                break;
            case SINGLE_TOP:
                intent = NearITNotificationHistoryActivitySingleTop.createIntent(context, getParams());
                break;
            case SINGLE_TASK:
                intent = NearITNotificationHistoryActivitySingleTask.createIntent(context, getParams());
                break;
            default:
            case STANDARD:
                intent = NearITNotificationHistoryActivity.createIntent(context, getParams());
        }

        intent.addFlags(flags);
        return intent;
    }

    @NonNull
    private NotificationHistoryExtraParams getParams() {
        return new NotificationHistoryExtraParams(
                mEmptyListLayout,
                includeCustomJSON,
                includeFeedbacks,
                includeCoupons,
                activityTitle);
    }


}
