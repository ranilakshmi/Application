package com.app3c.application.feed;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app3c.application.NGOLoginPage;
import com.app3c.application.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateEvent extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://application-36c82-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        final EditText eventName = findViewById(R.id.event_name);
        final EditText organizationName = findViewById(R.id.organization_name);
        final EditText description = findViewById(R.id.event_description);
        final EditText eventContact = findViewById(R.id.event_contact);
        final EditText eventLocation = findViewById(R.id.event_location);
        final Button RegisterBtn = findViewById(R.id.eventRegisterBtn);


        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String EventName = eventName.getText().toString();
                final String OrgName = organizationName.getText().toString();
                final String desc = description.getText().toString();
                final String contact = eventContact.getText().toString();
                final String venue = eventLocation.getText().toString();


                if (EventName.isEmpty() || desc.isEmpty()||contact.isEmpty()||OrgName.isEmpty()||venue.isEmpty()){
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter all the details";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    Event_Post event_post = new Event_Post(EventName,OrgName,desc,contact,venue);
                    FirebaseHelper helper = new FirebaseHelper(databaseReference);
                    helper.save(event_post,"event");
                    Context context = getApplicationContext();
                    CharSequence text = "Event Registered";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    finish();

                }
            }
        });
    }
}