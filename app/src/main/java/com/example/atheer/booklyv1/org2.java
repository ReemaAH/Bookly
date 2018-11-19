package com.example.atheer.booklyv1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class org2 {
    private String Status;
    private String cat;
    private String email;
    private int groupID;
    private String name;
    private String phoneNo;
    private String recordNo;
    private services services[];
   // private uloadOffers offer[];
    private ArrayList<uloadOffers> offer;
    private String key;

    public org2(String status, String cat, String email, int groupID, String name, String phoneNo, String recordNo, com.example.atheer.booklyv1.services[] services, ArrayList offer, String key) {
        Status = status;
        this.cat = cat;
        this.email = email;
        this.groupID = groupID;
        this.name = name;
        this.phoneNo = phoneNo;
        this.recordNo = recordNo;
        this.services = services;
        this.offer = offer;
        this.key = key;
        Arrays.asList(offer);
        Arrays.asList(services);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public org2(){

        offer=new ArrayList<>();

}
    public org2(String status, String cat, String email, int groupID, String name, String phoneNo, String recordNo, com.example.atheer.booklyv1.services[] services, ArrayList offer) {
        Status = status;
        this.cat = cat;
        this.email = email;
        this.groupID = groupID;
        this.name = name;
        this.phoneNo = phoneNo;
        this.recordNo = recordNo;
        this.services = services;
        this.offer = offer;
        Arrays.asList(offer);
        Arrays.asList(services);
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

    public uloadOffers getOfferP(int i){return offer.get(i);}

    public int offerSize(){
        if(offer.isEmpty())
            return 0;
        return offer.size();}

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public com.example.atheer.booklyv1.services[] getServices() {
     //   Arrays.asList(offer);
        Arrays.asList(services);
        return services;

    }

    public void setServices(com.example.atheer.booklyv1.services[] services) {
        this.services = services;
        Arrays.asList(services);
    }

    public ArrayList getOffer() {
      //  if(offer.length==0)
        //    return null;
        Arrays.asList(offer);
        return offer;
    }

    public void setOffer(ArrayList offer) {
        Arrays.asList(offer);
        this.offer = offer;
    }
}
