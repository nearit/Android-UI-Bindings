package com.nearit.ui_bindings.permissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.nearit.ui_bindings.utils.PreRequirementsUtil;

import it.near.sdk.NearItManager;

/**
 * @author Federico Boschini
 */

public class NearItPermissionsActivity extends AppCompatActivity {

    private static final String TAG = "NearItPermissions";

    private static final int NEAR_BLUETOOTH_SETTINGS_CODE = 4000;
    private static final int NEAR_LOCATION_SETTINGS_CODE = 5000;
    private static final int NEAR_PERMISSION_REQUEST_FINE_LOCATION = 6000;
    private static final String NEAR_PERMISSION_ASKED = "nearit_ui_permission_asked";

    /**
     * Flow parameters
     */
    private boolean isEnableTapToClose = false;
    private boolean isInvisibleLayoutMode = false;
    private boolean isNoBeacon = false;
    private boolean isNonBlockingBeacon = false;
    private boolean isAutoStartRadar = false;
    private int headerDrawable = 0;
    private boolean isNoHeader = false;

    private boolean flightModeDialogLaunched = false;

    @Nullable
    private PermissionButton locationButton;
    @Nullable
    private PermissionButton bleButton;
    @Nullable
    private TextView closeButton;
    @Nullable
    private ImageView headerImageView;
    @Nullable
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("nearit_ui", Activity.MODE_PRIVATE);

