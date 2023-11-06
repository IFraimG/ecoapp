package com.example.ecoapp;

import com.example.ecoapp.presentation.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class ActivityUnitTest {
    private MainActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveWelcomeFragment() {
        assertNotNull(activity.getFragmentManager().findFragmentById(R.id.homeFragment));
    }
}