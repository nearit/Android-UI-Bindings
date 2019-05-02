package com.nearit.ui_bindings.inbox;

import android.support.annotation.NonNull;
import android.util.Log;

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.utils.CollectionsUtils;

import java.util.Collections;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.recipes.inbox.NotificationHistoryManager;
import it.near.sdk.recipes.inbox.model.HistoryItem;
import it.near.sdk.recipes.models.Recipe;

import static com.nearit.ui_bindings.utils.CollectionsUtils.filter;

class NotificationHistoryPresenterImpl implements NotificationHistoryContract.NotificationHistoryPresenter {

    public static final String TAG = "NotificationHistoryPres";

    private final NearItManager nearItManager;
    private final NotificationHistoryContract.NotificationHistoryView view;
    private final NotificationHistoryExtraParams params;

    NotificationHistoryPresenterImpl(NearItManager nearItManager, NotificationHistoryContract.NotificationHistoryView view, NotificationHistoryExtraParams params) {
        this.nearItManager = nearItManager;
        this.view = view;
        this.params = params;
        init();
    }

    private void init() {
        view.injectPresenter(this);
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
    public void itemClicked(HistoryItem item) {
        nearItManager.sendTracking(item.trackingInfo, Recipe.OPENED);
        if (item.reaction instanceof CustomJSON) {
            if (NearITUIBindings.customJsonFromHistoryListener != null) {
                NearITUIBindings.customJsonFromHistoryListener.onCustomJsonClicked((CustomJSON) item.reaction, item.trackingInfo);
            } else {
                Log.e(TAG, "A CustomJson was tapped but no listener was set. Set it with `NearITUIBindings.getInstance(context).setOnCustomJsonClickedFromHistoryListener(yourListener)`");
            }
        }
        view.openDetail(item);
    }

    @Override
    public void onNotificationRead(HistoryItem item) {
        if (item.reaction instanceof SimpleNotification && !item.read)
            nearItManager.sendTracking(item.trackingInfo, Recipe.OPENED);
    }

    private void loadHistory() {
        view.showRefreshing();
        nearItManager.getHistory(new NotificationHistoryManager.OnNotificationHistoryListener() {
            @Override
            public void onNotifications(@NonNull List<HistoryItem> itemList) {
                if (!params.shouldIncludeCustomJSON() ||
                        !params.shouldIncludeFeedbacks() ||
                        !params.shouldIncludeCoupons()) {
                    itemList = filter(itemList, new CollectionsUtils.Predicate<HistoryItem>() {
                        @Override
                        public boolean apply(HistoryItem item) {
                            return (!(item.reaction instanceof CustomJSON) || params.shouldIncludeCustomJSON()) &&
                                    (!(item.reaction instanceof Feedback) || params.shouldIncludeFeedbacks()) &&
                                    (!(item.reaction instanceof Coupon) || params.shouldIncludeCoupons());
                        }
                    });
                }

                view.hideRefreshing();

                if (itemList.isEmpty()) {
                    view.showEmptyLayout();
                } else {
                    view.showNotificationHistory(itemList);
                    view.hideEmptyLayout();
                }
            }

            @Override
            public void onError(String error) {
                view.showNotificationHistory(Collections.<HistoryItem>emptyList());
                view.showEmptyLayout();
                view.hideRefreshing();
                view.showRefreshError(R.string.nearit_ui_history_error_message);
            }
        });
    }
}
