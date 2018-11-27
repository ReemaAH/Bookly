package com.example.atheer.booklyv1;




public class services {

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

    public services( String name, String maxNO, String key, String orgID, String orgName) {

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


    public services(){

    }

    public services(String name, String maxNO, String key) {

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



