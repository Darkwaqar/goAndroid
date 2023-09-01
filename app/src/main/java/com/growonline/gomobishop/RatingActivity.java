package com.growonline.gomobishop;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.adapter.RatingAdapter;
import com.growonline.gomobishop.adapter.RatingDetailAdapter;
import com.growonline.gomobishop.asynctask.AddReviewAsyncTask;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetRating;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.control.FontEditText;
import com.growonline.gomobishop.control.RecyclerTouchListener;
import com.growonline.gomobishop.model.Review;
import com.growonline.gomobishop.model.ReviewSummaryModel;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends BaseActivity {
    private AspectRatioImageView ratingImage;
    private AspectRatioImageView vendorRatingImage;
    private TextView ratingName;
    private TextView ratingTotal;
    private RecyclerView reviewRating;
    private RecyclerView ratingDetails;
    private RatingAdapter ratingAdapter;
    private RatingDetailAdapter ratingDetailAdapter;
    private int Type;
    private int Id;
    private int totalRating;
    private List<Integer> ratingCounts = new ArrayList<>();
    private String name;
    private String imageUrl;
    private List<Review> reviews = new ArrayList<>();
    private LinearLayout productImageLayout;
    private LinearLayout loadingLayout;
    private FontEditText reviewTitle;
    private FontEditText reviewText;
    private RatingBar ratingStars;
    private Button addReview;
    private NestedScrollView rating_scroll;
    private AddReviewAsyncTask backgroundTask;
    private LinearLayout addReviewLayout;
    private FrameLayout noReviewFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        Type = getIntent().getIntExtra("Type", 0);
        Id = getIntent().getIntExtra("Id", 0);

        rating_scroll = (NestedScrollView) findViewById(R.id.rating_scroll);
        ratingImage = (AspectRatioImageView) findViewById(R.id.rating_image);
        productImageLayout = (LinearLayout) findViewById(R.id.rating_product_layout);
        loadingLayout = (LinearLayout) findViewById(R.id.review_loading);
        vendorRatingImage = (AspectRatioImageView) findViewById(R.id.rating_vendor_image);
        ratingName = (TextView) findViewById(R.id.rating_name);
        ratingTotal = (TextView) findViewById(R.id.rating_total);
        reviewRating = (RecyclerView) findViewById(R.id.review_rating);
        ratingDetails = (RecyclerView) findViewById(R.id.rating_details);
        reviewRating.setNestedScrollingEnabled(false);
        ratingDetails.setNestedScrollingEnabled(false);
        reviewTitle = (FontEditText) findViewById(R.id.review_title);
        reviewText = (FontEditText) findViewById(R.id.review_text);
        ratingStars = (RatingBar) findViewById(R.id.rating_stars);
        addReview = (Button) findViewById(R.id.add_review);
        addReviewLayout = (LinearLayout) findViewById(R.id.add_review_linearLayout);
        noReviewFound = (FrameLayout) findViewById(R.id.no_review_layout);
        if (Type == AppConstant.VENDOR_REVIEW) {
            setToolBarTitle(getString(R.string.title_store_rating));
            GoMobileApp.getmCacheManager().loadImage(Uri.parse(mVendor.getMarketPlaceLogoURL()), vendorRatingImage, null);
            productImageLayout.setVisibility(View.GONE);
        } else {
            setToolBarTitle(getString(R.string.title_product_rating));
            vendorRatingImage.setVisibility(View.GONE);
        }


        ratingAdapter = new RatingAdapter(RatingActivity.this, ratingCounts);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        reviewRating.setLayoutManager(mLayoutManager);
        reviewRating.setAdapter(ratingAdapter);


        ratingDetailAdapter = new RatingDetailAdapter(RatingActivity.this, reviews, Type);
        RecyclerView.LayoutManager mLayoutManager1 =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        ratingDetails.setItemAnimator(new DefaultItemAnimator());
        ratingDetails.setLayoutManager(mLayoutManager1);
        ratingDetails.setAdapter(ratingDetailAdapter);

        new GetRating(RatingActivity.this, false, Type, Id).execute();
        ratingDetails.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), ratingDetails, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (reviews.size() > 0 && Type == AppConstant.VENDOR_REVIEW) {
                    Review review = reviews.get(position);
                    Intent intent = new Intent(RatingActivity.this, RatingActivity.class).putExtra("Type", AppConstant.PRODUCT_REVIEW).putExtra("Id", review.getId()).putExtra(AppConstant.INTENT_VENDOR, mVendor);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("productId", Id);
                        params.put("Title", reviewTitle.getText());
                        params.put("ReviewText", reviewText.getText());
                        params.put("Rating", ratingStars.getRating());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    backgroundTask = new AddReviewAsyncTask(params, Type);
                    backgroundTask.addOnResultListener(new AsyncTaskResultListener<String>() {
                        @Override
                        public void response(AsyncTaskResult<String> response) {
                            if (!response.hasException()) {
                                AppHelper.showMsgDialog(RatingActivity.this, getString(R.string.app_name), response.getResult(), null, null);
                                new GetRating(RatingActivity.this, false, Type, Id).execute();
                            } else {
                                AppHelper.showMsgDialog(RatingActivity.this, getString(R.string.app_name), response.getException().getMessage(), null, null);
                            }
                        }
                    });
                    backgroundTask.execute();
                }
            }
        });


    }

    public void ScrollToView(final View view) {
        ObjectAnimator.ofInt(rating_scroll, "scrollY", view.getTop()).setDuration(500).start();
        view.requestFocus();
    }


    public boolean isValid() {
        if (GoMobileApp.isNullOrEmpty(reviewTitle)) {
            GoMobileApp.Toast(R.string.required_title);
            ScrollToView(reviewTitle);
            return false;
        }
        if (GoMobileApp.isNullOrEmpty(reviewText)) {
            GoMobileApp.Toast(R.string.required_review);
            ScrollToView(reviewText);
            return false;
        }
        return true;
    }

    public void LoadRating(ReviewSummaryModel reviewSummaryModel) {
        loadingLayout.setVisibility(View.GONE);
        if (reviewSummaryModel == null) return;
        totalRating = reviewSummaryModel.getTotalReviews();
        ratingTotal.setText(String.valueOf(totalRating));
        this.ratingCounts.clear();
        this.ratingCounts.addAll(reviewSummaryModel.getRatingCounts());
        name = reviewSummaryModel.getName();
        ratingName.setText(name);
        if (Type == AppConstant.PRODUCT_REVIEW) {
            imageUrl = reviewSummaryModel.getImage();
            GoMobileApp.getmCacheManager().loadImage(Uri.parse(imageUrl), ratingImage, null);
        } else {
            addReviewLayout.setVisibility(View.GONE);
        }
        this.reviews.clear();
        this.reviews.addAll(reviewSummaryModel.getReviews());

        if (reviews.size() < 1)
            noReviewFound.setVisibility(View.VISIBLE);

        ratingDetailAdapter.notifyDataSetChanged();
        ratingAdapter.notifyDataSetChanged();
    }
}