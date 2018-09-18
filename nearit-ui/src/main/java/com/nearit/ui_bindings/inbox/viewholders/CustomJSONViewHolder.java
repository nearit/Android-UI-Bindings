package com.nearit.ui_bindings.inbox.viewholders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.inbox.NotificationsAdapter;
import com.nearit.ui_bindings.inbox.customviews.NearITNotificationCardLayout;

import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;

public class CustomJSONViewHolder extends BaseViewHolder<CustomJSON> {

    public static final int VIEWTYPE = 4;

    private final Button button;
    private final NearITNotificationCardLayout layout;

    public CustomJSONViewHolder(LayoutInflater inflater, ViewGroup parent, final NotificationsAdapter.InboxAdapterListener listener, NotificationsAdapter.NotificationReadListener readListener) {
        super(inflater.inflate(R.layout.nearit_ui_notification_customjson_item, parent, false), readListener);
        layout = itemView.findViewById(R.id.layout);
        button = itemView.findViewById(R.id.detail_button);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.read = true;
                layout.setMessageUnread(false);
                button.setTypeface(null, Typeface.NORMAL);
                listener.onInboxItemTap(item);
            }
        });
    }

    @Override
    public void bindToView(CustomJSON content) {
        layout.setTimestamp(item.timestamp);
        layout.setNotification(content.notificationMessage);
        layout.setMessageUnread(!item.read);
        button.setTypeface(null,
                item.read ? Typeface.NORMAL : Typeface.BOLD);
        Context context = itemView.getContext();
        button.setTextColor(context.getResources().getColor(item.read ?
                R.color.nearit_ui_notification_card_text_read_color :
                R.color.nearit_ui_notification_card_text_unread_color)
        );
    }
}
