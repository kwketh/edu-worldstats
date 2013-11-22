package com.app.worldbankapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeseriesDataPoint extends DataPoint
{
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    Date m_date;

    TimeseriesDataPoint() {
        super();
    }

    TimeseriesDataPoint(Date date, Double value) {
        m_date = date;
        m_value = value;
    }

    public Date getDate() {
        return m_date;
    }
<<<<<<< HEAD
    
    public String toString()
    {
        return "[DataPoint value=" + prettyValueFormat.format(m_value) + " date=" + m_date + "]";
    }    
    
    public void fromJSON(JSONObject json) throws JSONException
    {
=======

    public String toString() {
        return "[DataPoint value=" + prettyValueFormat.format(m_value) + " date=" + m_date + "]";
    }

    public void fromJSON(JSONObject json) throws JSONException {
>>>>>>> aaec1ff6f6e7ccd29452be75b07f1c26de6f5b18
        super.fromJSON(json);
        String date = json.getString("date");
        try {
            m_date = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("fromJSON failed parsing date from string:");

            System.out.println(date);
        }
    }
}
