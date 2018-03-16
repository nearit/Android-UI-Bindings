package com.nearit.ui_bindings.inbox;

import android.support.annotation.NonNull;

import com.nearit.ui_bindings.utils.CollectionsUtils;

import java.util.Collections;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.recipes.inbox.InboxManager;
import it.near.sdk.recipes.inbox.model.InboxItem;
import it.near.sdk.recipes.models.Recipe;

import static com.nearit.ui_bindings.utils.CollectionsUtils.filter;

class InboxPresenterImpl implements InboxContract.InboxPresenter {

    private final NearItManager nearItManager;
    private final InboxContract.InboxView view;
    private final InboxListExtraParams params;

    InboxPresenterImpl(NearItManager nearItManager, InboxContract.InboxView view, InboxListExtraParams params) {
        this.nearItManager = nearItManager;
        this.view = view;
        this.params = params;
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

    @Override
    public void onNotificationRead(InboxItem item) {
        if (item.reaction instanceof SimpleNotification)
            nearItManager.sendTracking(item.trackingInfo, Recipe.ENGAGED_STATUS);
    }

    private void loadInbox() {
        nearItManager.getInbox(new InboxManager.OnInboxMessages() {
            @Override
            public void onMessages(@NonNull List<InboxItem> inboxItemList) {
                if (!params.shouldIncludeCustomJSON() ||
                        !params.shouldIncludeFeedbacks()) {
                    inboxItemList = filter(inboxItemList, new CollectionsUtils.Predicate<InboxItem>() {
                        @Override
                        public boolean apply(InboxItem item) {
                            return item.reaction instanceof SimpleNotification ||
                                    item.reaction instanceof Content ||
                                    (params.shouldIncludeCustomJSON() && item.reaction instanceof CustomJSON) ||
                                    (params.shouldIncludeFeedbacks() && item.reaction instanceof Feedback);
                        }
                    });
                }

                if (inboxItemList.isEmpty()) {
                    view.showEmptyLayout();
                } else {
                    view.showInboxItems(inboxItemList);
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
