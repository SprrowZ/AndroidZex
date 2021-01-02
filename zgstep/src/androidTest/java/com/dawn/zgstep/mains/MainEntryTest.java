package com.dawn.zgstep.mains;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.dawn.zgstep.R;
import com.dawn.zgstep.ui.activity.ZStepMainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainEntryTest { //JUnit4

   @Rule
    public ActivityTestRule<ZStepMainActivity> mActivityRule =
           new ActivityTestRule<>(ZStepMainActivity.class);

   @Test
    public void testOnClick(){
    onView(withId(R.id.btn1)).perform(click());
    // onView(withId(R.id.btn2)).perform(typeText("3333"));
    }
}