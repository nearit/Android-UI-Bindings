package com.nearit.ui_bindings.permissions.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.R;

/**
 * @author Federico Boschini
 */

public class PermissionButton extends RelativeLayout {

    private static final int NO_ICON = 0;
    private ImageView icon;
    private TextView text;

    private String buttonText;
    private int originalIconRes;
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
                R.styleable.NearItUIPermissionButtonView,
                0, 0);
        try {
            buttonText = a.getString(R.styleable.NearItUIPermissionButtonView_buttonText);
            originalIconRes = a.getResourceId(R.styleable.NearItUIPermissionButtonView_iconRes, NO_ICON);
            iconRes = a.getResourceId(R.styleable.NearItUIPermissionButtonView_iconRes, NO_ICON);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_permission_button, this);
        icon = findViewById(R.id.permission_button_icon);
        text = findViewById(R.id.permission_button_text);
    }

    private void setIcon(int iconRes) {
        this.iconRes = iconRes;
        invalidate();
        requestLayout();
    }

    private void resetIcon() {
        iconRes = originalIconRes;
        invalidate();
        requestLayout();
    }

    private void setText(String buttonText) {
        this.buttonText = buttonText;
        invalidate();
        requestLayout();
    }

    public void setChecked() {
        this.setIcon(R.drawable.ic_nearit_ui_check);
        this.setOnClickListener(null);
        this.setEnabled(false);
        this.setActivated(true);
    }

    public void setUnchecked() {
        this.resetIcon();
        this.setText(buttonText);
        this.setEnabled(true);
        this.setActivated(false);
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
