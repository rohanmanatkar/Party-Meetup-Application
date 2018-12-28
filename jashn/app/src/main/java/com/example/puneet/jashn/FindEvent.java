package com.example.puneet.jashn;

public class FindEvent {
    private String location;
    private int distance;
    private int number_of_guests;
    private int entry_fee;
    private String sortBy;
    private String category;

    public FindEvent(String location, int distance, int number_of_guests, int entry_fee, String sortBy, String category) {
        this.location = location;
        this.distance = distance;
        this.number_of_guests = number_of_guests;
        this.entry_fee = entry_fee;
        this.sortBy = sortBy;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getNumber_of_guests() {
        return number_of_guests;
    }

    public void setNumber_of_guests(int number_of_guests) {
        this.number_of_guests = number_of_guests;
    }

    public int getEntry_fee() {
        return entry_fee;
    }

    public void setEntry_fee(int entry_fee) {
        this.entry_fee = entry_fee;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
