package com.your_company.ui_bindings_sample;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.test.suitebuilder.annotation.LargeTest;
import android.support.test.uiautomator.UiDevice;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.your_company.ui_bindings_sample.utils.EspressoUtils;
import com.your_company.ui_bindings_sample.utils.UiAutomatorUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.your_company.ui_bindings_sample.utils.UiAutomatorUtils.assertViewWithTextIsVisible;
import static com.your_company.ui_bindings_sample.utils.UiAutomatorUtils.denyCurrentPermission;
import static com.your_company.ui_bindings_sample.utils.UiAutomatorUtils.denyCurrentPermissionPermanently;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InvisiblePermissions {

    private UiDevice device;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /*@Rule
    GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);*/

    @Before
    public void setUp() {
        this.device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void onStart_ifPermissionNotGranted_shouldBeAsked() {
        EspressoUtils.pause(6);

        ViewInteraction appCompatButton = onView(
                allOf(withText("Permissions"),
                        childAtPosition(
                                allOf(withId(R.id.main_activity_container),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        EspressoUtils.pause(1);

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.permissions_invisible), withText("Permissions Invisible Layout"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton2.perform(scrollTo(), click());

        EspressoUtils.pause(2);

        assertViewWithTextIsVisible(device, UiAutomatorUtils.TEXT_ALLOW);
        assertViewWithTextIsVisible(device, UiAutomatorUtils.TEXT_DENY);

        try {
            denyCurrentPermission(device);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void onStart_ifRuntimeDeniedPermanently_shouldShowDialog() {
        EspressoUtils.pause(3);

        ViewInteraction appCompatButton = onView(
                allOf(withText("Permissions"),
                        childAtPosition(
                                allOf(withId(R.id.main_activity_container),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        EspressoUtils.pause(1);

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.permissions_invisible), withText("Permissions Invisible Layout"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton2.perform(scrollTo(), click());

        EspressoUtils.pause(2);

        try {
            denyCurrentPermissionPermanently(device);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.permissions_invisible), withText("Permissions Invisible Layout"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton3.perform(scrollTo(), click());

        assertViewWithTextIsVisible(device, UiAutomatorUtils.TEXT_MISSING_LOCATION_PERMISSION);

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
