package com.nearit.ui_bindings.permissions.invisible;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

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
import com.nearit.ui_bindings.permissions.PermissionsRequestExtraParams;

import static com.nearit.ui_bindings.permissions.invisible.NearItInvisiblePresenterImpl.NEAR_BLUETOOTH_SETTINGS_CODE;
import static com.nearit.ui_bindings.permissions.invisible.NearItInvisiblePresenterImpl.NEAR_LOCATION_SETTINGS_CODE;
import static com.nearit.ui_bindings.permissions.invisible.NearItInvisiblePresenterImpl.NEAR_PERMISSION_REQUEST_FINE_LOCATION;

/**
 * @author Federico Boschini
 */
public class NearItInvisiblePermissionsActivity extends AppCompatActivity implements InvisiblePermissionsContract.InvisiblePermissionsView{

    private InvisiblePermissionsContract.InvisiblePermissionsPresenter presenter;

    private PermissionsRequestExtraParams params;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            params = PermissionsRequestExtraParams.fromIntent(intent);
        }

        NearItInvisiblePresenterImpl.obtain(this, params, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.stop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.handlePermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void finishWithOkResult() {
        setResult(RESULT_OK);
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
                                        NearItInvisiblePermissionsActivity.this, NEAR_LOCATION_SETTINGS_CODE);
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
                presenter.onDialogClosed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
                presenter.onDialogClosed();
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
                presenter.onDialogClosed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
                presenter.onDialogClosed();
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
                if (intent.resolveActivity(NearItInvisiblePermissionsActivity.this.getPackageManager()) != null) {
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
                presenter.onDialogClosed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
                presenter.onDialogClosed();
            }
        });
        dialog.show();
    }

    @Override
    public void injectPresenter(@NonNull InvisiblePermissionsContract.InvisiblePermissionsPresenter presenter) {
        this.presenter = presenter;
    }

    public static Intent createIntent(Context context, PermissionsRequestExtraParams params) {
        return new Intent(context, NearItInvisiblePermissionsActivity.class).putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }
}
