package com.nearit.ui_bindings.inbox;

import java.util.List;

import it.near.sdk.recipes.inbox.model.InboxItem;

public class InboxContract {
    interface InboxView {
        void showInboxItems(List<InboxItem> itemList);
        void showEmptyLayout();
        void hideEmptyLayout();
        void showRefreshError(String error);
        void openDetail(InboxItem inboxItem);
    }

    interface InboxPresenter {
        void start();
        void stop();
        void requestRefresh();
        void itemClicked(InboxItem inboxItem);
        void onNotificationRead(InboxItem item);
    }
}
