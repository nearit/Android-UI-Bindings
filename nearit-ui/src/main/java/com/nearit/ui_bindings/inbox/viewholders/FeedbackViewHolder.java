package com.nearit.ui_bindings.inbox.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.inbox.NotificationsAdapter;
import com.nearit.ui_bindings.inbox.customviews.NearITNotificationCardButton;
import com.nearit.ui_bindings.inbox.customviews.NearITNotificationCardLayout;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

public class FeedbackViewHolder extends BaseViewHolder<Feedback> {

    public static final int VIEWTYPE = 3;

    private final NearITNotificationCardButton button;
    private final NearITNotificationCardLayout layout;

    public FeedbackViewHolder(LayoutInflater inflater, ViewGroup parent, final NotificationsAdapter.NotificationAdapterListener listener, NotificationsAdapter.NotificationReadListener readListener) {
        super(inflater.inflate(R.layout.nearit_ui_notification_feedback_item, parent, false), readListener);
        layout = itemView.findViewById(R.id.bg_layout);
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
    public void bindToView(Feedback feedback) {
        layout.setTimestamp(item.timestamp);
        layout.setNotification(feedback.notificationMessage);
        layout.setMessageUnread(!item.read);
        if (item.read) {
            button.setRead();
        } else {
            button.setUnread();
        }
    }


}
