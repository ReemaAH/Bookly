package com.example.atheer.booklyv1;

public class Orgz {

    private String Status;
    private String cat;
    private String email;
    private int groupID;
    private String name;
    private String phoneNo;
    private String recordNo;
    private services services[];

    public services[] getServices() {
        return services;
    }

    public void setServices(services[] services) {
        this.services = services;
    }

    public Orgz() {
    }

    public Orgz(String status, String cat, String email, String name, String phoneNo, String recordNo, int groupID,services[] services) {
        Status = status;
        this.cat = cat;
        this.email = email;
        this.groupID = groupID;
        this.name = name;
        this.phoneNo = phoneNo;
        this.recordNo = recordNo;
        this.services = services;

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


}
