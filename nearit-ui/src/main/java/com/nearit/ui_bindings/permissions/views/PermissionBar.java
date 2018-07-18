package com.nearit.ui_bindings.permissions.views;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.permissions.PermissionsRequestIntentBuilder;
import com.nearit.ui_bindings.utils.PermissionsUtils;

/**
 * @author Federico Boschini
 */

public class PermissionBar extends RelativeLayout {

    private static final int NO_ICON = 0;
    private final Context context;

    private ImageView face;
    private TextView alertMessage;
    private PermissionBarButton okButton;

    private boolean noBeacon;
    private boolean nonBlockingBeacon;
    private boolean invisibleMode;
    private boolean noDialogHeader;
    private boolean autostartRadar;
    private String alertMessageText;
    private int btIconResId;
    private int locIconResId;
    private int notifIconResId;
    private int dialogHeaderResId;

    @Nullable
    private Activity activity;
    private int requestCode;

    public PermissionBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PermissionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        obtainAttrs(attrs);
        init();
    }

    public PermissionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        obtainAttrs(attrs);
        init();
    }

    public void setAlertMessage(String alertText) {
        alertMessageText = alertText;
        alertMessage.setText(alertMessageText);
        init();
    }

    public boolean isNoBeacon() {
        return noBeacon;
    }

    public void setNoBeacon(boolean noBeacon) {
        this.noBeacon = noBeacon;
    }

    public boolean isNonBlockingBeacon() {
        return nonBlockingBeacon;
    }

    public void setNonBlockingBeacon(boolean nonBlockingBeacon) {
        this.nonBlockingBeacon = nonBlockingBeacon;
    }

    public boolean isInvisibleMode() {
        return invisibleMode;
    }

    public void setInvisibleMode(boolean invisibleMode) {
        this.invisibleMode = invisibleMode;
    }

    public boolean isNoDialogHeader() {
        return noDialogHeader;
    }

    public void setNoDialogHeader(boolean noDialogHeader) {
        this.noDialogHeader = noDialogHeader;
    }

    public boolean isAutostartRadar() {
        return autostartRadar;
    }

    public void setAutostartRadar(boolean autostartRadar) {
        this.autostartRadar = autostartRadar;
    }

    public void bindToActivity(@Nullable Activity activity, int requestCode) {
        this.activity = activity;
        this.requestCode = requestCode;
    }

    public void unbindFromActivity() {
        getContext().unregisterReceiver(mReceiver);
        this.activity = null;
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NearItUIBar,
                0, 0);
        try {
            if (a.getString(R.styleable.NearItUIBar_barButtonText) != null) {
                alertMessageText = a.getString(R.styleable.NearItUIBar_barAlertText);
            } else {
                alertMessageText = getContext().getResources().getString(R.string.nearit_ui_permission_bar_alert_text);
            }
            btIconResId = a.getResourceId(R.styleable.NearItUIBar_barBluetoothIcon, NO_ICON);
            locIconResId = a.getResourceId(R.styleable.NearItUIBar_barLocationIcon, NO_ICON);
            notifIconResId = a.getResourceId(R.styleable.NearItUIBar_barNotificationsIcon, NO_ICON);

            noBeacon = a.getBoolean(R.styleable.NearItUIBar_noBeacon, false);
            nonBlockingBeacon = a.getBoolean(R.styleable.NearItUIBar_nonBlockingBeacon, false);
            invisibleMode = a.getBoolean(R.styleable.NearItUIBar_invisibleMode, true);
            dialogHeaderResId = a.getResourceId(R.styleable.NearItUIBar_dialogHeader, NO_ICON);
            noDialogHeader = a.getBoolean(R.styleable.NearItUIBar_noDialogHeader, false);
            autostartRadar = a.getBoolean(R.styleable.NearItUIBar_autostartRadar, false);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_permission_bar, this);

        face = findViewById(R.id.face);
        alertMessage = findViewById(R.id.alert_message);
        okButton = findViewById(R.id.ok_button);
        okButton.setClickable(true);

        final PermissionsRequestIntentBuilder builder = NearITUIBindings.getInstance(context).permissionsIntentBuilder();

        if (noBeacon) {
            hideBluetoothIcon();
            builder.noBeacon();
        }
        if (nonBlockingBeacon) {
            builder.nonBlockingBeacon();
        }
        if (invisibleMode) {
            builder.invisibleLayoutMode();
        }
        if (noDialogHeader) {
            builder.setNoHeader();
        }
        if (autostartRadar) {
            builder.automaticRadarStart();
        }
        if (dialogHeaderResId != NO_ICON) {
            builder.setHeaderResourceId(dialogHeaderResId);
        }

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity != null) {
                    activity.startActivityForResult(
                            builder
                                    .build()
                                    .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT),
                            requestCode
                    );
                }
            }
        });

        okButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity != null) {
                    activity.startActivityForResult(
                            builder
                                    .build()
                                    .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT),
                            requestCode
                    );
                }
            }
        });

        getContext().registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        getContext().registerReceiver(mReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

        checkPermissionsAndUpdateUI();
    }

    private void hideBluetoothIcon() {
        okButton.hideBluetoothIcon();
    }

    private void showBluetoothIcon() {
        okButton.showBluetoothIcon();
    }

    private void hideNotificationsIcon() {
        okButton.hideNotificationsIcon();
    }

    private void showNotificationsIcon() {
        okButton.showNotificationsIcon();
    }

    private void hideLocationIcon() {
        okButton.hideLocationIcon();
    }

    private void showLocationIcon() {
        okButton.showLocationIcon();
    }

    public void setSad() {
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.nearit_ui_permission_bar_sad_color));
        face.setImageResource(R.drawable.ic_nearit_ui_sad_white);
    }

    public void setWorried() {
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.nearit_ui_permission_bar_worried_color));
        face.setImageResource(R.drawable.ic_nearit_ui_worried_white);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        alertMessage.setText(alertMessageText);
        if (btIconResId != NO_ICON) {
            okButton.setBluetoothIcon(btIconResId);
        }
        if (locIconResId != NO_ICON) {
            okButton.setLocationIcon(locIconResId);
        }
        if (notifIconResId != NO_ICON) {
            okButton.setNotificationIcon(notifIconResId);
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            checkPermissionsAndUpdateUI();
        }
    };

    private void checkPermissionsAndUpdateUI() {
        if (PermissionsUtils.areNotificationsEnabled(context)
                && (PermissionsUtils.checkBluetooth(context) || noBeacon || !PermissionsUtils.isBleAvailable(context))
                && (PermissionsUtils.checkLocationPermission(context) && PermissionsUtils.checkLocationServices(context))) {
            this.setVisibility(GONE);
        } else {

            setSad();
            PermissionBar.this.setVisibility(VISIBLE);

            if (!PermissionsUtils.areNotificationsEnabled(context)) {
                showNotificationsIcon();
                setWorried();
            } else {
                hideNotificationsIcon();
            }

            if (!PermissionsUtils.checkBluetooth(context) && !noBeacon && PermissionsUtils.isBleAvailable(context)) {
                showBluetoothIcon();
                setWorried();
            } else {
                hideBluetoothIcon();
            }

            if (!PermissionsUtils.checkLocationPermission(context)) {
                showLocationIcon();
                setSad();
            } else {
                hideLocationIcon();
                if (!PermissionsUtils.checkLocationServices(context)) {
                    showLocationIcon();
                    setWorried();
                }
            }

        }
    }

    public boolean onActivityResult(int requestCode, int resultCode) {
        if (requestCode == this.requestCode) {
            checkPermissionsAndUpdateUI();
            return true;
        }
        return false;
    }
}
