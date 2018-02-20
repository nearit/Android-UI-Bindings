package com.nearit.ui_bindings.inbox;

import java.util.Collections;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.recipes.inbox.InboxManager;
import it.near.sdk.recipes.inbox.model.InboxItem;
import it.near.sdk.recipes.models.Recipe;

class InboxPresenterImpl implements InboxContract.InboxPresenter{

    private final NearItManager nearItManager;
    private final InboxContract.InboxView view;

    InboxPresenterImpl(NearItManager nearItManager, InboxContract.InboxView view) {
        this.nearItManager = nearItManager;
        this.view = view;
    }

    @Override
    public void start() {
        view.showEmptyLayout();
        loadInbox();
    }

    @Override
    public void stop() {

    }

    @Override
    public void requestRefresh() {
        loadInbox();
    }

    @Override
    public void itemClicked(InboxItem inboxItem) {
        nearItManager.sendTracking(inboxItem.trackingInfo, Recipe.ENGAGED_STATUS);
        view.openDetail(inboxItem);
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
