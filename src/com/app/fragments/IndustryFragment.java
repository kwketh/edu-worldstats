package com.app.fragments;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.app.R;
import com.app.worldbankapi.CountryIndicatorResults;
import com.app.worldbankapi.TimeseriesDataPoint;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.industry_fragment, container,
                false);

        maleUnemployed = (TextView) rootView.findViewById(R.id.maleUnemployed);
        femaleUnemployed = (TextView) rootView
                .findViewById(R.id.femaleUnemployed);
        maleLabourParticipation = (TextView) rootView
                .findViewById(R.id.maleParticipation);
        femaleLabourParticipation = (TextView) rootView
                .findViewById(R.id.femaleParticipation);
        maleEmployers = (TextView) rootView.findViewById(R.id.maleEmployers);
        femaleEmployers = (TextView) rootView.findViewById(R.id.femaleEmployers);
        maleSelfEmployed = (TextView) rootView.findViewById(R.id.maleSelfEmployed);
        femaleSelfEmployed = (TextView) rootView.findViewById(R.id.femaleSelfEmployed);
        
        year = (TextView) rootView.findViewById(R.id.year);
        yearSeek = (SeekBar) rootView.findViewById(R.id.seekBar);
        updateValues(yearSeek.getProgress());
        
        yearSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                updateValues(arg1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }
        });
        
        
        return rootView;
    }

    private void updateValues(int value) {
        year.setText((value + 1960) + "");
        // Update all fields here
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
