package com.app.worldbankapi;

import org.json.JSONArray;
import org.json.JSONException;

import com.loopj.android.http.JsonHttpResponseHandler;
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
     * @param dateRange
     *   format 1960:2013
     * @return
     */
    public static CountryIndicatorResults fetchCountriesIndicatorResults(CountryList countries, Indicator indicator, String dateRange) 
    {
        String url = "/countries/" + countries.getQueryData() + "/indicators/" + indicator.getId();
        
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("date", dateRange);
                       
        CountryIndicatorResults results = new CountryIndicatorResults();         
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
