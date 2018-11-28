package com.example.atheer.booklyv1;

public class commentobject {
String date;
String value;
String writerEmail;
String orgName;

    public commentobject(String date, String writerEmail, String value , String orgID) {
        this.date = date;
        this.value = value;
        this.writerEmail = writerEmail;
        this.orgName = orgID;
    }

    public commentobject(){}

    public String getOrgID() {
        return orgName;
    }

    public void setOrgID(String orgID) {
        this.orgName = orgID;
    }

    public commentobject(String writerEmail, String value, String date ) {
        this.writerEmail = writerEmail;
        this.value=value;
        this.date=date;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
