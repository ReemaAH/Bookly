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

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ImageViewHolder> {

    private Context mContext;
    private List<uloadOffers> mUpload;
    private OfferAdapter.OnItemClickListener mListener;


    public OfferAdapter(Context mContext, List<uloadOffers> mUpload) {
        this.mContext = mContext;
        this.mUpload = mUpload;
    }

    @NonNull
    @Override
    public OfferAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.offer_item, viewGroup, false);
        return new OfferAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.ImageViewHolder imageViewHolder, int i) {
        uloadOffers uploadCurrent = mUpload.get(i);
        imageViewHolder.textViewName.setText(uploadCurrent.getmName()+"  "+uploadCurrent.getPrecentage()+"% discount");
        //   imageViewHolder.textViewPercentage.setText(uploadCurrent.getPrecentage()+"%");
        //  imageViewHolder.textViewSdate.setText(uploadCurrent.getsDate());
        imageViewHolder.mEditTextFileCoupon.setText(uploadCurrent.getPrecentage()+"% discount");
        //  imageViewHolder.textViewEdate.setText(uploadCurrent.geteDate());
        Picasso.with(mContext)
                .load(uploadCurrent.getmImageUrl())
                .fit()
                .centerCrop()
                .into(imageViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUpload.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName, mEditTextFileCoupon;
        ;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            //    textViewPercentage=itemView.findViewById(R.id.text_view_percentage);
            // textViewSdate=itemView.findViewById(R.id.text_view_sDate);
            // textViewEdate=itemView.findViewById(R.id.text_view_eDate);
            imageView = itemView.findViewById(R.id.image_view_upload);
            mEditTextFileCoupon = itemView.findViewById(R.id.text_view_coupon);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {

            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //   menu.setHeaderTitle("Select Action");
            //  MenuItem doWhatever = menu.add(Menu.NONE, 1,1,"Do whatever");
            //     MenuItem delete= menu.add(Menu.NONE, 1,1,"Delete");

            //   doWhatever.setOnMenuItemClickListener(this);
            //     delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            //    mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
         void onOfferClick(int position);
        // void onWhateverClick(int position);
    }

    public interface OnImageClickListener {
        void onImageClick(String imageData);
    }

    public void setOnClickListener(OfferAdapter.OnItemClickListener listenet) {
        mListener = listenet;
    }


}
