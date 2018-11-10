package com.example.atheer.booklyv1;

public class Reservation {

    private boolean approved;
    private String date;
    private String service;
    private String org, orgID, resNum, clientID;
    private String time,Uid;
    private int num;

public Reservation(){

}

    public Reservation(boolean approved, String date, String service, String org, String orgID, String resNum, String clientID, String time, String uid, int num) {
        this.approved = approved;
        this.date = date;
        this.service = service;
        this.org = org;
        this.orgID = orgID;
        this.resNum = resNum;
        this.clientID = clientID;
        this.time = time;
        Uid = uid;
        this.num = num;
    }

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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
