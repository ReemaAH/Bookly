package com.example.atheer.booklyv1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class orguserAdapter extends BaseAdapter {

    private Context mContext;
    private List<orguser> org;


    public orguserAdapter(Context mContext, List<orguser> org) {
        this.mContext = mContext;
        this.org = org;
    }


    @Override
    public int getCount() {
        return org.size();
    }

    @Override
    public Object getItem(int position) {
        return org.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.org_layout,null);
        TextView orginfo = (TextView) v.findViewById(R.id.serviceInfo);
        ImageView image = (ImageView) v.findViewById(R.id.userimage);
        orginfo.setText(org.get(position).getName());
        Glide.with(mContext).load(org.get(position).getImage()).into(image);

        return v;

    }
}