package com.nearit.ui_bindings.inbox.viewholders;

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

    Button button;
    InboxCardLayout layout;

    public ContentNotificationViewHolder(LayoutInflater inflater, ViewGroup parent, final InboxAdapter.InboxAdapterListener listener) {
        super(inflater.inflate(R.layout.nearit_ui_inbox_content_item, parent, false));
        layout = itemView.findViewById(R.id.layout);
        button = itemView.findViewById(R.id.detail_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.read = true;
                layout.setMessageUnread(false);
                listener.onInboxItemTap(item);
            }
        });
    }

    @Override
    public void bindToView(Content content) {
        layout.setTimestamp(item.timestamp);
        layout.setNotification(content.notificationMessage);
        layout.setMessageUnread(!item.read);
    }
}
