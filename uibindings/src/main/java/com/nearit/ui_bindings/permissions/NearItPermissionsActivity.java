package com.nearit.ui_bindings.permissions;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.permissions.views.PermissionButton;

import it.near.sdk.NearItManager;
import it.near.sdk.logging.NearLog;

/**
 * Created by Federico Boschini on 28/08/17.
 */

public class NearItPermissionsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "NearItPermissionsActivity";

    private GoogleApiClient mGoogleApiClient;
    private static final int BLUETOOTH_SETTINGS_CODE = 4000;
    private static final int LOCATION_SETTINGS_CODE = 5000;
    public static final int PERMISSION_REQUEST_FINE_LOCATION = 6000;

    /**
     * Flow parameters
     */
    private boolean isEnableTapToClose = false;
    private boolean isInvisibleLayoutMode = false;
    private boolean isNoBeacon = false;
    private boolean isNonBlockingBeacon = false;
    private boolean isAutoStartRadar = false;

    private boolean allPermissionsGiven = false;

    @Nullable
    private PermissionButton locationButton;
    @Nullable
    private PermissionButton bleButton;
    @Nullable
    private TextView closeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            PermissionsRequestIntentExtras params = PermissionsRequestIntentExtras.fromIntent(intent);
            isEnableTapToClose = params.isEnableTapToClose();
            isInvisibleLayoutMode = params.isInvisibleLayoutMode();
            isNoBeacon = params.isNoBeacon();
            isNonBlockingBeacon = params.isNonBlockingBeacon();
            isAutoStartRadar = params.isAutoStartRadar();
        }

        if (!isBleAvailable()) {
            isNoBeacon = true;
        }

        allPermissionsGiven = checkBluetooth() && checkLocation();

        if (!isInvisibleLayoutMode) {
            setContentView(R.layout.activity_nearui_permissions);
            locationButton = (PermissionButton) findViewById(R.id.location_button);
            bleButton = (PermissionButton) findViewById(R.id.ble_button);
            closeButton = (TextView) findViewById(R.id.close_text);
        } else {
            if (!allPermissionsGiven) {
                askPermissions();
            } else {
                setResult(Activity.RESULT_OK);
                finish();
            }
        }
    }

    public static Intent createIntent(Context context, PermissionsRequestIntentExtras params) {
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

        if(isNoBeacon && bleButton!=null) {
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

    public boolean checkLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean anyLocationProv = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        anyLocationProv |= locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

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
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    /**
     * Asks to enable bluetooth
     */
    private void openBluetoothSettings() {
        if (!checkBluetooth()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, BLUETOOTH_SETTINGS_CODE);
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
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
        }
    }

    private void onPermissionsReady() {
        // You have all the right permissions to start the NearIT radar
        if (isAutoStartRadar) {
            NearItManager.getInstance().startRadar();
        }
        setResult(Activity.RESULT_OK);
        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        setLocationButton();
        setBluetoothButton();

        //  LOCATION DIALOG CALLBACK
        if (requestCode == LOCATION_SETTINGS_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                onLocationSettingsOkResult();
            } else {
                if (isInvisibleLayoutMode) {
                    finalCheck();
                }
            }
            //  BLUETOOTH DIALOG CALLBACK
        } else if (requestCode == BLUETOOTH_SETTINGS_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //  bt on
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_FINE_LOCATION) {
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setNeedBle(isInvisibleLayoutMode && !isNoBeacon);

        final PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        onLocationSettingsOkResult();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(
                                    NearItPermissionsActivity.this,
                                    LOCATION_SETTINGS_CODE);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        finish();
    }

    private boolean isBleAvailable() {
        boolean available = false;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            BLE not supported prior to API 18
            NearLog.d(TAG, "BLE not supported prior to API 18");
        } else if (!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//            BLE not available
            NearLog.d(TAG, "BLE not available on this device");
        } else {
            available = true;
        }
        return available;
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