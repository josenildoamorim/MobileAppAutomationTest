package com.mytaxi.android_demo;

import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class PageObjects {

    public String username;
    public String password;
    public String wrongUsername;
    public String wrongPassword;
    public String driverName;
    public String search;

    protected static final int BOTH_FIELDS_ID = -1;

    protected void setup(){
        username = "whiteelephant261";
        password = "video1";
        wrongUsername = "blueelephant123";
        wrongPassword = "audio2";
        driverName = "Sarah Friedrich";
        search = "sa";
    }

    protected void typeCredentials(String username, String password){
        onView(withId(R.id.edt_username)).perform(typeText(username));
        onView(withId(R.id.edt_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
    }

    protected void typeEmptyCredentials(int notEmptyFieldId, String value){
        if (notEmptyFieldId != BOTH_FIELDS_ID)
            onView(withId(notEmptyFieldId)).perform(typeText(value), closeSoftKeyboard());

        onView(withId(R.id.btn_login)).perform(click());
    }

    protected void typeAnSearch(String search){
        onView(ViewMatchers.withId(R.id.textSearch)).perform(typeText(search));
    }

    protected void selectDriver(String name){
        onView(withText(name)).inRoot(RootMatchers.isPlatformPopup()).perform(click());
    }

    protected void clickCallDriverBtn(){
        onView(withId(R.id.fab)).perform(click());
    }

    protected void logout() {
        onView(withContentDescription("Open navigation drawer")).perform(click());
        onView(withText("Logout")).perform(click());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    protected void selectAnSearchedDriver() {
        typeAnSearch(search);
        selectDriver(driverName);

        onView(withId(R.id.textViewDriverName)).check(matches(withText(driverName)));
    }

}

