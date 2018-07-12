package com.nearit.ui_bindings.permissions.views;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.permissions.PermissionsRequestIntentBuilder;
import com.nearit.ui_bindings.utils.PermissionsUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Federico Boschini
 */
public class PermissionSnackBar implements View.OnClickListener {

    @Nullable
    private Snackbar snackbar;
    private View permissionView;
    private PermissionBarButton actionButton;
    private ImageView btIcon;
    private ImageView locIcon;

    @Nullable
    private Activity activity;
    private Context context;
    private int requestCode;

    private boolean noBeacon = false;
    private boolean nonBlockingBeacon = false;
    private boolean autoStartRadar = false;
    private TextView alertText;

    private PermissionSnackBar(Snackbar snackbar) {
        this.snackbar = snackbar;
        this.context = snackbar.getContext();
        setupUI();
    }

    public static PermissionSnackBar make(View view, String text, int length) {
        return new PermissionSnackBar(Snackbar.make(view, text, length));
    }

    public void show() {
        if (snackbar != null) {
            snackbar.show();
        }
    }

    public PermissionSnackBar setAction(String actionText) {
        actionButton.setButtonText(actionText);
        return this;
    }

    public PermissionSnackBar noBeacon() {
        this.noBeacon = true;
        return this;
    }

    public PermissionSnackBar nonBlockingBeacon() {
        this.nonBlockingBeacon = true;
        return this;
    }

    public PermissionSnackBar autoStartRadar() {
        this.autoStartRadar = true;
        return this;
    }

    public void dismiss() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }

    private void setupUI() {
        if (snackbar != null) {
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
            // Hide the text
            TextView textView = layout.findViewById(android.support.design.R.id.snackbar_text);
            textView.setVisibility(View.INVISIBLE);

            permissionView = LayoutInflater.from(context).inflate(R.layout.nearit_ui_layout_permission_bar, null);
            actionButton = permissionView.findViewById(R.id.ok_button);
            alertText = permissionView.findViewById(R.id.alert_message);
            btIcon = permissionView.findViewById(R.id.bluetooth_icon);
            locIcon = permissionView.findViewById(R.id.location_icon);
            alertText.setText(textView.getText());
            actionButton.setOnClickListener(this);

            layout.setPadding(0, 0, 0, 0);
            layout.addView(permissionView, 0);

            context.registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
            context.registerReceiver(mReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

            if (PermissionsUtils.checkLocationPermission(context) && PermissionsUtils.checkLocationServices(context) && (PermissionsUtils.checkBluetooth(context) || noBeacon)) {
                dismiss();
            } else {
                if (PermissionsUtils.checkLocationPermission(context) && PermissionsUtils.checkLocationServices(context)) {
                    hideLocationIcon();
                } else {
                    showLocationIcon();
                }
                if (PermissionsUtils.checkBluetooth(context) || noBeacon) {
                    hideBluetoothIcon();
                } else {
                    showBluetoothIcon();
                }
            }
        }
    }

    private void hideBluetoothIcon() {
        btIcon.setVisibility(GONE);
    }

    private void showBluetoothIcon() {
        btIcon.setVisibility(VISIBLE);
    }

    private void hideLocationIcon() {
        locIcon.setVisibility(GONE);
    }

    private void showLocationIcon() {
        locIcon.setVisibility(VISIBLE);
    }

    private void checkLocationAndUpdateUI() {
        if (!PermissionsUtils.checkLocationServices(context)) {
            showLocationIcon();
            if (!(PermissionsUtils.checkBluetooth(context) || noBeacon)) {
                showBluetoothIcon();
            }
        } else {
            if (PermissionsUtils.checkLocationPermission(context)) {
                hideLocationIcon();
                if (PermissionsUtils.checkBluetooth(context) || noBeacon) {
                    dismiss();
                } else {
                    showBluetoothIcon();
                }
            } else {
                showLocationIcon();
                if (PermissionsUtils.checkBluetooth(context) || noBeacon) {
                    hideBluetoothIcon();
                } else {
                    showBluetoothIcon();
                }
            }
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            show();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if (!(PermissionsUtils.checkBluetooth(context) || noBeacon)) {
                    showBluetoothIcon();
                    if (PermissionsUtils.checkLocationServices(context) && PermissionsUtils.checkLocationPermission(context)) {
                        hideLocationIcon();
                    } else {
                        showLocationIcon();
                    }
                } else {
                    hideBluetoothIcon();
                    if (PermissionsUtils.checkLocationServices(context) && PermissionsUtils.checkLocationPermission(context)) {
                        dismiss();
                    } else {
                        showLocationIcon();
                    }
                }
            }

            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(action)) {
                checkLocationAndUpdateUI();
            }
        }
    };

    public void bindToActivity(@Nullable Activity activity, int requestCode) {
        this.activity = activity;
        this.requestCode = requestCode;
    }

    public void unbindFromActivity() {
        this.activity = null;
    }

    @Override
    public void onClick(View v) {
        if (snackbar != null) {
            final PermissionsRequestIntentBuilder builder = NearITUIBindings.getInstance(context).permissionsIntentBuilder();

            builder.invisibleLayoutMode();

            if (noBeacon) {
                hideBluetoothIcon();
                builder.noBeacon();
            }
            if (nonBlockingBeacon) {
                builder.nonBlockingBeacon();
            }
            if (autoStartRadar) {
                builder.automaticRadarStart();
            }

            if (activity != null) {
                activity.startActivityForResult(
                        builder
                                .build()
                                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT),
                        requestCode
                );
            }
        }
    }

    public boolean onActivityResult(int requestCode, int resultCode) {
        if (requestCode == this.requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                checkLocationAndUpdateUI();
            }
            return true;
        }
        return false;
    }
}
