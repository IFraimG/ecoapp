package com.example.ecoapp;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.ecoapp.presentation.fragments.SearchFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SearchInstrumentedTest {
    @Before
    public void setUp() {
        FragmentScenario.launchInContainer(SearchFragment.class);
    }

    @Test
    public void testSearchFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.foundRecyclerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.foundRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        Espresso.onView(ViewMatchers.withId(R.id.search_bar_edit_text))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));
    }
}