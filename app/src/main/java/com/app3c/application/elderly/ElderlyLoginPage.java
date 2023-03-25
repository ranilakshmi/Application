package com.app3c.application.elderly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app3c.application.R;
import com.app3c.application.switchActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ElderlyLoginPage extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_login);

        final Button LoginBtn = findViewById(R.id.elderlyLoginButton);
        LoginBtn.setOnClickListener(view -> {
            final EditText phoneNumber =findViewById(R.id.phoneNumber);
            final EditText Password = findViewById(R.id.password);
            final String phonenumber = phoneNumber.getText().toString();
            final String password = Password.getText().toString();

            if (phonenumber.isEmpty() || password.isEmpty()){
                Context context = getApplicationContext();
                CharSequence text = "Please enter all the details";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            else{
                databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(phonenumber)) {
                            String userpassword = Objects.requireNonNull(snapshot.child(phonenumber).child("password").getValue()).toString();
                            if (password.equals(userpassword)){
                                Context context = getApplicationContext();
                                CharSequence text = "Success";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                Intent intent = new Intent(ElderlyLoginPage.this, switchActivity.class);
                                Elderly u = new Elderly(phonenumber);
                                intent.putExtra("user",u);
                                startActivity(intent);
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

        final TextView ElderlyRegisterNow = findViewById(R.id.ElderlyRegister);
        ElderlyRegisterNow.setOnClickListener(view -> startActivity(new Intent(ElderlyLoginPage.this, ElderlyRegistrationPage.class)));
    }
}