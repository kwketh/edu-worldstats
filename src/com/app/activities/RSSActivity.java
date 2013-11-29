package com.app.activities;

import com.app.R;
import com.app.fragments.RSSFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

//RSS Reader made with help from a tutorial on androidresearch.wordpress.com 

public class RSSActivity extends FragmentActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        // adding fragment to activity screen if it is empty
        if (savedInstanceState == null) {
            addRssFragment();
        }
    }

    private void addRssFragment() {
        // adding fragment to screen
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RSSFragment fragment = new RSSFragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }
}
