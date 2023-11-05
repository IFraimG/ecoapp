package com.example.ecoapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.presentation.fragments.HabitFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

//    @Test
//    public void useAppContext() {
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("com.example.ecoapp", appContext.getPackageName());
//    }

    @Before
    public void init() {
        activityRule.getActivity().getSupportFragmentManager().beginTransaction().add(new HabitFragment(), "habit");
    }

    @Test
    public void teTest() {
        onView(withText("Hello world!")).check(matches(isDisplayed()));
    }
}