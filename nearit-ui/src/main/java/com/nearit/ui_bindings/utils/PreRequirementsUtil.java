package com.nearit.ui_bindings.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;

import it.near.sdk.logging.NearLog;

/**
 * Created by Federico Boschini on 28/09/17.
 */

public class PreRequirementsUtil {

    private final static String TAG = "PreRequirementsUtil";

    public static boolean checkLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) | locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean checkBluetooth(Context context) {
        return !isBleAvailable(context) || (BluetoothAdapter.getDefaultAdapter() != null && BluetoothAdapter.getDefaultAdapter().isEnabled());
    }


    /**
     * Checks for BLE availability
     */
    public static boolean isBleAvailable(Context context) {
        boolean available = false;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            NearLog.d(TAG, "BLE not supported prior to API 18");
        } else if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            NearLog.d(TAG, "BLE not available on this device");
        } else {
            available = true;
        }
        return available;
    }


}
