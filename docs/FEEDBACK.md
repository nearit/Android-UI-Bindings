# NearIt-UI for feedback request pop-up
#### Basic example
If you want your app to display a feedback request in a beautiful pop-up dialog, use this simple code:

```java
 // ...
 startActivity(
         NearITUIBindings.getInstance(YourActivity.this)
            .createFeedbackIntentBuilder(feedback)
            .build());
```

where, `feedback` is an instance of NearIT SDK `Feedback` class. Further information on coupons and other in-app content can be found [here](http://nearit-android.readthedocs.io/en/latest/in-app-content/).
The Feedback UI also takes care of delivering the user response to the SDK library and showing the proper success or failure status of the user action.

![NearIT-UI feedback request dialog, success](feedback_request_success.gif)
![NearIT-UI feedback request dialog, fail](feedback_request_fail.gif)

#### Advanced examples
If you need to simplify the feedback request you are able to ask the user for the 1 to 5 rating only, without any textual comment (please notice that the text response is optional in every scenerio), you can hide the text box adding one method call:

```java
  // ...
  startActivity(
          NearITUIBindings.getInstance(YourActivity.this)
             .createFeedbackIntentBuilder(feedback)
             .withoutComment()
             .build());
```


![NearIT-UI no text response feedback dialog](feedback_no_text.png)

Optionally, you can display the feedback request in your custom Activity by adding a Fragment to it. You can get a Fragment via another builder:

```java
  // ...
  Fragment feedbackFragment = NearITUIBindings.getInstance(YourActivity.this)
        .createFeedbackFragmentBuilder(feedback)
        //  here you can call other methods of the builder
        .build();
```

If you need to tweak the way your dialog looks, you can override some resources (see [UI Customization](#ui-customization)).
To replace or to hide the success icon you can call these methods
`setSuccessIconResId(R.drawable.your_custom_drawable)` 
`setNoSuccessIcon()`
on both of the builders.

## UI Customization

If you wish to change some messages, the existing strings can be overridden by name in your application. For example, consider the following `res/values/strings.xml`

```xml
<resources>
    <!-- -->
    <string name="nearit_ui_feedback_error_message">Your error message!</string>
    <string name="nearit_ui_feedback_close_button">Close the dialog text</string>
    <string name="nearit_ui_feedback_send_success_message">Your custom success message</string>
    <!-- -->
</resources>
```

these string resources will replace those that are provided by NearIT-UI library.

The same is for colors. Please have a look at this `res/values/colors.xml`

```xml
    <!-- -->
    <color name="nearit_ui_feedback_background_color">your_background_color</color>
    <color name="nearit_ui_feedback_comment_box_border_color">your_comment_box_border_color</color>
    <color name="nearit_ui_feedback_comment_box_background_color">your_comment_box_background_color</color>
    <!-- -->
```

To replace the drawables of the rating bar you have to provide your own `drawable/nearit_ui_feedback_rating_bar.xml` that should have at least this structure:

```xml
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@android:id/background"
        android:drawable="@drawable/your_tipically_empty_star" />
    <item android:id="@android:id/progress"
        android:drawable="@drawable/your_tipically_full_star_drawable" />
</layer-list>
```

The confirmation button can be replaced by a custom one that match the style of your application. A custom `nearit_ui_selector_feedback_button.xml` should be something like this:

```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_activated="true" android:state_pressed="true" android:drawable="@drawable/your_error_button_when_pressed" />
    <item android:state_pressed="true" android:drawable="@drawable/your_default_button_when_is_pressed" />
    <item android:state_activated="true" android:drawable="@drawable/your_error_button" />
    <item android:drawable="@drawable/your_default_button" />
</selector>
```