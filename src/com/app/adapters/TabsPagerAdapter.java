package com.app.adapters;

import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.fragments.BasicInfoFragment;
import com.app.fragments.EducationFragment;
import com.app.fragments.IndustryFragment;
import com.app.fragments.SocialDevelopmentFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter
{
    private HashMap<Integer, Fragment> m_fragmentsMap;

    public TabsPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
        m_fragmentsMap = new HashMap<Integer, Fragment>();
    }
 
    @Override
    public Fragment getItem(int index)
    {
        if (!m_fragmentsMap.containsKey(index))
        {
            Fragment fragment;
            switch (index)
            {
                case 0:
                    fragment = new BasicInfoFragment();
                    break;

                case 1:
                    fragment = new EducationFragment();
                    break;

                case 2:
                    fragment = new IndustryFragment();
                    break;

                case 3:
                    fragment = new SocialDevelopmentFragment();
                    break;

                default:
                    throw new Error("No fragment found for index " + index);
            }
            m_fragmentsMap.put(index, fragment);
        }

        return m_fragmentsMap.get(index);
    }
 
    @Override
    public int getCount()
    {
        return 4;
    }
    

}
