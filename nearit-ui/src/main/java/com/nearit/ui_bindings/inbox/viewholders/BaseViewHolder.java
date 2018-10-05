package com.nearit.ui_bindings.inbox.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearit.ui_bindings.inbox.NotificationsAdapter;

import it.near.sdk.recipes.inbox.model.HistoryItem;
import it.near.sdk.recipes.models.ReactionBundle;

public abstract class BaseViewHolder<T extends ReactionBundle> extends RecyclerView.ViewHolder {

    HistoryItem item;
    private final NotificationsAdapter.NotificationReadListener readListener;

    BaseViewHolder(final View itemView, NotificationsAdapter.NotificationReadListener readListener) {
        super(itemView);
        this.readListener = readListener;
    }

    BaseViewHolder(LayoutInflater inflater, int res, ViewGroup parent, NotificationsAdapter.NotificationReadListener readListener) {
        super(inflater.inflate(res, parent, false));
        this.readListener = readListener;
    }

    public void setItem(HistoryItem item) {
        this.item = item;
        readListener.notificationRead(item);
        bindToView((T) item.reaction);
    }

    protected abstract void bindToView(T element);
}
