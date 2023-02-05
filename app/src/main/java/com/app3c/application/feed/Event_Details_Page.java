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
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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

        /*
        TextView image_url = findViewById(R.id.imageurldetails);

        image_url.setText(event.getImageurl().toString());

        // getting ImageView by its id
        ImageView imageview = findViewById(R.id.image1);
        if (event.getImageurl() != null) {
            imageview.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(event.getImageurl())
                    .into(imageview);

            //Picasso.get().load(event.getImageurl()).into(imageview);
        }
        */
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

                            //TODO Store Volunteer details in firebase
                            //databaseReference.child("event").child(p.getKey()).child("volunteers").setValueAsync(phoneno);
                            //DatabaseReference newref = databaseReference.child("event").child(event.getKey()).child("volunteers").push();
                            //newref.setValue(phoneno);

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