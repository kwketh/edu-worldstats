package com.app;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

public class DataPoint
{    
    final static DecimalFormat prettyFormat = new DecimalFormat("#.##");
    Double m_value;
    
    DataPoint()
    {

    }
    
    DataPoint(Double value) 
    {
        m_value = value;
    }
    
    public Double getValue()
    {
        return m_value;
    }

    public String toString()
    {
        return "[DataPoint value=" + prettyFormat.format(m_value) + "]";
    }
    
    public void fromJSON(JSONObject json) throws JSONException
    {
        m_value = json.getDouble("value");
    }    
}
