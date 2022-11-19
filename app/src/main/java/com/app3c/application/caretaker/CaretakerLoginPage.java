package com.app3c.application.caretaker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app3c.application.R;
import com.app3c.application.elderly.Elderly;
import com.app3c.application.elderly.ElderlyLoginPage;
import com.app3c.application.feed.Event;
import com.app3c.application.medicine.MedicineActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CaretakerLoginPage extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_login);

        //Context context = getApplicationContext();

        // Logic for logging in
        final Button LoginBtn = findViewById(R.id.caretakerLoginButton);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText PhoneNumber =findViewById(R.id.caretakerphoneNumber);
                final EditText Password = findViewById(R.id.caretakerpassword);
                final String phonenumber = PhoneNumber.getText().toString();
                final String password = Password.getText().toString();

                // If phone number or password is not entered, ask the user to enter it
                if (phonenumber.isEmpty() || password.isEmpty()){
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter all the details";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    databaseReference.child("caretaker").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Check if the phone number is already registered
                            if (snapshot.hasChild(phonenumber)) {
                                // Get the password of the caretaker
                                String userpassword = snapshot.child(phonenumber).child("password").getValue().toString();
                                //Check if the entered password is correct
                                if (password.equals(userpassword)){
                                    Context context = getApplicationContext();
                                    CharSequence text = "Success";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();

                                    Intent intent = new Intent(CaretakerLoginPage.this, MedicineActivity.class);
                                    //Caretaker caretaker = new Caretaker(phonenumber);
                                    //intent.putExtra("caretaker",caretaker);
                                    startActivity(intent);


                                    //TODO Go to the caretaker's main page
                                    //startActivity(new Intent(CaretakerLoginPage.this, CaretakerAppliedEventsPage.class));

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

        final TextView CaretakerRegisterNow = findViewById(R.id.CaretakerRegister);
        CaretakerRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to Caretaker register page
                startActivity(new Intent(CaretakerLoginPage.this, CaretakerRegistrationPage.class));
            }
        });
    }
}