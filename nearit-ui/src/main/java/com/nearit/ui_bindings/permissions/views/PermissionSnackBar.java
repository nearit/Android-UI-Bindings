package com.nearit.ui_bindings.permissions.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.permissions.PermissionsRequestIntentBuilder;
import com.nearit.ui_bindings.utils.PermissionsUtils;

/**
 * @author Federico Boschini
 */
public class PermissionSnackBar extends BaseTransientBottomBar.BaseCallback<Snackbar> implements View.OnClickListener {

    private View parentView;
    private String alertText;

    private Snackbar snackbar;
    private PermissionBar permissionBar;

    @Nullable
    private Activity activity;
    private Context context;
    private int requestCode;

    @Nullable
    private BaseTransientBottomBar.BaseCallback<PermissionSnackBar> callback;

    private boolean noBeacon = false;
    private boolean nonBlockingBeacon = false;
    private boolean autoStartRadar = false;

    private boolean swiped = false;

    private PermissionSnackBar(View parentView, String alertText) {
        this.parentView = parentView;
        this.alertText = alertText;
        init();
    }

    private void init() {
        snackbar = Snackbar.make(parentView, alertText, Snackbar.LENGTH_INDEFINITE);
        snackbar.addCallback(this);
        this.context = snackbar.getContext();
        setupUI();
    }

    public static PermissionSnackBar make(View view, String text) {
        return new PermissionSnackBar(view, text);
    }

    public PermissionSnackBar addCallback(BaseTransientBottomBar.BaseCallback<PermissionSnackBar> callback) {
        this.callback = callback;
        return this;
    }

    public PermissionSnackBar show() {
        if (swiped) {
            swiped = false;
            init();
        }
        if (!allPermissionsGranted()) {
            snackbar.show();
        }
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

    @SuppressWarnings("WeakerAccess")
    public void dismiss() {
        snackbar.dismiss();
    }

    public View getView() {
        return snackbar.getView();
    }

    @SuppressLint("InflateParams")
    private void setupUI() {
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        // Hide the text
        TextView textView = layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        View permissionView = LayoutInflater.from(context).inflate(R.layout.nearit_ui_layout_snackbar_container, null);

        permissionBar = permissionView.findViewById(R.id.snackbar_permission_bar);
        permissionBar.setAlertMessage("fdgfdg");
        permissionBar.setNoBeacon(noBeacon);
        permissionBar.setNonBlockingBeacon(nonBlockingBeacon);
        permissionBar.setAutostartRadar(autoStartRadar);

        layout.setPadding(0, 0, 0, 0);
        layout.addView(permissionView, 0);

        //permissionBar.setLayoutGravity();

        context.registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        context.registerReceiver(mReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

        checkPermissionsAndUpdateUI();
    }

    private void checkPermissionsAndUpdateUI() {
        if (PermissionsUtils.areNotificationsEnabled(context)
                && (PermissionsUtils.checkBluetooth(context) || noBeacon || !PermissionsUtils.isBleAvailable(context))
                && (PermissionsUtils.checkLocationPermission(context) && PermissionsUtils.checkLocationServices(context))) {
            dismiss();
        } else {
            show();
        }
    }

    private boolean allPermissionsGranted() {
        return PermissionsUtils.checkLocationPermission(context) && PermissionsUtils.checkLocationServices(context) && (PermissionsUtils.checkBluetooth(context) || noBeacon) && PermissionsUtils.areNotificationsEnabled(context);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

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
        permissionBar.bindToActivity(activity, requestCode);
        return this;
    }

    public void unbindFromActivity() {
        permissionBar.unbindFromActivity();
        this.activity = null;
        context.unregisterReceiver(mReceiver);
    }

    @Override
    public void onClick(View v) {
        final PermissionsRequestIntentBuilder builder = NearITUIBindings.getInstance(context).permissionsIntentBuilder();

        builder.invisibleLayoutMode();

        if (noBeacon) {
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

    public boolean onActivityResult(int requestCode, int resultCode) {
        permissionBar.onActivityResult(requestCode, resultCode);
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
        if (event == DISMISS_EVENT_SWIPE) {
            swiped = true;
        }
        if (callback != null) {
            callback.onDismissed(this, event);
        }
    }

    @Override
    public void onShown(Snackbar transientBottomBar) {
        swiped = false;
        if (callback != null) {
            callback.onShown(this);
        }
    }
}
