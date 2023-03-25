package com.app3c.application.feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app3c.application.R;
import com.app3c.application.elderly.Elderly;
import com.app3c.application.elderly.RecommendationSystemThread;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Event extends AppCompatActivity {

    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = findViewById(R.id.lv);

        //INITIALIZE FIREBASE DB

        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");
        helper = new FirebaseHelper(db);

        //Display details of current user
        Intent i = getIntent();
        Elderly user = (Elderly) i.getSerializableExtra("user");
        String userContact = user.getPhoneNo();

        //ArrayList<Event_Post> all_events =  helper.retrieve();
        //ArrayList<Event_Post> completed_events =  helper.retrieve_completed_events();
        //ArrayList<Event_Post> upcoming_events = helper.retrieve_upcoming_events();
        //ArrayList<String> previous_event_ids =  helper.retrieve_registered_events(userContact);
        //ArrayList<Event_Post> previous_events = helper.retrieve_previous_events(userContact);
        //ArrayList<Event_Post> recommended_events =  helper.retrieve();
        // TODO: recommend function should be added here
        ArrayList <Event_Post> recommended_events = new ArrayList<>();
        helper.upcoming_events = new ArrayList<>();
        helper.past_events = new ArrayList<>();
        helper.isRegistered = new ArrayList<>();
        helper.past = new HashMap<>();
        helper.upcoming = new HashMap<>();
        helper.retrieve_registered_events(userContact);
        Boolean b = helper.retrieveByDate();
//        for (Integer num: helper.isRegistered){
//            Log.i("is_registered",Integer.toString(num));
//        }
//        Log.i("upcoming_events_size",Integer.toString(helper.upcoming.size()));
//        Log.i("past_events_size",Integer.toString(helper.past.size()));
     try {
         RecommendationSystemThread r = new RecommendationSystemThread();
         r.setHelper(helper);
         r.setUser(userContact);
         Thread t = new Thread(r);
         t.run();
         //recommended_events = helper.retrieve_recommended_events(userContact);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //ADAPTER
        //adapter = new CustomAdapter(this,completed_events,user);
        //adapter = new CustomAdapter(this,upcoming_events,user);
        adapter = new CustomAdapter(this,recommended_events ,user);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Event_Post p = (Event_Post) adapter.getItem(position);
                String value = p.getHeading();
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            }
        });

        //RETRIEVE
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }
}