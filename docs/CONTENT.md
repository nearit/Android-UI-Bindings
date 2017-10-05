# NearIt-UI for content notification
#### Basic example
If you want your app to display a NearIT content in a pop-up dialog, use this simple code:

```java
 // ...
 startActivity(
         NearITUIBindings.getInstance(YourActivity.this)
            .createContentIntentBuilder(content)
            .build());
```

where, `content` is an instance of NearIT SDK `Content` class. Further information on contents and other in-app content can be found [here](http://nearit-android.readthedocs.io/en/latest/in-app-content/).

![NearIT-UI content dialog](content.png)

If the user taps on the button, the link will be open: some types of link will be managed by a specific app, if that app is installed (e.g. a Facebook link).

#### Advanced examples
If you want to enable the tap outside to close functionality, add `.enableTapOutsideToClose()` call on the builder, like the following example:
```java
 // ...
 startActivity(
         NearITUIBindings.getInstance(YourActivity.this)
            .createContentIntentBuilder(content)
            .enableTapOutsideToClose()
            .build());
```

Methods `setSeparatorResourceId(R.drawable.your_separator_drawable)` and `setNoSeparator()` are available for both of the builders.

## UI Customization
If you need to tweak the way your dialog looks, you can override some resources by name in your application.
Placing an xml file named `nearit_ui_selector_cta_button.xml` in your app `res/drawable` directory will replace the default button at the bottom of the content dialog and make it look the way you prefer. 