# NearIT-UI for Android
NearIT-UI is an open-source library that provides customizable UI bindings on top of the core [NearIT SDK](https://github.com/nearit/Android-SDK).
This library aims to minimize the effort of creating UI for NearIT contents and dialogs.

For example, using NearIT-UI, a developer can launch a dialog for requesting the user location/bluetooth permissions by adding a few lines of code.

#### Features
Permissions related:
- [Permissions request](docs/PERMISSIONS.md)
- [Missing permissions alert bar](docs/PERMISSIONBAR.md)
- [Permissions utils](docs/PERMISSIONS_UTILS.md)

Content related:
- [Handle in-app content](docs/NOTIFICATIONS.md)
- [Coupon detail](docs/COUPON.md)
- [Feedback request](docs/FEEDBACK.md)
- [Content detail](docs/CONTENT.md)
- [Coupon list](docs/COUPON_LIST.md)

## Configuration
Add the NearIT-UI library dependency. If your project uses Gradle build system, add the following dependency to `build.gradle` of your app:

```groovy
 dependencies {
    //  ...
    compile 'it.near.sdk:nearit-ui:@@versionNumber@@'
 }
```

**Important**: NearIT-UI will only work with NearIT SDK version @@nearitVersionNumber@@ or higher.