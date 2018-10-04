package com.nearit.ui_bindings.inbox;

import com.nearit.ui_bindings.base.BasePresenter;
import com.nearit.ui_bindings.base.BaseView;

import java.util.List;

import it.near.sdk.recipes.inbox.model.InboxItem;

interface InboxContract {
    interface View extends BaseView<Presenter> {
        void showInboxItems(List<InboxItem> itemList);
        void showEmptyLayout();
        void hideEmptyLayout();
        void showRefreshError(String error);
        void openDetail(InboxItem inboxItem);
    }

    interface Presenter extends BasePresenter {
        void requestRefresh();
        void itemClicked(InboxItem inboxItem);
        void onNotificationRead(InboxItem item);
    }
}