        Intent intent = getIntent();
        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            PermissionsRequestExtraParams params = PermissionsRequestExtraParams.fromIntent(intent);
            isEnableTapToClose = params.isEnableTapToClose();
            isInvisibleLayoutMode = params.isInvisibleLayoutMode();
            isNoBeacon = params.isNoBeacon();
            isNonBlockingBeacon = params.isNonBlockingBeacon();
            isAutoStartRadar = params.isAutoStartRadar();
            headerDrawable = params.getHeaderDrawable();
            isNoHeader = params.isNoHeader();
        }

        if (!PreRequirementsUtil.isBleAvailable(this)) {
            isNoBeacon = true;
        }

        boolean allPermissionsGiven = PreRequirementsUtil.checkBluetooth(this) && checkLocation();

        if (!isInvisibleLayoutMode) {
            setContentView(R.layout.nearit_ui_activity_permissions);
            locationButton = findViewById(R.id.location_button);
            bleButton = findViewById(R.id.ble_button);
            closeButton = findViewById(R.id.close_text);
            headerImageView = findViewById(R.id.header);
        } else {
            if (PreRequirementsUtil.isAirplaneModeOn(this)) {
                createAirplaneDialog().show();
            } else {
                if (!allPermissionsGiven) {
                    askPermissions();
                } else {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isInvisibleLayoutMode && PreRequirementsUtil.isAirplaneModeOn(this)) {
            createAirplaneDialog().show();
        }

        if (isInvisibleLayoutMode && flightModeDialogLaunched) {
            recreate();
        }
    }

    public static Intent createIntent(Context context, PermissionsRequestExtraParams params) {
        return new Intent(context, NearItPermissionsActivity.class).putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
            if (!isEnableTapToClose) {
                return true;
            }
            finalCheck();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        locationPermissionGranted = Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (isNoHeader && headerImageView != null) {
            headerImageView.setVisibility(View.GONE);
        } else if (headerImageView != null && headerDrawable != 0) {
            headerImageView.setBackgroundResource(headerDrawable);
        }

        if (isNoBeacon && bleButton != null) {
            bleButton.setVisibility(View.GONE);
        }
        setBluetoothButton();
        setLocationButton();

        if (closeButton != null) {
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalCheck();
                }
            });
        }
    }

    public boolean checkLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean anyLocationProv = false;
        if (locationManager != null) {
            anyLocationProv = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            anyLocationProv |= locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        return anyLocationProv &&
                permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Checks and asks for missing permissions for Android 23+ devices.
     * Otherwise request for enabling system wise location services.
     */
    private void askPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionState = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);

            boolean isPermissionGranted = permissionState == PackageManager.PERMISSION_GRANTED;
            if (isPermissionGranted) {
                openLocationSettings();
            } else {
                boolean alreadyAsked = false;
                if (sp != null) {
                    alreadyAsked = sp.getBoolean(NEAR_PERMISSION_ASKED, false);
                }
                if (alreadyAsked && !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    createDialog().show();
                } else {
                    requestFineLocationPermission();
                }
            }
        } else {
            openLocationSettings();
        }
    }

    /**
     * Asks to enable location services.
     */
    private void openLocationSettings() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setNeedBle(isInvisibleLayoutMode && !isNoBeacon);

        final Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    task.getResult(ApiException.class);
                    onLocationSettingsOkResult();
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                Log.e(TAG, "Resolution required");
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                resolvable.startResolutionForResult(
                                        NearItPermissionsActivity.this,
                                        NEAR_LOCATION_SETTINGS_CODE);
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

    /**
     * Asks to enable bluetooth
     */
    private void openBluetoothSettings() {
        if (!PreRequirementsUtil.checkBluetooth(this)) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, NEAR_BLUETOOTH_SETTINGS_CODE);
        } else {
            // bt on
            finalCheck();
        }
    }

    /**
     * Asks for location permissions.
     */
    private void requestFineLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, NEAR_PERMISSION_REQUEST_FINE_LOCATION);
            if (sp != null) {
                sp.edit().putBoolean(NEAR_PERMISSION_ASKED, true).apply();
            }
        }
    }

    /**
     * Manages location and bluetooth callbacks
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setLocationButton();
        setBluetoothButton();

        /*if (PreRequirementsUtil.isAirplaneModeOn(NearItPermissionsActivity.this)) {
            createAirplaneDialog().show();
        }*/

        if (requestCode == NEAR_LOCATION_SETTINGS_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                onLocationSettingsOkResult();
            } else {
                if (isInvisibleLayoutMode) {
                    finalCheck();
                }
            }
        } else if (requestCode == NEAR_BLUETOOTH_SETTINGS_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (checkLocation()) {
                    finalCheck();
                }
            } else {
                if (isInvisibleLayoutMode) {
                    finalCheck();
                } else {
                    if (isNonBlockingBeacon && checkLocation()) {
                        finalCheck();
                    }
                }
            }
        }
    }

    private void onLocationSettingsOkResult() {
        if (isInvisibleLayoutMode) {
            if (!isNoBeacon) {
                openBluetoothSettings();
            } else {
                finalCheck();
            }
        } else {
            if (isNoBeacon) {
                finalCheck();
            } else {
                if (PreRequirementsUtil.checkBluetooth(this)) {
                    finalCheck();
                }
            }
        }
    }

    /**
     * Manages permissions request callbacks
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        setLocationButton();
        setBluetoothButton();

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NEAR_PERMISSION_REQUEST_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openLocationSettings();
            } else {
                if (isInvisibleLayoutMode) {
                    finalCheck();
                }
            }
        }
    }

    /**
     * Checks one last time that everything is ok
     */
    public void finalCheck() {
        if (PreRequirementsUtil.isAirplaneModeOn(this)) {
            finish();
        }
        if (checkLocation()) {
            if (PreRequirementsUtil.checkBluetooth(this) || isNoBeacon || isNonBlockingBeacon) {
                onPermissionsReady();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    /**
     * If everything is fine we set result code to RESULT_OK and we close the activity
     */
    @SuppressLint("MissingPermission")
    private void onPermissionsReady() {
        if (isAutoStartRadar) {
            NearItManager.getInstance().startRadar();
        }
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void setBluetoothButton() {
        if (bleButton != null) {
            if (PreRequirementsUtil.checkBluetooth(this)) {
                bleButton.setChecked();
            } else {
                bleButton.setUnchecked();
                bleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (PreRequirementsUtil.isAirplaneModeOn(NearItPermissionsActivity.this)) {
                            createAirplaneDialog().show();
                        } else {
                            openBluetoothSettings();
                        }
                    }
                });
            }
        }
    }

    private void setLocationButton() {
        if (locationButton != null) {
            if (checkLocation()) {
                locationButton.setChecked();
            } else {
                locationButton.setUnchecked();
                locationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (PreRequirementsUtil.isAirplaneModeOn(NearItPermissionsActivity.this)) {
                            createAirplaneDialog().show();
                        } else {
                            askPermissions();
                        }
                    }
                });
            }
        }
    }


    private AlertDialog createAirplaneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.nearit_ui_turn_on_location_title).setMessage(R.string.nearit_ui_turn_on_location_message);

        builder.setPositiveButton(R.string.nearit_ui_go_to_settings, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                flightModeDialogLaunched = true;
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.nearit_ui_cancel_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                finalCheck();
            }
        });
        return builder.create();
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.nearit_ui_permission_message).setTitle(R.string.nearit_ui_permission_title);

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
                if (isInvisibleLayoutMode) {
                    finalCheck();
                }
            }
        });
        return builder.create();
    }

}
