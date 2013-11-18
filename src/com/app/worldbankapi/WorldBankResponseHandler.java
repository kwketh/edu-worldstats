package com.app.worldbankapi;

import org.json.JSONArray;
import org.json.JSONException;

import com.loopj.android.http.JsonHttpResponseHandler;

public class WorldBankResponseHandler extends JsonHttpResponseHandler
{
    Results m_results;
    
    WorldBankResponseHandler(Results results)
    {
        m_results = results;
    }
    
    @Override
    public void onSuccess(JSONArray response) 
    {
        try 
        {
            m_results.fromJSON(response);                
            m_results.notifyObservers("fetchComplete");
        } 
        catch (JSONException e) 
        {
            e.printStackTrace();
            System.out.println("fromJSON failed with response:");
            System.out.println(response);
        }      
    }   
  
}