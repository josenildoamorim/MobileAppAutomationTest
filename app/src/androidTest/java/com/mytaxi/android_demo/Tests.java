package com.mytaxi.android_demo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class Tests extends PageObjects {

    private String username;
    private String password;
    private String wrongUsername;
    private String wrongPassword;
    private String driverName;
    private String search;

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setCredentials() {
        username = "whiteelephant261";
        password = "video1";
        wrongUsername = "blueelephant123";
        wrongPassword = "audio2";
        driverName = "Sarah Friedrich";
        search = "sa";
    }

    @Test
    public void verifyInitialState() {
        onView(withId(R.id.edt_username)).check(matches(isDisplayed()));
        onView(withId(R.id.edt_password)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void loginSuccessful() throws InterruptedException {
        typeCredentials(username, password);
        Thread.sleep(2000);

        onView(withText(R.string.message_login_fail)).check(doesNotExist());
    }

    @Test
    public void tryLoginWithWrongCredentials() throws InterruptedException {
        typeCredentials(wrongUsername, wrongPassword);
        Thread.sleep(2000);

        onView(withText(R.string.message_login_fail)).check(doesNotExist());
    }

    @Test
    public void tryLoginWithBlankUsername() throws InterruptedException {
        typeEmptyCredentials(R.id.edt_password, password);

        Thread.sleep(2000);
        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }

    @Test
    public void tryLoginWithBlankPassword() throws InterruptedException {
        typeEmptyCredentials(R.id.edt_username, username);

        Thread.sleep(2000);
        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }

    @Test
    public void tryLoginWithAllBlankFields() throws InterruptedException {
        typeEmptyCredentials(BOTH_FIELDS_ID, "");

        Thread.sleep(2000);
        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }

    @Test
    public void selectAnSearchedDriver() throws InterruptedException {
        loginSuccessful();
        typeAnSearch(search);
        Thread.sleep(2000);
        selectDriver(driverName);
        Thread.sleep(2000);

        onView(withText(driverName)).perform(scrollTo()).perform(click());
        onView(withId(R.id.textViewDriverName)).check(matches(withText(driverName)));
    }

    @Test
    public void callToAnSelectedDriver() throws InterruptedException {
        selectAnSearchedDriver();
        callDriver();
    }





}
