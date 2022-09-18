package com.app3c.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CaretakerLoginPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_login);

        final TextView CaretakerRegisterNow = findViewById(R.id.CaretakerRegister);
        CaretakerRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CaretakerLoginPage.this,CaretakerRegistrationPage.class));
            }
        });
    }
}