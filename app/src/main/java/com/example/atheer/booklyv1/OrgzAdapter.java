package com.example.atheer.booklyv1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class OrgzAdapter extends BaseAdapter {

    private Context mContext;
    private List<Orgz> org;


    public OrgzAdapter(Context mContext, List<Orgz> org) {
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
        View v = View.inflate(mContext,R.layout.org_item,null);
        TextView serviceInfo = (TextView) v.findViewById(R.id.servicename);
        serviceInfo.setText(org.get(position).getName());
        return v;

    }
}
