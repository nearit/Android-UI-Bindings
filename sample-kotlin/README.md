# How to run this sample

* Load the project in Android Studio.

* Edit the app module `build.gradle` file to depend on the published NearIT-UI library (recommended) or to depend on the local module.

```groovy
    //  published version
    compile 'it.nearit.sdk:nearit-ui:X.Y.Z'
    //  local module
    compile project(':nearit-ui')
```

* Add a `nearit_api` variable to the app `local.properties` file.

```
    nearit_api=your-secret-nearit-api-key
```

* Build and run the sample.
