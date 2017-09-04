package com.nearit.ui_bindings.permissions.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.R;

/**
 * Created by Federico Boschini on 31/08/17.
 */

public class PermissionButton extends RelativeLayout {

    public static final int NO_ICON = 0;
    private ImageView icon;
    private TextView text;

    private String buttonText;
    private int iconRes;

    public PermissionButton(Context context) {
        super(context);
        init();
    }

    public PermissionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttrs(attrs);
        init();
    }

    public PermissionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(attrs);
        init();
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PermissionButtonView,
                0, 0);
        try {
            buttonText = a.getString(R.styleable.PermissionButtonView_buttonText);
            iconRes = a.getResourceId(R.styleable.PermissionButtonView_iconRes, NO_ICON);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.layout_permission_button, this);
        icon = (ImageView) findViewById(R.id.permission_button_icon);
        text = (TextView) findViewById(R.id.permission_button_text);
    }

    public void setIcon(int iconRes) {
        this.iconRes = iconRes;
        invalidate();
        requestLayout();
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
        if (iconRes != NO_ICON) {
            icon.setImageDrawable(
                    ResourcesCompat.getDrawable(getResources(), iconRes, null)
            );
        }
    }
}