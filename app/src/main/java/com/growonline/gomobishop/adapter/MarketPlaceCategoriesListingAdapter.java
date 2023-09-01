package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.model.CategorySimpleModel;
import com.growonline.gomobishop.util.AppHelper;

import java.util.List;
import java.util.Random;

/**
 * Created by asifrizvi on 1/29/2019.
 */

public class MarketPlaceCategoriesListingAdapter extends RecyclerView.Adapter<MarketPlaceCategoriesListingAdapter.MyViewHolder> {

    private Context mContext;
    private List<CategorySimpleModel> categoryList;
    private Random mRandom = new Random(System.currentTimeMillis());
    private int type = 1;

    public MarketPlaceCategoriesListingAdapter(Context mContext, List<CategorySimpleModel> categories, int type) {
        this.mContext = mContext;
        this.categoryList = categories;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);

        if (type == 2) {
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.width = AppHelper.dpToPx(100, parent.getContext());
            itemView.setLayoutParams(params);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CategorySimpleModel categorySimpleModel = categoryList.get(position);
        holder.title.setText(categorySimpleModel.getName());
        // loading album cover using Glide library
        GoMobileApp.getmCacheManager().loadImageAndFit(Uri.parse(categorySimpleModel.getImage()), holder.thumbnail, null);
        if (categorySimpleModel.getVendorId()<0){
            holder.title.setVisibility(View.GONE);
            holder.thumbnail.setColorFilter(generateRandomColor());
            holder.thumbnail.setAlpha(0.1f);
        }

        if (mContext instanceof StoreActivity)
            holder.thumbnail.setHeightRatio(1.5f);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public int generateRandomColor() {
        // This is the base color which will be mixed with the generated one
        final int baseColor = Color.WHITE;

        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);

        final int red = (baseRed + mRandom.nextInt(256)) / 2;
        final int green = (baseGreen + mRandom.nextInt(256)) / 2;
        final int blue = (baseBlue + mRandom.nextInt(256)) / 2;

        return Color.rgb(red, green, blue);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public AspectRatioImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (AspectRatioImageView) view.findViewById(R.id.thumbnail);
        }
    }
}

