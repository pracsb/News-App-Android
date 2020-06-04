package com.example.myapp;

public class BookmarkItem {

    private String b_title;
    private String b_date;
    private String b_imageUrl;
    private String b_section;
    private String b_body;
    private String b_shareUrl;

    public BookmarkItem(String b_title, String b_date, String b_imageUrl, String b_section, String b_body, String b_shareUrl) {
        this.b_title = b_title;
        this.b_date = b_date;
        this.b_imageUrl = b_imageUrl;
        this.b_section = b_section;
        this.b_body = b_body;
        this.b_shareUrl = b_shareUrl;
    }

    public String getB_title() {
        return b_title;
    }

    public String getB_date() {
        return b_date;
    }

    public String getB_imageUrl() {
        return b_imageUrl;
    }

    public String getB_section() {
        return b_section;
    }

    public String getB_body() {
        return b_body;
    }

    public String getB_shareUrl() {
        return b_shareUrl;
    }


    public void setB_title(String b_title) {
        this.b_title = b_title;
    }

    public void setB_date(String b_date) {
        this.b_date = b_date;
    }

    public void setB_imageUrl(String b_imageUrl) {
        this.b_imageUrl = b_imageUrl;
    }

    public void setB_section(String b_section) {
        this.b_section = b_section;
    }

    public void setB_body(String b_body) {
        this.b_body = b_body;
    }

    public void setB_shareUrl(String b_shareUrl) {
        this.b_shareUrl = b_shareUrl;
    }
}
