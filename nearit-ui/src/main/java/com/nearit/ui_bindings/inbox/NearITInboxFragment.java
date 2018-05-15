package com.nearit.ui_bindings.inbox;

import android.os.Bundle;
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
import it.near.sdk.recipes.inbox.model.InboxItem;

public class NearITInboxFragment extends Fragment implements InboxContract.InboxView, InboxAdapter.InboxAdapterListener, InboxAdapter.NotificationReadListener {

    private static final String EXTRAS = "extras";
    private InboxContract.InboxPresenter presenter;
    private TextView noInboxText;
    private RelativeLayout noInboxContainer;
    private InboxAdapter inboxAdapter;
    private SwipeRefreshLayout swipeToRefreshLayout;

    private int customNoInboxLayoutRef = 0;
    private View customNoInbox;
    private InboxListExtraParams extras;

    public static NearITInboxFragment newInstance(@Nullable InboxListExtraParams extras) {
        NearITInboxFragment fragment = new NearITInboxFragment();
        Bundle bundle =  new Bundle();
        bundle.putParcelable(EXTRAS, extras);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void setPresenter(InboxContract.InboxPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            extras = getArguments().getParcelable(EXTRAS);
            if (extras != null) {
                customNoInboxLayoutRef = extras.getNoInboxCustomLayout();
            }
        }
        loadPresenter();
    }

    private void loadPresenter() {
        InboxContract.InboxPresenter presenter = new InboxPresenterImpl(NearItManager.getInstance(), this, extras);
        setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_inbox, container, false);

        noInboxText = rootView.findViewById(R.id.no_inbox_text);
        noInboxContainer = rootView.findViewById(R.id.empty_layout);
        RecyclerView recyclerView = rootView.findViewById(R.id.inbox_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        inboxAdapter = new InboxAdapter(inflater, this, this);
        recyclerView.setAdapter(inboxAdapter);

        swipeToRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        swipeToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestRefresh();
            }
        });

        if (customNoInboxLayoutRef != 0) {
            customNoInbox = inflater.inflate(customNoInboxLayoutRef, noInboxContainer, false);
            noInboxContainer.addView(customNoInbox);
        } else {
            noInboxText.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRAS, extras);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void showInboxItems(List<InboxItem> itemList) {
        swipeToRefreshLayout.setRefreshing(false);
        inboxAdapter.updateItems(itemList);
    }

    @Override
    public void showEmptyLayout() {
        showInboxItems(Collections.<InboxItem>emptyList());
        if (customNoInbox != null) {
            customNoInbox.setVisibility(View.VISIBLE);
            noInboxContainer.setVisibility(View.VISIBLE);
        }
        noInboxText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyLayout() {
        if (customNoInbox != null) {
            customNoInbox.setVisibility(View.GONE);
            noInboxContainer.setVisibility(View.GONE);
        }
        noInboxText.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshError(String error) {
        swipeToRefreshLayout.setRefreshing(false);
        inboxAdapter.updateItems(Collections.<InboxItem>emptyList());
        Toast.makeText(getActivity(), "Error downloading inbox", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInboxItemTap(InboxItem itemList) {
        presenter.itemClicked(itemList);
    }

    @Override
    public void openDetail(InboxItem inboxItem) {
        NearITUIBindings.onNewContent(getActivity(), inboxItem.reaction, inboxItem.trackingInfo);
    }

    @Override
    public void notificationRead(InboxItem item) {
        presenter.onNotificationRead(item);
    }
}
