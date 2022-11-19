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
import androidx.appcompat.app.AppCompatActivity;

import com.app3c.application.feed.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.app3c.application.medicine.MedicineActivity;

import java.util.Objects;

public class CaretakerLoginPage extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://application-36c82-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_login);

        final Button LoginBtn = findViewById(R.id.caretakerLoginButton);
        LoginBtn.setOnClickListener(view -> {
            final EditText PhoneNumber =findViewById(R.id.caretakerphoneNumber);
            final EditText Password = findViewById(R.id.caretakerpassword);
            final String phonenumber = PhoneNumber.getText().toString();
            final String password = Password.getText().toString();

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
        });

        final TextView CaretakerRegisterNow = findViewById(R.id.CaretakerRegister);
        CaretakerRegisterNow.setOnClickListener(view -> startActivity(new Intent(CaretakerLoginPage.this,CaretakerRegistrationPage.class)));
    }
}