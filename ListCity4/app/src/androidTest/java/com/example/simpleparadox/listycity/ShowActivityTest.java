package com.example.simpleparadox.listycity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ShowActivityTest {
    private Solo solo;
    private String MOCK_CITY = "Edmonton";

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void checkSwitching() {
        // Check whether activity correctly switched
        // (1) Create a new city in list
        solo.assertCurrentActivity("Not in Main Activity", MainActivity.class);
        solo.clickOnButton("ADD CITY");
        solo.enterText((EditText) solo.getView(R.id.editText_name), MOCK_CITY);
        solo.clickOnButton("CONFIRM");
        assertTrue(solo.waitForText(MOCK_CITY, 1, 2000));

        // (2) Click on the city in the list
        MainActivity activity = (MainActivity) solo.getCurrentActivity();
        final ListView cityList = activity.cityList;
        TextView city = (TextView) cityList.getChildAt(0);
        solo.clickOnView(city);

        // (3) Assert that activity has changed
        solo.assertCurrentActivity("Not in Show Activity", ShowActivity.class);
    }

    @Test
    public void checkCityName() {
        // Test whether the city name is consistent
        checkSwitching(); // switch to ShowActivity
        // Search for the text Edmonton
        assertTrue(solo.searchText(MOCK_CITY));
    }

    @Test
    public void checkBackButton() {
        // Test back button
        checkSwitching(); // switch to ShowActivity
        solo.clickOnButton("BACK");

        solo.assertCurrentActivity("Not in Main Activity", MainActivity.class);

    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
