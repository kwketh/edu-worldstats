package com.app.rssreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

//RSS Reader made with help from a tutorial on androidresearch.wordpress.com 

public class RSSService extends IntentService {

        private static final String RSS_LINK = "http://www.google.com/alerts/feeds/11738636093529069530/14181202769593344747";
        public static final String ITEMS = "items";
        public static final String RECEIVER = "receiver";

        public RSSService() {
                super("RSSService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            // getting the xml parser from RSSParser
                Log.d("RSSService", "Service started");
                List<RSSItem> rssItems = null;
                try {
                        RSSParser parser = new RSSParser();
                        rssItems = parser.parse(getInputStream(RSS_LINK));
                } catch (XmlPullParserException e) {
                        Log.w(e.getMessage(), e);
                } catch (IOException e) {
                        Log.w(e.getMessage(), e);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(ITEMS, (Serializable) rssItems);
                ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
                receiver.send(0, bundle);
        }

        public InputStream getInputStream(String link) {
            // getting the input stream from the URL
                try {
                        URL url = new URL(link);
                        return url.openConnection().getInputStream();
                } catch (IOException e) {
                        Log.w("getInputStream", "Exception while retrieving the input stream", e);
                        return null;
                }
        }
}