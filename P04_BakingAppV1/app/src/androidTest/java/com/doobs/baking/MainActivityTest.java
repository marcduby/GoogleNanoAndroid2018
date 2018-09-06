package com.doobs.baking;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test class to tes the main activity
 *
 * Created by mduby on 9/6/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    // instance rule
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    /**
     * test the transition from the main activity to the recipe step list activity
     *
     */
    @Test
    public void clickRecyclerSelection_CheckNewFragment() {
        // perform action
        onView(withId(R.id.recipe_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // make sure new receipe step list view is called
        onView(withId(R.id.recipe_step_list_fragment)).check(matches(isDisplayed()));
    }
}
