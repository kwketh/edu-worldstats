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
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class BasicInfoFragment extends Fragment implements Observer
{
    SeekBar yearSeek;
    TextView capitalCity;
    TextView population;
    TextView gdp;
    TextView countryArea;
    TextView growth;
    TextView year;
    
    CountryIndicatorResults m_resultsPopulation;
    CountryIndicatorResults m_resultsGdp;
    CountryIndicatorResults m_resultsCountryArea;
    CountryIndicatorResults m_resultsGrowth;
    
    final static int YEAR_MIN = 1960;
    final static int YEAR_MAX = 2013;    
    
    private int m_currentYear = 2010;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.basic_info_fragment,
                container, false);
        
        Intent intent = getActivity().getIntent();
        final String countryCode = intent.getStringExtra("countryCode");

        capitalCity = (TextView) rootView.findViewById(R.id.capitalCity);
        year = (TextView) rootView.findViewById(R.id.year);
        population = (TextView) rootView.findViewById(R.id.population);
        gdp = (TextView) rootView.findViewById(R.id.GDP);
        countryArea = (TextView) rootView.findViewById(R.id.countryArea);
        growth = (TextView) rootView.findViewById(R.id.growth);
        yearSeek = (SeekBar) rootView.findViewById(R.id.seekBar1);

        yearSeek.setMax(YEAR_MAX - YEAR_MIN);
        yearSeek.setProgress(m_currentYear - YEAR_MIN);        
        
        capitalCity.setText(intent.getStringExtra("capitalCity"));        
        
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
                
        m_resultsPopulation = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.POPULATION, fetchDate);        
        m_resultsGdp = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.GDP, fetchDate);
        m_resultsCountryArea = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.AREA_OF_COUNTRY, fetchDate);
        m_resultsGrowth = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.GROWTH, fetchDate);
                
        /* Observe for any changes to the results */
        m_resultsPopulation.addObserver(this);
        m_resultsGdp.addObserver(this);
        m_resultsCountryArea.addObserver(this);
        m_resultsGrowth.addObserver(this);
    }

    public void updateValues() {      
        year.setText("Change year: " + m_currentYear);
        population.setText("(loading...)");
        gdp.setText("(loading...)");
        countryArea.setText("(loading...)");
        growth.setText("(loading...)");
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
    
        if (results == m_resultsPopulation) {
            labelValue = population;
        } else
        if (results == m_resultsGdp) {
            labelValue = gdp;
        } else
        if (results == m_resultsCountryArea) {
            labelValue = countryArea;
        } else
        if (results == m_resultsGrowth) {
            labelValue = growth;
        } else {
            return;
        }
        
        if (eventName.equals("fetchComplete")) 
        {
            /* Retrieve the results object from the event source */
            ArrayList<TimeseriesDataPoint> points = results.getDataPoints();
            boolean hasData = points.size() > 0; 
            
            TimeseriesDataPoint point = hasData ? points.get(0) : null;
            String value = (point != null && !point.isNullValue()) ? point.getFormattedValue() : "(no data)";
            
            if (results == m_resultsCountryArea) {
                labelValue.setText(value + " sq. km");
            } else
            if (results == m_resultsGdp) {
                labelValue.setText("$" + value + " USD");
            } else {              
                labelValue.setText(value);
            }
            
            
        } else
        if (eventName.equals("errorTimeout")) 
        {
            labelValue.setText("(timeout)");
        } else
        if (eventName.equals("errorTimeout")) 
        {
            labelValue.setText("(timeout)");
        } else
        if (eventName.equals("errorJson")) 
        {
            labelValue.setText("(json error)");
        } else
        if (eventName.equals("errorNetwork")) 
        {
            labelValue.setText("(network error)");
        }            
            
    }    
}
