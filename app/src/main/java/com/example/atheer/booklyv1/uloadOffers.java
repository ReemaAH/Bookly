package com.example.atheer.booklyv1;

import com.google.firebase.database.Exclude;

public class uloadOffers {
    private String mName, eDate, sDate, precentage,coupon;
    private String mImageUrl;
    private String mKey;

    public uloadOffers(){

    }

    public uloadOffers( String mName,String trim, String eDate, String sDate, String precentage, String coupon) {
        this.mName = mName;
        this.eDate = eDate;
        this.sDate = sDate;
        this.precentage = precentage;
        this.coupon = coupon;
        mImageUrl=trim;
    }

    public uloadOffers(String mName, String eDate, String sDate, String precentage, String coupon, String mImageUrl, String mKey) {
        this.mName = mName;
        this.eDate = eDate;
        this.sDate = sDate;
        this.precentage = precentage;
        this.coupon = coupon;
        this.mImageUrl = mImageUrl;
        this.mKey = mKey;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getPrecentage() {
        return precentage;
    }

    public void setPrecentage(String precentage) {
        this.precentage = precentage;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
