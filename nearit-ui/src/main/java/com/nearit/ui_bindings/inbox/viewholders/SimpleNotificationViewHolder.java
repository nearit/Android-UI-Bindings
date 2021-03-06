package com.nearit.ui_bindings.inbox.viewholders;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.inbox.NotificationsAdapter;
import com.nearit.ui_bindings.inbox.customviews.NearITNotificationCardLayout;

import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;

public class SimpleNotificationViewHolder extends BaseViewHolder<SimpleNotification> {

    public static final int VIEWTYPE = 1;

    private final NearITNotificationCardLayout layout;

    public SimpleNotificationViewHolder(LayoutInflater inflater, ViewGroup parent, NotificationsAdapter.NotificationReadListener readListener) {
        super(inflater, R.layout.nearit_ui_notification_simple_item, parent, readListener);
        layout = itemView.findViewById(R.id.layout);
    }

    @Override
    public void bindToView(SimpleNotification simpleNotification) {
        layout.setTimestamp(item.timestamp);
        layout.setNotification(simpleNotification.notificationMessage);
        // is always read
        // layout.setMessageUnread(!item.read);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout.setElevation(0F);
        }
    }
}
