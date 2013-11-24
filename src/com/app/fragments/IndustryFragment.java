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

public class IndustryFragment extends Fragment implements Observer
{
    SeekBar yearSeek;
    TextView year;
    TextView maleUnemployed;
    TextView femaleUnemployed;
    TextView maleLabourParticipation;
    TextView femaleLabourParticipation;
    TextView maleEmployers;
    TextView femaleEmployers;
    TextView maleSelfEmployed;
    TextView femaleSelfEmployed;

    CountryIndicatorResults m_resultsMaleUnemployed;
    CountryIndicatorResults m_resultsFemaleUnemployed;
    CountryIndicatorResults m_resultsMaleLabourParticipation;
    CountryIndicatorResults m_resultsFemaleLabourParticipation;
    CountryIndicatorResults m_resultsMaleEmployers;
    CountryIndicatorResults m_resultsFemaleEmployers;
    CountryIndicatorResults m_resultsMaleSelfEmployed;
    CountryIndicatorResults m_resultsFemaleSelfEmployed;
    
    final static int YEAR_MIN = 1960;
    final static int YEAR_MAX = 2013;    
    
    private int m_currentYear = 2010;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.industry_fragment, container,
                false);

        Intent intent = getActivity().getIntent();
        final String countryCode = intent.getStringExtra("countryCode");

        maleUnemployed = (TextView) rootView.findViewById(R.id.maleUnemployed);
        femaleUnemployed = (TextView) rootView
                .findViewById(R.id.femaleUnemployed);
        maleLabourParticipation = (TextView) rootView
                .findViewById(R.id.maleParticipation);
        femaleLabourParticipation = (TextView) rootView
                .findViewById(R.id.femaleParticipation);
        maleEmployers = (TextView) rootView.findViewById(R.id.maleEmployers);
        femaleEmployers = (TextView) rootView
                .findViewById(R.id.femaleEmployers);
        maleSelfEmployed = (TextView) rootView
                .findViewById(R.id.maleSelfEmployed);
        femaleSelfEmployed = (TextView) rootView
                .findViewById(R.id.femaleSelfEmployed);

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
    
    public void loadCountryIndicators(String countryCode)
    {
        String fetchDate = String.valueOf(m_currentYear);
        
        /* Construct the results with the country code and indicators */
        CountryList country = new CountryList(countryCode);
                
        m_resultsMaleUnemployed = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.MALE_UNEMPLOYMENT, fetchDate);        
        m_resultsFemaleUnemployed = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.FEMALE_UNEMPLOYMENT, fetchDate);
        m_resultsMaleLabourParticipation = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.LABOUR_PARTICIPATION_MALE, fetchDate);
        m_resultsFemaleLabourParticipation = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.LABOUR_PARTICIPATION_FEMALE, fetchDate);
        m_resultsMaleEmployers = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.EMPLOYERS_MALE, fetchDate);
        m_resultsFemaleEmployers = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.EMPLOYERS_FEMALE, fetchDate);
        m_resultsMaleSelfEmployed = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.SELF_EMPLOYED_MALE, fetchDate);
        m_resultsFemaleSelfEmployed = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.SELF_EMPLOYED_FEMALE, fetchDate);
                
        /* Observe for any changes to the results */
        m_resultsMaleUnemployed.addObserver(this);
        m_resultsFemaleUnemployed.addObserver(this);
        m_resultsMaleLabourParticipation.addObserver(this);
        m_resultsFemaleLabourParticipation.addObserver(this);
        m_resultsMaleEmployers.addObserver(this);
        m_resultsFemaleEmployers.addObserver(this);
        m_resultsMaleSelfEmployed.addObserver(this);
        m_resultsFemaleSelfEmployed.addObserver(this);
    }

    public void updateValues() {      
        year.setText("Change year: " + m_currentYear);
        maleUnemployed.setText("(loading...)");
        femaleUnemployed.setText("(loading...)");
        maleLabourParticipation.setText("(loading...)");
        femaleLabourParticipation.setText("(loading...)");
        maleEmployers.setText("(loading...)");
        femaleEmployers.setText("(loading...)");
        maleSelfEmployed.setText("(loading...)");
        femaleSelfEmployed.setText("(loading...)");
    }

    /**
     * This method is called whenever the observed object has changed. (in this
     * case the CountryIndicatorResults object)
     * 
     * @param eventSource
     *            the observable object triggering the event.
     * 
     * @param eventName
     *            an argument passed from the observable object. the value will
     *            always be an instance of String.
     * 
     * @see Observer#update
     */
    @Override
    public void update(Observable eventSource, Object eventName) {
        CountryIndicatorResults results = (CountryIndicatorResults) eventSource;
        TextView labelValue;

        if (results == m_resultsMaleUnemployed) {
            labelValue = maleUnemployed;
        } else if (results == m_resultsFemaleUnemployed) {
            labelValue = femaleUnemployed;
        } else if (results == m_resultsMaleLabourParticipation) {
            labelValue = maleLabourParticipation;
        } else if (results == m_resultsFemaleLabourParticipation) {
            labelValue = femaleLabourParticipation;
        } else if (results == m_resultsMaleEmployers) {
            labelValue = maleEmployers;
        } else if (results == m_resultsFemaleEmployers) {
            labelValue = femaleEmployers;
        } else if (results == m_resultsMaleSelfEmployed) {
            labelValue = maleSelfEmployed;
        } else if (results == m_resultsFemaleSelfEmployed) {
            labelValue = femaleSelfEmployed;
        } else {
            return;
        }

        if (eventName.equals("fetchComplete")) {
            /* Retrieve the results object from the event source */
            ArrayList<TimeseriesDataPoint> points = results.getDataPoints();
            boolean hasData = points.size() > 0;

            TimeseriesDataPoint point = hasData ? points.get(0) : null;
            String value = (point != null && !point.isNullValue()) ? point
                    .getFormattedValue() : "(no data)";

            
                labelValue.setText(value);
            

        } else if (eventName.equals("errorTimeout")) {
            labelValue.setText("(timeout)");
        } else if (eventName.equals("errorTimeout")) {
            labelValue.setText("(timeout)");
        } else if (eventName.equals("errorJson")) {
            labelValue.setText("(json error)");
        } else if (eventName.equals("errorNetwork")) {
            labelValue.setText("(network error)");
        }

    }

}
