package com.app.worldbankapi;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class CountryIndicatorResults extends Results
{    
    ArrayList<TimeseriesDataPoint> m_dataPoints;

    public CountryIndicatorResults() 
    {
        /* Default values */
        m_dataPoints = new ArrayList<TimeseriesDataPoint>();
    }

    public final ArrayList<TimeseriesDataPoint> getDataPoints()
    {
        return m_dataPoints;
    }
    
    public void fromJSON(JSONArray response) throws JSONException
    {
        super.fromJSON(response);
        
        JSONArray dataPoints = response.getJSONArray(1);        
        m_dataPoints.clear();
        
        for (int i = 0; i < dataPoints.length(); i++) 
        {
            TimeseriesDataPoint dataPoint = new TimeseriesDataPoint();
            dataPoint.fromJSON(dataPoints.getJSONObject(i));
            m_dataPoints.add(dataPoint);
        }

        setChanged();           
    }
}
