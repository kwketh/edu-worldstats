package com.app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.app.R;
import com.app.worldbankapi.CountryIndicatorResults;
import com.app.worldbankapi.CountryList;
import com.app.worldbankapi.Indicator;
import com.app.worldbankapi.IndicatorDefinitionResults;
import com.app.worldbankapi.TimeseriesDataPoint;
import com.app.worldbankapi.WorldBankAPI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class BasicInfoFragment extends Fragment implements Observer
{
    SeekBar yearSeek;
    TextView population;
    TextView gdp;
    TextView gniPerCapita;
    TextView growth;
    TextView year;
    
    CountryIndicatorResults m_resultsPopulation;
    CountryIndicatorResults m_resultsGdp;
    CountryIndicatorResults m_resultsGniPerCapita;
    CountryIndicatorResults m_resultsGrowth;
    
    final static int YEAR_MIN = 1960;
    final static int YEAR_MAX = 2012;    
    
    private int m_currentYear = 2012;

    static public String formatValue(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return output;
    }    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.basic_info_fragment,
                container, false);
        
        Intent intent = getActivity().getIntent();
        final String countryCode = intent.getStringExtra("countryCode");

        year = (TextView) rootView.findViewById(R.id.year);
        population = (TextView) rootView.findViewById(R.id.population);
        gdp = (TextView) rootView.findViewById(R.id.GDP);
        gniPerCapita = (TextView) rootView.findViewById(R.id.GNIPerCapita);
        growth = (TextView) rootView.findViewById(R.id.growth);
        yearSeek = (SeekBar) rootView.findViewById(R.id.seekBar1);

        yearSeek.setMax(YEAR_MAX - YEAR_MIN);
        yearSeek.setProgress(m_currentYear - YEAR_MIN);
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
        /* Construct the results with the country code and indicators */
        String yearRange = m_currentYear + ":" + m_currentYear;
        CountryList country = new CountryList(countryCode);
        
        m_resultsPopulation = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.POPULATION, yearRange);        
        m_resultsGdp = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.GDP, yearRange);
        m_resultsGniPerCapita = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.GNI_PER_CAPITA, yearRange);
        m_resultsGrowth = WorldBankAPI.fetchCountriesIndicatorResults(country, Indicator.GROWTH, yearRange);
                
        /* Observe for any changes to the results */
        m_resultsPopulation.addObserver(this);
        m_resultsGdp.addObserver(this);
        m_resultsGniPerCapita.addObserver(this);
        m_resultsGrowth.addObserver(this);
    }

    public void updateValues() {
        year.setText("Change year: " + m_currentYear);
        population.setText("(loading...)");
        gdp.setText("(loading...)");
        gniPerCapita.setText("(loading...)");
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
        if (eventName.equals("fetchComplete")) 
        {
            /* Retrieve the results object from the event source */
            CountryIndicatorResults results = (CountryIndicatorResults)eventSource;
            
        
            ArrayList<TimeseriesDataPoint> points = results.getDataPoints();
            boolean hasData = points.size() > 0; 
            
            TimeseriesDataPoint point = hasData ? points.get(0) : null;
            String value = (point != null && !point.isNullValue()) ? point.getFormattedValue() : "(no data)";
            
            if (results == m_resultsPopulation) {
                population.setText(value);
            } else
            if (results == m_resultsGdp) {
                gdp.setText(value);
            } else
            if (results == m_resultsGniPerCapita) {
                gniPerCapita.setText(value);
            } else
            if (results == m_resultsGrowth) {
                growth.setText(value);
            }            
        }
    }    
    
    public void getDefinition(View view) {
        IndicatorDefinitionResults results = WorldBankAPI.fetchIndicatorDefinition(Indicator.POPULATION);
        Toast.makeText(getActivity(), results.getName(), Toast.LENGTH_LONG).show();
    }
}
