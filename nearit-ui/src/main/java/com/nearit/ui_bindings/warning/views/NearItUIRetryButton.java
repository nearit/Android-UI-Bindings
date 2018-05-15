package com.nearit.ui_bindings.warning.views;

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

public class NearItUIRetryButton extends RelativeLayout {

    private TextView buttonTextView;
    private String buttonText;

    public NearItUIRetryButton(Context context) {
        super(context);
        init();
    }

    public NearItUIRetryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttrs(attrs);
        init();
    }

    public NearItUIRetryButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
            buttonText = a.getString(R.styleable.NearItUIButtonView_genericButtonText);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_warning_dialog_retry_button, this);
        buttonTextView = findViewById(R.id.warning_retry_button_text);
    }

    private void setText(String text) {
        this.buttonText = text;
        invalidate();
        requestLayout();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        buttonTextView.setText(buttonText);
    }
}
