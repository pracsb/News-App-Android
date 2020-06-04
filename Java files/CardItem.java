package com.example.myapp;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CardItem {

    private String mimageUrl;
    private String mtitle;
    private String mshareUrl;
    private String time;
    private String msection;
    private String marticle_id;
    private String mbody;
    private String date;
    public String getMbody() {
        return mbody;
    }

    public void setMbody(String mbody) {
        this.mbody = mbody;
    }

    public String getMsection() {
        return msection;
    }

    public void setMsection(String msection) {
        this.msection = msection;
    }



    public CardItem(String mimageUrl, String mtitle, String marticle_id, String mshareUrl, String time, String date, String msection, String mbody) {
        this.mimageUrl = mimageUrl;
        this.mtitle = mtitle;
        this.mshareUrl = mshareUrl;
        this.time = time;
        this.marticle_id = marticle_id;
        this.msection = msection;
        this.mbody = mbody;
        this.date = date;

    }


    public void changeBookmark(){
        Log.d("Bookmark Clicked : ", "YES BOOKMARK CLICKED");
    }


    public String getMarticle_id() {
        return marticle_id;
    }

    public void setMarticle_id(String marticle_id) {
        this.marticle_id = marticle_id;
    }

    public String getMimageUrl() {
        return mimageUrl;
    }

    public void setMimageUrl(String mimageUrl) {
        this.mimageUrl = mimageUrl;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMshareUrl() {
        return mshareUrl;
    }

    public void setMshareUrl(String mshareUrl) {
        this.mshareUrl = mshareUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}


