package com.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CountryIndicatorResults extends Observable
{    
    ArrayList<TimeseriesDataPoint> m_dataPoints;
    int m_numberOfPoints;
    int m_numberOfPages;
    int m_pageNo;
    
    String m_countryCode;
    Indicator m_indicator;
    
    /* todo: allow array of countryCodes for plotting values */
    public CountryIndicatorResults(String countryCode, Indicator indicator) 
    {
        /* Default values */
        m_dataPoints = new ArrayList<TimeseriesDataPoint>();
        m_numberOfPoints = 0;
        
        m_countryCode = countryCode;
        m_indicator = indicator;
    }
    
    public void fetch()
    {
        WorldBankAPI.getCountryDataPoints(m_countryCode, m_indicator, this);        
    }
    
    public final ArrayList<TimeseriesDataPoint> getDataPoints()
    {
        return m_dataPoints;
    }
    
    public void fromJSON(JSONArray response)
    {
        try 
        {
            JSONObject metaData = response.getJSONObject(0);
            JSONArray dataPoints = response.getJSONArray(1);
            
            m_numberOfPoints = metaData.getInt("total");
            m_numberOfPages = metaData.getInt("pages");
            m_pageNo = metaData.getInt("page");
            
            m_dataPoints.clear();
            
            for (int i = 0; i < dataPoints.length(); i++) 
            {
                TimeseriesDataPoint dataPoint = new TimeseriesDataPoint();
                dataPoint.fromJSON(dataPoints.getJSONObject(i));
                m_dataPoints.add(dataPoint);
            }

            setChanged();
            notifyObservers("fetchComplete");
        } 
        catch (JSONException e) 
        {
            e.printStackTrace();
            System.out.println("fromJSON failed with response:");
            System.out.println(response);
        }
    }
}
