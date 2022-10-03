package com.app3c.application.feed;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app3c.application.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Event extends AppCompatActivity {

    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.lv);

        //INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://application-36c82-default-rtdb.firebaseio.com/");
        helper = new FirebaseHelper(db);

        //ADAPTER
        adapter = new CustomAdapter(this, helper.retrieve());
        lv.setAdapter(adapter);

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
    //DISPLAY INPUT DIALOG
    /*private void displayInputDialog() {
      Dialog d = new Dialog(this);
      d.setTitle("Save To Firebase");
        d.setContentView(R.layout.input_dialog);

        EditText headEditTxt = (EditText) d.findViewById(R.id.headEditText);
        EditText subheadTxt = (EditText) d.findViewById(R.id.subheadEditText);
        EditText detailTxt = (EditText) d.findViewById(R.id.detailEditText);
        Button saveBtn = (Button) d.findViewById(R.id.saveBtn);

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                String head = headEditTxt.getText().toString();
                String subhead = subheadTxt.getText().toString();
                String detail = detailTxt.getText().toString();

                //SET DATA
                Post p = new Post();
                p.setHeading(head);
                p.setSubheading(subhead);
                p.setDetail(detail);


                //SIMPLE VALIDATION
                if (head != null && head.length() > 0) {
                    //THEN SAVE
                    if (helper.save(p,"post")) {
                        //IF SAVED CLEAR EDITXT
                        headEditTxt.setText("");
                        subheadTxt.setText("");
                        detailTxt.setText("");


                        adapter = new CustomAdapter(Event.this, helper.retrieve());
                        lv.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(Event.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        d.show();
    }*/
}