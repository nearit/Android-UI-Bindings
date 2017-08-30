package com.nearit.ui_bindings.permissions;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
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

/**
 * Created by Federico Boschini on 28/08/17.
 */

public class PermissionsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    private static final int BLUETOOTH_SETTINGS_CODE = 4000;
    private static final int LOCATION_SETTINGS_CODE = 5000;
    public static final int PERMISSION_REQUEST_FINE_LOCATION = 6000;

    private boolean permissionGiven = false;
    private PermissionsRequestIntentExtras params;
    private boolean isEnableTapToClose = false;
    private boolean isInvisibleLayoutMode = false;
    private boolean isAutoStartRadar = false;
    private boolean isNoBLE = false;
    private boolean isNonBlockingBLE = false;

    @Nullable
    private Button bleButton;
    @Nullable
    private TextView closeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        Intent intent = getIntent();
        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            params = PermissionsRequestIntentExtras.fromIntent(intent);
            isEnableTapToClose = params.isEnableTapToClose();
            isInvisibleLayoutMode = params.isInvisibleLayoutMode();
            isAutoStartRadar = params.isAutoStartRadar();
            isNoBLE = params.isNoBLE();
            isNonBlockingBLE = params.isNonBlockingBLE();
        }

        if (!isInvisibleLayoutMode) {
            setContentView(R.layout.activity_nearui_permissions);
            bleButton = (Button) findViewById(R.id.ble_button);
            closeButton = (TextView) findViewById(R.id.close_text);
        } else {
            if (!permissionGiven) {
                askPermissions();
            } else {
                setResult(Activity.RESULT_OK);
                finish();
            }
        }


//        just a reminder
//        bleButton.setCompoundDrawables(spunta, null, null, null);

    }

    public static Intent createIntent(Context context, PermissionsRequestIntentExtras params) {
        return new Intent(context, PermissionsActivity.class).putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (isNoBLE) {
            if (bleButton != null) {
                bleButton.setVisibility(View.GONE);
            }
        } else {
            if (bleButton != null) {
                bleButton.setVisibility(View.VISIBLE);
            }
        }

        if (closeButton != null) {
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            if (isEnableTapToClose) {
                finish();
            }
            return true;
        }
        return super.onTouchEvent(event);
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
     * Asks for location permissions.
     */
    private void requestFineLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
        }
    }

    private void openBluetoothSettings() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, BLUETOOTH_SETTINGS_CODE);
        } else {
            onPermissionsReady();
        }

    }

    private void onPermissionsReady() {
        // You have all the right permissions to start the NearIT radar
        permissionGiven = true;
        setResult(Activity.RESULT_OK);
        finish();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setNeedBle(true);

        final PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // The bluetooth permissions are strictly necessary for beacons,
                        // but not for geofences
                        openBluetoothSettings();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(
                                    PermissionsActivity.this,
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

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
