package com.mytaxi.android_demo;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.uiautomator.UiDevice;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests extends PageObjects {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            // Save to external storage (usually /sdcard/screenshots)
            File path = new File("/home/circleci/code/app/build/eports/androidTests/connected/screenshots/" + getTargetContext().getPackageName());
            if (!path.exists()) {
                path.mkdirs();
            }

            // Take advantage of UiAutomator screenshot method
            UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            String filename = description.getClassName() + "-" + description.getMethodName() + ".png";
            device.takeScreenshot(new File(path, filename));
        }
    };

    @Before
    public void setCredentials() {
        setup();
    }

    @Test
    public void testA_verifyInitialState() {
        onView(withId(R.id.edt_username)).check(matches(isDisplayed()));
        onView(withId(R.id.edt_password)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void testB_tryLoginWithWrongCredentials() throws InterruptedException {
        typeCredentials(wrongUsername, wrongPassword);
        Thread.sleep(2000);

        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }

    @Test
    public void testC_tryLoginWithBlankUsername() throws InterruptedException {
        typeEmptyCredentials(R.id.edt_password, password);
        Thread.sleep(2000);

        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }

    @Test
    public void testD_tryLoginWithBlankPassword() throws InterruptedException {
        typeEmptyCredentials(R.id.edt_username, username);
        Thread.sleep(2000);

        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }

    @Test
    public void testE_tryLoginWithAllBlankFields() throws InterruptedException {
        typeEmptyCredentials(BOTH_FIELDS_ID,"");
        Thread.sleep(2000);

        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }

    @Test
    public void testF_loginSuccessful() throws InterruptedException {
        typeCredentials(username, password);
        Thread.sleep(2000);

        onView(withText(R.string.message_login_fail)).check(doesNotExist());
    }

    @Test
    public void testG_callToAnSelectedDriver() throws InterruptedException {
        Intents.init();

        selectAnSearchedDriver();
        clickCallDriverBtn();

        intended(allOf(hasAction(Intent.ACTION_DIAL), toPackage("com.google.android.dialer")));
        Intents.release();
    }
}
