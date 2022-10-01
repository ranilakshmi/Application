package com.app3c.application;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NGORegistrationPage extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://application-36c82-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_register);

        final EditText NGO_FullName = findViewById(R.id.NGOfullname);
        final EditText description = findViewById(R.id.description_ngo);
        final EditText NGOemail = findViewById(R.id.NGOemail);
        final EditText NGOcontact = findViewById(R.id.NGOcontact);
        final EditText NGOlocation = findViewById(R.id.NGOlocation);
        final EditText NGOpassword = findViewById(R.id.NGOpassword);
        final EditText NGOconfirmpassword = findViewById(R.id.NGOconfirmpassword);
        final Button RegisterBtn = findViewById(R.id.NGORegisterBtn);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname = NGO_FullName.getText().toString();
                final String desc = description.getText().toString();
                final String email = NGOemail.getText().toString();
                final String contact = NGOcontact.getText().toString();
                final String location = NGOlocation.getText().toString();
                final String password = NGOpassword.getText().toString();
                final String confirmpassword = NGOconfirmpassword.getText().toString();

                if (fullname.isEmpty() || desc.isEmpty()||email.isEmpty()||contact.isEmpty()||location.isEmpty()||password.isEmpty()||confirmpassword.isEmpty()){
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter all the details";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if(!password.equals(confirmpassword)){
                    Context context = getApplicationContext();
                    CharSequence text = "Passwords are not matching";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    databaseReference.child("ngo").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(email)){
                                Context context = getApplicationContext();
                                CharSequence text = "Email is already registered";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                            else{
                                Context context = getApplicationContext();
                                CharSequence text = "NGO Registered";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                finish();

                                databaseReference.child("ngo").child(contact).child("name").setValue(fullname);
                                databaseReference.child("ngo").child(contact).child("description").setValue(desc);
                                databaseReference.child("ngo").child(contact).child("contact").setValue(email);
                                databaseReference.child("ngo").child(contact).child("location").setValue(location);
                                databaseReference.child("ngo").child(contact).child("password").setValue(password);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        final TextView NGOLoginNow = findViewById(R.id.NGOLoginNow);
        NGOLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGORegistrationPage.this,NGOLoginPage.class));
            }
        });
    }
}