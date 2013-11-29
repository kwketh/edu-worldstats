package com.app.rssreader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

//RSS Reader made with help from a tutorial on androidresearch.wordpress.com 

public class RSSParser {
    
    private final String ns = null;
 
    public ArrayList<RSSItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        // parsing the data from the feed
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            inputStream.close();
        }
    }
 
    private ArrayList<RSSItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        // get the data from the actual rss feed
        parser.require(XmlPullParser.START_TAG, null, "rss");
        String title = null;
        String link = null;
        ArrayList<RSSItem> items = new ArrayList<RSSItem>();
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("link")) {
                link = readLink(parser);
            }
            if (title != null && link != null) {
                RSSItem item = new RSSItem(title, link);
                items.add(item);
                title = null;
                link = null;
            }
        }
        return items;
    }
 
    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        // getting the link to the article from the feed
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }
 
    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        // getting the title of the article from the xml
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }
 
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        // For the tags title and link, extracting their text values.
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}