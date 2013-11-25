package com.app.fragments;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.app.R;
import com.app.worldbankapi.CountryIndicatorResults;
import com.app.worldbankapi.CountryList;
import com.app.worldbankapi.Indicator;
import com.app.worldbankapi.TimeseriesDataPoint;
import com.app.worldbankapi.WorldBankAPI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SocialDevelopmentFragment extends Fragment implements Observer
{

    SeekBar yearSeek;
    TextView year;
    TextView maleLifeExpectancy;
    TextView femaleLifeExpectancy;
    TextView femaleParliamentSeats;
    TextView fertilityRate;
    TextView cpiaRating;

    CountryIndicatorResults m_resultsMaleLifeExpectancy;
    CountryIndicatorResults m_resultsFemaleLifeExpectancy;
    CountryIndicatorResults m_resultsFemaleParliamentSeats;
    CountryIndicatorResults m_resultsFertilityRate;
    CountryIndicatorResults m_resultsCpiaRating;

    final static int YEAR_MIN = 1960;
    final static int YEAR_MAX = 2010;

    private int m_currentYear = 2010;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.social_development_fragment,
                container, false);

        Intent intent = getActivity().getIntent();
        final String countryCode = intent.getStringExtra("countryCode");

        maleLifeExpectancy = (TextView) rootView
                .findViewById(R.id.maleLifeExpectancy);
        femaleLifeExpectancy = (TextView) rootView
                .findViewById(R.id.femaleLifeExpectancy);
        femaleParliamentSeats = (TextView) rootView
                .findViewById(R.id.proportionOfSeats);
        fertilityRate = (TextView) rootView.findViewById(R.id.fertilityRate);
        cpiaRating = (TextView) rootView.findViewById(R.id.cpiaRating);

        year = (TextView) rootView.findViewById(R.id.year);
        yearSeek = (SeekBar) rootView.findViewById(R.id.seekBar);

        updateValues();

        yearSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                m_currentYear = YEAR_MIN + arg1;
                updateValues();
                loadCountryIndicators(countryCode);
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }
        });

        loadCountryIndicators(countryCode);

        return rootView;
    }

    public void loadCountryIndicators(String countryCode) {
        String fetchDate = String.valueOf(m_currentYear);

        /* Construct the results with the country code and indicators */
        CountryList country = new CountryList(countryCode);

        m_resultsMaleLifeExpectancy = WorldBankAPI.fetchCountriesIndicatorResults(
                country, Indicator.LIFE_EXPECTANCY_MALE, fetchDate);
        m_resultsFemaleLifeExpectancy = WorldBankAPI.fetchCountriesIndicatorResults(country,
                Indicator.LIFE_EXPECTANCY_FEMALE, fetchDate);
        m_resultsFemaleParliamentSeats = WorldBankAPI.fetchCountriesIndicatorResults(
                country, Indicator.PARLIAMENT_SEATS_FEMALE, fetchDate);
        m_resultsFertilityRate = WorldBankAPI.fetchCountriesIndicatorResults(country,
                Indicator.FERTILITY_RATE, fetchDate);
        m_resultsCpiaRating = WorldBankAPI.fetchCountriesIndicatorResults(country,
                Indicator.CPIA_GENDER_RATING, fetchDate);

        /* Observe for any changes to the results */
        m_resultsMaleLifeExpectancy.addObserver(this);
        m_resultsFemaleLifeExpectancy.addObserver(this);
        m_resultsFemaleParliamentSeats.addObserver(this);
        m_resultsFertilityRate.addObserver(this);
        m_resultsCpiaRating.addObserver(this);
    }

    public void updateValues() {      
        year.setText("Change year: " + m_currentYear);
        maleLifeExpectancy.setText("Loading...");
        femaleLifeExpectancy.setText("Loading...");
        femaleParliamentSeats.setText("Loading...");
        fertilityRate.setText("Loading...");
        cpiaRating.setText("Loading...");
    }

    /**
     * This method is called whenever the observed object has changed.
     * (in this case the CountryIndicatorResults object)
     * 
     * @param eventSource 
     *      the observable object triggering the event.
     * 
     * @param eventName
     *      an argument passed from the observable object.
     *      the value will always be an instance of String.
     * 
     * @see Observer#update
     */    
    @Override
    public void update(Observable eventSource, Object eventName) 
    {
        CountryIndicatorResults results = (CountryIndicatorResults)eventSource;
        TextView labelValue;
    
        if (results == m_resultsMaleLifeExpectancy) {
            labelValue = maleLifeExpectancy;
        } else
        if (results == m_resultsFemaleLifeExpectancy) {
            labelValue = femaleLifeExpectancy;
        } else
        if (results == m_resultsFemaleParliamentSeats) {
            labelValue = femaleParliamentSeats;
        } else
        if (results == m_resultsFertilityRate) {
            labelValue = fertilityRate;
        } else 
        if (results == m_resultsCpiaRating){
            labelValue = cpiaRating;
        } else
        {
            return;
        }
        
        if (eventName.equals("fetchComplete")) 
        {
            /* Retrieve the results object from the event source */
            ArrayList<TimeseriesDataPoint> points = results.getDataPoints();
            boolean hasData = points.size() > 0; 
            
            TimeseriesDataPoint point = hasData ? points.get(0) : null;
            String value = (point != null && !point.isNullValue()) ? point.getFormattedValue() : "No Data";
                         
                labelValue.setText(value);
           
        } else
        if (eventName.equals("errorTimeout")) 
        {
            labelValue.setText("Timeout");
        } else
        if (eventName.equals("errorTimeout")) 
        {
            labelValue.setText("Timeout");
        } else
        if (eventName.equals("errorJson")) 
        {
            labelValue.setText("JSON Error");
        } else
        if (eventName.equals("errorNetwork")) 
        {
            labelValue.setText("Network Error");
        }            
            
    }  
}
