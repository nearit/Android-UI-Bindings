# Fonts support

If you want to change fonts on our view, you have to override the style of that view.

In your `res/values/styles.xml` do something like this:

```xml
<!--    ...     -->

<style name="NearItUIPermissionsExplanationTextAppearance">
    <!--    ...     -->
    <item name="fontFamily">@font/your_font</item>
</style>
<!--    ...     -->
```

where `your_font` is a valid `.ttf` file.

__Android Studio 2.3__: put your `.ttf` files in `res/raw/` instead of `res/font/`