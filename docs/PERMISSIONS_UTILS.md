# NearIt-UI, permissions utils

If your are dealing with app permissions, turning on and off location and bluetooth, flight-mode and so on, maybe the following utilities can help you when implementing a UX.

For example, before invoking our permissions UIs, you should check for permissions/services status.

`PermissionsUtils` class, located in `com.nearit.ui_bindings.utils` package expose some useful static methods:

- `checkLocationPermission(Context context)`: returns true if permission (runtime permission on devices with API 23+) is granted

- `checkLocationServices(Context context)`: returns true if location services are available

- `checkBluetooth(Context context)`: returns true if bluetooth is ON

- `isBleAvailable(Context context)`: returns true if BLE hardware/software is available on the device

- `isAirplaneModeOn(Context context)`: returns true if airplane/flight mode has been turned ON

- `areNotificationsEnabled(Context context)`: true if notifications are enabled, false otherwise