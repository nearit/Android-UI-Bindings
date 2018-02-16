package com.nearit.ui_bindings.inbox;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.inbox.viewholders.BaseViewHolder;
import com.nearit.ui_bindings.inbox.viewholders.ContentNotificationViewHolder;
import com.nearit.ui_bindings.inbox.viewholders.FeedbackViewHolder;
import com.nearit.ui_bindings.inbox.viewholders.SimpleNotificationViewHolder;

import java.util.Collections;
import java.util.List;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.recipes.inbox.model.InboxItem;

public class InboxAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private LayoutInflater inflater;
    private InboxAdapterListener inboxAdapterListener;

    private List<InboxItem> items = Collections.emptyList();

    public InboxAdapter(LayoutInflater inflater, InboxAdapterListener inboxAdapterListener) {
        this.inflater = inflater;
        this.inboxAdapterListener = inboxAdapterListener;
    }

    public void updateItems(List<InboxItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        InboxItem item = items.get(position);
        if (item.reaction instanceof SimpleNotification) {
            return SimpleNotificationViewHolder.VIEWTYPE;
        } else if (item.reaction instanceof Content) {
            return ContentNotificationViewHolder.VIEWTYPE;
        } else if (item.reaction instanceof Feedback) {
            return FeedbackViewHolder.VIEWTYPE;
        } else if (item.reaction instanceof Coupon) {
            return SimpleNotificationViewHolder.VIEWTYPE;
        } else if (item.reaction instanceof CustomJSON) {
            return SimpleNotificationViewHolder.VIEWTYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SimpleNotificationViewHolder.VIEWTYPE:
                return new SimpleNotificationViewHolder(inflater, parent);
            case ContentNotificationViewHolder.VIEWTYPE:
                return new ContentNotificationViewHolder(inflater, parent, inboxAdapterListener);
            case FeedbackViewHolder.VIEWTYPE:
                return new FeedbackViewHolder(inflater, parent, inboxAdapterListener);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface InboxAdapterListener {
        void onInboxItemTap(InboxItem itemList);
    }
}
