package com.app;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;

public class MainApp extends Application 
{
    private final Map<String, Object> bundleData = new HashMap<String, Object>();

    public void storeData(String key, Object data) {
        this.bundleData.put(key, data);
    }

    public Object getData(String key) {
        return this.bundleData.get(key);
    }
}