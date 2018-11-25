package com.example.atheer.booklyv1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class serviceAdapter  extends RecyclerView.Adapter<serviceAdapter.ImageViewHolder> {

    private Context mContext;
    private List<services> mUpload;
    private serviceAdapter.OnItemClickListener mListener;

    public serviceAdapter(Context mContext,List<services> mUpload){
        this.mContext=mContext;
        this.mUpload=mUpload;
    }

    @NonNull
    @Override
    public serviceAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.requesttext2,viewGroup,false);
        return new serviceAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull serviceAdapter.ImageViewHolder imageViewHolder, int i) {
        services uploadCurrent=mUpload.get(i);
        imageViewHolder.textViewName.setText(uploadCurrent.getName());

    }

    @Override
    public int getItemCount() {
        return mUpload.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.requestInfo);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {

            if(mListener!=null){
                int position=getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //   menu.setHeaderTitle("Select Action");
            //  MenuItem doWhatever = menu.add(Menu.NONE, 1,1,"Do whatever");
            MenuItem delete= menu.add(Menu.NONE, 1,1,"Delete");

            //   doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mListener!=null){
                int position=getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnClickListener(serviceAdapter.OnItemClickListener listenet){
        mListener=listenet;
    }
}
