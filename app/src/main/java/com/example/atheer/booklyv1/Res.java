package com.example.atheer.booklyv1;

public class Res {

    public boolean approved;
    public String date;
    public int  num;
    public String org;
    public String orgID;
    public String resNum;
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

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }




    public String getResNum() {
        return resNum;
    }

    public void setResNum(String resNum) {
        this.resNum = resNum;
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



    public Res(boolean approved, String date, int num, String org, String orgIDString, String resNum,  String service, String time) {
        this.approved = approved;
        this.date = date;
        this.num = num;
        this.org = org;
        this.orgID = orgID;
        this.service = service;
        this.time = time;
        this.resNum = resNum;


    }

    public Res() {
    }
}
