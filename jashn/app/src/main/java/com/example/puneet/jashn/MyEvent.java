package com.example.puneet.jashn;

import android.os.Parcel;
import android.os.Parcelable;

public class MyEvent implements Parcelable {
    private String id;
    private String eventID;
    private String profileID;
    private String time;
    private String title;
    private String category;
    private String host;
    private String date;
    private String description;


    private String fees;

    public MyEvent(){
    }

    public MyEvent(String id, String eventID, String profileID, String time, String title, String category, String host,String date,String description,String fees) {
        this.id = id;
        this.eventID = eventID;
        this.profileID = profileID;
        this.time = time;
        this.title = title;
        this.category = category;
        this.host = host;
        this.date = date;
        this.description = description;
        this.fees = fees;
    }

    protected MyEvent(Parcel in) {
        id = in.readString();
        eventID = in.readString();
        profileID = in.readString();
        time = in.readString();
        title = in.readString();
        category = in.readString();
        host = in.readString();
        date = in.readString();
        description = in.readString();
        fees = in.readString();
    }

    public static final Creator<MyEvent> CREATOR = new Creator<MyEvent>() {
        @Override
        public MyEvent createFromParcel(Parcel in) {
            return new MyEvent(in);
        }

        @Override
        public MyEvent[] newArray(int size) {
            return new MyEvent[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(eventID);
        dest.writeString(profileID);
        dest.writeString(time);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(host);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(fees);
    }

    public void setEventID(MyEvent myEvent) {
    }
}
