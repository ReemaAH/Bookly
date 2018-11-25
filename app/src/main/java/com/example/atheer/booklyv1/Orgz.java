package com.example.atheer.booklyv1;


import java.io.Serializable;
import java.util.List;

public class Orgz implements Serializable {
    private String Uid;
    private String Status;
    private String cat;
    private String email;
    private int groupID;
    private String image;
    private String name;
    private String phoneNo;
    private String recordNo;
    private List<services> services;
    private List<uloadOffers> offers;


    public Orgz() {
    }

    public Orgz(String status, String cat, String email, String name, String phoneNo, String recordNo, int groupID,  String image) {
        Status = status;
        this.cat = cat;
        this.email = email;
        this.groupID = groupID;
        this.name = name;
        this.phoneNo = phoneNo;
        this.recordNo = recordNo;
     //   this.services = services;
        this.image = image;
      //  this.offers = offers;

    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public List<services> getServices() {
        return services;
    }

    public void setServices(List<services> services) {
        this.services = services;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public void setOffers(List<uloadOffers> offers) {

        this.offers = offers;
    }


    public List<uloadOffers> getOffers() {

        return offers;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}