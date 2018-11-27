package com.example.atheer.booklyv1;

public class Res {

    public boolean approved;
    public String date;
    public int  num;
    public String org;
    //public String orgID;
    public String resNum;
    public String service;
    public String time, clientID;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

   //public String getCoupon() {
   //     return coupon;
    //}

    //public void setCoupon(String coupon) {
    //    this.coupon = coupon;
    //}

    public Res(boolean approved, String date, int num, String org, String resNum, String service, String time, String clientID) {

        this.approved = approved;
        this.date = date;
        this.num = num;
        this.org = org;
      //  this.orgID = orgID;
        this.resNum = resNum;
        this.service = service;
        this.time = time;
        this.clientID = clientID;
        //this.coupon = coupon;
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

  //  public String getOrgID() {
       // return orgID;
   // }

    //public void setOrgID(String orgID) {
    //    this.orgID = orgID;
  //  }




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




    public Res() {
    }
}
