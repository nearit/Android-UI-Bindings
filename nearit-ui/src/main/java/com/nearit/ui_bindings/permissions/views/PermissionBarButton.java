package com.nearit.ui_bindings.permissions.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nearit.ui_bindings.R;

/**
 * @author Federico Boschini
 */

public class PermissionBarButton extends RelativeLayout {

    private int notificationIconResId;
    private int bluetoothIconResId;
    private int locationIconResId;

    private ImageView notificationIcon;
    private ImageView bluetoothIcon;
    private ImageView locationIcon;

    public PermissionBarButton(Context context) {
        super(context);
        init();
    }

    public PermissionBarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttrs(attrs);
        init();
    }

    public PermissionBarButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_permission_bar_button, this);
        notificationIcon = findViewById(R.id.notificationsIcon);
        bluetoothIcon = findViewById(R.id.bluetoothIcon);
        locationIcon = findViewById(R.id.locationIcon);
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NearItUIBar,
                0, 0);
        try {
            int NO_ICON = 0;
            notificationIconResId = a.getResourceId(R.styleable.NearItUIBar_notificationsIcon, NO_ICON);
            bluetoothIconResId = a.getResourceId(R.styleable.NearItUIBar_bluetoothIcon, NO_ICON);
            locationIconResId = a.getResourceId(R.styleable.NearItUIBar_locationIcon, NO_ICON);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (notificationIconResId != 0) {
            notificationIcon.setImageResource(notificationIconResId);
        }
        if (bluetoothIconResId != 0) {
            bluetoothIcon.setImageResource(bluetoothIconResId);
        }
        if (locationIconResId != 0) {
            locationIcon.setImageResource(locationIconResId);
        }
    }

    public void showNotificationsIcon() {
        notificationIcon.setVisibility(VISIBLE);
    }

    public void showBluetoothIcon() {
        bluetoothIcon.setVisibility(VISIBLE);
    }

    public void showLocationIcon() {
        locationIcon.setVisibility(VISIBLE);
    }

    public void hideNotificationsIcon() {
        notificationIcon.setVisibility(GONE);
    }

    public void hideBluetoothIcon() {
        bluetoothIcon.setVisibility(GONE);
    }

    public void hideLocationIcon() {
        locationIcon.setVisibility(GONE);
    }

    public void setNotificationIcon(int resId) {
        notificationIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(getResources(), resId, null)
            );
    }

    public void setBluetoothIcon(int resId) {
        bluetoothIcon.setImageDrawable(
                ResourcesCompat.getDrawable(getResources(), resId, null)
        );
    }

    public void setLocationIcon(int resId) {
        locationIcon.setImageDrawable(
                ResourcesCompat.getDrawable(getResources(), resId, null)
        );
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
