package com.proprog.newsapp;

/**
 * Created by mohamedAHMED on 2017-11-16.
 */

public class News {
    private String sectionName;
    private String webPublicationDate;
    private String webTitle;
    private String webUrl;

    public News(String sectionName, String webPublicationDate, String webTitle, String webUrl) {
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    @Override
    public String toString() {
        return "News{" +
                "sectionName='" + sectionName + '\'' +
                ", webPublicationDate='" + webPublicationDate + '\'' +
                ", webTitle='" + webTitle + '\'' +
                ", webUrl='" + webUrl + '\'' +
                '}';
    }
}
