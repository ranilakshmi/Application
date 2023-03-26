package com.app3c.application.ngo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.app3c.application.R;
import com.app3c.application.elderly.Elderly;
import com.app3c.application.feed.CustomAdapter;
import com.app3c.application.feed.Event_Post;
import com.app3c.application.feed.FirebaseHelper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class NGOFeed extends AppCompatActivity {
    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_feed);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        lv = findViewById(R.id.lv2);
        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");
        helper = new FirebaseHelper(db);
        //Display details of current user
        Intent i = getIntent();
        NGO ngo = (NGO) i.getSerializableExtra("ngo");
        String ngoContact = ngo.getContact();
        Log.i("ngocontact",ngoContact);
        ArrayList<Event_Post> all_events =  helper.retrieve();
        //ArrayList<Event_Post> completed_events =  helper.retrieve_completed_events();
        //ArrayList<Event_Post> upcoming_events = helper.retrieve_upcoming_events();
        //ArrayList<String> previous_event_ids =  helper.retrieve_registered_events(userContact);
        //ArrayList<Event_Post> previous_events = helper.retrieve_previous_events(userContact);
        //ArrayList<Event_Post> recommended_events =  helper.retrieve();
        // TODO: recommend function should be added here
        //ArrayList <Event_Post> recommended_events = new ArrayList<>();
        //helper.upcoming_events = new ArrayList<>();
        //helper.past_events = new ArrayList<>();
        //helper.isRegistered = new ArrayList<>();
        //helper.past = new HashMap<>();
        //helper.upcoming = new HashMap<>();
        //helper.retrieve_registered_events(userContact);
        //Boolean b = helper.retrieveByDate();
//        for (Integer num: helper.isRegistered){
//            Log.i("is_registered",Integer.toString(num));
//        }
//        Log.i("upcoming_events_size",Integer.toString(helper.upcoming.size()));
//        Log.i("past_events_size",Integer.toString(helper.past.size()));
        //ADAPTER
        //adapter = new CustomAdapter(this,completed_events,user);
        //adapter = new CustomAdapter(this,upcoming_events,user);
        Elderly user = new Elderly("7306562416");
        adapter = new CustomAdapter(this,all_events ,user);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Event_Post p = (Event_Post) adapter.getItem(position);
                String value = p.getHeading();
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
//
//    //ViewPager2 viewPager;
//    //CustomAdapter adapter1,adapter2;
//    //ListView lv1,lv2;
//    DatabaseReference db;
//    FirebaseHelper helper;
//    CustomAdapter adapter;
//    ListView lv;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ngo_feed);
//
//        //TODO- Implement Tabbed view using viewpager2
//
//        // Initializing the viewpager2 object
//        // It will find the view by its id which
//        // you have provided into XML file
//        //viewPager = findViewById(R.id.viewpager);
//
//        // Object of ViewPager2Adapter
//        // this will passes the
//        // context to the constructor
//        // of ViewPager2Adapter
//        //ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
//
//        // adding the adapter to viewPager2
//        // to show the views in recyclerview
//        //viewPager.setAdapter(viewPager2Adapter);
//
//        // To get swipe event of viewpager2
//        //viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//          //  @Override
//            // This method is triggered when there is any scrolling activity for the current page
//            //public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//              //  super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//            //}
//
//            // triggered when you select a new page
//            //@Override
//            //public void onPageSelected(int position) {
//              //  super.onPageSelected(position);
//            //}
//
//            // triggered when there is
//            // scroll state will be changed
//            //@Override
//            //public void onPageScrollStateChanged(int state) {
//              //  super.onPageScrollStateChanged(state);
//            //}
//        //});
//        //end
//
//
//        //ArrayList<Event_Post> completed_events = helper.retrieve_completed_events();
//        //ArrayList<Event_Post> upcoming_events  = helper.retrieve_upcoming_events();
//
//        //ADAPTER
//        //adapter1 = new CustomAdapter(this,upcoming_events);
//        //adapter2 = new CustomAdapter(this,completed_events);
//
//        //lv1.setAdapter(adapter1);
//
//        //lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//          //  @Override
//            //public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//              //  Event_Post p = (Event_Post) adapter1.getItem(position);
//                //String value = p.getHeading();
//                //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
//            //}
//        //});
//
//        //lv2.setAdapter(adapter2);
//
//        //lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//          //  @Override
//            //public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//              //  Event_Post p = (Event_Post) adapter1.getItem(position);
//        Toolbar toolbar = findViewById(R.id.toolbar2);
//        setSupportActionBar(toolbar);
//
//        Button neweventbtn = findViewById(R.id.neweventbtn);
//        neweventbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(NGOFeed.this, CreateEvent.class));
//            }
//        });
//
//        lv = findViewById(R.id.lv2);
//
//        //INITIALIZE FIREBASE DB
//
//        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");
//        helper = new FirebaseHelper(db);
//
//        //Display details of current user
//        Intent i = getIntent();
//        NGO ngo = (NGO) i.getSerializableExtra("ngo");
//        String username = ngo.getContact();
//
//        helper.retrieve_event_id_ngo(username);
//        ArrayList<Event_Post> events  = helper.retrieve_events_ngo(username);
//
//        //ADAPTER
//        adapter = new CustomAdapter(this, events,ngo);
//        lv.setAdapter(adapter);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Event_Post p = (Event_Post) adapter.getItem(position);
//                String value = p.getHeading();
//                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //RETRIEVE
//        db.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
//                lv.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
//                lv.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                lv.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
//                lv.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            /*
//                lv1.setAdapter(adapter1);
//                adapter1.notifyDataSetChanged();
//            }
//        });
//    }
//}
//class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {
//
//    // Array of images
//    // Adding images from drawable folder
////    private int[] images = {R.drawable.ic_baseline_looks_one_24, R.drawable.ic_baseline_looks_two_24, R.drawable.ic_baseline_looks_3_24,
////            R.drawable.ic_baseline_looks_4_24, R.drawable.ic_baseline_looks_5_24};
//     private Context ctx;
//
//    // Constructor of our ViewPager2Adapter class
//    ViewPager2Adapter(Context ctx) {
//        this.ctx = ctx;
//    }
//
//    // This method returns our layout
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(ctx).inflate(R.layout.images_holder, parent, false);
//        //TODO
//        View view = new View();
//        return new ViewHolder(view);
//    }
//
//    // This method binds the screen with the view
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        // This will set the images in imageview
//        holder.images.setImageResource(images[position]);
//    }
//
//    // This Method returns the size of the Array
//    @Override
//    public int getItemCount() {
//        return images.length;
//    }
//
//    // The ViewHolder class holds the view
//    public static class ViewHolder extends RecyclerView.ViewHolder {
////        ImageView images;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
////            images = itemView.findViewById(R.id.images);
//        }
//    }*/
//                lv.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }
