package com.growonline.gomobishop.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.model.Banner;

import java.util.List;

/**
 * Created by asifrizvi on 12/7/2018.
 */

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {

    private List<Banner> bannerList;

    public BannerAdapter(List<Banner> banners) {
        this.bannerList = banners;
    }

    @Override
    public BannerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner_list_row, parent, false);
//        ViewGroup.LayoutParams params = itemView.getLayoutParams();
//        params.width = (int)(GoMobileApp.getScreenWidth() * 0.8);
//        itemView.setLayoutParams(params);
        return new BannerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BannerAdapter.MyViewHolder holder, int position) {
        Banner banner = bannerList.get(position);
        GoMobileApp.getmCacheManager().loadImageWithDynamicRatio(Uri.parse(banner.getImage()), holder.aspectRatioImageView, null);
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AspectRatioImageView aspectRatioImageView;

        public MyViewHolder(View view) {
            super(view);
            aspectRatioImageView = (AspectRatioImageView) view.findViewById(R.id.banner_aspectRatioImageView);
        }
    }
}
