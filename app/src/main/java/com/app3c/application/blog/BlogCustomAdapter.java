package com.app3c.application.blog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app3c.application.R;
import com.app3c.application.switchActivity;

import java.util.ArrayList;

public class BlogCustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<BlogPost> Posts;

    public BlogCustomAdapter(Context c, ArrayList<BlogPost> Posts) {
        this.c = c;
        this.Posts = Posts;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        }

        TextView headTxt = (TextView) convertView.findViewById(R.id.headTxt);
        TextView subheadTxt= (TextView) convertView.findViewById(R.id.subheadTxt);
        TextView detailTxt = (TextView) convertView.findViewById(R.id.detailTxt);

        final BlogPost p = (BlogPost) this.getItem(position);

        headTxt.setText(p.getTitle());
        subheadTxt.setVisibility(View.GONE);
        detailTxt.setText(p.getContent());
        return convertView;
    }
/*
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,p.getTitle(),Toast.LENGTH_SHORT).show();
                ViewEventDetails(p);
                //startActivity(new Intent(this, Event_Details_Page.class));
            }
        });
        return convertView;
    }

    private void ViewEventDetails(BlogPost p) {
        Intent intent = new Intent(c, Event_Details_Page.class);
        intent.putExtra("selected_event",p);
        intent.putExtra("user",user);
        c.startActivity(intent);
    }*/
}