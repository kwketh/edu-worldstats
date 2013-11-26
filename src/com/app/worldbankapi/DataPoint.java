package com.app.worldbankapi;

import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

public class DataPoint
{    
    static DecimalFormat prettyDateFormat = new DecimalFormat("#.##");
    static DecimalFormat prettyValueFormat;
        
    protected Double m_value = 0.0;
    private int m_decimals = 0;
    private boolean m_isNull = false;
    
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
    
    public String getFormattedValue()
    {
        String format = "#,###";
        if (m_decimals > 0) format += ".";
        for (int i = 0; i < m_decimals; i++) format += "#";
        prettyValueFormat = new DecimalFormat(format);        
        return prettyValueFormat.format(getValue());
    }
    
    public boolean isNullValue()
    {
        return m_isNull;
    }
        
    public String toString()
    {
        return "[DataPoint value=" + prettyDateFormat.format(m_value) + "]";
    }
    
    public void fromJSON(JSONObject json) throws JSONException
    {
        m_isNull = json.isNull("value");
        if (!m_isNull) {
            m_value = json.getDouble("value");
            m_decimals = json.optInt("decimal");
        }
    }    
}
