package com.app.worldbankapi;

import java.util.ArrayList;
import java.util.Observable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CountryListResults extends Results
{
    public CountryList countries;
    
    CountryListResults()
    {
        countries = new CountryList();
    }
    
    public CountryList getCountries()
    {
        return countries;
    }
    
    public void fromJSON(JSONArray response) throws JSONException
    {
        super.fromJSON(response);
        
        JSONArray results = response.getJSONArray(1);        
        countries.clear();
        
        for (int i = 0; i < results.length(); i++) 
        {            
            JSONObject countryData = results.getJSONObject(i);
            
            String name = countryData.getString("name");
            String code = countryData.getString("iso2Code");            
            String capitalCity = countryData.optString("capitalCity");
            double latitude = countryData.optDouble("latitude");
            double longitude = countryData.optDouble("longitude");
            
            Country country = new Country(code);
            country.setName(name);
            country.setCapitalCity(capitalCity);
            country.setCoordinates(longitude, latitude);
            
            countries.add(country);
        }

        setChanged();           
    }
}
