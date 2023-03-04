package com.app3c.application.feed;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved;
    ArrayList<Event_Post> Posts=new ArrayList<>();
    ArrayList<String>event_ids = new ArrayList<>();
    SimpleDateFormat simpleDateFormat;
    Date today;

    ArrayList<Event_Post> past_events = new ArrayList<>();
    ArrayList<Event_Post> upcoming_events = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;

        Timestamp timestamp = new Timestamp(System. currentTimeMillis());
        today = new Date(timestamp. getTime());

        Log.i("today",today.toString());
    }

    public Boolean save(Post Post,String dbName)
    {
        if(Post==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child(dbName).push().setValue(Post);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        Event_Post post = dataSnapshot.getValue(Event_Post.class);
        String key = dataSnapshot.getKey();
        post.setKey(key);
        Posts.add(post);
    }

    private void fetchEvents(DataSnapshot dataSnapshot)
    {
        String event_id = dataSnapshot.getValue(String.class);
        Log.i("event_id",event_id);
        event_ids.add(event_id);
    }

    private void fetchEventsByDate(DataSnapshot dataSnapshot)
    {
        Event_Post post = dataSnapshot.getValue(Event_Post.class);
        String key = dataSnapshot.getKey();
        post.setKey(key);

        String event_date = post.getDate();
        SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
        Date d1 = new Date();
        try {
            d1 = sdformat.parse(event_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("postdate",post.getDate()+"_"+d1.toString());

        if (isUpcomingEvent(d1)) {
            upcoming_events.add(post);
        }
        else {
            past_events.add(post);
        }
    }

    //RETRIEVE
    public ArrayList<Event_Post> retrieve()
    {
        db.child("event").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return Posts;
    }

    public void retrieveByDate()
    {
        db.child("event").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchEventsByDate(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchEventsByDate(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Event_Post> retrieve_completed_events(){
        retrieveByDate();
        return past_events;
    }

    public ArrayList<Event_Post> retrieve_upcoming_events(){
        retrieveByDate();
        return upcoming_events;
    }

    //TODO Modify this method to retrieve the events the user has attended
    public ArrayList<String> retrieve_registered_events(String user)
    {
        db.child("user").child(user).child("applied_events").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchEvents(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchEvents(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return event_ids;
    }

    public Boolean isUpcomingEvent(Date date){
        Boolean result = TRUE;
        if(today.compareTo(date) > 0) {
           result =  FALSE;
        }
        return result;
    }
}