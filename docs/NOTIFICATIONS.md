# NearIt-UI, heads-up notifications for in-app content
NearIt-UI can manage and show in-app content (read more about background and foreground notifications[here](http://nearit-android.readthedocs.io/en/latest/in-app-content/))
for you. When a user enters in a specific beacon range, an heads-up notification (on devices with Android API level 21+) will show. If the user clicks on it, the right dialog will appear and the right tracking events will be handled.

To enable this behaviour you have to add one line of code.
You *MUST* add this in the `onCreate()` method of your `Application`

```java
public class YourApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NearITUIBindings.enableAutomaticForegroundNotifications(this);
        //  ...
    }
}
```


## Background and foreground notifications

NearIT-SDK has a built-in receiver that will handle every background notification.
If you want to directly show contents with the convenient UI provided by this library, you have to add the `NearItUIBroadcastReceiver` to the manifest of your application.

You can simply copy the following code and paste it in the `<application>` scope of the manifest.

```xml
<receiver
    android:name="com.nearit.ui_bindings.notifications.NearItUIBroadcastReceiver"
    android:exported="false">
    <intent-filter>
        <action android:name="it.near.sdk.permission.PUSH_MESSAGE" />
            <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter>
        <action android:name="it.near.sdk.permission.GEO_MESSAGE" />
            <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</receiver>
```