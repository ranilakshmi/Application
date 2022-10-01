package com.app3c.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NGOLoginPage extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://application-36c82-default-rtdb.firebaseio.com/");
    //private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_ngo_login);

        final TextView NGORegisterNow = findViewById(R.id.NGORegister);
        final Button loginBtn = findViewById(R.id.NGOLoginButton);
        NGORegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGOLoginPage.this,NGORegistrationPage.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final TextView NGOemail = findViewById(R.id.ngo_email);
                final TextView NGOpassword = findViewById(R.id.NGOpassword);
                String email = NGOemail.getText().toString();
                String password = NGOpassword.getText().toString();


                Context context = getApplicationContext();
                CharSequence text = "Email:" + email + " ,Password: " + password;
                //CharSequence text = "Login button clicked";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


                Query query = databaseReference.orderByChild("ngo").equalTo(email);
                Task<DataSnapshot> dataSnapshot = query.get();
                if (dataSnapshot.isSuccessful()) {
                    Log.d("firebase", String.valueOf(dataSnapshot.getResult().getValue()));
                }
                //signIn(email,password);
                //Query recentPostsQuery = databaseReference.child("ngo").limitToFirst(10);
                //final String TAG = "MyActivity";
                //Log.i(TAG,recentPostsQuery.toString());
            }
        });
    }

    /*private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(NGOLoginPage.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(NGOLoginPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }
    private void updateUI(FirebaseUser user) {
    }*/
}