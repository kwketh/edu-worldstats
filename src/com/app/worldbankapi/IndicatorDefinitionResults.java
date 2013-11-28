package com.app.worldbankapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IndicatorDefinitionResults extends Results
{
    String name;
    String definition;
    String source;
    
    IndicatorDefinitionResults() { }
    
    
    public void fromJSON(JSONArray response) throws JSONException
    {
        super.fromJSON(response);
        
        JSONArray results = response.getJSONArray(1);        
        
            JSONObject indicatorData = results.getJSONObject(0);
            
            String nameA = indicatorData.getString("name");            
            String definitionA = indicatorData.getString("sourceNote");
            String sourceA = indicatorData.getString("sourceOrganization");
            
            source = sourceA;
            name = nameA;
            definition = definitionA;
            
        setChanged();           
    }
    
    public String getName() {
        return name;
    }
    
    public String getDefinition() {
        return definition;
    }
}
