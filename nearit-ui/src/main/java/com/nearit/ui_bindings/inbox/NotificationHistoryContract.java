package com.nearit.ui_bindings.inbox;

import java.util.List;

import it.near.sdk.recipes.inbox.model.HistoryItem;

class NotificationHistoryContract {
    interface NotificationHistoryView {
        void showNotificationHistory(List<HistoryItem> itemList);
        void showEmptyLayout();
        void hideEmptyLayout();
        void showRefreshError(int res);
        void showRefreshError(String error);
        void openDetail(HistoryItem item);
    }

    interface NotificationHistoryPresenter {
        void start();
        void stop();
        void requestRefresh();
        void itemClicked(HistoryItem item);
        void onNotificationRead(HistoryItem item);
    }
}
