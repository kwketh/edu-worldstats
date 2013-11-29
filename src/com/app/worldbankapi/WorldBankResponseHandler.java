package com.app.worldbankapi;

import java.net.SocketTimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * WorldBankResponseHandler class.
 *
 * The class is responsible for handling any response coming from the
 * World Bank client, updating the results object and informing any
 * observers about that change.
 */
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
            m_results.setLoaded(true);

            m_results.setChanged();
            m_results.notifyObservers("fetchComplete");
        } 
        catch (Exception e) 
        {
            m_results.setChanged();
            e.printStackTrace();
            if (e instanceof JSONException)
            {
                System.out.println("fromJSON failed with response:");
                System.out.println(response);
                m_results.notifyObservers("errorParse");
            } else
            if (e instanceof SocketTimeoutException)
            {
                m_results.notifyObservers("errorTimeout");
            }
        }
    }   
    
    @Override
    public void onFailure(Throwable error, JSONObject responseResponse) 
    {
        m_results.setChanged();
        m_results.notifyObservers("errorNetwork");
    }
}