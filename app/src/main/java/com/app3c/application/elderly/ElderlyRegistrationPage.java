package com.app3c.application.elderly;

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

import com.app3c.application.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ElderlyRegistrationPage extends AppCompatActivity {

    
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_register);

        final EditText Name = findViewById(R.id.fullname);
        final EditText Age = findViewById(R.id.age);
        final EditText Email = findViewById(R.id.email);
        final EditText Phone = findViewById(R.id.phone);
        final EditText Location = findViewById(R.id.location);
        final EditText Password = findViewById(R.id.password);
        final EditText ConfirmPassword = findViewById(R.id.confirmpassword);

        final Button RegisterButton = findViewById(R.id.RegisterBtn);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname = Name.getText().toString();
                final String age = Age.getText().toString();
                final String email = Email.getText().toString();
                final String phone = Phone.getText().toString();
                final String location = Location.getText().toString();
                final String password = Password.getText().toString();
                final String confirmpassword = ConfirmPassword.getText().toString();

                if (fullname.isEmpty() || age.isEmpty()||email.isEmpty()||phone.isEmpty()||location.isEmpty()||password.isEmpty()||confirmpassword.isEmpty()){
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
                    databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(phone)){
                                Context context = getApplicationContext();
                                CharSequence text = "Phone is already registered";
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

                                databaseReference.child("user").child(phone).child("name").setValue(fullname);
                                databaseReference.child("user").child(phone).child("age").setValue(age);
                                databaseReference.child("user").child(phone).child("email").setValue(email);
                                databaseReference.child("user").child(phone).child("location").setValue(location);
                                databaseReference.child("user").child(phone).child("password").setValue(password);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        final TextView ElderlyLoginNow = findViewById(R.id.ElderlyLoginNow);
        ElderlyLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ElderlyRegistrationPage.this, ElderlyLoginPage.class));
            }
        });
    }
}