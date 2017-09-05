package android.fastrack.udacity.easybaking.ui.step;


import android.fastrack.udacity.easybaking.model.Steps;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by winzaldi on 8/30/17.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private List<Steps> stepsList;
    int mPosition;
    boolean firstFlag = false;


    public ScreenSlidePagerAdapter(FragmentManager fm, List<Steps> stepsList, int position) {
        super(fm);
        this.stepsList = stepsList;
        this.mPosition = position;
    }

    @Override
    public Fragment getItem(int position) {
        if (!firstFlag & mPosition > 0) {
            firstFlag = true;
            position = mPosition;
        }
        Steps steps = stepsList.get(position);
        Fragment fragment = ScreenSlidePageFragment.create(position, steps);
        return fragment;
    }

    @Override
    public int getCount() {
        return stepsList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mPosition > 0)
            return mPosition;
        return super.getItemPosition(object);
    }

    

}