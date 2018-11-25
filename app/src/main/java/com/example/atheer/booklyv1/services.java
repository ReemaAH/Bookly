package com.example.atheer.booklyv1;




public class services {

    private String price;
    private String name;
    private String maxNO;
    private String key;
    private String orgID;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public services(String price, String name, String maxNO, String key, String orgID, String orgName) {

        this.price = price;
        this.name = name;
        this.maxNO = maxNO;
        this.key = key;
        this.orgID = orgID;
        this.orgName = orgName;
    }

    private String orgName;

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public services(String price, String name, String maxNO, String key, String orgID) {

        this.price = price;
        this.name = name;
        this.maxNO = maxNO;
        this.key = key;
        this.orgID = orgID;
    }

    public services(){

    }

    public services(String price, String name, String maxNO, String key) {
        this.price = price;
        this.name = name;
        this.maxNO = maxNO;
        this.key = key;
    }

    public String getMaxNO() {
        return maxNO;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setMaxNO(String maxNO) {
        this.maxNO = maxNO;
    }

    public services (String name){
        this.name=name;
    }

    public services (String name, String price , String maxNO ){
        this.name=name;
        this.price=price;
        this.maxNO=maxNO;


    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getRating() {
        return maxNO;
    }

    public void setRating(String maxNO) {
        this.maxNO=maxNO;
    }
}



