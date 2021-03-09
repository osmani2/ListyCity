package com.example.simpleparadox.listycity;

import android.app.Activity;
import android.app.Instrumentation;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MainActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<MainActivity>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    //Sees if main activity can be gotten
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    //checks if list view adds and clears
    @Test
    public void checkList(){
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);    //assert it's the right activity, else display message
        solo.clickOnButton("ADD CITY");
        solo.enterText((EditText) solo.getView(R.id.editText_name),"Edmonton");
        solo.clickOnButton("CONFIRM");
        solo.clearEditText((EditText) solo.getView(R.id.editText_name));


        /* True if there is a text: Edmonton on the screen, wait at least 2 seconds and find minimum one match. */
        assertTrue(solo.waitForText("Edmonton",1,2000));

        solo.clickOnButton("ClEAR ALL");

        //Check if edmonton is no longer on the screen
        assertFalse(solo.waitForText("Edmonton",1,2000));
    }

    //Check if item adds to list correctly
    @Test
    public void checkCityListItem(){
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);    //assert it's the right activity, else display message
        solo.clickOnButton("ADD CITY");
        solo.enterText((EditText) solo.getView(R.id.editText_name),"Edmonton");
        solo.clickOnButton("CONFIRM");
        solo.clearEditText((EditText) solo.getView(R.id.editText_name));
        solo.waitForText("Edmonton",1,2000);

        //Check if "Edmonton" in list
        MainActivity activity=(MainActivity) solo.getCurrentActivity();
        final ListView cityList = activity.cityList;
        String city = cityList.getItemAtPosition(0).toString();
        assertEquals("Edmonton",city);
    }

    //Clean up after testing
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
