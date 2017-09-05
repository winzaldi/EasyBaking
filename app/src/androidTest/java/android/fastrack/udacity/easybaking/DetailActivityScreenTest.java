package android.fastrack.udacity.easybaking;

import android.fastrack.udacity.easybaking.ui.detail.DetailActivity;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by winzaldi on 9/4/17.
 */

@RunWith(AndroidJUnit4.class)
public class DetailActivityScreenTest {


    @Rule
    public ActivityTestRule<DetailActivity> mDetailActivityRule
            = new ActivityTestRule<>(DetailActivity.class);

    @Test
    public void detailActivity(){

        onView(withId(R.id.rv_detail_steps)).perform(RecyclerViewActions.scrollToPosition(2));

    }



}
