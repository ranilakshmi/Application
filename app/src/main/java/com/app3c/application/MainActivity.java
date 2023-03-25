package com.app3c.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.app3c.application.caretaker.CaretakerLoginPage;
import com.app3c.application.elderly.ElderlyLoginPage;
import com.app3c.application.ngo.NGOLoginPage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button ElderlyLoginBtn = findViewById(R.id.ElderlyLogin);
        final Button CaretakerLoginBtn = findViewById(R.id.CaretakerLogin);
        final Button NGOLoginBtn = findViewById(R.id.NGOLogin);

        ElderlyLoginBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ElderlyLoginPage.class)));
        CaretakerLoginBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CaretakerLoginPage.class)));
        NGOLoginBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, NGOLoginPage.class)));
    }
}