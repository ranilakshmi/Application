package com.app3c.application.feed;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app3c.application.R;
import com.app3c.application.elderly.Elderly;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Event extends AppCompatActivity {

    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.lv);

        //INITIALIZE FIREBASE DB
        
        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");
        helper = new FirebaseHelper(db);

        //Display details of current user
        Intent i = getIntent();
        Elderly user = (Elderly) i.getSerializableExtra("user");
        String userContact = user.getPhoneNo();

        ArrayList<Event_Post> all_events =  helper.retrieve();

        ArrayList<Event_Post> completed_events =  helper.retrieve_completed_events();
        ArrayList<Event_Post> upcoming_events = helper.retrieve_upcoming_events();

        //ArrayList<String> previous_event_ids =  helper.retrieve_registered_events(userContact);
        //ArrayList<Event_Post> previous_events = helper.retrieve_previous_events(userContact);
        //ArrayList<Event_Post> recommended_events =  helper.retrieve();
        // TODO: recommend function should be added here
        // ArrayList <Event_Post> recommended_events = recommend(all_events,previous_events)

        //ADAPTER
        //adapter = new CustomAdapter(this,completed_events,user);
        adapter = new CustomAdapter(this,upcoming_events,user);
        //adapter = new CustomAdapter(this,recommended_events ,user);
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