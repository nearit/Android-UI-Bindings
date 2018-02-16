package com.nearit.ui_bindings.inbox;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class InboxListIntentBuilder {
    private Context context;
    private int mNoInboxLayout = 0;

    public InboxListIntentBuilder(Context context) {
        this.context = context;
    }

    public InboxListIntentBuilder setNoInboxLayout(int res) {
        this.mNoInboxLayout = res;
        return this;
    }

    public Intent build() {
        return NearITInboxActivity.createIntent(context, getParams());
    }

    @NonNull
    private InboxListExtraParams getParams() {
        return new InboxListExtraParams(mNoInboxLayout);
    }


}
