package com.nearit.ui_bindings.inbox.viewholders;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.inbox.customviews.InboxCardLayout;

import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;

public class SimpleNotificationViewHolder extends BaseViewHolder<SimpleNotification> {

    public static final int VIEWTYPE = 1;

    InboxCardLayout layout;

    public SimpleNotificationViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, R.layout.nearit_ui_inbox_simple_item, parent);
        layout = itemView.findViewById(R.id.layout);
    }

    @Override
    public void bindToView(SimpleNotification simpleNotification) {
        layout.setTimestamp(item.timestamp);
        layout.setNotification(simpleNotification.notificationMessage);
        layout.setMessageUnread(!item.read);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout.setElevation(0F);
        }
    }
}
