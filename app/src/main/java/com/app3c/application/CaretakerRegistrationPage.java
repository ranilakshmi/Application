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

public class CaretakerRegistrationPage extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://application-36c82-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_register);

        final EditText CaretakerName = findViewById(R.id.caretakerfullname);
        final EditText ElderlyName = findViewById(R.id.elderlyfullname);
        final EditText CaretakerEmail = findViewById(R.id.caretakeremail);
        final EditText CaretakerPhone = findViewById(R.id.caretakerphone);
        final EditText ElderlyPhone = findViewById(R.id.elderlyphone);
        final EditText CaretakerPassword = findViewById(R.id.caretakerpassword);
        final EditText CaretakerConfirmPassword = findViewById(R.id.caretakerconfirmpassword);

        final Button CaretakerRegisterButton = findViewById(R.id.caretakerRegisterBtn);
        CaretakerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String caretakerfullname = CaretakerName.getText().toString();
                final String elderlyname = ElderlyName.getText().toString();
                final String caretakeremail = CaretakerEmail.getText().toString();
                final String caretakerphone = CaretakerPhone.getText().toString();
                final String elderlyphone = ElderlyPhone.getText().toString();
                final String caretakerpassword = CaretakerPassword.getText().toString();
                final String caretakerconfirmpassword = CaretakerConfirmPassword.getText().toString();

                if (caretakerfullname.isEmpty() || elderlyname.isEmpty()||caretakeremail.isEmpty()||caretakerphone.isEmpty()||elderlyphone.isEmpty()||caretakerpassword.isEmpty()||caretakerconfirmpassword.isEmpty()){
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter all the details";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if(!caretakerpassword.equals(caretakerconfirmpassword)){
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
                            if (snapshot.hasChild(caretakerphone)){
                                Context context = getApplicationContext();
                                CharSequence text = "Phone is already registered";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                            else{
                                Context context = getApplicationContext();
                                CharSequence text = "Caretaker Registered";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                finish();

                                databaseReference.child("caretaker").child(caretakerphone).child("caretaker_name").setValue(caretakerfullname);
                                databaseReference.child("caretaker").child(caretakerphone).child("elderly_user_name").setValue(elderlyname);
                                databaseReference.child("caretaker").child(caretakerphone).child("caretaker_email").setValue(caretakeremail);
                                databaseReference.child("caretaker").child(caretakerphone).child("elderly_phone").setValue(elderlyphone);
                                databaseReference.child("caretaker").child(caretakerphone).child("caretaker_password").setValue(caretakerpassword);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        final TextView CaretakerLoginNow = findViewById(R.id.caretakerLoginNow);
        CaretakerLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CaretakerRegistrationPage.this,CaretakerLoginPage.class));
            }
        });
    }
}
