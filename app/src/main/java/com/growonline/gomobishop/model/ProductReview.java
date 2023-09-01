package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 4/7/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductReview implements Parcelable {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("ProductSeName")
    @Expose
    private String productSeName;
    @SerializedName("Rating")
    @Expose
    private Integer rating;
    @SerializedName("ReviewText")
    @Expose
    private String reviewText;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("ProductImageUrl")
    @Expose
    private String productImageUrl;
    @SerializedName("CreatedOnUtc")
    @Expose
    private String createdOnUtc;

    public final static Parcelable.Creator<ProductReview> CREATOR = new Creator<ProductReview>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductReview createFromParcel(Parcel in) {
            return new ProductReview(in);
        }

        public ProductReview[] newArray(int size) {
            return (new ProductReview[size]);
        }

    };

    protected ProductReview(Parcel in) {
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productSeName = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.reviewText = ((String) in.readValue((String.class.getClassLoader())));
        this.customerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.productImageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.createdOnUtc = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ProductReview() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductSeName() {
        return productSeName;
    }

    public void setProductSeName(String productSeName) {
        this.productSeName = productSeName;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getCreatedOnUtc() {
        return createdOnUtc;
    }

    public void setCreatedOnUtc(String createdOnUtc) {
        this.createdOnUtc = createdOnUtc;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(productId);
        dest.writeValue(productSeName);
        dest.writeValue(rating);
        dest.writeValue(reviewText);
        dest.writeValue(customerId);
        dest.writeValue(title);
        dest.writeValue(productName);
        dest.writeValue(productImageUrl);
        dest.writeValue(createdOnUtc);
    }

    public int describeContents() {
        return 0;
    }

}
