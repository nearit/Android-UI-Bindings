package com.nearit.ui_bindings.inbox;

import com.nearit.ui_bindings.base.BasePresenter;
import com.nearit.ui_bindings.base.BaseView;

import java.util.List;

import it.near.sdk.recipes.inbox.model.HistoryItem;

class NotificationHistoryContract {

    interface NotificationHistoryView extends BaseView<NotificationHistoryPresenter> {
        void showNotificationHistory(List<HistoryItem> itemList);
        void showEmptyLayout();
        void hideEmptyLayout();
        void showRefreshError(int res);
        void showRefreshError(String error);
        void openDetail(HistoryItem item);
    }

    interface NotificationHistoryPresenter extends BasePresenter {
        void requestRefresh();
        void itemClicked(HistoryItem item);
        void onNotificationRead(HistoryItem item);
    }
}
