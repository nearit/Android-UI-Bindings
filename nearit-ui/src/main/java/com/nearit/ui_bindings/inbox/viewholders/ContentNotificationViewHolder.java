package com.nearit.ui_bindings.inbox.viewholders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.inbox.InboxAdapter;
import com.nearit.ui_bindings.inbox.customviews.InboxCardLayout;

import it.near.sdk.reactions.contentplugin.model.Content;

public class ContentNotificationViewHolder extends BaseViewHolder<Content> {

    public static final int VIEWTYPE = 2;

    final Button button;
    final InboxCardLayout layout;

    public ContentNotificationViewHolder(LayoutInflater inflater, ViewGroup parent, final InboxAdapter.InboxAdapterListener listener, InboxAdapter.NotificationReadListener readListener) {
        super(inflater.inflate(R.layout.nearit_ui_inbox_content_item, parent, false), readListener);
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
    public void bindToView(Content content) {
        layout.setTimestamp(item.timestamp);
        layout.setNotification(content.notificationMessage);
        layout.setMessageUnread(!item.read);
        button.setTypeface(null,
                item.read ? Typeface.NORMAL : Typeface.BOLD);
        Context context = itemView.getContext();
        button.setTextColor(context.getResources().getColor(item.read ?
                R.color.nearit_ui_inbox_card_text_read_color :
                R.color.nearit_ui_inbox_card_text_unread_color)
        );
    }
}
