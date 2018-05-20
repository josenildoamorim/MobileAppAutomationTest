package com.mytaxi.android_demo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTests extends PageObjects {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setCredentials() {
        setData();
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
        typeEmptyCredentials(BOTH_FIELDS_ID, "");

        Thread.sleep(2000);
        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }

    @Test
    public void test_FloginSuccessful() throws InterruptedException {
        typeCredentials(username, password);
        Thread.sleep(2000);

        onView(withText(R.string.message_login_fail)).check(doesNotExist());
    }
}
