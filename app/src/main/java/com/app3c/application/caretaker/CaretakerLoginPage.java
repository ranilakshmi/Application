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

<<<<<<< HEAD:app/src/main/java/com/app3c/application/caretaker/CaretakerLoginPage.java
import com.app3c.application.R;
import com.app3c.application.elderly.Elderly;
import com.app3c.application.elderly.ElderlyLoginPage;
=======
>>>>>>> 02f3ef1c87543f9ccc327b9d2bf6f6f47ef78362:app/src/main/java/com/app3c/application/CaretakerLoginPage.java
import com.app3c.application.feed.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.app3c.application.medicine.MedicineActivity;

import java.util.Objects;

public class CaretakerLoginPage extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_login);

        //Context context = getApplicationContext();

        // Logic for logging in
        final Button LoginBtn = findViewById(R.id.caretakerLoginButton);
        LoginBtn.setOnClickListener(view -> {
            final EditText PhoneNumber =findViewById(R.id.caretakerphoneNumber);
            final EditText Password = findViewById(R.id.caretakerpassword);
            final String phonenumber = PhoneNumber.getText().toString();
            final String password = Password.getText().toString();

<<<<<<< HEAD:app/src/main/java/com/app3c/application/caretaker/CaretakerLoginPage.java
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

                                    Intent intent = new Intent(CaretakerLoginPage.this, CaretakerAppliedEventsPage.class);
                                    Caretaker caretaker = new Caretaker(phonenumber);
                                    intent.putExtra("caretaker",caretaker);
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
=======
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
                        if (snapshot.hasChild(phonenumber)) {
                            String userpassword = Objects.requireNonNull(snapshot.child(phonenumber).child("caretaker_password").getValue()).toString();
                            if (password.equals(userpassword)){
                                Context context = getApplicationContext();
                                CharSequence text = "Success";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                startActivity(new Intent(CaretakerLoginPage.this, MedicineActivity.class));
>>>>>>> 02f3ef1c87543f9ccc327b9d2bf6f6f47ef78362:app/src/main/java/com/app3c/application/CaretakerLoginPage.java
                            }
                            else{
                                Context context = getApplicationContext();
                                CharSequence text = "Incorrect password";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
<<<<<<< HEAD:app/src/main/java/com/app3c/application/caretaker/CaretakerLoginPage.java
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
=======
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
>>>>>>> 02f3ef1c87543f9ccc327b9d2bf6f6f47ef78362:app/src/main/java/com/app3c/application/CaretakerLoginPage.java

                    }
                });
            }
        });

        final TextView CaretakerRegisterNow = findViewById(R.id.CaretakerRegister);
<<<<<<< HEAD:app/src/main/java/com/app3c/application/caretaker/CaretakerLoginPage.java
        CaretakerRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to Caretaker register page
                startActivity(new Intent(CaretakerLoginPage.this, CaretakerRegistrationPage.class));
            }
        });
=======
        CaretakerRegisterNow.setOnClickListener(view -> startActivity(new Intent(CaretakerLoginPage.this,CaretakerRegistrationPage.class)));
>>>>>>> 02f3ef1c87543f9ccc327b9d2bf6f6f47ef78362:app/src/main/java/com/app3c/application/CaretakerLoginPage.java
    }
}