package com.nearit.ui_bindings.inbox;

import android.support.annotation.NonNull;

import com.nearit.ui_bindings.utils.CollectionsUtils;

import java.util.Collections;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.recipes.inbox.InboxManager;
import it.near.sdk.recipes.inbox.model.InboxItem;
import it.near.sdk.recipes.models.Recipe;

import static com.nearit.ui_bindings.utils.CollectionsUtils.filter;

class NotificationHistoryPresenterImpl implements NotificationHistoryContract.NotificationHistoryPresenter {

    private final NearItManager nearItManager;
    private final NotificationHistoryContract.NotificationHistoryView view;
    private final NotificationHistoryExtraParams params;

    NotificationHistoryPresenterImpl(NearItManager nearItManager, NotificationHistoryContract.NotificationHistoryView view, NotificationHistoryExtraParams params) {
        this.nearItManager = nearItManager;
        this.view = view;
        this.params = params;
    }

    @Override
    public void start() {
        loadHistory();
    }

    @Override
    public void stop() {

    }

    @Override
    public void requestRefresh() {
        loadHistory();
    }

    @Override
    public void itemClicked(InboxItem item) {
        nearItManager.sendTracking(item.trackingInfo, Recipe.OPENED);
        view.openDetail(item);
    }

    @Override
    public void onNotificationRead(InboxItem item) {
        if (item.reaction instanceof SimpleNotification && !item.read)
            nearItManager.sendTracking(item.trackingInfo, Recipe.OPENED);
    }

    private void loadHistory() {
        nearItManager.getInbox(new InboxManager.OnInboxMessages() {
            @Override
            public void onMessages(@NonNull List<InboxItem> itemList) {
                if (!params.shouldIncludeCustomJSON() ||
                        !params.shouldIncludeFeedbacks() ||
                        !params.shouldIncludeCoupons()) {
                    itemList = filter(itemList, new CollectionsUtils.Predicate<InboxItem>() {
                        @Override
                        public boolean apply(InboxItem item) {
                            return (!(item.reaction instanceof CustomJSON) || params.shouldIncludeCustomJSON()) &&
                                    (!(item.reaction instanceof Feedback) || params.shouldIncludeFeedbacks()) &&
                                    (!(item.reaction instanceof Coupon) || params.shouldIncludeCoupons());
                        }
                    });
                }

                if (itemList.isEmpty()) {
                    view.showEmptyLayout();
                } else {
                    view.showNotificationHistory(itemList);
                    view.hideEmptyLayout();
                }
            }

            @Override
            public void onError(String error) {
                view.showNotificationHistory(Collections.<InboxItem>emptyList());
                view.showEmptyLayout();
                view.showRefreshError(error);
            }
        });
    }
}
