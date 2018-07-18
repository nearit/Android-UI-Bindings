package com.nearit.ui_bindings.permissions.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
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
    private TextView label;
    private ImageView face;

    private String buttonText;
    private String labelText;
    private int originalIconRes;
    private int iconRes;
    private int faceRes;

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
            faceRes = a.getResourceId(R.styleable.NearItUIPermissionButtonView_faceRes, NO_ICON);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_permission_button, this);
        icon = findViewById(R.id.permission_button_icon);
        text = findViewById(R.id.permission_button_text);
        label = findViewById(R.id.permission_button_label);
        face = findViewById(R.id.permission_button_face);
    }

    private void setFace(int iconRes) {
        this.faceRes = iconRes;
        face.setImageDrawable(
                ResourcesCompat.getDrawable(getResources(), iconRes, null)
        );
        invalidate();
        requestLayout();
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

    public void setText(String buttonText) {
        this.buttonText = buttonText;
        invalidate();
        requestLayout();
    }

    public void setWorriedLabel(String labelText) {
        this.labelText = labelText;
        this.label.setText(labelText);
        this.label.setVisibility(VISIBLE);
        this.label.setTextColor(getResources().getColor(R.color.nearit_ui_worried_color));
        invalidate();
        requestLayout();
    }

    public void setSadLabel(String labelText) {
        this.labelText = labelText;
        this.label.setText(labelText);
        this.label.setVisibility(VISIBLE);
        this.label.setTextColor(getResources().getColor(R.color.nearit_ui_sad_color));
        invalidate();
        requestLayout();
    }

    public void hideLabel() {
        this.label.setVisibility(GONE);
    }

    public void setHappy() {
        this.setIcon(R.drawable.ic_nearit_ui_check_black);
        this.setFace(R.drawable.ic_nearit_ui_happy_green);
        this.setOnClickListener(null);
        this.setEnabled(false);
        this.setActivated(true);
    }

    public void setWorried() {
        this.resetIcon();
        this.setFace(R.drawable.ic_nearit_ui_worried_yellow);
        this.setEnabled(true);
        this.setActivated(false);
    }

    public void setSad() {
        this.resetIcon();
        this.setFace(R.drawable.ic_nearit_ui_sad_red);
        this.setEnabled(true);
        this.setActivated(false);
    }

    public void setChecked() {
        this.setIcon(R.drawable.ic_nearit_ui_check);
        this.setOnClickListener(null);
        this.setEnabled(false);
        this.setActivated(true);
    }

    public void resetState() {
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
