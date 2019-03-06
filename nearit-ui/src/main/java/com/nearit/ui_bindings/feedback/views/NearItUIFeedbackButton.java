package com.nearit.ui_bindings.feedback.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.R;

/**
 * @author Federico Boschini
 */

public class NearItUIFeedbackButton extends RelativeLayout {

    private RelativeLayout button;
    private TextView buttonTextView;
    private RelativeLayout spinner;
    private String text;

    public NearItUIFeedbackButton(Context context) {
        super(context);
        init();
    }

    public NearItUIFeedbackButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttrs(attrs);
        init();
    }

    public NearItUIFeedbackButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(attrs);
        init();
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NearItUIButtonView,
                0, 0);
        try {
            text = a.getString(R.styleable.NearItUIButtonView_genericButtonText);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_feedback_button, this);
        buttonTextView = findViewById(R.id.button_text);
        button = findViewById(R.id.custom_button);
        spinner = findViewById(R.id.spinner_container);
    }

    private void setText(String text) {
        this.text = text;
        invalidate();
        requestLayout();
    }

    public void setChecked() {
        button.setVisibility(VISIBLE);
        spinner.setVisibility(GONE);
        button.setActivated(true);
        setText(getContext().getString(R.string.nearit_ui_feedback_send_button_retry));
    }

    public void setUnchecked() {
        button.setVisibility(VISIBLE);
        spinner.setVisibility(GONE);
        button.setActivated(false);
        setText(getContext().getString(R.string.nearit_ui_feedback_send_button_default_text));
    }

    public boolean isChecked() {
        return button.isActivated();
    }

    public void setLoading() {
        button.setVisibility(GONE);
        spinner.setVisibility(VISIBLE);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        buttonTextView.setText(text);
    }
}
