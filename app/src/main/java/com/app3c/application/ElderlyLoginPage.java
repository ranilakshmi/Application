package com.app3c.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ElderlyLoginPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_login);

        final TextView ElderlyRegisterNow = findViewById(R.id.ElderlyRegister);
        ElderlyRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ElderlyLoginPage.this,ElderlyRegistrationPage.class));
            }
        });
    }
}