package com.rye.catcher;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.rye.catcher.project.animations.AnimMainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Create by rye
 * at 2020-05-13
 *
 * @description:
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoT1 {

    @Rule
    public ActivityTestRule<AnimMainActivity> mAct = new ActivityTestRule<>(AnimMainActivity.class);


    @Test
    public void testClick(){
     onView(withId(R.id.translate)).perform(click(),closeSoftKeyboard()).check(matches(isDisplayed()));
    }

    @Test
    public void testText(){
        onView(withText("Alpha")).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void testMatch(){
        onView(withId(R.id.rotate)).check(matches(withText("Rotate")));
    }

    @Test
    public void testCheck1(){
       // replaceText 必须展示在屏幕上且是EditText
         onView(withId(R.id.edit)).perform(replaceText("sfx"),closeSoftKeyboard(),clearText())
                .check(matches(withText("sfx")));

    }

}
