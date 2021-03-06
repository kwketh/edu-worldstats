package com.app.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.app.R;
import com.app.loaders.CountryListLoader;
import com.app.worldbankapi.Country;
import com.app.worldbankapi.CountryList;

public class CompareCountries extends Activity
{
    private AutoCompleteTextView textViewFirstCountry, textViewSecondCountry;

    private CountryListLoader m_countryListLoader;
    private ProgressDialog m_progressDialog;
    private Country m_firstCountry;
    private Country m_secondCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_countries);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        /* Find elements in the view */
        textViewFirstCountry = (AutoCompleteTextView)findViewById(R.id.autoCompleteCountryOne);
        textViewSecondCountry = (AutoCompleteTextView)findViewById(R.id.autoCompleteCountryTwo);

        /* Construct the loader and retrieve list of all countries */
        m_countryListLoader = new CountryListLoader(this);
        m_countryListLoader.load();

        /* Assign the loader adapter to ListView */
        textViewFirstCountry.setAdapter(m_countryListLoader.getAdapter());
        textViewSecondCountry.setAdapter(m_countryListLoader.getAdapter());

        /* Setup events */
        textViewFirstCountry.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                m_firstCountry = (Country)adapterView.getItemAtPosition(position);
            }
        });

        textViewSecondCountry.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                m_secondCountry = (Country)adapterView.getItemAtPosition(position);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCompareClicked(View view)
    {
        if (m_firstCountry == null)
        {
            AlertDialog.Builder alert  = new AlertDialog.Builder(this);
            alert.setTitle("Invalid country");
            alert.setMessage("Please enter a valid country name by choose it from the suggestions.");
            alert.setPositiveButton("OK", null);
            alert.show();

            textViewFirstCountry.requestFocus();
            textViewFirstCountry.selectAll();

            return;
        }

        if (m_secondCountry == null)
        {
            AlertDialog.Builder alert  = new AlertDialog.Builder(this);
            alert.setTitle("Invalid country");
            alert.setMessage("Please enter a valid country name by choose it from the suggestions.");
            alert.setPositiveButton("OK", null);
            alert.show();

            textViewSecondCountry.requestFocus();
            textViewSecondCountry.selectAll();

            return;
        }

        CountryList countryList = new CountryList();
        countryList.add(m_firstCountry);
        countryList.add(m_secondCountry);

        Intent intent = new Intent(this, CountryDetails.class);
        intent.putExtra("countries", countryList);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
