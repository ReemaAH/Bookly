package com.example.atheer.booklyv1;

public class Res {

    public boolean approved;
    public String date;
    public int  num;
    public String org;
    public String service;
    public String time;


    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public Res(boolean approved, String date, int num, String org, String service, String time) {
        this.approved = approved;
        this.date = date;
        this.num = num;
        this.org = org;
        this.service = service;
        this.time = time;

    }

    public Res() {
    }
}
