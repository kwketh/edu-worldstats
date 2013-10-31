package com.example.testapp;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class WorldBankAPI
{
    private static final String defaultDateRange = "1960:2009";
    
    static void getCountryDataPoints(String countryCode, Indicator indicator, final CountryIndicatorResults out_results) 
    {
        String url = "/countries/" + countryCode + "/indicators/" + indicator.getId();
        
        RequestParams params = new RequestParams();
        params.put("date", defaultDateRange);
        params.put("format", "json");
        
        WorldBankClient.get(url, params, new JsonHttpResponseHandler() 
        {
            @Override
            public void onSuccess(JSONArray response) 
            {
                out_results.fromJSON(response);
            }
        });
    }
}
