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
import com.growonline.gomobishop.control.CircleImageView;
import com.growonline.gomobishop.model.CategorySimpleModel;

import java.util.List;
import java.util.Random;

/**
 * Created by asifrizvi on 1/29/2019.
 */

public class SubCategoriesListingAdapter extends RecyclerView.Adapter<SubCategoriesListingAdapter.MyViewHolder> {

    private Context mContext;
    private List<CategorySimpleModel> categoryList;
    private Random mRandom = new Random(System.currentTimeMillis());
    private int type = 1;

    public SubCategoriesListingAdapter(Context mContext, List<CategorySimpleModel> categories, int type) {
        this.mContext = mContext;
        this.categoryList = categories;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subcategory_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CategorySimpleModel categorySimpleModel = categoryList.get(position);
        holder.title.setText(categorySimpleModel.getName());
        // loading album cover using Glide library
        GoMobileApp.getmCacheManager().loadImageAndFit(Uri.parse(categorySimpleModel.getImage()), holder.thumbnail, null);
        if (categorySimpleModel.getVendorId() < 0) {
            holder.title.setVisibility(View.GONE);
            holder.thumbnail.setColorFilter(generateRandomColor());
            holder.thumbnail.setAlpha(0.1f);

        }
        holder.title.setBackgroundColor(generateRandomColor());
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
        public CircleImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (CircleImageView) view.findViewById(R.id.thumbnail);
        }
    }
}

