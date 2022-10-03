package com.app3c.application.feed;

public class Event_Post extends Post{
    Event_Post(){
        super();
    }
    Event_Post(String heading,String subheading,String detail,String contact,String venue){
        super(heading,subheading,detail);
        this.contact = contact;
        this.venue = venue;
    }
    String contact,venue;
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
}