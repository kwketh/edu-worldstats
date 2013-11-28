package com.app.worldbankapi;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;


public class CountryIndicatorResults extends Results
{    
    ArrayList<CountryDataPoint> m_dataPoints;
    Indicator m_indicator;

    public CountryIndicatorResults(Indicator indicator)
    {
        /* Default values */
        m_dataPoints = new ArrayList<CountryDataPoint>();
        m_indicator = indicator;
    }

    public final ArrayList<CountryDataPoint> getDataPoints()
    {
        return m_dataPoints;
    }

    public Indicator getIndicator()
    {
        return m_indicator;
    }

    public static ArrayList<CountryDataPoint> filterByYear(ArrayList<CountryDataPoint> dataPoints, int year)
    {
        ArrayList<CountryDataPoint> ret = new ArrayList<CountryDataPoint>();
        for (CountryDataPoint point : dataPoints)
        {
            final Calendar dateInstance = Calendar.getInstance();
            dateInstance.setTime(point.getDate());
            if (dateInstance.get(Calendar.YEAR) == year)
                ret.add(point);
        }
        return ret;
    }

    public static ArrayList<CountryDataPoint> filterByCountry(ArrayList<CountryDataPoint> dataPoints, Country country)
    {
        ArrayList<CountryDataPoint> ret = new ArrayList<CountryDataPoint>();
        for (CountryDataPoint point : dataPoints)
        {
            if (point.getCountryCode().equals(country.getCode()))
                ret.add(point);
        }
        return ret;
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
                CountryDataPoint dataPoint = new CountryDataPoint();
                dataPoint.fromJSON(dataPoints.getJSONObject(i));
                m_dataPoints.add(dataPoint);
            }
        }

        setChanged();           
    }
}
