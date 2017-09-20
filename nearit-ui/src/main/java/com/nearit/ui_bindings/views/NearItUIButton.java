package com.nearit.ui_bindings.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.R;

/**
 * Created by Federico Boschini on 14/09/17.
 */

public class NearItUIButton extends RelativeLayout {

    private RelativeLayout button;
    private TextView buttonTextView;
    private RelativeLayout spinner;
    private String text;

    public NearItUIButton(Context context) {
        super(context);
        init();
    }

    public NearItUIButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttrs(attrs);
        init();
    }

    public NearItUIButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
        inflate(getContext(), R.layout.nearit_ui_layout_button, this);
        buttonTextView = (TextView) findViewById(R.id.button_text);
        button = (RelativeLayout) findViewById(R.id.custom_button);
        spinner = (RelativeLayout) findViewById(R.id.spinner_container);
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
        requestLayout();
    }

    public void setChecked() {
        button.setVisibility(VISIBLE);
        spinner.setVisibility(GONE);
        button.setActivated(true);
    }

    public void setUnchecked() {
        button.setVisibility(VISIBLE);
        spinner.setVisibility(GONE);
        button.setActivated(false);
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
