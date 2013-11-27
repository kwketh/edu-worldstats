package com.app;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.content.Context;

public class MainApp extends Application 
{
    /**
     * bundleData is used to store a state of the application
     * through its running time.
     *  
     * A single object CountryListResults is stored to only 
     * load the results once and remember them, in other
     * words caching the country list.
     */
    private final Map<String, Object> bundleData = new HashMap<String, Object>();

    public void storeData(String key, Object data) 
    {
        this.bundleData.put(key, data);
    }
    
    public boolean hasData(String key) 
    {        
        return this.bundleData.containsKey(key);
    }

    public Object getData(String key) 
    {
        return this.bundleData.get(key);
    }
    
    public static int getResourceById(String variableName, Context context, Class<?> c) 
    {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } 
    }    
}