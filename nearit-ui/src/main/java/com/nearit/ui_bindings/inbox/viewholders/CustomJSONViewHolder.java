package com.nearit.ui_bindings.inbox.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.inbox.NotificationsAdapter;
import com.nearit.ui_bindings.inbox.customviews.NearITNotificationCardButton;
import com.nearit.ui_bindings.inbox.customviews.NearITNotificationCardLayout;

import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;

public class CustomJSONViewHolder extends BaseViewHolder<CustomJSON> {

    public static final int VIEWTYPE = 4;

    private final NearITNotificationCardButton button;
    private final NearITNotificationCardLayout layout;

    public CustomJSONViewHolder(LayoutInflater inflater, ViewGroup parent, final NotificationsAdapter.NotificationAdapterListener listener, NotificationsAdapter.NotificationReadListener readListener) {
        super(inflater.inflate(R.layout.nearit_ui_notification_customjson_item, parent, false), readListener);
        layout = itemView.findViewById(R.id.layout);
        button = itemView.findViewById(R.id.detail_button);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.read = true;
                layout.setMessageUnread(false);
                button.setRead();
                listener.onNotificationTap(item);
            }
        });
    }

    @Override
    public void bindToView(CustomJSON content) {
        layout.setTimestamp(item.timestamp);
        layout.setNotification(content.notificationMessage);
        layout.setMessageUnread(!item.read);
        if (item.read) {
            button.setRead();
        } else {
            button.setUnread();
        }
    }
}
