package com.nearit.ui_bindings.permissions;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.permissions.views.PermissionButton;

import it.near.sdk.NearItManager;

import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_BLUETOOTH_SETTINGS_CODE;
import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_LOCATION_SETTINGS_CODE;
import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_PERMISSION_REQUEST_FINE_LOCATION;

/**
 * @author Federico Boschini
 */
public class BasePermissionsActivity extends AppCompatActivity implements PermissionsContract.PermissionsView {

    @SuppressWarnings("unused")
    private static final String TAG = "NearItPermissions";

    private PermissionButton locationButton;
    private PermissionButton bleButton;
    private PermissionButton notificationsButton;
    private TextView closeButton;

    @Nullable
    private ImageView headerImageView;

    private PermissionsRequestExtraParams params;
    private boolean isEnableTapToClose = false;

    private PermissionsContract.PermissionsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearit_ui_activity_permissions);

        locationButton = findViewById(R.id.location_button);
        bleButton = findViewById(R.id.ble_button);
        notificationsButton = findViewById(R.id.notification_button);
        headerImageView = findViewById(R.id.header);


        Intent intent = getIntent();
        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            params = PermissionsRequestExtraParams.fromIntent(intent);
            isEnableTapToClose = params.isEnableTapToClose();
        }

        presenter = new PermissionsPresenterImpl(
                this,
                params,
                PermissionsManager.obtain(this),
                State.obtain(this),
                NearItManager.getInstance()
        );

        closeButton = findViewById(R.id.close_text);
        if (closeButton != null) {
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.finalCheck();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
        registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        registerReceiver(mReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.stop();
        unregisterReceiver(mReceiver);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
            if (!isEnableTapToClose) {
                return true;
            }
            presenter.finalCheck();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.handlePermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void injectPresenter(@NonNull PermissionsContract.PermissionsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void hideHeader() {
        if (headerImageView != null) {
            headerImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setHeader(int resId) {
        if (headerImageView != null) {
            headerImageView.setImageResource(resId);
        }
    }

    @Override
    public void setBluetoothIcon(int resId) {
        bleButton.setIcon(resId);
    }

    @Override
    public void setLocationIcon(int resId) {
        locationButton.setIcon(resId);
    }

    @Override
    public void setNotificationsIcon(int resId) {
        notificationsButton.setIcon(resId);
    }

    @Override
    public void setSadFaceIcon(int resId) {
        bleButton.setSadFaceRes(resId);
        locationButton.setSadFaceRes(resId);
        notificationsButton.setSadFaceRes(resId);
    }

    @Override
    public void setWorriedFaceIcon(int resId) {
        bleButton.setWorriedFaceRes(resId);
        locationButton.setWorriedFaceRes(resId);
        notificationsButton.setWorriedFaceRes(resId);
    }

    @Override
    public void setHappyIcon(int resId) {
        bleButton.setHappyFaceRes(resId);
        locationButton.setHappyFaceRes(resId);
        notificationsButton.setHappyFaceRes(resId);
    }

    @Override
    public void hideBluetoothButton() {
        bleButton.setVisibility(View.GONE);
    }

    @Override
    public void setBluetoothButtonHappy() {
        bleButton.setHappy();
        bleButton.setText(getResources().getString(R.string.nearit_ui_bluetooth_button_on_text));
    }

    @Override
    public void setBluetoothButtonSad() {
        bleButton.setSad();
        bleButton.setText(getResources().getString(R.string.nearit_ui_bluetooth_button_off_text));
        bleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBluetoothTapped();
            }
        });
    }

    @Override
    public void resetBluetoothButton() {
        bleButton.resetState();
        bleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBluetoothTapped();
            }
        });
    }

    @Override
    public void setLocationButtonHappy() {
        locationButton.setHappy();
        locationButton.setText(getResources().getString(R.string.nearit_ui_location_button_on_text));
        locationButton.hideLabel();
    }

    @Override
    public void setLocationButtonWorried() {
        locationButton.setWorried();
        locationButton.setText(getResources().getString(R.string.nearit_ui_location_button_text));
        locationButton.setWorriedLabel(getResources().getString(R.string.nearit_ui_location_button_worried_text));
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLocationTapped();
            }
        });
    }

    @Override
    public void setLocationButtonSad() {
        locationButton.setSad();
        locationButton.setText(getResources().getString(R.string.nearit_ui_location_button_text));
        locationButton.setSadLabel(getResources().getString(R.string.nearit_ui_location_button_sad_text));
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLocationTapped();
            }
        });
    }

    @Override
    public void resetLocationButton() {
        locationButton.resetState();
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLocationTapped();
            }
        });
    }

    @Override
    public void hideNotificationsButton() {
        notificationsButton.setVisibility(View.GONE);
    }

    @Override
    public void showNotificationsButton() {
        notificationsButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setNotificationsButtonHappy() {
        notificationsButton.setHappy();
        notificationsButton.setText("Notifications on");
    }

    @Override
    public void setNotificationsButtonSad() {
        notificationsButton.setSad();
        notificationsButton.setText("Notifications off");
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onNotificationsTapped();
            }
        });
    }

    @Override
    public void resetNotificationsButton() {
        notificationsButton.resetState();
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onNotificationsTapped();
            }
        });
    }

    @Override
    public void refreshCloseText() {
        closeButton.setText(getResources().getString(R.string.nearit_ui_close_permissions_text));
    }

    @Override
    public void finishWithOkResult() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void finishWithKoResult() {
        finish();
    }

    @Override
    public void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, NEAR_PERMISSION_REQUEST_FINE_LOCATION);
        }
    }

    @Override
    public void turnOnLocationServices(boolean needBle) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setNeedBle(needBle);

        final Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    task.getResult(ApiException.class);
                    presenter.onLocationServicesOn();
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                resolvable.startResolutionForResult(
                                        BasePermissionsActivity.this, NEAR_LOCATION_SETTINGS_CODE);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            finish();
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void turnOnBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, NEAR_BLUETOOTH_SETTINGS_CODE);
    }

    @Override
    public boolean shouldShowRequestPermissionRationale() {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void showAirplaneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.nearit_ui_flight_mode_detected_title).setMessage(R.string.nearit_ui_flight_mode_detected_message);

        builder.setPositiveButton(R.string.nearit_ui_go_to_settings, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.nearit_ui_cancel_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                presenter.onDialogCanceled();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
                presenter.onDialogCanceled();
            }
        });
        dialog.show();
    }

    @Override
    public void showDontAskAgainDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.nearit_ui_go_to_settings_location_message).setTitle(R.string.nearit_ui_go_to_settings_location_title);

        builder.setPositiveButton(R.string.nearit_ui_go_to_settings, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.nearit_ui_cancel_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                presenter.onDialogCanceled();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
                presenter.onDialogCanceled();
            }
        });
        dialog.show();
    }

    @Override
    public void showNotificationsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.nearit_ui_go_to_settings_notification_message).setTitle(R.string.nearit_ui_go_to_settings_notification_title);

        builder.setPositiveButton(R.string.nearit_ui_go_to_settings, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                //for Android 5-7
                intent.putExtra("app_package", getPackageName());
                intent.putExtra("app_uid", getApplicationInfo().uid);
                // for Android O
                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                if (intent.resolveActivity(BasePermissionsActivity.this.getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                }

            }
        });
        builder.setNegativeButton(R.string.nearit_ui_cancel_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                presenter.onDialogCanceled();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
                presenter.onDialogCanceled();
            }
        });
        dialog.show();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                presenter.checkPermissions();
            }

            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(action)) {
                presenter.checkPermissions();
            }
        }
    };

    public static Intent addExtras(Intent from, PermissionsRequestExtraParams params) {
        return from.putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

}
