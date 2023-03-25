package com.app3c.application.elderly;

import com.app3c.application.feed.Event_Post;
import com.app3c.application.feed.FirebaseHelper;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RecommendationSystemThread implements Runnable{
    String user;

    public ArrayList<Event_Post> getRecommended_events() {
        return recommended_events;
    }

    public void setRecommended_events(ArrayList<Event_Post> recommended_events) {
        this.recommended_events = recommended_events;
    }

    ArrayList<Event_Post> recommended_events;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public FirebaseHelper getHelper() {
        return helper;
    }

    public void setHelper(FirebaseHelper helper) {
        this.helper = helper;
    }

    FirebaseHelper helper;
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       //this.recommended_events =  this.helper.retrieve_recommended_events(this.user);
    }
}