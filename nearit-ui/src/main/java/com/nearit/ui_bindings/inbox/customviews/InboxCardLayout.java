package com.nearit.ui_bindings.inbox.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.nearit.ui_bindings.R;

public class InboxCardLayout extends RelativeLayout {

    private static final int[] STATE_MESSAGE_UNREAD = {R.attr.state_message_unread};

    private boolean messageUnread;

    public InboxCardLayout(Context context) {
        super(context);
    }

    public InboxCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InboxCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    }
}
