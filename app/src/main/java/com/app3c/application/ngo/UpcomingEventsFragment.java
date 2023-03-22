package com.app3c.application.ngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.app3c.application.R;
import com.app3c.application.elderly.Elderly;
import com.app3c.application.feed.CustomAdapter;
import com.app3c.application.feed.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpcomingEventsFragment extends Fragment {

    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;


    public UpcomingEventsFragment(){
        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");
        helper = new FirebaseHelper(db);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upcoming_events_fragment, container, false);
    }
}
