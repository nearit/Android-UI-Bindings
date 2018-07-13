package com.nearit.ui_bindings.permissions.views;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.permissions.PermissionsRequestIntentBuilder;
import com.nearit.ui_bindings.utils.PermissionsUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Federico Boschini
 */
public class PermissionSnackBar extends BaseTransientBottomBar.BaseCallback<Snackbar> implements View.OnClickListener {

    @Nullable
    private Snackbar snackbar;
    private PermissionBarButton actionButton;
    private ImageView btIcon;
    private ImageView locIcon;

    @Nullable
    private Activity activity;
    private Context context;
    private int requestCode;

    @Nullable
    private BaseTransientBottomBar.BaseCallback<PermissionSnackBar> callback;

    private boolean noBeacon = false;
    private boolean nonBlockingBeacon = false;
    private boolean autoStartRadar = false;

    private boolean isDismissed = false;

    private PermissionSnackBar(Snackbar snackbar) {
        this.snackbar = snackbar;
        this.snackbar.addCallback(this);
        this.context = snackbar.getContext();
        setupUI();
    }

    public static PermissionSnackBar make(View view, String text, int length) {
        return new PermissionSnackBar(Snackbar.make(view, text, length));
    }

    public PermissionSnackBar addCallback(BaseTransientBottomBar.BaseCallback<PermissionSnackBar> callback) {
        this.callback = callback;
        return this;
    }

    public PermissionSnackBar show() {
        if (snackbar != null) {
            snackbar.show();
            checkPermissionsAndUpdateUI();
        }
        return this;
    }

    public PermissionSnackBar setAction(String actionText) {
        actionButton.setButtonText(actionText);
        return this;
    }

    public PermissionSnackBar noBeacon() {
        this.noBeacon = true;
        checkPermissionsAndUpdateUI();
        return this;
    }

    public PermissionSnackBar nonBlockingBeacon() {
        this.nonBlockingBeacon = true;
        checkPermissionsAndUpdateUI();
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

            /*CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) layout.getLayoutParams();
            params.gravity = Gravity.TOP;
            layout.setLayoutParams(params);*/

            // Hide the text
            TextView textView = layout.findViewById(android.support.design.R.id.snackbar_text);
            textView.setVisibility(View.INVISIBLE);

            View permissionView = LayoutInflater.from(context).inflate(R.layout.nearit_ui_layout_permission_bar, null);
            TextView alertText = permissionView.findViewById(R.id.alert_message);
            alertText.setText(textView.getText());
            //alertText.setTextSize(10);
            actionButton = permissionView.findViewById(R.id.ok_button);
            actionButton.setOnClickListener(this);
            btIcon = permissionView.findViewById(R.id.bluetooth_icon);
            locIcon = permissionView.findViewById(R.id.location_icon);

            layout.setPadding(0, 0, 0, 0);
            layout.addView(permissionView, 0);

            context.registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
            context.registerReceiver(mReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

            checkPermissionsAndUpdateUI();
        }
    }

    private void hideBluetoothIcon() {
        btIcon.setVisibility(GONE);
    }

    private void showBluetoothIcon() {
        if (snackbar != null) {
            snackbar.show();
            btIcon.setVisibility(VISIBLE);
        }
    }

    private void hideLocationIcon() {
        locIcon.setVisibility(GONE);
    }

    private void showLocationIcon() {
        if (snackbar != null) {
            snackbar.show();
            locIcon.setVisibility(VISIBLE);
        }
    }

    private void checkPermissionsAndUpdateUI() {
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

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            //show();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                checkPermissionsAndUpdateUI();
            }

            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(action)) {
                checkPermissionsAndUpdateUI();
            }
        }
    };

    public PermissionSnackBar bindToActivity(@Nullable Activity activity, int requestCode) {
        this.activity = activity;
        this.requestCode = requestCode;
        return this;
    }

    public void unbindFromActivity() {
        this.activity = null;
        context.unregisterReceiver(mReceiver);
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
                checkPermissionsAndUpdateUI();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onDismissed(Snackbar transientBottomBar, int event) {
        if (callback != null) {
            callback.onDismissed(this, event);
        }
        isDismissed = true;
        //Toast.makeText(context, "Dismissed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShown(Snackbar transientBottomBar) {
        if (callback != null) {
            callback.onShown(this);
        }
        isDismissed = false;
        //Toast.makeText(context, "Shown", Toast.LENGTH_SHORT).show();
    }
}
