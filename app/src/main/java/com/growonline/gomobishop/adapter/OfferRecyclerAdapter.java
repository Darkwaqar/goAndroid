package com.growonline.gomobishop.adapter;

/**
 * Created by asifrizvi on 9/27/2018.
 */

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.model.BannerPicture;

import java.util.List;

public class OfferRecyclerAdapter extends RecyclerView.Adapter<OfferRecyclerAdapter.MyViewHolder> {

    private List<BannerPicture> offerList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AspectRatioImageView aspectRatioImageView;
        private TextView offerName;
        private TextView offerType;
        private TextView offerButtonText;
        private LinearLayout mainLayout;


        public MyViewHolder(View view) {
            super(view);
            aspectRatioImageView = (AspectRatioImageView) view.findViewById(R.id.aspectRatioImageView);
            offerName = (TextView) view.findViewById(R.id.offer_name);
            offerType = (TextView) view.findViewById(R.id.offer_type);
            offerButtonText = (TextView) view.findViewById(R.id.offer_button_text);
            mainLayout = (LinearLayout) view.findViewById(R.id.offer_list_main);
        }
    }


    public OfferRecyclerAdapter(List<BannerPicture> offerList) {
        this.offerList = offerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_list_row, parent, false);
        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        params.width = (int) (GoMobileApp.getScreenWidth() * 0.8);
        itemView.setLayoutParams(params);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BannerPicture offer = offerList.get(position);
        if (offer.getWidth() == 0) return;
        float ratio = (float) offer.getHeight() / (float) offer.getWidth();
        // Set the ratio for the image
        holder.aspectRatioImageView.setHeightRatio(ratio);
        GoMobileApp.getmCacheManager().loadImage(Uri.parse(offer.getImageUrl()), holder.aspectRatioImageView, null);

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }
}


