package com.app3c.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app3c.application.blog.Blog;
import com.app3c.application.blog.CreatePost;
import com.app3c.application.elderly.Elderly;
import com.app3c.application.feed.Event;

import com.app3c.application.medicine.MedicineActivity;

public class switchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        final Button medButton = findViewById(R.id.button1);
        final Button feedButton = findViewById(R.id.button2);
        final Button blogButton = findViewById(R.id.button3);
        final Button chatButton = findViewById(R.id.chatbot);


        medButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(switchActivity.this, MedicineActivity.class));
            }
        });

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                Elderly user = (Elderly) i.getSerializableExtra("user");
                Intent intent = new Intent(switchActivity.this, Event.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        blogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(switchActivity.this,Blog.class));
                //startActivity(new Intent(switchActivity.this, CreatePost.class));
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(switchActivity.this,ChatBot.class));
                //startActivity(new Intent(switchActivity.this, CreatePost.class));
            }
        });
    }
}
