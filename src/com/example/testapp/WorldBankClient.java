package com.example.testapp;

import com.loopj.android.http.*;

public class WorldBankClient
{
    private static final String baseUrl = "http://api.worldbank.org";
    private static AsyncHttpClient clientProtocol = new AsyncHttpClient();
    
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        clientProtocol.get(baseUrl + url, params, responseHandler);
    }
}