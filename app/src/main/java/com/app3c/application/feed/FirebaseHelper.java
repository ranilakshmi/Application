package com.app3c.application.feed;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved;
    ArrayList<Event_Post> Posts=new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
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
        //Post Post=dataSnapshot.getValue(Post.class);
        //Posts.add(Post);
        Event_Post post = dataSnapshot.getValue(Event_Post.class);
        Posts.add(post);
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
}