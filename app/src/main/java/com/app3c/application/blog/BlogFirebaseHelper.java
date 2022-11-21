package com.app3c.application.blog;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class BlogFirebaseHelper {
    DatabaseReference db;
    Boolean saved;
    ArrayList<BlogPost> Posts = new ArrayList<>();

    public BlogFirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public Boolean save(BlogPost Post, String dbName) {
        if (Post == null) {
            saved = false;
        } else {
            try {
                db.child(dbName).push().setValue(Post);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }

        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot) {
        BlogPost post = dataSnapshot.getValue(BlogPost.class);
        Posts.add(post);
    }

    //RETRIEVE
    public ArrayList<BlogPost> retrieve() {
        db.child("post").addChildEventListener(new ChildEventListener() {
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
