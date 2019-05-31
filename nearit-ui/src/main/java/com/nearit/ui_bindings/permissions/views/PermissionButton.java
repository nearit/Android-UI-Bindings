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

    public static final int NO_ICON = 0;
    private ImageView icon;
    private TextView text;
    private TextView label;
    private ImageView face;

    private String buttonText;
    private int originalIconRes;
    private int iconRes;
    private int sadFaceRes;
    private int happyFaceRes;
    private int worriedFaceRes;

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
            originalIconRes = a.getResourceId(R.styleable.NearItUIPermissionButtonView_permissionIcon, NO_ICON);
            iconRes = a.getResourceId(R.styleable.NearItUIPermissionButtonView_permissionIcon, NO_ICON);
            sadFaceRes = a.getResourceId(R.styleable.NearItUIPermissionButtonView_sadFaceRes, NO_ICON);
            worriedFaceRes = a.getResourceId(R.styleable.NearItUIPermissionButtonView_worriedFaceRes, NO_ICON);
            happyFaceRes = a.getResourceId(R.styleable.NearItUIPermissionButtonView_happyFaceRes, NO_ICON);
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
        face.setImageDrawable(ContextCompat.getDrawable(getContext(), iconRes));
        invalidate();
        requestLayout();
    }

    private void setChecked() {
        this.iconRes = R.drawable.ic_nearit_ui_check_black;
    }

    public void setIcon(int iconRes) {
        this.iconRes = iconRes;
        this.originalIconRes = iconRes;
        invalidate();
        requestLayout();
    }

    public void setSadFaceRes(int sadFaceRes) {
        this.sadFaceRes = sadFaceRes;
        invalidate();
        requestLayout();
    }

    public void setHappyFaceRes(int happyFaceRes) {
        this.happyFaceRes = happyFaceRes;
        invalidate();
        requestLayout();
    }

    public void setWorriedFaceRes(int worriedFaceRes) {
        this.worriedFaceRes = worriedFaceRes;
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
        this.label.setText(labelText);
        this.label.setVisibility(VISIBLE);
        this.label.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_worried_color));
        invalidate();
        requestLayout();
    }

    public void setSadLabel(String labelText) {
        this.label.setText(labelText);
        this.label.setVisibility(VISIBLE);
        this.label.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_sad_color));
        invalidate();
        requestLayout();
    }

    public void hideLabel() {
        this.label.setVisibility(GONE);
    }

    public void setHappy() {
        this.setChecked();
        if (happyFaceRes != NO_ICON) {
            this.setFace(happyFaceRes);
        } else {
            this.setFace(R.drawable.ic_nearit_ui_happy_green);
        }
        this.setOnClickListener(null);
        this.setEnabled(false);
        this.setActivated(true);
    }

    public void setWorried() {
        this.resetIcon();
        if (worriedFaceRes != NO_ICON) {
            this.setFace(worriedFaceRes);
        } else {
            this.setFace(R.drawable.ic_nearit_ui_worried_yellow);
        }
        this.setEnabled(true);
        this.setActivated(false);
    }

    public void setSad() {
        this.resetIcon();
        if (sadFaceRes != NO_ICON) {
            this.setFace(sadFaceRes);
        } else {
            this.setFace(R.drawable.ic_nearit_ui_sad_red);
        }
        this.setEnabled(true);
        this.setActivated(false);
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
            icon.setImageDrawable(ContextCompat.getDrawable(getContext(), iconRes));
        }
    }
}
