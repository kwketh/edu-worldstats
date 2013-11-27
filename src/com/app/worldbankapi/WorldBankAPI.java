package com.app.worldbankapi;

import com.loopj.android.http.RequestParams;


public class WorldBankAPI
{
    /**
     * Returns an object with results about a country (or multiple
     * countries).
     * 
     * The object returned will inform any observers of the object
     * as soon as the data has been successfully fetched and parsed.
     * 
     * @param countryCode
     * @param indicator
     * @param date
     *   Format:
     *   single date (i.e. 1980)
     *   date range accepted (i.e. 1960:2013)
     * @return
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
   
   public static IndicatorDefinitionResults fetchIndicatorDefinition(Indicator indicator) 
   {
      String url = "/indicator/" + indicator.getId();
      
      RequestParams params = new RequestParams();        
      params.put("format", "json");
                     
      IndicatorDefinitionResults results = new IndicatorDefinitionResults();         
      WorldBankClient.get(url, params, new WorldBankResponseHandler(results));
      
      return results;
  }   
}
