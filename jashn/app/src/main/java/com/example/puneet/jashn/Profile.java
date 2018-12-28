package com.example.puneet.jashn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile {
    private String profileID;
    private String fName;
    private String lName;
    private String eMail;
    private String address;
    private String abtYourself;
    private String interest;
    private String image;
    private String password;
    private String createdEvent;
    private String joinedEvent;



    private Map<String,String> crtevnt = new HashMap<>();
    private Map<String,String> actevnt = new HashMap<>();


    public Profile(String profileID, String fName, String lName, String eMail, String password, String address, String abtYourself, String interest, String image, Map<String, String> crtevnt, Map<String, String> actevnt) {
        this.profileID = profileID;
        this.fName = fName;
        this.lName = lName;
        this.eMail = eMail;
        this.address = address;
        this.abtYourself = abtYourself;
        this.interest = interest;
        this.image = image;
        this.password = password;
        this.createdEvent = createdEvent;
        this.joinedEvent = joinedEvent;
        this.crtevnt = crtevnt;
        this.actevnt = actevnt;
    }

    public Profile(){

    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAbtYourself() {
        return abtYourself;
    }

    public void setAbtYourself(String abtYourself) {
        this.abtYourself = abtYourself;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedEvent() {
        return createdEvent;
    }

    public void setCreatedEvent(String createdEvent) {
        this.createdEvent = createdEvent;
    }

    public String getJoinedEvent() {
        return joinedEvent;
    }

    public void setJoinedEvent(String joinedEvent) {
        this.joinedEvent = joinedEvent;
    }
    public Map<String, String> getCrtevnt() {
        return crtevnt;
    }

    public void setCrtevnt(Map<String, String> crtevnt) {
        this.crtevnt = crtevnt;
    }

    public Map<String, String> getActevnt() {
        return actevnt;
    }

    public void setActevnt(Map<String, String> actevnt) {
        this.actevnt = actevnt;
    }


}
