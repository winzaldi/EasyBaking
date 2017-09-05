package android.fastrack.udacity.easybaking;

import android.fastrack.udacity.easybaking.ui.main.MainActivity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by winzaldi on 9/4/17.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    public static final String INGREDIENT_TITLE = "Ingredient";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;


    @Test
    public void clickListReceip_OpensDetailActivity() {

        onView(withId(R.id.item_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the DetailActivity opens with the correct receip name displayed
        onView(withId(R.id.tv_title_ingredient)).check(matches(withText(INGREDIENT_TITLE)));

    }

    @Test
    public void mainActivity(){
        onView(withId(R.id.item_list)).perform(RecyclerViewActions.scrollToPosition(2));
    }

    @Before
    public void registerIdlingResource() {
       mIdlingResource =  mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        Espresso.registerIdlingResources(mIdlingResource);

    }


}
