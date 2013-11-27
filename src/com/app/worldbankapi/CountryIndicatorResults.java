package com.app.worldbankapi;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;

public class CountryIndicatorResults extends Results
{    
    ArrayList<TimeseriesDataPoint> m_dataPoints;
    Indicator m_indicator;

    public CountryIndicatorResults(Indicator indicator)
    {
        /* Default values */
        m_dataPoints = new ArrayList<TimeseriesDataPoint>();
        m_indicator = indicator;
    }

    public final ArrayList<TimeseriesDataPoint> getDataPoints()
    {
        return m_dataPoints;
    }

    public Indicator getIndicator()
    {
        return m_indicator;
    }

    public TimeseriesDataPoint getPointAtYear(int year)
    {
        for (TimeseriesDataPoint point : m_dataPoints)
        {
            final Calendar dateInstance = Calendar.getInstance();
            dateInstance.setTime(point.getDate());
            if (dateInstance.get(Calendar.YEAR) == year)
                return point;
        }

        return null;
    }
    
    public void fromJSON(JSONArray response) throws JSONException
    {
        super.fromJSON(response);
        
        JSONArray dataPoints = response.optJSONArray(1);        
        m_dataPoints.clear();
        
        if (dataPoints != null)
        {
            for (int i = 0; i < dataPoints.length(); i++) 
            {
                TimeseriesDataPoint dataPoint = new TimeseriesDataPoint();
                dataPoint.fromJSON(dataPoints.getJSONObject(i));
                m_dataPoints.add(dataPoint);
            }
        }

        setChanged();           
    }
}
