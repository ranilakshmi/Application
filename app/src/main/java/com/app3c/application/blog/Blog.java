package com.app3c.application.blog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app3c.application.R;
import com.app3c.application.elderly.Elderly;
import com.app3c.application.switchActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Blog extends AppCompatActivity {

    DatabaseReference db;
    BlogFirebaseHelper helper;
    BlogCustomAdapter adapter;
    ListView lv;
    Button newpostbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        newpostbtn = findViewById(R.id.newpostbtn);
        newpostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Blog.this,CreatePost.class));
            }
        });

        lv = (ListView) findViewById(R.id.lv1);

        //INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");
        helper = new BlogFirebaseHelper(db);

        //ADAPTER
        adapter = new BlogCustomAdapter(this, helper.retrieve());
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                BlogPost p = (BlogPost) adapter.getItem(position);
                String value = p.getTitle();
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            }
        });

        //RETRIEVE
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }
}