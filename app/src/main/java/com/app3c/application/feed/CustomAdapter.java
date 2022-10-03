package com.app3c.application.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.app3c.application.R;
import java.util.ArrayList;
public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Post> Posts;

    public CustomAdapter(Context c, ArrayList<Post> Posts) {
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
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.model,parent,false);
        }

        TextView headTxt= (TextView) convertView.findViewById(R.id.headTxt);
        TextView subheadTxt= (TextView) convertView.findViewById(R.id.subheadTxt);
        TextView detailTxt= (TextView) convertView.findViewById(R.id.detailTxt);

        final Post s= (Post) this.getItem(position);

        headTxt.setText(s.getHeading());
        subheadTxt.setText(s.getSubheading());
        detailTxt.setText(s.getDetail());

        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,s.getHeading(),Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}