package com.nearit.ui_bindings.inbox;

import android.support.annotation.NonNull;

import it.near.sdk.NearItManager;

/**
 * @author Federico Boschini
 */

public class InboxListFragmentBuilder {

    private int mNoInboxLayout = 0;
    private boolean includeCustomJSON = false;
    private boolean includeFeedbacks = true;

    public InboxListFragmentBuilder() {}

    public InboxListFragmentBuilder setNoInboxLayout(int res) {
        this.mNoInboxLayout = res;
        return this;
    }

    public InboxListFragmentBuilder includeCustomJSON() {
        this.includeCustomJSON = true;
        return this;
    }

    public InboxListFragmentBuilder noFeedbacks() {
        this.includeFeedbacks = false;
        return this;
    }

    public NearITInboxFragment build() {
        return NearITInboxFragment.newInstance(getParams(), NearItManager.getInstance());
    }

    @NonNull
    private InboxListExtraParams getParams() {
        return new InboxListExtraParams(mNoInboxLayout, includeCustomJSON, includeFeedbacks);
    }

}
