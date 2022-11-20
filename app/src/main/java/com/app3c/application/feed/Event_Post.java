package com.app3c.application.feed;

import java.util.ArrayList;
import java.util.List;

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

    List<String> volunteers;
/*
    public StorageReference getImageurl() {
        return imageurl;
    }

    public void setImageurl(StorageReference imageurl) {
        this.imageurl = imageurl;
    }

    StorageReference imageurl;*/
    public Event_Post(String heading,String subheading,String detail,String contact,String venue,int day,int month,int year){
        super(heading,subheading,detail);
        this.contact = contact;
        this.venue = venue;
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = String.valueOf(day) +"-" + String.valueOf(month)+ "-" + String.valueOf(year);
    }

    public Event_Post(String heading, String subheading, String detail, String contact, String venue, int day, int month, int year, ArrayList<String> volunteers){
        super(heading,subheading,detail);
        this.contact = contact;
        this.venue = venue;
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = String.valueOf(day) +"-" + String.valueOf(month)+ "-" + String.valueOf(year);
        this.volunteers = volunteers;
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
        if (date == null){
            return "";
        }
        return date;
    }
}