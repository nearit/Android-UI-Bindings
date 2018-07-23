# Global customization

## Fonts
If you want to apply fonts that match your app style, you have to override our base styles.

In your `res/values/styles.xml` do something like this:

```xml
<!--    REGULAR     -->
    <style name="NearItUIGlobalTheme.RegularText" parent="NearItUIGlobalTheme">
        <item name="fontFamily">@font/your_font</item>
    </style>

    <!--    ITALIC      -->
    <style name="NearItUIGlobalTheme.ItalicText" parent="NearItUIGlobalTheme">
        <item name="fontFamily">@font/your_font</item>
    </style>

    <!--    BOLD        -->
    <style name="NearItUIGlobalTheme.BoldText" parent="NearItUIGlobalTheme">
        <item name="fontFamily">@font/your_font</item>
    </style>

    <!--    MEDIUM      -->
    <style name="NearItUIGlobalTheme.MediumText" parent="NearItUIGlobalTheme">
        <item name="fontFamily">@font/your_font</item>
    </style>

    <!--    BOLDITALIC      -->
    <style name="NearItUIGlobalTheme.BoldItalicText" parent="NearItUIGlobalTheme">
        <item name="fontFamily">@font/your_font</item>
    </style>

    <!--    MEDIUMITALIC        -->
    <style name="NearItUIGlobalTheme.MediumItalicText" parent="NearItUIGlobalTheme">
        <item name="fontFamily">@font/your_font</item>
    </style>
```

where `your_font` is a valid `.ttf` file.

__Android Studio 2.3__: put your `.ttf` files in `res/raw/` instead of `res/font/`

## Text colors
To replace text colors, please override one or more of the following resources (e.g. in your `res/values/colors.xml`):

```xml
    <color name="nearit_ui_black_text">#333333</color>
    <color name="nearit_ui_grey_text">#777777</color>
    <color name="nearit_ui_grey_pressed_text">#80777777</color>
    <color name="nearit_ui_white_text">#FFFFFF</color>
    <color name="nearit_ui_green_text">#68C600</color>
    <color name="nearit_ui_red_text">#FF314F</color>
```