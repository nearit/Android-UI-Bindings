package com.nearit.ui_bindings.inbox;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.R;

import java.util.Collections;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.recipes.inbox.model.HistoryItem;

public class NearITNotificationHistoryFragment extends Fragment implements NotificationHistoryContract.NotificationHistoryView, NotificationsAdapter.NotificationAdapterListener, NotificationsAdapter.NotificationReadListener {

    private static final String EXTRAS = "extras";
    public static final String EMPTY_LIST_ARG = "nearit_inbox_empty_layout";
    public static final String INCLUDE_CUSTOM_JSON_ARG = "nearit_inbox_include_json";
    public static final String INCLUDE_FEEDBACKS_ARG = "nearit_inbox_include_feedback";
    public static final String INCLUDE_COUPONS_ARG = "nearit_inbox_include_coupons";
    public static final String ACTIVITY_TITLE_ARG = "nearit_inbox_activity_title";
    private NotificationHistoryContract.NotificationHistoryPresenter presenter;
    private TextView emptyListText;
    private RelativeLayout emptyListContainer;
    private NotificationsAdapter notificationsAdapter;
    @Nullable
    private SwipeRefreshLayout swipeToRefreshLayout;

    @LayoutRes
    private int customEmptyListLayoutRef = 0;
    private View customEmptyListView;
    private NotificationHistoryExtraParams extras;

    public static NearITNotificationHistoryFragment newInstance(@Nullable NotificationHistoryExtraParams extras) {
        NearITNotificationHistoryFragment fragment = new NearITNotificationHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRAS, extras);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getParams(getArguments());
        customEmptyListLayoutRef = extras.getEmptyListCustomLayout();

        new NotificationHistoryPresenterImpl(NearItManager.getInstance(), this, extras);
    }

    private NotificationHistoryExtraParams getParams(@Nullable Bundle arguments) {
        int emptyLayout = NotificationHistoryExtraParams.EMPTY_LIST_CUSTOM_LAYOUT_DEFAULT;
        boolean includeJson = NotificationHistoryExtraParams.INCLUDE_CUSTOM_JSON_DEFAULT;
        boolean includeFeedback = NotificationHistoryExtraParams.INCLUDE_FEEDBACKS_DEFAULT;
        boolean includeCoupon = NotificationHistoryExtraParams.INCLUDE_COUPONS_DEFAULT;
        String title = NotificationHistoryExtraParams.ACTIVITY_TITLE_DEFAULT;
        if (arguments != null) {
            if (arguments.containsKey(EXTRAS)) {
                return arguments.getParcelable(EXTRAS);
            } else {
                emptyLayout = arguments.getInt(EMPTY_LIST_ARG, NotificationHistoryExtraParams.EMPTY_LIST_CUSTOM_LAYOUT_DEFAULT);
                includeJson = arguments.getBoolean(INCLUDE_CUSTOM_JSON_ARG, NotificationHistoryExtraParams.INCLUDE_CUSTOM_JSON_DEFAULT);
                includeFeedback = arguments.getBoolean(INCLUDE_FEEDBACKS_ARG, NotificationHistoryExtraParams.INCLUDE_FEEDBACKS_DEFAULT);
                includeCoupon = arguments.getBoolean(INCLUDE_COUPONS_ARG, NotificationHistoryExtraParams.INCLUDE_COUPONS_DEFAULT);
                title = arguments.getString(ACTIVITY_TITLE_ARG, NotificationHistoryExtraParams.ACTIVITY_TITLE_DEFAULT);
            }
        }
        return new NotificationHistoryExtraParams(
                emptyLayout,
                includeJson,
                includeFeedback,
                includeCoupon,
                title
        );
    }

    @Override
    public void injectPresenter(@NonNull NotificationHistoryContract.NotificationHistoryPresenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_notification_history, container, false);

        emptyListText = rootView.findViewById(R.id.empty_notification_history_text);
        emptyListContainer = rootView.findViewById(R.id.empty_layout);
        RecyclerView recyclerView = rootView.findViewById(R.id.notification_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationsAdapter = new NotificationsAdapter(inflater, this, this);
        recyclerView.setAdapter(notificationsAdapter);

        swipeToRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        if (swipeToRefreshLayout != null) {
            swipeToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    presenter.requestRefresh();
                }
            });
        }

        if (customEmptyListLayoutRef != 0) {
            customEmptyListView = inflater.inflate(customEmptyListLayoutRef, emptyListContainer, false);
            emptyListContainer.addView(customEmptyListView);
        } else {
            emptyListText.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRAS, extras);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void showNotificationHistory(List<HistoryItem> itemList) {
        if (swipeToRefreshLayout != null) {
            swipeToRefreshLayout.setRefreshing(false);
        }
        notificationsAdapter.updateItems(itemList);
    }

    @Override
    public void showEmptyLayout() {
        if (swipeToRefreshLayout != null) {
            swipeToRefreshLayout.setRefreshing(false);
        }
        showNotificationHistory(Collections.<HistoryItem>emptyList());
        if (customEmptyListView != null) {
            customEmptyListView.setVisibility(View.VISIBLE);
            emptyListContainer.setVisibility(View.VISIBLE);
        }
        emptyListText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyLayout() {
        if (swipeToRefreshLayout != null) {
            swipeToRefreshLayout.setRefreshing(false);
        }
        if (customEmptyListView != null) {
            customEmptyListView.setVisibility(View.GONE);
            emptyListContainer.setVisibility(View.GONE);
        }
        emptyListText.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshing() {
        if (swipeToRefreshLayout != null) {
            swipeToRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideRefreshing() {
        if (swipeToRefreshLayout != null) {
            swipeToRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showRefreshError(int res) {
        if (swipeToRefreshLayout != null) {
            swipeToRefreshLayout.setRefreshing(false);
        }
        Context context = getActivity();
        if (context != null) {
            try {
                String errorMessage = context.getResources().getString(res);
                showRefreshError(errorMessage);
            } catch (Resources.NotFoundException e) {
                showRefreshError("We could not get your notifications. Please try again later.");
            }
        }
    }

    @Override
    public void showRefreshError(String error) {
        if (swipeToRefreshLayout != null) {
            swipeToRefreshLayout.setRefreshing(false);
        }
        notificationsAdapter.updateItems(Collections.<HistoryItem>emptyList());
        if (getActivity() != null) {
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNotificationTap(HistoryItem itemList) {
        presenter.itemClicked(itemList);
    }

    @Override
    public void openDetail(HistoryItem item) {
        NearITUIBindings.onNewContent(getActivity(), item.reaction, item.trackingInfo);
    }

    @Override
    public void notificationRead(HistoryItem item) {
        presenter.onNotificationRead(item);
    }

    public void refreshList() {
        if (!isAdded()) return;

        if (swipeToRefreshLayout != null) {
            swipeToRefreshLayout.setRefreshing(true);
        }
        presenter.requestRefresh();
    }
}
