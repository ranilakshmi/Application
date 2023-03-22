package com.app3c.application.feed;

import java.util.ArrayList;
import java.util.List;

public class Event_Post extends Post{
    int day,month,year;
    String contact,venue,key,date,id;
    List<String> volunteers,categories;
    public Event_Post(){
        super();
    }
    public Event_Post(String eventName, String orgName, String desc, String contact, String venue, String date, String categories){
        super(eventName,orgName,desc);
        this.contact = contact;
        this.venue = venue;

        this.date = date;
        this.categories = categories;
    }

    public Event_Post(String Id,String heading, String subheading, String detail, String contact, String venue,String date,String categories){
        super(heading,subheading,detail);
        this.id = Id;
        this.contact = contact;
        this.venue = venue;
        this.date = date;
        this.categories = categories;
    }
    public Event_Post(String heading,String subheading,String detail,String contact,String venue,int day,int month,int year){
        super(heading,subheading,detail);
        this.contact = contact;
        this.venue = venue;
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = String.valueOf(day) +"-" + String.valueOf(month)+ "-" + String.valueOf(year);
        }


    String contact,venue,key,date;
    String id;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    String categories;


    public String getDate() {
        if (date == null) {
            return "";
        }
        return date;
    }
    public List<String> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<String> volunteers) {
        this.volunteers = volunteers;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    /*
    public StorageReference getImageurl() {
        return imageurl;
    }

    public void setImageurl(StorageReference imageurl) {
        this.imageurl = imageurl;
    }

    StorageReference imageurl;*/
    public void setDate(String date){
        this.date = date;
        }
}