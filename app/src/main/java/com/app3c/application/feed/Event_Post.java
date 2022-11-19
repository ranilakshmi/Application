package com.app3c.application.feed;

import android.text.style.SuperscriptSpan;

import java.util.Date;

public class Event_Post extends Post{
    public Event_Post(){
        super();
    }
    public Event_Post(String heading, String subheading, String detail, String contact, String venue){
        super(heading,subheading,detail);
        this.contact = contact;
        this.venue = venue;
    }
    public Event_Post(String Id,String heading, String subheading, String detail, String contact, String venue){
        super(heading,subheading,detail);
        this.id = Id;
        this.contact = contact;
        this.venue = venue;
    }

    int day,month,year;
    String contact,venue,key,date;
    String id;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    String imageurl;
    public Event_Post(String heading,String subheading,String detail,String contact,String venue,int day,int month,int year){
        super(heading,subheading,detail);
        this.contact = contact;
        this.venue = venue;
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = String.valueOf(day) +"-" + String.valueOf(month)+ "-" + String.valueOf(year);
    }

    public void setContact(String contact){
        this.contact = contact;
    }
    public String getContact(){
        return contact;
    }
    public void setVenue(String venue){
        this.venue = venue;
    }
    public String getVenue(){
        return venue;
    }
    public void setKey(String key){this.key = key;}
    public String getKey(){return this.key;}
    public String getDate() {
        return date;
    }
}