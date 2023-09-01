package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 2/22/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review implements Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ProductImage")
    @Expose
    private String productImage;

    public Review(Integer id, String productImage, Integer rating, String reviewText, String title, String createdOnUtc) {
        this.id = id;
        this.productImage = productImage;
        this.rating = rating;
        this.reviewText = reviewText;
        this.title = title;
        this.createdOnUtc = createdOnUtc;
    }

    @SerializedName("Rating")

    @Expose
    private Integer rating;
    @SerializedName("ReviewText")
    @Expose
    private String reviewText;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("CreatedOnUtc")
    @Expose
    private String createdOnUtc;
    public final static Parcelable.Creator<Review> CREATOR = new Creator<Review>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return (new Review[size]);
        }

    };

    protected Review(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productImage = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.reviewText = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.createdOnUtc = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Review() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedOnUtc() {
        return createdOnUtc;
    }

    public void setCreatedOnUtc(String createdOnUtc) {
        this.createdOnUtc = createdOnUtc;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(productImage);
        dest.writeValue(rating);
        dest.writeValue(reviewText);
        dest.writeValue(title);
        dest.writeValue(createdOnUtc);
    }

    public int describeContents() {
        return 0;
    }

}