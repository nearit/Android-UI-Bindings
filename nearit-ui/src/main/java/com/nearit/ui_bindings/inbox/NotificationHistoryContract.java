package com.nearit.ui_bindings.inbox;

import java.util.List;

import it.near.sdk.recipes.inbox.model.InboxItem;

class NotificationHistoryContract {
    interface NotificationHistoryView {
        void showNotificationHistory(List<InboxItem> itemList);
        void showEmptyLayout();
        void hideEmptyLayout();
        void showRefreshError(String error);
        void openDetail(InboxItem item);
    }

    interface NotificationHistoryPresenter {
        void start();
        void stop();
        void requestRefresh();
        void itemClicked(InboxItem item);
        void onNotificationRead(InboxItem item);
    }
}
