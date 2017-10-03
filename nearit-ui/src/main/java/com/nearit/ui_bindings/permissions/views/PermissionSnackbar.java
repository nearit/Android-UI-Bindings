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
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.permissions.PermissionsRequestIntentBuilder;
import com.nearit.ui_bindings.utils.PreRequirementsUtil;

/**
 * Created by Federico Boschini on 25/09/17.
 */

public class PermissionSnackbar extends RelativeLayout {

    public static final int NO_ICON = 0;
    final Context context;

    ImageView btIcon, locIcon;
    TextView alertMessage;
    PermissionSnackbarButton okButton;

    boolean noBeacon, nonBlockingBeacon, invisibleMode, noDialogHeader, autostartRadar;
    String buttonText, alertMessageText;
    int btIconResId, locIconResId, dialogHeaderResId;

    @Nullable
    private Activity activity;
    private int requestCode;

    public PermissionSnackbar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PermissionSnackbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        obtainAttrs(attrs);
        init();
    }

    public PermissionSnackbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        obtainAttrs(attrs);
        init();
    }

    public void setActivity(@Nullable Activity activity, int requestCode) {
        this.activity = activity;
        this.requestCode = requestCode;
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NearItUISnackbar,
                0, 0);
        try {
            if (a.getString(R.styleable.NearItUISnackbar_snackbarButtonText) != null) {
                buttonText = a.getString(R.styleable.NearItUISnackbar_snackbarButtonText);
            } else {
                buttonText = getContext().getResources().getString(R.string.nearit_ui_permission_snackbar_button_text);
            }
            if (a.getString(R.styleable.NearItUISnackbar_snackbarButtonText) != null) {
                alertMessageText = a.getString(R.styleable.NearItUISnackbar_snackbarAlertText);
            } else {
                alertMessageText = getContext().getResources().getString(R.string.nearit_ui_permission_snackbar_alert_text);
            }
            btIconResId = a.getResourceId(R.styleable.NearItUISnackbar_snackbarBluetoothIcon, NO_ICON);
            locIconResId = a.getResourceId(R.styleable.NearItUISnackbar_snackbarLocationIcon, NO_ICON);

            noBeacon = a.getBoolean(R.styleable.NearItUISnackbar_noBeacon, false);
            nonBlockingBeacon = a.getBoolean(R.styleable.NearItUISnackbar_nonBlockingBeacon, false);
            invisibleMode = a.getBoolean(R.styleable.NearItUISnackbar_invisibleMode, true);
            dialogHeaderResId = a.getResourceId(R.styleable.NearItUISnackbar_dialogHeader, NO_ICON);
            noDialogHeader = a.getBoolean(R.styleable.NearItUISnackbar_noDialogHeader, false);
            autostartRadar = a.getBoolean(R.styleable.NearItUISnackbar_autostartRadar, false);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_permission_snackbar, this);

        btIcon = (ImageView) findViewById(R.id.bluetooth_icon);
        locIcon = (ImageView) findViewById(R.id.location_icon);
        alertMessage = (TextView) findViewById(R.id.alert_message);
        okButton = (PermissionSnackbarButton) findViewById(R.id.ok_button);
        okButton.setClickable(true);

        final PermissionsRequestIntentBuilder builder = NearITUIBindings.getInstance(context).createPermissionRequestIntentBuilder();

        if (noBeacon) {
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

        if (PreRequirementsUtil.checkLocationPermission(context) && PreRequirementsUtil.checkLocation(context) && PreRequirementsUtil.checkBluetooth(context)) {
            this.setVisibility(GONE);
        } else {
            if (PreRequirementsUtil.checkLocationPermission(context) && PreRequirementsUtil.checkLocation(context)) {
                hideLocationIcon();
            } else {
                showLocationIcon();
            }
            if (PreRequirementsUtil.checkBluetooth(context)) {
                hideBluetoothIcon();
            } else {
                showBluetoothIcon();
            }
        }
    }

    public void hideBluetoothIcon() {
        btIcon.setVisibility(GONE);
    }

    public void showBluetoothIcon() {
        btIcon.setVisibility(VISIBLE);
    }

    public void hideLocationIcon() {
        locIcon.setVisibility(GONE);
    }

    public void showLocationIcon() {
        locIcon.setVisibility(VISIBLE);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        alertMessage.setText(alertMessageText);
        okButton.setButtonText(buttonText);
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

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            PermissionSnackbar.this.setVisibility(VISIBLE);

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if (!PreRequirementsUtil.checkBluetooth(context)) {
                    showBluetoothIcon();
                    if (PreRequirementsUtil.checkLocation(context) && PreRequirementsUtil.checkLocationPermission(context)) {
                        hideLocationIcon();
                    } else {
                        showLocationIcon();
                    }
                } else {
                    hideBluetoothIcon();
                    if (PreRequirementsUtil.checkLocation(context) && PreRequirementsUtil.checkLocationPermission(context)) {
                        PermissionSnackbar.this.setVisibility(GONE);
                    } else {
                        showLocationIcon();
                    }
                }
            }

            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(action)) {
                if (!PreRequirementsUtil.checkLocation(context)) {
                    showLocationIcon();
                    if (!PreRequirementsUtil.checkBluetooth(context)) {
                        showBluetoothIcon();
                    }
                } else {
                    if (PreRequirementsUtil.checkLocationPermission(context)) {
                        hideLocationIcon();
                        if (PreRequirementsUtil.checkBluetooth(context)) {
                            PermissionSnackbar.this.setVisibility(GONE);
                        } else {
                            showBluetoothIcon();
                        }
                    } else {
                        showLocationIcon();
                        if (PreRequirementsUtil.checkBluetooth(context)) {
                            hideBluetoothIcon();
                        } else {
                            showBluetoothIcon();
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(mReceiver);
    }
}