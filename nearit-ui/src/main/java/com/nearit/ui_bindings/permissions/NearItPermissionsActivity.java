package com.nearit.ui_bindings.permissions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
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
import it.near.sdk.logging.NearLog;

/**
 * @author Federico Boschini
 */

public class NearItPermissionsActivity extends AppCompatActivity {

    private static final String TAG = "NearItPermissions";
    
    private static final int NEAR_BLUETOOTH_SETTINGS_CODE = 4000;
    private static final int NEAR_LOCATION_SETTINGS_CODE = 5000;
    private static final int NEAR_PERMISSION_REQUEST_FINE_LOCATION = 6000;

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

    @Nullable
    private PermissionButton locationButton;
    @Nullable
    private PermissionButton bleButton;
    @Nullable
    private TextView closeButton;
    @Nullable
    private ImageView headerImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        if (!isBleAvailable()) {
            isNoBeacon = true;
        }

        boolean allPermissionsGiven = checkBluetooth() && checkLocation();

        if (!isInvisibleLayoutMode) {
            setContentView(R.layout.nearit_ui_activity_permissions);
            locationButton = findViewById(R.id.location_button);
            bleButton = findViewById(R.id.ble_button);
            closeButton = findViewById(R.id.close_text);
            headerImageView = findViewById(R.id.header);
        } else {
            if (!allPermissionsGiven) {
                askPermissions();
            } else {
                setResult(Activity.RESULT_OK);
                finish();
            }
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

    private boolean checkBluetooth() {
        return BluetoothAdapter.getDefaultAdapter() != null && BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    /**
     * Checks and asks for missing permissions for Android 23+ devices.
     * Otherwise request for enabling system wise location services.
     */
    private void askPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean isPermissionGranted = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            if (isPermissionGranted) {
                openLocationSettings();
            } else {
                requestFineLocationPermission();
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
        if (!checkBluetooth()) {
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
        }
    }

    /**
     * Manages location and bluetooth callbacks
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setLocationButton();
        setBluetoothButton();

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
                if (checkBluetooth()) {
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
     * Checks for BLE availability
     */
    private boolean isBleAvailable() {
        boolean available = false;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            NearLog.d(TAG, "BLE not supported prior to API 18");
        } else if (!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            NearLog.d(TAG, "BLE not available on this device");
        } else {
            available = true;
        }
        return available;
    }

    /**
     * Checks one last time that everything is ok
     */
    public void finalCheck() {
        if (checkLocation()) {
            if (checkBluetooth() || isNoBeacon || isNonBlockingBeacon) {
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
            if (checkBluetooth()) {
                bleButton.setChecked();
            } else {
                bleButton.setUnchecked();
                bleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openBluetoothSettings();
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
                        askPermissions();
                    }
                });
            }
        }
    }

}
