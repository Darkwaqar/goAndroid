package com.growonline.gomobishop;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.growonline.gomobishop.asynctask.AddReviewAsyncTask;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.control.FontEditText;
import com.growonline.gomobishop.model.PendingReview;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONException;
import org.json.JSONObject;

public class AddReviewsActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ratingProductLayout;
    private AspectRatioImageView productRatingImage;
    private TextView productRatingName;
    private FontEditText productReviewTitle;
    private FontEditText productReviewText;
    private RatingBar productRatingStars;
    private AspectRatioImageView vendorRatingImage;
    private TextView vendorRatingName;
    private FontEditText vendorReviewTitle;
    private FontEditText vendorReviewText;
    private RatingBar vendorRatingStars;

    private PendingReview pendingReview;
    private AddReviewAsyncTask backgroundTask;

    private int orderId, productId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reviews);


        pendingReview = getIntent().getParcelableExtra(AppConstant.PENDING_REVIEW_INTENT);


        ratingProductLayout = (LinearLayout) findViewById(R.id.rating_product_layout);
        productRatingImage = (AspectRatioImageView) findViewById(R.id.product_rating_image);
        productRatingName = (TextView) findViewById(R.id.product_rating_name);
        productReviewTitle = (FontEditText) findViewById(R.id.product_review_title);
        productReviewText = (FontEditText) findViewById(R.id.product_review_text);
        productRatingStars = (RatingBar) findViewById(R.id.product_rating_stars);
        vendorRatingImage = (AspectRatioImageView) findViewById(R.id.vendor_rating_image);
        vendorRatingName = (TextView) findViewById(R.id.vendor_rating_name);
        vendorReviewTitle = (FontEditText) findViewById(R.id.vendor_review_title);
        vendorReviewText = (FontEditText) findViewById(R.id.vendor_review_text);
        vendorRatingStars = (RatingBar) findViewById(R.id.vendor_rating_stars);
        findViewById(R.id.add_review).setOnClickListener(this);

        BindData(pendingReview);
    }

    private void BindData(PendingReview pendingReview) {
        if (pendingReview == null) return;
        setToolBarTitle("PRODUCT REVIEWS FOR " + pendingReview.getProductName());
        GoMobileApp.getmCacheManager().loadImage(Uri.parse(pendingReview.getProductImageUrl()), productRatingImage, null);
        if (pendingReview.getVendorImageUrl() != null)
            GoMobileApp.getmCacheManager().loadImage(Uri.parse(pendingReview.getVendorImageUrl()), vendorRatingImage, null);
        productRatingName.setText(pendingReview.getProductName());
        vendorRatingName.setText(pendingReview.getVendorName());
        orderId = pendingReview.getOrderId();
        productId = pendingReview.getProductId();

    }

    public void ScrollToView(final View view) {
        ObjectAnimator.ofInt(findViewById(R.id.rating_scroll), "scrollY", view.getTop()).setDuration(500).start();
        view.requestFocus();
    }

    public boolean isValid() {
        if (GoMobileApp.isNullOrEmpty(productReviewTitle)) {
            GoMobileApp.Toast(R.string.required_product_title);
            ScrollToView(productReviewTitle);
            return false;
        }
        if (GoMobileApp.isNullOrEmpty(productReviewText)) {
            GoMobileApp.Toast(R.string.required_product_review);
            ScrollToView(productReviewText);
            return false;
        }

        if (GoMobileApp.isNullOrEmpty(productReviewText)) {
            GoMobileApp.Toast(R.string.required_vendor_review_title);
            ScrollToView(vendorReviewTitle);
            return false;
        }
        if (GoMobileApp.isNullOrEmpty(vendorReviewText)) {
            GoMobileApp.Toast(R.string.required_vendor_review);
            ScrollToView(vendorReviewText);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_review:
                if (isValid()) {
                    findViewById(R.id.add_review).setClickable(false);
                    JSONObject params = new JSONObject();
                    try {
                        params.put("OrderId", orderId);
                        params.put("ProductId", productId);
                        params.put("AddProductReviewModel.Title", GoMobileApp.getText(productReviewTitle));
                        params.put("AddProductReviewModel.ReviewText", GoMobileApp.getText(productReviewText));
                        params.put("AddProductReviewModel.Rating", productRatingStars.getRating());
                        params.put("VendorReviewListModel.Title", GoMobileApp.getText(vendorReviewTitle));
                        params.put("VendorReviewListModel.ReviewText", GoMobileApp.getText(vendorReviewText));
                        params.put("VendorReviewListModel.Rating", vendorRatingStars.getRating());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    setLoadingAnimation(true);
                    backgroundTask = new AddReviewAsyncTask(params, AppConstant.VENDOR_REVIEW);
                    backgroundTask.addOnResultListener(new AsyncTaskResultListener<String>() {
                        @Override
                        public void response(AsyncTaskResult<String> response) {
                            if (!response.hasException()) {
                                setLoadingAnimation(false);
                                ClearFields();
                                Intent returnIntent = new Intent();
                                setResult(RESULT_OK, returnIntent);
                                finish();
                            } else {
                                GoMobileApp.Toast(response.getException().getMessage());
                            }
                            findViewById(R.id.add_review).setClickable(true);
                        }
                    });
                    backgroundTask.execute();
                }
                break;
        }
    }

    public void ClearFields() {
        productReviewTitle.setText("");
        productReviewText.setText("");
        vendorReviewTitle.setText("");
        vendorReviewText.setText("");
    }


}