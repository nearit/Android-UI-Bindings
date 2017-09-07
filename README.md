# NearIT-UI for Android
NearIT-UI is an open-source library that provides customizable UI bindings on top of the core [NearIT SDK](https://github.com/nearit/Android-SDK).
This library aims to minimize the effort needed to create the UI for NearIT contents and dialogs.

For example, using NearIT-UI, a developer can launch a dialog to request location/bluetooth permissions to the user via a few lines of code.

#### Upcoming features
- Feedback dialog
- Coupon detail
- Coupon list
- Heads up notifications
- Permissions alert snackbar

## Configuration
Add the NearIT-UI library dependency. If your project uses Gradle build system, add the following dependency:
```groovy
 dependencies {
    //  ...
    compile 'it.nearit.sdk:nearitui:alpha'
 }
```

## NearIt-UI for permissions request
If your app integrates NearIT services you surely want that your user gives your app the location permission. NearIT supports the use of Beacon technology, so bluetooth could be a requirement for your app.

To launch a permission request, your app should start an activity and wait for its result. The activity is provided by our convenient and customizable builder. It exposes a number of methods that enable a developer to define which permissions are needed and how to request them.

#### Basic example
If you just want your app to ask user for both location and bluetooth permissions (and turning on both), you can use the following code:
```java
 // You can choose an arbitrary request code
 private static final int NEAR_PERMISSION_REQUEST = 1000;
 
 // ...
 
 startActivityForResult(
        NearITUIBindings.getInstance(YourActivity.this)
                .createPermissionRequestIntentBuilder()
                .build(),
        NEAR_PERMISSION_REQUEST);
```
then you can check in onActivityResult(...) if it succeded or failed.

#### More examples
You can define the permissions request behaviour via our builder.

If your app does not use Beacons technology, you should not ask user to turn on the Bluetooth adapter. You can achieve this via `noBeacon()` method.
```java
 startActivityForResult(
        NearITUIBindings.getInstance(YourActivity.this)
                .createPermissionRequestIntentBuilder()
                .noBeacon()
                .build(),
        NEAR_PERMISSION_REQUEST);
```

If your app uses Beacons but you consider the bluetooth a non-blocking requirement, just use `nonBlockingBeacon()` method
```java
 startActivityForResult(
        NearITUIBindings.getInstance(YourActivity.this)
                .createPermissionRequestIntentBuilder()
                .nonBlockingBeacon()
                .build(),
        NEAR_PERMISSION_REQUEST);
```

#### No-UI request
The previous examples can be reproduced without UI. Our `Intent` will cause the opening of system dialogs that ask user for permissions.

In order to kick off this flow without UI you should call `invisibleLayoutMode()` and then the methods needed to define the behaviour of the flow (see [More examples](#more-examples)).
```java
 startActivityForResult(
        NearITUIBindings.getInstance(YourActivity.this)
                .createPermissionRequestIntentBuilder()
                .invisibleLayoutMode()
                // ...
                .build(),
        NEAR_PERMISSION_REQUEST);
```

## UI Customization


If you wish to change the string message, the existing strings can be overridden by name in your application. See the module's strings.xml file and simply redefine a string to change it:
```xml

```