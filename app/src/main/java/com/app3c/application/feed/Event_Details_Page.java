package com.app3c.application.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app3c.application.R;
import com.app3c.application.elderly.Elderly;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Event_Details_Page extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_page);

        Intent intent = getIntent();

        Elderly user = (Elderly) intent.getSerializableExtra("user");

        Event_Post event = (Event_Post) intent.getSerializableExtra("selected_event");

        TextView event_name = findViewById(R.id.label_event_title);
        event_name.setText(event.getHeading());

        //TODO date should be included in event details
        TextView event_date = findViewById(R.id.label_event_date);
        String date = event.getDate();
        if (date.isEmpty()){
            event_date.setVisibility(View.INVISIBLE);
        }
        else {
            event_date.setText("Date: " + event.getDate());
        }
        //TODO time of event

        TextView event_venue = findViewById(R.id.label_event_venue);
        event_venue.setText("Venue :" + event.getVenue());

        // getting ImageView by its id
        ImageView imageview = findViewById(R.id.image1);

        // we will get the default FirebaseDatabase instance
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        // we will get a DatabaseReference for the database
        // root node
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        // Here "image" is the child node value we are
        // getting child node data in the getImage variable
        DatabaseReference getImage
                = databaseReference.child("Image");

        // Adding listener for a single change
        // in the data at this location.
        // this listener will triggered once
        // with the value of the data at the location
        getImage.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(
                            @NonNull DataSnapshot dataSnapshot)
                    {// getting a DataSnapshot for the
                        // location at the specified relative
                        // path and getting in the link variable
                        String link = dataSnapshot.getValue(
                                String.class);

                        // loading that data into rImage
                        // variable which is ImageView
                        Picasso.get().load(link).into(imageview);
                    }

                    // this will called when any problem
                    // occurs in getting data
                    @Override
                    public void onCancelled(
                            @NonNull DatabaseError databaseError)
                    {
                        // we are showing that error message in
                        // toast
                        Toast
                                .makeText(Event_Details_Page.this,
                                        "Error Loading Image",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        TextView event_details = findViewById(R.id.text_event_details);
        event_details.setText(event.getDetail());


        String phoneno = user.getPhoneNo();
        Button Apply_btn = findViewById(R.id.ApplyButton);
        Apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(phoneno)){
                            String name = snapshot.child(phoneno).child("name").getValue().toString();
                            Context context = getApplicationContext();

                            //databaseReference.child("event").child(p.getKey()).child("volunteers").setValueAsync(phoneno);
                            DatabaseReference newref = databaseReference.child("event").child(event.getKey()).child("volunteers").push();
                            newref.setValue(phoneno);

                            DatabaseReference newappliedevent = databaseReference.child("user").child(phoneno).child("applied_events").push();
                            newappliedevent.setValue(event.getKey());

                            CharSequence text = "Applied";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                        else{
                            Context context = getApplicationContext();
                            CharSequence text = "User Registered";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}