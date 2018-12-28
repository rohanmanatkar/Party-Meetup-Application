package com.example.puneet.jashn;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {

    private String eventID;
    private String eventName;
    private String date;
    private String time;
    private String Location;
    private String Category;
    private String MinAge;
    private String EntryFee;
    private int Color;
    private String Host;
    private String Description;
    private String Guests;
    private String CreatedBy;


    public Event(String eventID, String eventName, String date, String time, String location, String category, String minAge, String entryFee, int color, String host,String description,String guests,String createdBy) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.date = date;
        this.time = time;
        this.Location = location;
        this.Category = category;
        this.MinAge = minAge;
        this.EntryFee = entryFee;
        this.Color = color;
        this.Host = host;
        this.Description = description;
        this.Guests = guests;
        this.CreatedBy = createdBy;
    }
    public Event(){

    }

    protected Event(Parcel in) {
        eventID = in.readString();
        eventName = in.readString();
        date = in.readString();
        time = in.readString();
        Location = in.readString();
        Category = in.readString();
        MinAge = in.readString();
        EntryFee = in.readString();
        Color = in.readInt();
        Host = in.readString();
        Description = in.readString();
        Guests = in.readString();
        CreatedBy = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getMinAge() {
        return MinAge;
    }

    public void setMinAge(String minAge) {
        MinAge = minAge;
    }

    public String getEntryFee() {
        return EntryFee;
    }

    public void setEntryFee(String entryFee) {
        EntryFee = entryFee;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
    public String getGuests() {
        return Guests;
    }

    public void setGuests(String guests) {
        Guests = guests;
    }
    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventID);
        dest.writeString(eventName);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(Location);
        dest.writeString(Category);
        dest.writeString(MinAge);
        dest.writeString(EntryFee);
        dest.writeInt(Color);
        dest.writeString(Host);
        dest.writeString(Description);
        dest.writeString(Guests);
        dest.writeString(CreatedBy);
    }
}
