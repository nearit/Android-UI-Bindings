package com.nearit.ui_bindings.inbox.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.R;

/**
 * @author Federico Boschini
 */
public class NearITNotificationCardButton extends RelativeLayout {

    private TextView buttonTextView;
    private String text;

    public NearITNotificationCardButton(Context context) {
        super(context);
        init();
    }

    public NearITNotificationCardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttrs(attrs);
        init();
    }

    public NearITNotificationCardButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(attrs);
        init();
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NearItUINotificationHistoryButtonView,
                0, 0);
        try {
            text = a.getString(R.styleable.NearItUINotificationHistoryButtonView_text);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_notification_card_button, this);
        buttonTextView = findViewById(R.id.nearit_ui_detail_button_text);
    }

    public void setRead() {
        buttonTextView.setTypeface(null, Typeface.NORMAL);
        buttonTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_notification_card_text_read_color));
    }

    public void setUnread() {
        buttonTextView.setTypeface(null, Typeface.BOLD);
        buttonTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_notification_card_text_unread_color));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        buttonTextView.setText(text);
    }

}
