package com.nearit.ui_bindings.inbox.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.near.sdk.recipes.inbox.model.InboxItem;
import it.near.sdk.recipes.models.ReactionBundle;

public abstract class BaseViewHolder<T extends ReactionBundle> extends RecyclerView.ViewHolder {

    InboxItem item;

    public BaseViewHolder(final View itemView) {
        super(itemView);
    }

    public BaseViewHolder(LayoutInflater inflater, int res, ViewGroup parent) {
        super(inflater.inflate(res, parent, false));
    }

    public void setItem(InboxItem item) {
        this.item = item;
        bindToView((T) item.reaction);
    }

    public abstract void bindToView(T item);
}
