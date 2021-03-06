package com.nearit.ui_bindings.inbox;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nearit.ui_bindings.inbox.viewholders.BaseViewHolder;
import com.nearit.ui_bindings.inbox.viewholders.ContentNotificationViewHolder;
import com.nearit.ui_bindings.inbox.viewholders.CouponNotificationViewHolder;
import com.nearit.ui_bindings.inbox.viewholders.CustomJSONViewHolder;
import com.nearit.ui_bindings.inbox.viewholders.FeedbackViewHolder;
import com.nearit.ui_bindings.inbox.viewholders.SimpleNotificationViewHolder;

import java.util.Collections;
import java.util.List;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.recipes.inbox.model.HistoryItem;

public class NotificationsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final NotificationReadListener readListener;
    private final LayoutInflater inflater;
    private final NotificationAdapterListener notificationAdapterListener;

    private List<HistoryItem> items = Collections.emptyList();

    NotificationsAdapter(LayoutInflater inflater, NotificationAdapterListener notificationAdapterListener, NotificationReadListener readListener) {
        this.inflater = inflater;
        this.notificationAdapterListener = notificationAdapterListener;
        this.readListener = readListener;
    }

    public void updateItems(List<HistoryItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        HistoryItem item = items.get(position);
        if (item.reaction instanceof SimpleNotification) {
            return SimpleNotificationViewHolder.VIEWTYPE;
        } else if (item.reaction instanceof Content) {
            return ContentNotificationViewHolder.VIEWTYPE;
        } else if (item.reaction instanceof Feedback) {
            return FeedbackViewHolder.VIEWTYPE;
        } else if (item.reaction instanceof CustomJSON) {
            return CustomJSONViewHolder.VIEWTYPE;
        } else if (item.reaction instanceof Coupon) {
            return CouponNotificationViewHolder.VIEWTYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case SimpleNotificationViewHolder.VIEWTYPE:
                return new SimpleNotificationViewHolder(inflater, parent, readListener);
            case ContentNotificationViewHolder.VIEWTYPE:
                return new ContentNotificationViewHolder(inflater, parent, notificationAdapterListener, readListener);
            case FeedbackViewHolder.VIEWTYPE:
                return new FeedbackViewHolder(inflater, parent, notificationAdapterListener, readListener);
            case CustomJSONViewHolder.VIEWTYPE:
                return new CustomJSONViewHolder(inflater, parent, notificationAdapterListener, readListener);
            case CouponNotificationViewHolder.VIEWTYPE:
                return new CouponNotificationViewHolder(inflater, parent, notificationAdapterListener, readListener);
            default:
                return new SimpleNotificationViewHolder(inflater, parent, readListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface NotificationAdapterListener {
        void onNotificationTap(HistoryItem itemList);
    }

    public interface NotificationReadListener {
        void notificationRead(HistoryItem item);
    }
}
