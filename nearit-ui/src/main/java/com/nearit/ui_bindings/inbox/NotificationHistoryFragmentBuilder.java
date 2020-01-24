package com.nearit.ui_bindings.inbox;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Federico Boschini
 */

public class NotificationHistoryFragmentBuilder {

    private int mEmptyListLayout = 0;
    private boolean includeCustomJSON = false;
    private boolean includeFeedbacks = true;
    private boolean includeCoupons = false;
    @Nullable
    private String activityTitle = null;

    public NotificationHistoryFragmentBuilder() {}

    public NotificationHistoryFragmentBuilder setEmptyListLayout(@LayoutRes int res) {
        this.mEmptyListLayout = res;
        return this;
    }

    public NotificationHistoryFragmentBuilder includeCustomJSON() {
        this.includeCustomJSON = true;
        return this;
    }

    public NotificationHistoryFragmentBuilder noFeedbacks() {
        this.includeFeedbacks = false;
        return this;
    }

    public NotificationHistoryFragmentBuilder includeCoupons() {
        this.includeCoupons = true;
        return this;
    }

    public NotificationHistoryFragmentBuilder setTitle(String title) {
        this.activityTitle = title;
        return this;
    }

    public NearITNotificationHistoryFragment build() {
        return NearITNotificationHistoryFragment.newInstance(getParams());
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
