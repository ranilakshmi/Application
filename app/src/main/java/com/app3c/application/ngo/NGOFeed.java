package com.app3c.application.ngo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.app3c.application.R;
import com.app3c.application.feed.CustomAdapter;
import com.app3c.application.feed.Event_Post;
import com.app3c.application.feed.FirebaseHelper;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class NGOFeed extends AppCompatActivity {

    ViewPager2 viewPager;

    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter1,adapter2;
    ListView lv1,lv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_feed);

        //TODO- Implement Tabbed view using viewpager2

        // Initializing the viewpager2 object
        // It will find the view by its id which
        // you have provided into XML file
        viewPager = findViewById(R.id.viewpager);

        // Object of ViewPager2Adapter
        // this will passes the
        // context to the constructor
        // of ViewPager2Adapter
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);

        // adding the adapter to viewPager2
        // to show the views in recyclerview
        viewPager.setAdapter(viewPager2Adapter);

        // To get swipe event of viewpager2
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            // This method is triggered when there is any scrolling activity for the current page
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            // triggered when you select a new page
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            // triggered when there is
            // scroll state will be changed
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        //end


        ArrayList<Event_Post> completed_events = helper.retrieve_completed_events();
        ArrayList<Event_Post> upcoming_events  = helper.retrieve_upcoming_events();

        //ADAPTER
        adapter1 = new CustomAdapter(this,upcoming_events);
        adapter2 = new CustomAdapter(this,completed_events);

        lv1.setAdapter(adapter1);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Event_Post p = (Event_Post) adapter1.getItem(position);
                String value = p.getHeading();
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            }
        });

        lv2.setAdapter(adapter2);

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Event_Post p = (Event_Post) adapter1.getItem(position);
                String value = p.getHeading();
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            }
        });

        //RETRIEVE
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                lv1.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                lv1.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                lv1.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                lv1.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lv1.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            }
        });
    }
}
class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    // Array of images
    // Adding images from drawable folder
//    private int[] images = {R.drawable.ic_baseline_looks_one_24, R.drawable.ic_baseline_looks_two_24, R.drawable.ic_baseline_looks_3_24,
//            R.drawable.ic_baseline_looks_4_24, R.drawable.ic_baseline_looks_5_24};
     private Context ctx;

    // Constructor of our ViewPager2Adapter class
    ViewPager2Adapter(Context ctx) {
        this.ctx = ctx;
    }

    // This method returns our layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(ctx).inflate(R.layout.images_holder, parent, false);
        //TODO
        View view = new View();
        return new ViewHolder(view);
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
        holder.images.setImageResource(images[position]);
    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return images.length;
    }

    // The ViewHolder class holds the view
    public static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView images;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            images = itemView.findViewById(R.id.images);
        }
    }
}