package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.BaseActivity;
import com.growonline.gomobishop.R;

import java.util.List;

/**
 * Created by asifrizvi on 2/20/2019.
 */

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder> {
    private List<Integer> rating;
    private int totalRating;
    private LayoutInflater mLayoutInflator;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RatingBar ratingStars;
        private ProgressBar ratingBar;
        private TextView ratingTotal;


        public MyViewHolder(View view) {
            super(view);
            ratingStars = (RatingBar) view.findViewById(R.id.rating_stars);
            ratingBar = (ProgressBar) view.findViewById(R.id.rating_bar);
            ratingTotal = (TextView) view.findViewById(R.id.rating_total);
        }
    }

    public RatingAdapter(BaseActivity activity, List<Integer> rating) {
        this.rating = rating;
        this.mLayoutInflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RatingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_rating_item, parent, false);
        return new RatingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RatingAdapter.MyViewHolder holder, int position) {
        Integer order = rating.get(position);
        holder.ratingStars.setRating(5 - position);
        totalRating=sumAll(rating);
        if (totalRating > 0)
            holder.ratingBar.setProgress(order / totalRating * 100);
        else
            holder.ratingBar.setProgress(0);
        holder.ratingTotal.setText(String.valueOf(order));
    }

    @Override
    public int getItemCount() {
        return rating.size();
    }


    public static int sumAll(List<Integer> numbers) {
        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        return total;
    }

}
