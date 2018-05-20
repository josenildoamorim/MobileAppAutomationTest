package com.mytaxi.android_demo;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import com.mytaxi.android_demo.activities.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class DriverTests extends PageObjects {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<MainActivity>
            mActivityRule = new ActivityTestRule<>(MainActivity.class, false, true);

    @Before
    public void setup() {
        setData();
    }

    @Test
    public void selectAnSearchedDriver() throws InterruptedException {
        typeCredentials(username, password);
        Thread.sleep(1000);
        typeAnSearch(search);
        Thread.sleep(2000);
        selectDriver(driverName);
        Thread.sleep(2000);

        onView(withText(driverName)).perform(scrollTo()).perform(click());
        onView(withId(R.id.textViewDriverName)).check(matches(withText(driverName)));
    }

    @Test
    public void callToAnSelectedDriver() throws InterruptedException {
        Intents.init();
        selectAnSearchedDriver();
        Thread.sleep(2000);
        Matcher<Intent> matcher = toPackage("com.android.contacts");

        ActivityResult result = new ActivityResult(Activity.RESULT_OK, null);
        intending(matcher).respondWith(result);

        clickCallDriverBtn();
        Thread.sleep(1000);
        intended(matcher);
        Intents.release();
    }
}
