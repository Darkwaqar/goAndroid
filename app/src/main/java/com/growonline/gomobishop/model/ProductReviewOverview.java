package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 3/3/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductReviewOverview implements Parcelable {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("RatingSum")
    @Expose
    private Integer ratingSum;
    @SerializedName("TotalReviews")
    @Expose
    private Integer totalReviews;
    @SerializedName("AllowCustomerReviews")
    @Expose
    private Boolean allowCustomerReviews;

    public final static Parcelable.Creator<ProductReviewOverview> CREATOR = new Creator<ProductReviewOverview>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductReviewOverview createFromParcel(Parcel in) {
            return new ProductReviewOverview(in);
        }

        public ProductReviewOverview[] newArray(int size) {
            return (new ProductReviewOverview[size]);
        }

    };

    protected ProductReviewOverview(Parcel in) {
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.ratingSum = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalReviews = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.allowCustomerReviews = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public ProductReviewOverview() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(Integer ratingSum) {
        this.ratingSum = ratingSum;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public Boolean getAllowCustomerReviews() {
        return allowCustomerReviews;
    }

    public void setAllowCustomerReviews(Boolean allowCustomerReviews) {
        this.allowCustomerReviews = allowCustomerReviews;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(productId);
        dest.writeValue(ratingSum);
        dest.writeValue(totalReviews);
        dest.writeValue(allowCustomerReviews);
    }

    public int describeContents() {
        return 0;
    }

}