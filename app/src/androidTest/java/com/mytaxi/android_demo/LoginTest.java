package com.mytaxi.android_demo;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.activities.AuthenticationActivity;
import com.mytaxi.android_demo.activities.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<AuthenticationActivity>
            mActivityRule = new ActivityTestRule<>(AuthenticationActivity.class, false, true);
    @Test
    public void verifyInitialState() {
        onView(withId(R.id.edt_username)).check(matches(isDisplayed()));
        onView(withId(R.id.edt_password)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void loginSuccessful() {
//        onView(withId(R.id.edt_username)).perform(typeText("whiteelephant261"));
//        onView(withId(R.id.edt_password)).perform(typeText("video1"), closeSoftKeyboard());
//        onView(withId(R.id.btn_login)).perform(click());

        Intents.init();
        onView(withId(R.id.edt_username)).perform(typeText("whiteelephant261"));
        onView(withId(R.id.edt_password)).perform(typeText("video1"));
        Matcher<Intent> matcher = hasComponent(MainActivity.class.getName());

        ActivityResult result = new ActivityResult(Activity.RESULT_OK, null);
        intending(matcher).respondWith(result);

        onView(withId(R.id.btn_login)).perform(click());
        intended(matcher);
        Intents.release();
    }

    @Test
    public void tryLoginWithBlankUsername() throws InterruptedException {
        onView(withId(R.id.edt_password)).perform(typeText("video1"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(1000);
        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }

    @Test
    public void tryLoginWithBlankPassword() throws InterruptedException {
        onView(withId(R.id.edt_username)).perform(typeText("whiteelephant261"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(1000);
        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }

    @Test
    public void tryLoginWithAllBlankFields() throws InterruptedException {
        onView(withId(R.id.btn_login)).perform(click(), closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }
}
