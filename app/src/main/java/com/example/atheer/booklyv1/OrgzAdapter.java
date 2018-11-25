package com.example.atheer.booklyv1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrgzAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<Orgz> org;

    // added + Filterable
    private CustomFilter customFilter;
    public List<Orgz> filterorgs;

    public OrgzAdapter(Context mContext, List<Orgz> org) {
        this.mContext = mContext;
        this.org = org;
        this.filterorgs=org;
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
        ImageView image = (ImageView) v.findViewById(R.id.userimage);
        serviceInfo.setText(org.get(position).getName());
        //Picasso.with(mContext).load(org.get(position).getImage()).into(image);
        Glide.with(mContext).load(org.get(position).getImage()).into(image);

        return v;

    }

    // added

    @Override
    public Filter getFilter(){
        if(customFilter == null){
            customFilter = new CustomFilter();

        }

        return customFilter;
    }


    // added

    class CustomFilter extends Filter {

        public ArrayList<Orgz> fillters;

        public ArrayList<Orgz> getFillters() {
            return fillters;
        }




        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults f = new FilterResults();

            if(constraint != null && constraint.length()>0){
                constraint =constraint.toString().toUpperCase();
                ArrayList<Orgz> fillters = new ArrayList<Orgz>();

                for(int i=0;i<filterorgs.size();i++){
                    if(filterorgs.get(i).getName().toUpperCase().contains(constraint)){
                        Orgz o = new Orgz(
                                filterorgs.get(i).getStatus(),
                                filterorgs.get(i).getCat(),
                                filterorgs.get(i).getEmail(),
                                filterorgs.get(i).getName(),
                                filterorgs.get(i).getPhoneNo(),
                                filterorgs.get(i).getRecordNo(),
                                filterorgs.get(i).getGroupID(),
                                filterorgs.get(i).getImage()
                        );
                        fillters.add(o);
                    }
                }

                f.count = fillters.size();
                f.values = fillters;


            } else {
                f.count = filterorgs.size();
                f.values = filterorgs;
            }


            return f;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            org = (List<Orgz>) results.values;
            notifyDataSetChanged();

        }
    }

}