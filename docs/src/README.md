# NearIT-UI for Android

[![API](https://img.shields.io/badge/API-15%2B-blue.svg?style=flat)](https://developer.android.com/about/dashboards/index.html#Platform) [![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
[![Download](https://api.bintray.com/packages/nearit/NearIT-Android-SDK/it.near.sdk%3Anearit-ui/images/download.svg) ](https://bintray.com/nearit/NearIT-Android-SDK/it.near.sdk%3Anearit-ui/_latestVersion)
![CircleCI](https://circleci.com/gh/nearit/Android-UI-Bindings.svg?style=svg)

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

## Customization
Each feature has its own `UI Customization` section. To globally customize fonts and text colors, please see [here](docs/GLOBAL_CUSTOMIZATION.md).

## Configuration
Add the NearIT-UI library dependency. If your project uses Gradle build system, add the following dependency to `build.gradle` of your app:

```groovy
 dependencies {
    //  ...
    implementation 'it.near.sdk:nearit-ui:@@versionNumber@@'
 }
```

**Important**: NearIT-UI will only work with NearIT SDK version @@nearitVersionNumber@@ or higher.