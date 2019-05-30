package com.nearit.ui_bindings.utils;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import it.near.sdk.logging.NearLog;

/**
 * @author Federico Boschini
 */

public class PermissionsUtils {

    private final static String TAG = "PermissionsUtils";

    public final static int LOCATION_PERMISSION_GRANTED = 0;
    public final static int LOCATION_PERMISSION_ONLY_IN_USE = 1;
    public final static int LOCATION_PERMISSION_DENIED = -1;

    /**
     * Checks the location permission. On devices with API < 23 the permission is granted on app installation,
     * and this method will always return 'true'.
     *
     * @param context a valid Context
     * @return {@link PermissionsUtils#LOCATION_PERMISSION_GRANTED} if "fully" granted (even in background)
     * {@link PermissionsUtils#LOCATION_PERMISSION_ONLY_IN_USE} if granted while app is in use (Android Q)
     * {@link PermissionsUtils#LOCATION_PERMISSION_DENIED} otherwise
     */
    public static int checkLocationPermission(Context context) {
        // TODO: fix check when Q is stable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (checkBackgroundLocationPermission(context)) {
                return LOCATION_PERMISSION_GRANTED;
            } else if (checkForegroundLocationPermission(context)) {
                return LOCATION_PERMISSION_ONLY_IN_USE;
            }
        } else if (checkForegroundLocationPermission(context)) {
            return LOCATION_PERMISSION_GRANTED;
        }
        return LOCATION_PERMISSION_DENIED;
    }

    /**
     * Checks the background location permission.
     *
     * @param context a valid Context
     * @return true if granted, false otherwise
     */
    public static boolean checkBackgroundLocationPermission(Context context) {
        // TODO: fix check when Q is stable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;
        } else return checkForegroundLocationPermission(context);
    }

    /**
     * Checks the foreground location permission.
     *
     * @param context a valid Context
     * @return true if granted, false otherwise
     */
    public static boolean checkForegroundLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * This method checks if the location setting is on.
     *
     * @param context a valid Context
     * @return 'true' if the location is on, 'false' otherwise.
     */
    public static boolean checkLocationServices(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                || (locationManager != null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    /**
     * This method checks if the Bluetooth adapter is enabled.
     *
     * @param context a valid Context
     * @return 'true' if BLE feature is available and API version is 18+ and the adapter is enabled
     * 'false' otherwise.
     */
    public static boolean checkBluetooth(Context context) {
        return !isBleAvailable(context)
                || (BluetoothAdapter.getDefaultAdapter() != null
                && BluetoothAdapter.getDefaultAdapter().isEnabled());
    }


    /**
     * Checks for BLE availability. It depends on API version and on the adapter itself.
     *
     * @param context a valid Context
     * @return 'true' if it is available, 'false' otherwise
     */
    public static boolean isBleAvailable(Context context) {
        boolean available = false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            NearLog.d(TAG, "BLE not supported prior to API 18");
        } else if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            NearLog.d(TAG, "BLE not available on this device");
        } else {
            available = true;
        }
        return available;
    }

    /**
     * Checks if flight mode is ON. Works with every API version
     *
     * @param context a valid Context
     * @return 'true' if flight mode is on, 'false' otherwise
     */
    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    /**
     * Checks if notifications are enabled. Works with every API version
     * <p>
     * Please, check {@link NotificationManagerCompat#areNotificationsEnabled()} for implementation
     *
     * @param context a valid Context
     * @return 'true' if notifications are enabled, 'false' otherwise
     */
    public static boolean areNotificationsEnabled(Context context) {
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    /**
     * Returns the user specified importance for notifications from the calling package.
     *
     * @param context a valid Context
     * @return An importance level, such as {@link NotificationManagerCompat#IMPORTANCE_DEFAULT}.
     */
    public static int getNotificationsImportanceLevel(Context context) {
        return NotificationManagerCompat.from(context).getImportance();
    }

}
