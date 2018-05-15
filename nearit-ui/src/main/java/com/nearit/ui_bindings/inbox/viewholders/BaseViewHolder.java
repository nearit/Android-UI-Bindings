package com.nearit.ui_bindings.inbox.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearit.ui_bindings.inbox.InboxAdapter;

import it.near.sdk.recipes.inbox.model.InboxItem;
import it.near.sdk.recipes.models.ReactionBundle;

public abstract class BaseViewHolder<T extends ReactionBundle> extends RecyclerView.ViewHolder {

    InboxItem item;
    private final InboxAdapter.NotificationReadListener readListener;

    BaseViewHolder(final View itemView, InboxAdapter.NotificationReadListener readListener) {
        super(itemView);
        this.readListener = readListener;
    }

    BaseViewHolder(LayoutInflater inflater, int res, ViewGroup parent, InboxAdapter.NotificationReadListener readListener) {
        super(inflater.inflate(res, parent, false));
        this.readListener = readListener;
    }

    public void setItem(InboxItem item) {
        this.item = item;
        readListener.notificationRead(item);
        bindToView((T) item.reaction);
    }

    public abstract void bindToView(T element);
}
