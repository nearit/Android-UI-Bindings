package com.nearit.ui_bindings.inbox;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class NotificationHistoryIntentBuilder {
    private final Context context;
    private int mEmptyListLayout = 0;
    private boolean includeCustomJSON = false;
    private boolean includeFeedbacks = true;
    private boolean includeCoupons = false;

    public NotificationHistoryIntentBuilder(Context context) {
        this.context = context;
    }

    public NotificationHistoryIntentBuilder setEmptyListLayout(int res) {
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

    public Intent build() {
        return NearITNotificationHistoryActivity.createIntent(context, getParams());
    }

    @NonNull
    private NotificationHistoryExtraParams getParams() {
        return new NotificationHistoryExtraParams(mEmptyListLayout, includeCustomJSON, includeFeedbacks, includeCoupons);
    }


}
