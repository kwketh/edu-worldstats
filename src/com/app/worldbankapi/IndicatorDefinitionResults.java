package com.app.worldbankapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IndicatorDefinitionResults extends Results
{
    String name;
    String definition;

    public void fromJSON(JSONArray response) throws JSONException
    {
        // fetches JSON results and takes the indicator name and definition from them
        super.fromJSON(response);
        
        JSONArray results = response.getJSONArray(1);
        JSONObject indicatorData = results.getJSONObject(0);

        name = indicatorData.getString("name");
        definition = indicatorData.getString("sourceNote");
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDefinition()
    {
        return definition;
    }
}
