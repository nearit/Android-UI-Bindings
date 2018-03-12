package com.nearit.ui_bindings.inbox.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InboxCardLayout extends RelativeLayout {

    private static final int[] STATE_MESSAGE_UNREAD = {R.attr.state_message_unread};

    private boolean messageUnread;
    private TextView timestampTV, notificationTV;

    public InboxCardLayout(Context context) {
        super(context);
        init();
    }

    public InboxCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InboxCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_inbox_card_layout, this);
        timestampTV = findViewById(R.id.timestampTextView);
        notificationTV = findViewById(R.id.notificationTextView);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InboxCardLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        // If the message is unread then we merge our custom message unread state into
        // the existing drawable state before returning it.
        if (messageUnread) {
            // We are going to add 1 extra state.
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

            mergeDrawableStates(drawableState, STATE_MESSAGE_UNREAD);
            return drawableState;
        } else {
            return super.onCreateDrawableState(extraSpace);
        }
    }

    public void setMessageUnread(boolean messageUnread) {
        if (this.messageUnread != messageUnread) {
            this.messageUnread = messageUnread;

            // Refresh the drawable state so that it includes the message unread
            // state if required.
            refreshDrawableState();
        }
        
        timestampTV.setTypeface(timestampTV.getTypeface(),
                messageUnread ? Typeface.BOLD_ITALIC : Typeface.ITALIC);
        notificationTV.setTypeface(null,
                messageUnread ? Typeface.BOLD : Typeface.NORMAL);
    }

    public void setTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        Date date = new Date(timestamp * 1000);
        String timestampString = sdf.format(date);
        if (timestampString != null) {
            timestampTV.setText(timestampString);
        }
    }

    public void setNotification(String notif) {
        notificationTV.setText(notif);
    }
}
