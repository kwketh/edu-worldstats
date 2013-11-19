package com.app.worldbankapi;

import java.util.Observable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Results extends Observable
{
    boolean m_loaded;
    int m_totalCount;
    int m_pagesCount;
    int m_pageNo;
    
    Results()
    {
        super();
        m_loaded = false;
    }
    
    public void fromJSON(JSONArray response) throws JSONException 
    {
        JSONObject pagingData = response.getJSONObject(0);
                    
        m_totalCount = pagingData.getInt("total");
        m_pagesCount = pagingData.getInt("pages");
        m_pageNo = pagingData.getInt("page");
                
        setChanged();
    }
    
    public boolean isLoaded()
    {
        return m_loaded;
    }
    
    public void setLoaded(boolean loaded)
    {
        m_loaded = loaded;
    }
    
    public void setChanged()
    {
        super.setChanged();
    }   
}
