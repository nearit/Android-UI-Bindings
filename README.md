# NearIT-UI for Android
NearIT-UI is an open-source library that provides customizable UI bindings on top of the core [NearIT SDK](https://github.com/nearit/Android-SDK).
This library aims to minimize the effort of creating UI for NearIT contents and dialogs.

For example, using NearIT-UI, a developer can launch a dialog for requesting the user location/bluetooth permissions by writing a few lines of code.

#### Features
- [Permissions request](docs/PERMISSIONS.md)
- [Coupon detail](docs/COUPON.md)
- [Feedback request](docs/FEEDBACK.md)

#### Upcoming features
- Heads up notifications for in-app content
- Content notifications
- Missing permissions alert snackbar
- Coupon list

## Configuration
Add the NearIT-UI library dependency. If your project uses Gradle build system, add the following dependency to `build.gradle` of your app:

```groovy
 dependencies {
    //  ...
    compile 'it.nearit.sdk:nearit-ui:1.0.4'
 }
```

**Important**: NearIT-UI will only work with NearIT SDK version 2.2.0 or higher.
