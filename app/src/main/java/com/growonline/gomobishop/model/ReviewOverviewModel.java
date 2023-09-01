package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewOverviewModel implements Parcelable {

    public final static Parcelable.Creator<ReviewOverviewModel> CREATOR = new Creator<ReviewOverviewModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ReviewOverviewModel createFromParcel(Parcel in) {
            return new ReviewOverviewModel(in);
        }

        public ReviewOverviewModel[] newArray(int size) {
            return (new ReviewOverviewModel[size]);
        }

    };
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("RatingSum")
    @Expose
    private double ratingSum;
    @SerializedName("TotalReviews")
    @Expose
    private Integer totalReviews;
    @SerializedName("AllowCustomerReviews")
    @Expose
    private Boolean allowCustomerReviews;

    protected ReviewOverviewModel(Parcel in) {
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.ratingSum = ((double) in.readValue((double.class.getClassLoader())));
        this.totalReviews = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.allowCustomerReviews = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public ReviewOverviewModel() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public double getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(double ratingSum) {
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
