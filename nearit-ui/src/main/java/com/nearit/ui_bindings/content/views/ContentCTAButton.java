package com.nearit.ui_bindings.content.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.R;

/**
 * Created by Federico Boschini on 22/09/17.
 */

public class ContentCTAButton extends RelativeLayout {

    private TextView buttonTextView;
    private String buttonLabel;

    public ContentCTAButton(Context context) {
        super(context);
        init();
    }

    public ContentCTAButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContentCTAButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_cta_button, this);
        buttonTextView = (TextView) findViewById(R.id.button_label);
    }

    public void setText(String buttonLabel) {
        this.buttonLabel = buttonLabel;
        invalidate();
        requestLayout();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        buttonTextView.setText(buttonLabel);
    }

}
