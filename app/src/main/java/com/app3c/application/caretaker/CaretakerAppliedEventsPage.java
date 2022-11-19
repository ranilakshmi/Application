package com.app3c.application.caretaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app3c.application.R;
import com.app3c.application.elderly.Elderly;
import com.app3c.application.feed.CustomAdapter;
import com.app3c.application.feed.Event_Post;
import com.app3c.application.feed.FirebaseHelper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CaretakerAppliedEventsPage extends AppCompatActivity {

    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_applied_events_page);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.lv);
        //INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");
        helper = new FirebaseHelper(db);
        //Display details of current user
        Intent i = getIntent();
        Caretaker caretaker = (Caretaker) i.getSerializableExtra("caretaker");
        String username = caretaker.getPhoneNumber();
        //ADAPTER
        adapter = new CustomAdapter(this, helper.retrieve(),caretaker);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
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