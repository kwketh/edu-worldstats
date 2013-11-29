package com.app.worldbankapi;

import com.loopj.android.http.RequestParams;

/**
 * WorldBankAPI class.
 *
 * We have created a simple API to make it easier for
 * all group members to implement features we might need.
 *
 * All of us have decided initially that this would be a
 * mandatory step to ensure the app is robust, would also
 * make easier to write new code, to make things more
 * consistent as well as to ensure the code works smooth
 * and fast throughout the whole app.
 *
 * It consists of only 3 base functions: loading country data
 * loading list of all countries and loading details about an
 * indicator.
 */
public class WorldBankAPI
{
    /**
     * Fetches indicator information about a country or multiple
     * countries.
     * 
     * @param countries list of countries to fetch
     * @param indicator the indicator
     * @param date a single date or date range
     *   Format:
     *   single date (i.e. 1980)
     *   date range (i.e. 1960:2013)
     *
     * @return The results object returned will inform any observers of the
     *         object as soon as the data has been successfully fetched
     *         and parsed using Observer pattern.
     *
     * @see java.util.Observer
     */
    public static CountryIndicatorResults fetchCountriesIndicatorResults(CountryList countries, Indicator indicator, String date)
    {
        String url = "/countries/" + countries.getQueryData() + "/indicators/" + indicator.getId();
        
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("date", date);
        params.put("per_page", "1000");

        CountryIndicatorResults results = new CountryIndicatorResults(indicator);
        WorldBankClient.get(url, params, new WorldBankResponseHandler(results));
        
        return results;
    }

    /**
     * Fetches list of all the countries.
     *
     * @return The results object returned will inform any observers of the
     *         object as soon as the data has been successfully fetched
     *         and parsed using Observer pattern.
     *
     * @see java.util.Observer
     */
   public static CountryListResults fetchCountryList() 
     {
        String url = "/countries";
        
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("per_page", "1000");
                       
        CountryListResults results = new CountryListResults();         
        WorldBankClient.get(url, params, new WorldBankResponseHandler(results));
        
        return results;
    }

    /**
     * Fetch information about an indicator (the full name and full definition)
     *
     * @return The results object returned will inform any observers of the
     *         object as soon as the data has been successfully fetched
     *         and parsed using Observer pattern.
     *
     * @see java.util.Observer
     */
   public static IndicatorDefinitionResults fetchIndicatorDefinition(String indicator) 
   {
      String url = "/indicator/" + indicator;
      
      RequestParams params = new RequestParams();        
      params.put("format", "json");
                     
      IndicatorDefinitionResults results = new IndicatorDefinitionResults();         
      WorldBankClient.get(url, params, new WorldBankResponseHandler(results));
      
      return results;
  }   
}
