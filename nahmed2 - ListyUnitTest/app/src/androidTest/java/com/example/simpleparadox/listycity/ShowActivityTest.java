package com.example.simpleparadox.listycity;

import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShowActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<MainActivity>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        //Start main activity
        //Add city edmonton to list
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);    //assert it's the right activity, else display message
        solo.clickOnButton("ADD CITY");
        solo.enterText((EditText) solo.getView(R.id.editText_name),"Edmonton");
        solo.clickOnButton("CONFIRM");
        solo.clearEditText((EditText) solo.getView(R.id.editText_name));
        solo.waitForText("Edmonton",1,2000);
    }

    //Sees if new activity starts
    @Test
    public void start() throws Exception{
        //Click on "Edmonton" see if new activity starts
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity",ShowActivity.class);
        solo.waitForText("Edmonton",1,2000);
    }

    //Check if displayed city is correct
    @Test
    public void testCityDisplayed() throws Exception{
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity",ShowActivity.class);
        solo.waitForText("Edmonton",1,2000);

        ShowActivity activity=(ShowActivity) solo.getCurrentActivity();
        TextView text = activity.findViewById(R.id.city_display);
        String city = text.getText().toString();
        String mainCity = activity.getIntent().getStringExtra("City");
        assertEquals(mainCity,city);
    }

    //Test if clicking back button sends you back to main
    @Test
    public void backButtonTest() throws Exception{
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity",ShowActivity.class);
        solo.waitForText("Edmonton",1,2000);

        solo.clickOnButton("BACK");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.waitForText("Edmonton",1,2000);

    }

    //Clean up after testing
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
