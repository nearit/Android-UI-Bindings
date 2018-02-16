package com.nearit.ui_bindings.inbox;

import java.util.Collections;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.recipes.inbox.InboxManager;
import it.near.sdk.recipes.inbox.model.InboxItem;

class InboxPresenter {

    private final NearItManager nearItManager;
    private final NearITInboxFragment view;

    InboxPresenter(NearItManager nearItManager, NearITInboxFragment view) {
        this.nearItManager = nearItManager;
        this.view = view;
    }

    public void start() {
        view.showEmptyLayout();
        loadInbox();
    }

    public void stop() {

    }

    public void requestRefresh() {
        loadInbox();
    }

    private void loadInbox() {
        nearItManager.getInbox(new InboxManager.OnInboxMessages() {
            @Override
            public void onMessages(List<InboxItem> inboxItemList) {
                view.showInboxItems(inboxItemList);
                if (inboxItemList.size() == 0) {
                    view.showEmptyLayout();
                } else {
                    view.hideEmptyLayout();
                }
            }

            @Override
            public void onError(String error) {
                view.showInboxItems(Collections.<InboxItem>emptyList());
                view.showEmptyLayout();
                view.showRefreshError(error);
            }
        });
    }
}
