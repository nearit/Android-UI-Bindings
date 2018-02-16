package com.nearit.ui_bindings.inbox.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.inbox.InboxAdapter;

import it.near.sdk.reactions.contentplugin.model.Content;

public class ContentNotificationViewHolder extends BaseViewHolder<Content> {

    public static final int VIEWTYPE = 2;

    TextView timeTV, notificationTV;
    Button button;

    public ContentNotificationViewHolder(LayoutInflater inflater, ViewGroup parent, final InboxAdapter.InboxAdapterListener listener) {
        super(inflater.inflate(R.layout.nearit_ui_inbox_content_item, parent, false));
        timeTV = itemView.findViewById(R.id.timestampTextView);
        notificationTV = itemView.findViewById(R.id.notificationTextView);
        button = itemView.findViewById(R.id.detail_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onInboxItemTap(item);
            }
        });
    }


    @Override
    public void bindToView(Content content) {
        timeTV.setText(getTimestampLabel(item.timestamp));
        notificationTV.setText(content.notificationMessage);
        setReadBackground(item.read);
    }

    private void setReadBackground(boolean read) {
        // TODO background
    }
}
