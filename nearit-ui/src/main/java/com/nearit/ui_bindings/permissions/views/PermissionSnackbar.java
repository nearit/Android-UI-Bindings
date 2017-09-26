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
 * Created by Federico Boschini on 25/09/17.
 */

public class PermissionSnackbar extends RelativeLayout {

    public static final int NO_ICON = 0;
    ImageView btIcon, locIcon;
    TextView alertMessage;
    RelativeLayout layout;
    PermissionSnackbarButton okButton;

    String buttonText, alertMessageText;
    int btIconResId, locIconResId;

    public PermissionSnackbar(Context context) {
        super(context);
        init();
    }

    public PermissionSnackbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttrs(attrs);
        init();
    }

    public PermissionSnackbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(attrs);
        init();
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NearItUISnackbar,
                0, 0);
        try {
            buttonText = a.getString(R.styleable.NearItUISnackbar_snackbarButtonText);
            alertMessageText = a.getString(R.styleable.NearItUISnackbar_snackbarAlertText);
            btIconResId = a.getResourceId(R.styleable.NearItUISnackbar_snackbarBluetoothIcon, NO_ICON);
            locIconResId = a.getResourceId(R.styleable.NearItUISnackbar_snackbarLocationIcon, NO_ICON);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_permission_snackbar, this);
        layout = (RelativeLayout) findViewById(R.id.snackbar_relative_layout);
        btIcon = (ImageView) findViewById(R.id.bluetooth_icon);
        locIcon = (ImageView) findViewById(R.id.location_icon);
        alertMessage = (TextView) findViewById(R.id.alert_message);
        okButton = (PermissionSnackbarButton) findViewById(R.id.ok_button);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//        okButton.setText(buttonText);
        if (btIconResId != NO_ICON) {
            btIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(getResources(), btIconResId, null)
            );
        }
        if (locIconResId != NO_ICON) {
            locIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(getResources(), locIconResId, null)
            );
        }
    }
}
