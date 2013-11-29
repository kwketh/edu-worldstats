package com.app.examples;

import java.util.Observable;
import java.util.Observer;

import com.app.worldbankapi.Country;
import com.app.worldbankapi.CountryListResults;
import com.app.worldbankapi.WorldBankAPI;

/**
 * CountryListExample class.
 *
 * This is our API example to test and to show other group
 * members how to fetch data using our own API, so they can
 * create features of their own.
 */

public class CountryListExample implements Observer
{
    /**
     * Runs the demo by fetching the results.
     */
    public void run()
    {
        /* Construct the results with a country code and indicator */
        CountryListResults results = WorldBankAPI.fetchCountryList();
        
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
            CountryListResults results = (CountryListResults)eventSource;
            
            /* Iterate through the data points */
            for (Country country : results.getCountries()) 
            {
                System.out.println(country);
            }
        }
    }
}
