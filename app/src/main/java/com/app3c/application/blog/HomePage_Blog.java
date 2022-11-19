package com.app3c.application.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app3c.application.R;
import com.app3c.application.caretaker.CaretakerLoginPage;
import com.app3c.application.elderly.ElderlyLoginPage;
import com.app3c.application.ngo.NGOLoginPage;

public class HomePage_Blog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_homepage);

        final Button UploadImageBtn = findViewById(R.id.UploadImagePage);
        final Button DisplayImageBtn = findViewById(R.id.DisplayImagePage);
        final Button CreatePostBtn = findViewById(R.id.CreatePostPage);

        UploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage_Blog.this,UploadImage.class));
            }
        });
        DisplayImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage_Blog.this,DisplayImage.class));
            }
        });
        CreatePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage_Blog.this,CreatePost.class));
            }
        });
    }
}