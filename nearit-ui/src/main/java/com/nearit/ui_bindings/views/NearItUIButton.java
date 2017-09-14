package com.nearit.ui_bindings.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.R;

/**
 * Created by Federico Boschini on 14/09/17.
 */

public class NearItUIButton extends RelativeLayout {

    private TextView text;

    private String buttonText;

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
            buttonText = a.getString(R.styleable.NearItUIButtonView_genericButtonText);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_button, this);
        text = (TextView) findViewById(R.id.button_text);
    }

    public void setText(String buttonText) {
        this.buttonText = buttonText;
        invalidate();
        requestLayout();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        text.setText(buttonText);
    }
}
