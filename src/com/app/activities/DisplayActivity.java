package com.app.activities;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.widget.SeekBar;
import android.widget.TextView;
import com.app.adapters.TabsPagerAdapter;
import com.app.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import com.app.fragments.GenericIndicatorsFragment;

public class DisplayActivity extends FragmentActivity implements ActionBar.TabListener
{
    /* Static members */
    final public static int YEAR_DEFAULT = 2010;
    final public static int YEAR_MIN = 1960;
    final public static int YEAR_MAX = 2013;

    final public static String YEAR_RANGE = YEAR_MIN + ":" + YEAR_MAX;

    /* Private members */
    private ViewPager m_pager;
    private TabsPagerAdapter m_adapter;
    private SeekBar m_yearBar;
    private TextView m_yearBarLabel;
    private int m_fetchYear;

    /* Tab titles in order */
    private String[] tabTitles =
    {
        "Basic Info",
        "Education",
        "Industry",
        "Social Development"
    };

    Fragment getActiveFragment()
    {
        int position = getActionBar().getSelectedNavigationIndex();
        return getFragment(position);
    }

    Fragment getFragment(int position)
    {
        return m_adapter.getItem(position);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /* Construct view phase */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        /* Find elements phase */
        m_yearBar = (SeekBar)findViewById(R.id.yearBar);
        m_yearBarLabel = (TextView)findViewById(R.id.yearBarLabel);

        /* Update phase */
        setFetchYear(YEAR_DEFAULT, false);

        /* Gather information about the intent and view */
        final String countryName = getIntent().getStringExtra("countryName");
        final ActionBar actionBar = getActionBar();

        /* Setup action bar */
        actionBar.setTitle(countryName);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);

        /* Construct tabs pager adapter */
        m_adapter = new TabsPagerAdapter(getSupportFragmentManager());
        m_pager = (ViewPager)findViewById(R.id.pager);
        m_pager.setAdapter(m_adapter);

        /* Add all tabs to the action bar */
        for (String title : tabTitles)
        {
            Tab tab = actionBar.newTab();
            tab.setText(title);
            tab.setTabListener(this);
            actionBar.addTab(tab);
        }

        /* Events phase: listen for any events of the pager */
        m_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });

        m_yearBar.setMax(YEAR_MAX - YEAR_MIN);
        m_yearBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar bar, int progress, boolean fromUser)
            {
                if (fromUser)
                {
                    setFetchYear(YEAR_MIN + progress, true);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {}

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {}
        });


    }

    public void setFetchYear(int year, boolean updateFragment)
    {
        boolean didChange = m_fetchYear != year;
        if (didChange)
        {
            m_fetchYear = year;
            m_yearBarLabel.setText("Year is set to " + year);
            m_yearBar.setProgress(year - YEAR_MIN);

            if (updateFragment)
                updateFragment(getActiveFragment());
        }
    }

    public void updateFragment(Fragment fragment)
    {
        /* Ensure the fragment is our fragment with indicators */
        if (fragment instanceof GenericIndicatorsFragment)
        {
            /* Get fragment of indicators */
            final Fragment activeFragment = getActiveFragment();
            final GenericIndicatorsFragment indicatorsFragment = (GenericIndicatorsFragment)activeFragment;

            /* Update the year and fetch new data if the year changed */
            if (indicatorsFragment.getFetchYear() != m_fetchYear)
            {
                indicatorsFragment.setFetchYear(m_fetchYear);
                indicatorsFragment.fetchData();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }    
    
    @Override
    public void onTabReselected(Tab arg0, android.app.FragmentTransaction unused) {}
    
    @Override
    public void onTabUnselected(Tab arg0, android.app.FragmentTransaction unused) {}
    
    @Override
    public void onTabSelected(Tab tab, android.app.FragmentTransaction unused)
    {
        int position = tab.getPosition();
        m_pager.setCurrentItem(position);

        Fragment activeFragment = getActiveFragment();
        updateFragment(activeFragment);

        if (activeFragment instanceof GenericIndicatorsFragment)
            ((GenericIndicatorsFragment)activeFragment).animateIn();
    }
}
