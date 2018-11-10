package com.example.atheer.booklyv1;

import com.google.firebase.database.Exclude;

public class uloadOffers {
    private String mName, eDate, sDate, precentage;
    private String mImageUrl;
    private String mKey;

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public uloadOffers(String mName, String mImageUrl, String sDate, String eDate, String precentage) {
        this.sDate = sDate;
        this.eDate = eDate;
        this.mName = mName;
        this.mImageUrl = mImageUrl;
        this.precentage = precentage;


    }

    public uloadOffers() {
        this.sDate = "";
        this.eDate = "";
        this.mName = "";
        this.mImageUrl = "";
        this.precentage = "";
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getPrecentage() {

        return precentage;
    }

    public void setPrecentage(String precentage) {
        this.precentage = precentage;
    }

    public String getsDate() {

        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {

        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getmName() {

        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
