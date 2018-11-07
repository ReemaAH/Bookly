package com.example.atheer.booklyv1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResAdapter extends BaseAdapter {
    public ResAdapter(Context mContext, List<Res> res) {
        this.mContext = mContext;
        this.res = res;
    }

    private Context mContext;
    private List<Res> res;


    @Override
    public int getCount() {
        return res.size();
    }

    @Override
    public Object getItem(int position) {
        return res.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext,R.layout.resv_item,null);
        TextView serviceInfo = (TextView) v.findViewById(R.id.servicename);
        serviceInfo.setText(res.get(position).getService() + " | " + res.get(position).getOrg());
        TextView date = (TextView) v.findViewById(R.id.date);
        date.setText(res.get(position).getDate());
        TextView time = (TextView) v.findViewById(R.id.time);
        time.setText(res.get(position).getTime());
     CircleImageView image = (CircleImageView) v.findViewById(R.id.userimage);
//      if(res.get(position).isApproved()){
//          image.setImageResource(R.drawable.approve);
//    }



        return v;
    }
}
