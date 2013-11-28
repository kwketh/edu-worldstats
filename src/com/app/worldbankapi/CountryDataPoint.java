package com.app.worldbankapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class CountryDataPoint extends TimeseriesDataPoint
{
    private String m_countryCode;

    CountryDataPoint()
    {
        super();
    }

    CountryDataPoint(Date date, Double value, String countryCode)
    {
        super(date, value);
        m_countryCode = countryCode;
    }

    public String getCountryCode()
    {
        return m_countryCode;
    }

    @Override
    public void fromJSON(JSONObject json) throws JSONException
    {
        super.fromJSON(json);

        JSONObject country = json.getJSONObject("country");
        m_countryCode = country.getString("id");
    }
}
