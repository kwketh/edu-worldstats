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

public class EducationFragment extends Fragment implements Observer
{

    SeekBar yearSeek;
    TextView year;
    TextView primaryEnrollment;
    TextView secondaryEnrollment;
    TextView tertiaryEnrollment;
    TextView maleLiteracyRate;
    TextView femaleLiteracyRate;
    
    
    CountryIndicatorResults m_resultsPrimaryEnrollment;
    CountryIndicatorResults m_resultsSecondaryEnrollment;
    CountryIndicatorResults m_resultsTertiaryEnrollment;
    CountryIndicatorResults m_resultsLiteracyRateMale;
    CountryIndicatorResults m_resultsLiteracyRateFemale;
    
    final static int YEAR_MIN = 1960;
    final static int YEAR_MAX = 2013;    
    
    private int m_currentYear = 2010;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.education_fragment,
                container, false);
        
        Intent intent = getActivity().getIntent();
        final String countryCode = intent.getStringExtra("countryCode");

        year = (TextView) rootView.findViewById(R.id.year);
        primaryEnrollment = (TextView) rootView
                .findViewById(R.id.primaryEnrollment);
        secondaryEnrollment = (TextView) rootView
                .findViewById(R.id.secondaryEnrollment);
        tertiaryEnrollment = (TextView) rootView
                .findViewById(R.id.tertiaryEnrollment);
        maleLiteracyRate = (TextView) rootView
                .findViewById(R.id.maleLiteracyRate);
        femaleLiteracyRate = (TextView) rootView
                .findViewById(R.id.femaleLiteracyRate);
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
                
        m_resultsPrimaryEnrollment = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.RATIO_F_M_PRIMARY, fetchDate);        
        m_resultsSecondaryEnrollment = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.RATIO_F_M_SECONDARY, fetchDate);
        m_resultsTertiaryEnrollment = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.RATIO_F_M_TERTIARY, fetchDate);
        m_resultsLiteracyRateMale = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.LITERACY_RATE_M, fetchDate);
        m_resultsLiteracyRateFemale = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.LITERACY_RATE_F, fetchDate);
                
        /* Observe for any changes to the results */
        m_resultsPrimaryEnrollment.addObserver(this);
        m_resultsSecondaryEnrollment.addObserver(this);
        m_resultsTertiaryEnrollment.addObserver(this);
        m_resultsLiteracyRateMale.addObserver(this);
        m_resultsLiteracyRateFemale.addObserver(this);
    }
    
    public void updateValues() {    
        
        primaryEnrollment.setText("Loading...");
        secondaryEnrollment.setText("Loading...");
        tertiaryEnrollment.setText("Loading...");
        maleLiteracyRate.setText("Loading...");
        femaleLiteracyRate.setText("Loading...");
        year.setText("Change year: " + m_currentYear);
    }

    @Override
    public void update(Observable eventSource, Object eventName) 
    {
        
        CountryIndicatorResults results = (CountryIndicatorResults)eventSource;
        TextView labelValue;
    
        if (results == m_resultsPrimaryEnrollment) {
            labelValue = primaryEnrollment;
        } else
        if (results == m_resultsSecondaryEnrollment) {
            labelValue = secondaryEnrollment;
        } else
        if (results == m_resultsTertiaryEnrollment) {
            labelValue = tertiaryEnrollment;
        } else
        if (results == m_resultsLiteracyRateMale) {
            labelValue = maleLiteracyRate;
        } else
        if (results == m_resultsLiteracyRateFemale){
            labelValue = femaleLiteracyRate;
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