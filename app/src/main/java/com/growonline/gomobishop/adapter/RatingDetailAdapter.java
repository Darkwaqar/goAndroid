package com.growonline.gomobishop.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.AddReviewsActivity;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.PendingReview;
import com.growonline.gomobishop.model.ProductReview;
import com.growonline.gomobishop.model.Review;
import com.growonline.gomobishop.model.VendorReview;
import com.growonline.gomobishop.util.AppConstant;

import java.util.List;

/**
 * Created by asifrizvi on 2/20/2019.
 */

public class RatingDetailAdapter extends RecyclerView.Adapter<RatingDetailAdapter.MyViewHolder> {
    private List<Review> reviews;
    private LayoutInflater mLayoutInflator;
    private int mType;
    private Activity activity;


    private List<ProductReview> productReviews;
    private List<VendorReview> vendorReviews;
    private List<PendingReview> pendingReviews;


    public RatingDetailAdapter(Activity activity, List<Review> reviews, int type) {
        this.mLayoutInflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.reviews = reviews;
        this.mType = type;
    }

    public RatingDetailAdapter(FragmentActivity activity, List<ProductReview> productReviews, List<VendorReview> vendorReviews, List<PendingReview> pendingReviews, int mType) {
        this.mLayoutInflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        this.productReviews = productReviews;
        this.vendorReviews = vendorReviews;
        this.pendingReviews = pendingReviews;
        this.mType = mType;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RatingBar ratingStars;
        private TextView ratingDate;
        private TextView reviewTitle;
        private TextView reviewVendor;
        private TextView reviewComment;
        private ImageView image;
        private Button addReview;

        public MyViewHolder(View view) {
            super(view);
            ratingStars = (RatingBar) view.findViewById(R.id.rating_stars);
            ratingDate = (TextView) view.findViewById(R.id.rating_date);
            reviewTitle = (TextView) view.findViewById(R.id.review_title);
            reviewComment = (TextView) view.findViewById(R.id.review_comment);
            reviewVendor = (TextView) view.findViewById(R.id.review_Vendor);
            image = (ImageView) view.findViewById(R.id.image);
            addReview = (Button) view.findViewById(R.id.add_review_button);
        }
    }

    @Override
    public RatingDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_detail, parent, false);
        return new RatingDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RatingDetailAdapter.MyViewHolder holder, int position) {
        if (mType == AppConstant.TYPE_PRODUCT) {
            ProductReview productReview = productReviews.get(position);
            holder.ratingStars.setRating(productReview.getRating());
            if (productReview.getCreatedOnUtc() != null)
                holder.ratingDate.setText(GoMobileApp.ConvertUtcToDate(productReview.getCreatedOnUtc()));
            holder.reviewTitle.setText(productReview.getTitle());
            holder.reviewComment.setText(productReview.getReviewText());
            holder.reviewVendor.setVisibility(View.GONE);
            GoMobileApp.getmCacheManager().loadImage(Uri.parse(productReview.getProductImageUrl()), holder.image, null);
        } else if (mType == AppConstant.TYPE_VENDOR) {
            VendorReview vendorReview = vendorReviews.get(position);
            holder.ratingStars.setRating(vendorReview.getRating());
            if (vendorReview.getCreatedOnUTC() != null)
                holder.ratingDate.setText(GoMobileApp.ConvertUtcToDate(vendorReview.getCreatedOnUTC()));
            holder.reviewTitle.setText(vendorReview.getTitle());
            holder.reviewComment.setText(vendorReview.getReviewText());
            holder.reviewVendor.setText(vendorReview.getVendorName());
            GoMobileApp.getmCacheManager().loadImage(Uri.parse(vendorReview.getVendorImageUrl()), holder.image, null);
        } else if (mType == AppConstant.TYPE_PENDING) {
            final PendingReview pendingReview = pendingReviews.get(position);
            holder.ratingStars.setVisibility(View.GONE);
            holder.ratingDate.setVisibility(View.GONE);
            holder.reviewTitle.setText(pendingReview.getProductName());
            holder.reviewComment.setText(pendingReview.getVendorName());
            GoMobileApp.getmCacheManager().loadImage(Uri.parse(pendingReview.getProductImageUrl()), holder.image, null);
            holder.addReview.setVisibility(View.VISIBLE);
            holder.reviewVendor.setText(pendingReview.getVendorName());
            holder.addReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivityForResult(new Intent(activity, AddReviewsActivity.class).putExtra(AppConstant.PENDING_REVIEW_INTENT, pendingReview), AppConstant.CODE_PENDING_REVIEW_ACTIVITY);
                }
            });
        } else {
            Review vendorReview = reviews.get(position);
            holder.ratingStars.setRating(vendorReview.getRating());
            if (vendorReview.getCreatedOnUtc() != null)
                holder.ratingDate.setText(GoMobileApp.ConvertUtcToDate(vendorReview.getCreatedOnUtc()));
            holder.reviewTitle.setText(vendorReview.getTitle());
            holder.reviewComment.setText(vendorReview.getReviewText());
            holder.reviewVendor.setVisibility(View.GONE);
            GoMobileApp.getmCacheManager().loadImage(Uri.parse(vendorReview.getProductImage()), holder.image, null);
        }
    }

    @Override
    public int getItemCount() {
        if (mType == AppConstant.TYPE_PRODUCT) {
            return productReviews.size();
        } else if (mType == AppConstant.TYPE_VENDOR) {
            return vendorReviews.size();
        } else if (mType == AppConstant.TYPE_PENDING)
            return pendingReviews.size();
        else
            return reviews.size();
    }

}