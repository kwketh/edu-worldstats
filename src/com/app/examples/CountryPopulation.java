package com.app.examples;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.app.CountryIndicatorResults;
import com.app.Indicator;

public class CountryPopulation implements Observer
{
    public void run()
    {
        /* Construct the results with a country code and indicator */
        CountryIndicatorResults results = new CountryIndicatorResults("GB", Indicator.POPULATION);
        
        /* Lets observe for any changes to the results */
        results.addObserver(this);        
        
        /* And finally fetch the results */
        results.fetch();
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
        if (eventName == "fetchComplete") 
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
