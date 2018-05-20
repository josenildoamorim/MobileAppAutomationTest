package com.mytaxi.android_demo;

import android.support.test.espresso.matcher.ViewMatchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PageObjects {

    public String username;
    public String password;
    public String wrongUsername;
    public String wrongPassword;
    public String driverName;
    public String search;

    protected static final int BOTH_FIELDS_ID = -1;

    protected void setData(){
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

    protected void typeEmptyCredentials(int notEmptyFieldId, String value) throws InterruptedException {
        if (notEmptyFieldId != BOTH_FIELDS_ID)
            onView(withId(notEmptyFieldId)).perform(typeText(value), closeSoftKeyboard());

        onView(withId(R.id.btn_login)).perform(click());
    }

    protected void typeAnSearch(String search){
        onView(ViewMatchers.withId(R.id.textSearch)).perform(typeText(search));
    }

    protected void selectDriver(String name){
        onView(withId(R.id.textViewDriverName)).check(matches(isDisplayed()));
        onView(withId(R.id.fab)).perform(click());
    }

    protected void clickCallDriverBtn(){
        onView(withId(R.id.fab)).perform(click());


    }
}

