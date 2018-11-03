package com.udacity.gradle.builditbigger;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test class to tes the main activity
 *
 * Created by mduby on 9/6/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityJokeIntentTest {

    // instance rule
    @Rule
    public IntentsTestRule<MainActivity> mainActivityIntentsTestRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    /**
     * test that an intent is fired when the main activity recycler view item is clicked
     *
     */
    @Test
    public void clickJokeButton_CheckIntent() {
        // perform action
        onView(withId(R.id.tell_joke_button)).perform(click());

        // make sure the selected recipe intent is sent
        intended(toPackage("com.udacity.gradle.builditbigger"));
    }

}
