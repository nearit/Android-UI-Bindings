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

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

public class FeedbackViewHolder extends BaseViewHolder<Feedback> {

    public static final int VIEWTYPE = 3;

    private final Button button;
    private final InboxCardLayout layout;

    public FeedbackViewHolder(LayoutInflater inflater, ViewGroup parent, final InboxAdapter.InboxAdapterListener listener, InboxAdapter.NotificationReadListener readListener) {
        super(inflater.inflate(R.layout.nearit_ui_inbox_feedback_item, parent, false), readListener);
        layout = itemView.findViewById(R.id.bg_layout);
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
    public void bindToView(Feedback feedback) {
        layout.setTimestamp(item.timestamp);
        layout.setNotification(feedback.notificationMessage);
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
