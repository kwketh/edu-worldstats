package com.app.rssreader;


//RSS Reader made with help from a tutorial on androidresearch.wordpress.com 

public class RSSItem
{
    private final String title;
    private final String link;
 
    public RSSItem(String title, String link) {
        title = title.replaceAll("\\<.*?>","");
        title = title.replaceAll("&#39;", "'");
        this.title = title;
        this.link = link;
    }
 
    public String getTitle() {
        return title;
    }
 
    public String getLink() {
        return link;
    }
}