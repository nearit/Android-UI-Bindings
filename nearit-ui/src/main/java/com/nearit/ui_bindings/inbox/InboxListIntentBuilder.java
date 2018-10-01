package com.nearit.ui_bindings.inbox;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class InboxListIntentBuilder {
    private final Context context;
    private int mNoInboxLayout = 0;
    private boolean includeCustomJSON = false;
    private boolean includeFeedbacks = true;
    @Nullable
    private String mActivityTitle;

    public InboxListIntentBuilder(Context context) {
        this.context = context;
    }

    public InboxListIntentBuilder setNoInboxLayout(int res) {
        this.mNoInboxLayout = res;
        return this;
    }

    public InboxListIntentBuilder includeCustomJSON() {
        this.includeCustomJSON = true;
        return this;
    }

    public InboxListIntentBuilder noFeedbacks() {
        this.includeFeedbacks = false;
        return this;
    }

    public InboxListIntentBuilder setTitle(String title) {
        this.mActivityTitle = title;
        return this;
    }

    public Intent build() {
        return NearITInboxActivity.createIntent(context, getParams());
    }

    @NonNull
    private InboxListExtraParams getParams() {
        return new InboxListExtraParams(mNoInboxLayout, includeCustomJSON, includeFeedbacks, mActivityTitle);
    }


}
