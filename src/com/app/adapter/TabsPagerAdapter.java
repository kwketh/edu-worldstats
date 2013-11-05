package com.app.adapter;

import com.app.BasicInfoFragment;
import com.app.EducationFragment;
import com.app.IndustryFragment;
import com.app.SocialDevelopmentFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            return new BasicInfoFragment();
        case 1:
            return new EducationFragment();
        case 2:
            return new IndustryFragment();
        case 3:
            return new SocialDevelopmentFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
    

}
