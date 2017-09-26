package com.nearit.ui_bindings.permissions.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.R;

/**
 * Created by Federico Boschini on 26/09/17.
 */

public class PermissionSnackbarButton extends RelativeLayout {

    private String buttonText;

    private TextView textView;

    final Context context;

    public PermissionSnackbarButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PermissionSnackbarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        obtainAttrs(attrs);
        init();
    }

    public PermissionSnackbarButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        obtainAttrs(attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_permission_snackbar_button, this);
        textView = (TextView) findViewById(R.id.snackbar_button_text);
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NearItUISnackbar,
                0, 0);
        try {
            buttonText = a.getString(R.styleable.NearItUISnackbar_snackbarButtonText);
        } finally {
            a.recycle();
        }
    }

    public void setButtonText(String text) {
        textView.setText(text);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        textView.setText(buttonText);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
