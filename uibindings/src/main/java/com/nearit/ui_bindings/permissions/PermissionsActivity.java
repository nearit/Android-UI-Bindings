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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
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

/**
 * Created by Federico Boschini on 28/08/17.
 */

public class PermissionsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

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

    private boolean isBluetoothOn = false;
    private boolean isLocationOn = false;
    private boolean locationPermissionGranted = false;
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

        getWindow().setFlags(LayoutParams.FLAG_NOT_TOUCH_MODAL, LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        Intent intent = getIntent();
        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            PermissionsRequestIntentExtras params = PermissionsRequestIntentExtras.fromIntent(intent);
            isEnableTapToClose = params.isEnableTapToClose();
            isInvisibleLayoutMode = params.isInvisibleLayoutMode();
            isNoBeacon = params.isNoBeacon();
            isNonBlockingBeacon = params.isNonBlockingBeacon();
//            isAutoStartRadar = params.isAutoStartRadar();
        }

        isBluetoothOn = checkBluetooth();
        isLocationOn = checkLocation();
        allPermissionsGiven = isBluetoothOn && isLocationOn;

        if (!isInvisibleLayoutMode) {
            setContentView(R.layout.activity_nearui_permissions);
            bleButton = (PermissionButton) findViewById(R.id.ble_button);
            closeButton = (TextView) findViewById(R.id.close_text);
            locationButton = (PermissionButton) findViewById(R.id.location_button);
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
        return new Intent(context, PermissionsActivity.class).putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            if (isEnableTapToClose) {
                finalCheck();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (locationButton != null) {
            if (isLocationOn) {
                setButtonChecked(locationButton);
            } else {
                locationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        askPermissions();
                    }
                });
            }
        }

        if (isNoBeacon) {
            if (bleButton != null) {
                bleButton.setVisibility(View.GONE);
            }
        } else {
            if (bleButton != null) {
                bleButton.setVisibility(View.VISIBLE);
                if (checkBluetooth()) {
                    setButtonChecked(bleButton);
                    Log.d("BLEBUTTON", String.valueOf(bleButton.isEnabled()));
                } else {
                    bleButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openBluetoothSettings();
                        }
                    });
                }
            }
        }

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
        if (isLocationOn) {
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
        allPermissionsGiven = true;
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void onLocationSettingsOkResult() {
        isLocationOn = true;
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
                if (locationButton != null) {
                    setButtonChecked(locationButton);
                }
                if (checkBluetooth()) {
                    finalCheck();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  LOCATION DIALOG CALLBACK
        if (requestCode == LOCATION_SETTINGS_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                onLocationSettingsOkResult();
            } else {
                if (isInvisibleLayoutMode) {
                    finalCheck();
                }
                isLocationOn = false;
            }
            //  BLUETOOTH DIALOG CALLBACK
        } else if (requestCode == BLUETOOTH_SETTINGS_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //  bt on
                isBluetoothOn = true;
                if (bleButton != null) {
                    setButtonChecked(bleButton);
                }
                if (isLocationOn) {
                    finalCheck();
                }
            } else {
                if (isInvisibleLayoutMode) {
                    finalCheck();
                } else {
                    if (isNonBlockingBeacon && locationPermissionGranted && isLocationOn) {
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
                locationPermissionGranted = true;
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
                .setNeedBle(!isNoBeacon);

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
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        finish();
    }

    private void setButtonChecked(PermissionButton button) {
        button.setIcon(R.drawable.spunta);
        button.setOnClickListener(null);
        button.setEnabled(false);
        button.setActivated(true);
//        button.setClickable(false);
    }

}
