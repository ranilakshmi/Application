package com.app3c.application.feed;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.app3c.application.R;
import com.app3c.application.caretaker.Caretaker;
import com.app3c.application.elderly.Elderly;
import com.app3c.application.elderly.ElderlyLoginPage;
import com.app3c.application.elderly.ElderlyRegistrationPage;
import com.app3c.application.ngo.NGO;
import com.app3c.application.ngo.NGOFeed;

import java.lang.reflect.Array;
import java.util.ArrayList;
public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Event_Post> Posts;
    Elderly user;
    Caretaker caretaker;
    NGO ngo;

    public CustomAdapter(Context c, ArrayList<Event_Post> Posts) {
        this.c = c;
        this.Posts = Posts;
    }
    public CustomAdapter(Context c, ArrayList<Event_Post> Posts, Elderly user) {
        this.c = c;
        this.Posts = Posts;
        this.user = user;
    }
    public CustomAdapter(Context c, ArrayList<Event_Post> Posts, Caretaker caretaker){
        this.c = c;
        this.Posts = Posts;
        this.caretaker = caretaker;
    }

    public CustomAdapter(Context c, ArrayList<Event_Post> posts, NGO ngo) {
        this.c = c;
        this.Posts = posts;
        this.ngo = ngo;
    }

    @Override
    public int getCount() {
        return Posts.size();
    }

    @Override
    public Object getItem(int position) {
        return Posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.model,parent,false);
        }

        TextView headTxt= (TextView) convertView.findViewById(R.id.headTxt);
        TextView subheadTxt= (TextView) convertView.findViewById(R.id.subheadTxt);
        TextView detailTxt= (TextView) convertView.findViewById(R.id.detailTxt);

        final Event_Post p= (Event_Post) this.getItem(position);

        headTxt.setText(p.getHeading());
        subheadTxt.setText(p.getSubheading());
        detailTxt.setText(p.getDetail());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,p.getHeading(),Toast.LENGTH_SHORT).show();
                ViewEventDetails(p);
                //startActivity(new Intent(this, Event_Details_Page.class));
            }
        });
        return convertView;
    }

    private void ViewEventDetails(Event_Post p) {
        Intent intent = new Intent(c,Event_Details_Page.class);
        intent.putExtra("selected_event",p);
        intent.putExtra("user",user);
        c.startActivity(intent);
    }
}