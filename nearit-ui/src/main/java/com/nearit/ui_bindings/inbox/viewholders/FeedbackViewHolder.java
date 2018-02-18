package com.nearit.ui_bindings.inbox.viewholders;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.inbox.InboxAdapter;
import com.nearit.ui_bindings.inbox.customviews.InboxCardLayout;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

public class FeedbackViewHolder extends BaseViewHolder<Feedback> {

    public static final int VIEWTYPE = 3;

    TextView timeTV, notificationTV;
    Button button;
    InboxCardLayout layout;

    public FeedbackViewHolder(LayoutInflater inflater, ViewGroup parent, final InboxAdapter.InboxAdapterListener listener) {
        super(inflater.inflate(R.layout.nearit_ui_inbox_feedback_item, parent, false));
        timeTV = itemView.findViewById(R.id.timestampTextView);
        notificationTV = itemView.findViewById(R.id.notificationTextView);
        layout = itemView.findViewById(R.id.bg_layout);
        button = itemView.findViewById(R.id.detail_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onInboxItemTap(item);
            }
        });
    }

    @Override
    public void bindToView(Feedback feedback) {
        timeTV.setText(getTimestampLabel(item.timestamp));
        notificationTV.setText(feedback.notificationMessage);
        setReadBackground(item.read);
    }

    private void setReadBackground(boolean read) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout.setElevation(read ? 4F : 30F);
            layout.refreshDrawableState();
        }
    }
}
