package com.app3c.application.ngo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app3c.application.R;
import com.app3c.application.feed.CreateEvent;
//import com.app3c.application.test.UpcomingEvents;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NGOLoginPage extends AppCompatActivity {

    
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_login);

        final TextView NGORegisterNow = findViewById(R.id.NGORegister);
        final Button loginBtn = findViewById(R.id.NGOLoginButton);
        NGORegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGOLoginPage.this, NGORegistrationPage.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final TextView NGOcontact = findViewById(R.id.ngo_contact);
                final TextView NGOpassword = findViewById(R.id.NGOpassword);
                String contact = NGOcontact.getText().toString();
                String password = NGOpassword.getText().toString();

                if (contact.isEmpty() || password.isEmpty()){
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter all the details";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    databaseReference.child("ngo").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(contact)) {
                                String ngopassword = snapshot.child(contact).child("password").getValue().toString();
                                if (password.equals(ngopassword)){
                                    Context context = getApplicationContext();
                                    CharSequence text = "Success";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    startActivity(new Intent(NGOLoginPage.this, CreateEvent.class));
                                }
                                else{
                                    Context context = getApplicationContext();
                                    CharSequence text = "Incorrect password";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                }
                            }
                            else{
                                Context context = getApplicationContext();
                                CharSequence text = "Incorrect phone number";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}
