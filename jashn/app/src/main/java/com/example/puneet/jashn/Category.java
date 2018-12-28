package com.example.puneet.jashn;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Locale;

public class  Category implements Serializable,Parcelable{
    private String date;
    private int color;
    private String eventName;
    private String eventDetail;
    private String eventID;
    private String minAge;
    private String guests;
    private String venue;
    private String fees;
    private String time;



    public Category(){

    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public static Creator<Category> getCREATOR() {
        return CREATOR;
    }

    public Category(String date, String eventName, String eventDetail, String eventID, String minAge, String guests, String venue, String fees, String time, int color) {
        this.date = date;
        this.eventName = eventName;
        this.eventDetail = eventDetail;
        this.eventID = eventID;
        this.minAge = minAge;
        this.guests = guests;
        this.venue = venue;
        this.fees = fees;
        this.time = time;
        this.color = color;
    }

    protected Category(Parcel in) {
        date = in.readString();
        eventName = in.readString();
        eventDetail = in.readString();
        time = in.readString();
        venue = in.readString();
        guests = in.readString();
        fees = in.readString();
        minAge = in.readString();
        eventID = in.readString();
        color = in.readInt();


    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int col) {
        this.color = col;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(eventName);
        dest.writeString(eventDetail);
        dest.writeString(time);
        dest.writeString(venue);
        dest.writeString(guests);
        dest.writeString(fees);
        dest.writeString(minAge);
        dest.writeString(eventID);
        dest.writeInt(color);

    }
}
