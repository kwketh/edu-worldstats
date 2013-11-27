package com.app.activities;


import android.widget.AutoCompleteTextView;
import com.app.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.annotation.TargetApi;
import android.os.Build;
import com.app.loaders.CountryListLoader;

public class CompareCountries extends Activity
{
    private CountryListLoader countryListLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_countries);
        setupActionBar();

        AutoCompleteTextView firstCountry = (AutoCompleteTextView)findViewById(R.id.autoCompleteCountryOne);
        AutoCompleteTextView secondCountry = (AutoCompleteTextView)findViewById(R.id.autoCompleteCountryTwo);

        /* Construct the loader and retrieve list of all countries */
        countryListLoader = new CountryListLoader(this);
        countryListLoader.load();

        /* Assign the loader adapter to ListView */
        firstCountry.setAdapter(countryListLoader.getAdapter());
        secondCountry.setAdapter(countryListLoader.getAdapter());
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compare_countries, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
