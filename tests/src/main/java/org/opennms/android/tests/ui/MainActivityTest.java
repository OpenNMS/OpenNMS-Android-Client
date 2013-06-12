package org.opennms.android.tests.ui;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import com.jayway.android.robotium.solo.Solo;
import com.squareup.spoon.Spoon;
import org.opennms.android.R;
import org.opennms.android.ui.MainActivity;

import java.util.ArrayList;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;
    private MainActivity activity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        solo = new Solo(getInstrumentation(), activity);

        if (solo.searchText(solo.getString(R.string.welcome_message)))
        {
            solo.clickOnButton(solo.getString(R.string.welcome_message_neg_button));
        }
    }

    public void testAboutDialog() throws Exception {
        Spoon.screenshot(activity, "initial_state");

        solo.clickOnMenuItem(solo.getString(R.string.about), true);
        assertTrue("About dialog text not found", solo.searchText(solo.getString(R.string.about_info)));
        Spoon.screenshot(activity, "dialog_opened");

        Button dialogCloseButton = (Button) solo.getView(android.R.id.button3);
        solo.clickOnButton(solo.getString(R.string.close_dialog));
        solo.sleep(200);
        Spoon.screenshot(activity, "dialog_closed");

        ArrayList<View> currentViews = solo.getCurrentViews();
        assertFalse("Not returned to MainActivity", currentViews.contains(dialogCloseButton));
    }

    public void testSettings() throws Exception {
        Spoon.screenshot(activity, "initial_state");

        solo.clickOnMenuItem(solo.getString(R.string.settings), true);
        assertTrue("SettingsActivity was not found", solo.waitForActivity("SettingsActivity"));
        Spoon.screenshot(activity, "settings_activity_opened");
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}