package com.nearit.ui_bindings.inbox.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nearit.ui_bindings.R;

import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;

public class SimpleNotificationViewHolder extends BaseViewHolder<SimpleNotification> {

    public static final int VIEWTYPE = 1;

    TextView timeTV, notificationTV;

    public SimpleNotificationViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, R.layout.nearit_ui_inbox_simple_item, parent);
        timeTV = itemView.findViewById(R.id.timestampTextView);
        notificationTV = itemView.findViewById(R.id.notificationTextView);
    }

    @Override
    public void bindToView(SimpleNotification simpleNotification) {
        timeTV.setText(getTimestampLabel(item.timestamp));
        notificationTV.setText(simpleNotification.getNotificationMessage());
        setReadBackground(item.read);
    }

    private void setReadBackground(boolean read) {
        // TODO read background
    }


}
