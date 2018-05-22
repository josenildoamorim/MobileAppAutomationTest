package com.mytaxi.android_demo;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

public class DriverTests extends PageObjects {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<MainActivity>
            mActivityRule = new ActivityTestRule<>(MainActivity.class, false, true);

    @Before
    public void setData() {
        setup();
    }

    @Test
    public void callToAnSelectedDriver() throws InterruptedException {
        Intents.init();

        selectAnSearchedDriver();
        Thread.sleep(2000);
        clickCallDriverBtn();
        Thread.sleep(2000);

        intended(allOf(hasAction(Intent.ACTION_DIAL), toPackage("com.google.android.dialer")));
        Intents.release();
    }

    public void selectAnSearchedDriver() throws InterruptedException {
        typeCredentials(username, password);
        Thread.sleep(5000);
        typeAnSearch(search);
        Thread.sleep(1000);
        selectDriver(driverName);
        Thread.sleep(1000);

        onView(withId(R.id.textViewDriverName)).check(matches(withText(driverName)));
    }

}
