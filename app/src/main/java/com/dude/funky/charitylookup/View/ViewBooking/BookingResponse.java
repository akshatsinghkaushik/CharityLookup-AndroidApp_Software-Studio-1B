package com.dude.funky.charitylookup.View.ViewBooking;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class BookingResponse {
    private String time;
    private String item;
    private String date;
    //private String name;
    //private String email;


    public BookingResponse() {
    }

    public BookingResponse(String time, String item, String date) {
        this.time = time;
        this.item = item;
        this.item = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
