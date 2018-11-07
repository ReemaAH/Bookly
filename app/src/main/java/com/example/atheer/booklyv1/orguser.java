package com.example.atheer.booklyv1;

import java.io.Serializable;

public class orguser implements Serializable {
    private  String Uid;
    private  String Status;
    private  String cat;
    private  String email;
    private  int groupID;
    private   String name;
    private   String phoneNO;
    private   String recordNO;

    public orguser(){

    }


    public orguser(String name){
        this.name=name;

    }

    public orguser(String Status, String email, int groupID,String name ,String phoneNO, String recocrdNO){
        this.name=name;
        this.Status=Status;
        this.email=email;
        this.groupID=groupID;
        this.phoneNO=phoneNO;

        this.recordNO=recocrdNO;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
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

    public String getPhoneNO() {
        return phoneNO;
    }

    public void setPhoneNO(String phoneNO) {
        this.phoneNO = phoneNO;
    }


    public String getRecordNO() {
        return recordNO;
    }

    public void setRecordNO(String recordNO) {
        this.recordNO = recordNO;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}