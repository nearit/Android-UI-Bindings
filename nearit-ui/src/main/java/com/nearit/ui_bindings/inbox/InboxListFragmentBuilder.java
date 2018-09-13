package com.nearit.ui_bindings.inbox;

import android.support.annotation.NonNull;

/**
 * @author Federico Boschini
 */

public class InboxListFragmentBuilder {

    private int mNoInboxLayout = 0;
    private boolean includeCustomJSON = false;
    private boolean includeFeedbacks = true;
    private boolean includeCoupons = false;

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

    public InboxListFragmentBuilder includeCoupons() {
        this.includeCoupons = true;
        return this;
    }

    public NearITInboxFragment build() {
        return NearITInboxFragment.newInstance(getParams());
    }

    @NonNull
    private InboxListExtraParams getParams() {
        return new InboxListExtraParams(mNoInboxLayout, includeCustomJSON, includeFeedbacks, includeCoupons);
    }

}
