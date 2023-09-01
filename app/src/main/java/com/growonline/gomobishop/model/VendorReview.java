package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 4/7/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorReview implements Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("IsApproved")
    @Expose
    private Boolean isApproved;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("ReviewText")
    @Expose
    private String reviewText;
    @SerializedName("Rating")
    @Expose
    private Integer rating;
    @SerializedName("HelpfulnessYesTotal")
    @Expose
    private Integer helpfulnessYesTotal;
    @SerializedName("HelpfulnessNoTotal")
    @Expose
    private Integer helpfulnessNoTotal;
    @SerializedName("CreatedOnUTC")
    @Expose
    private String createdOnUTC;
    @SerializedName("OrderId")
    @Expose
    private Integer orderId;
    @SerializedName("CertifiedBuyerReview")
    @Expose
    private Boolean certifiedBuyerReview;
    @SerializedName("DisplayCertifiedBadge")
    @Expose
    private Boolean displayCertifiedBadge;
    @SerializedName("VendorSeName")
    @Expose
    private String vendorSeName;
    @SerializedName("ProductSeName")
    @Expose
    private String productSeName;
    @SerializedName("VendorImageUrl")
    @Expose
    private String vendorImageUrl;
    @SerializedName("ProductImageUrl")
    @Expose
    private String productImageUrl;
    public final static Parcelable.Creator<VendorReview> CREATOR = new Creator<VendorReview>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VendorReview createFromParcel(Parcel in) {
            return new VendorReview(in);
        }

        public VendorReview[] newArray(int size) {
            return (new VendorReview[size]);
        }

    };

    protected VendorReview(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vendorName = ((String) in.readValue((String.class.getClassLoader())));
        this.customerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customerName = ((String) in.readValue((String.class.getClassLoader())));
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.isApproved = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.reviewText = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.helpfulnessYesTotal = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.helpfulnessNoTotal = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdOnUTC = ((String) in.readValue((String.class.getClassLoader())));
        this.orderId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.certifiedBuyerReview = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.displayCertifiedBadge = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.vendorSeName = ((String) in.readValue((String.class.getClassLoader())));
        this.productSeName = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorImageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.productImageUrl = ((String) in.readValue((String.class.getClassLoader())));
    }

    public VendorReview() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getHelpfulnessYesTotal() {
        return helpfulnessYesTotal;
    }

    public void setHelpfulnessYesTotal(Integer helpfulnessYesTotal) {
        this.helpfulnessYesTotal = helpfulnessYesTotal;
    }

    public Integer getHelpfulnessNoTotal() {
        return helpfulnessNoTotal;
    }

    public void setHelpfulnessNoTotal(Integer helpfulnessNoTotal) {
        this.helpfulnessNoTotal = helpfulnessNoTotal;
    }

    public String getCreatedOnUTC() {
        return createdOnUTC;
    }

    public void setCreatedOnUTC(String createdOnUTC) {
        this.createdOnUTC = createdOnUTC;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Boolean getCertifiedBuyerReview() {
        return certifiedBuyerReview;
    }

    public void setCertifiedBuyerReview(Boolean certifiedBuyerReview) {
        this.certifiedBuyerReview = certifiedBuyerReview;
    }

    public Boolean getDisplayCertifiedBadge() {
        return displayCertifiedBadge;
    }

    public void setDisplayCertifiedBadge(Boolean displayCertifiedBadge) {
        this.displayCertifiedBadge = displayCertifiedBadge;
    }

    public String getVendorSeName() {
        return vendorSeName;
    }

    public void setVendorSeName(String vendorSeName) {
        this.vendorSeName = vendorSeName;
    }

    public String getProductSeName() {
        return productSeName;
    }

    public void setProductSeName(String productSeName) {
        this.productSeName = productSeName;
    }

    public String getVendorImageUrl() {
        return vendorImageUrl;
    }

    public void setVendorImageUrl(String vendorImageUrl) {
        this.vendorImageUrl = vendorImageUrl;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(vendorId);
        dest.writeValue(vendorName);
        dest.writeValue(customerId);
        dest.writeValue(customerName);
        dest.writeValue(productId);
        dest.writeValue(productName);
        dest.writeValue(isApproved);
        dest.writeValue(title);
        dest.writeValue(reviewText);
        dest.writeValue(rating);
        dest.writeValue(helpfulnessYesTotal);
        dest.writeValue(helpfulnessNoTotal);
        dest.writeValue(createdOnUTC);
        dest.writeValue(orderId);
        dest.writeValue(certifiedBuyerReview);
        dest.writeValue(displayCertifiedBadge);
        dest.writeValue(vendorSeName);
        dest.writeValue(productSeName);
        dest.writeValue(vendorImageUrl);
        dest.writeValue(productImageUrl);
    }

    public int describeContents() {
        return 0;
    }

}
