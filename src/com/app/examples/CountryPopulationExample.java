package com.app.examples;

import java.util.Observable;
import java.util.Observer;

import com.app.worldbankapi.CountryIndicatorResults;
import com.app.worldbankapi.CountryList;
import com.app.worldbankapi.Indicator;
import com.app.worldbankapi.WorldBankAPI;

/**
 * CountryPopulationExample class.
 *
 * This is our API example to test and to show other group
 * members how to fetch data using our own API, so they can
 * create features of their own.
 */

public class CountryPopulationExample implements Observer
{
    /**
     * Runs the demo by fetching the results.
     */
    public void run()
    {
        /* Construct the results with a country code and indicator */
        CountryIndicatorResults results = WorldBankAPI.fetchCountriesIndicatorResults(new CountryList("GB"), Indicator.POPULATION, "1960:2013");
        
        /* Lets observe for any changes to the results */
        results.addObserver(this);     
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
            
            /* Iterate through the data points */
            for (Object dataPoint : results.getDataPoints()) 
            {
                System.out.println(dataPoint);
            }
        }
    }
}
