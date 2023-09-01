package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 4/7/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingReview implements Parcelable {

    @SerializedName("OrderId")
    @Expose
    private Integer orderId;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("ProductSeName")
    @Expose
    private String productSeName;
    @SerializedName("ProductImageUrl")
    @Expose
    private String productImageUrl;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("VendorSeName")
    @Expose
    private String vendorSeName;
    @SerializedName("VendorImageUrl")
    @Expose
    private String vendorImageUrl;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    public final static Parcelable.Creator<PendingReview> CREATOR = new Creator<PendingReview>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PendingReview createFromParcel(Parcel in) {
            return new PendingReview(in);
        }

        public PendingReview[] newArray(int size) {
            return (new PendingReview[size]);
        }

    };

    protected PendingReview(Parcel in) {
        this.orderId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.productSeName = ((String) in.readValue((String.class.getClassLoader())));
        this.productImageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorName = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorSeName = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorImageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));

    }

    public PendingReview() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSeName() {
        return productSeName;
    }

    public void setProductSeName(String productSeName) {
        this.productSeName = productSeName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorSeName() {
        return vendorSeName;
    }

    public void setVendorSeName(String vendorSeName) {
        this.vendorSeName = vendorSeName;
    }

    public String getVendorImageUrl() {
        return vendorImageUrl;
    }

    public void setVendorImageUrl(String vendorImageUrl) {
        this.vendorImageUrl = vendorImageUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(orderId);
        dest.writeValue(productName);
        dest.writeValue(productSeName);
        dest.writeValue(productImageUrl);
        dest.writeValue(vendorName);
        dest.writeValue(vendorSeName);
        dest.writeValue(vendorImageUrl);
        dest.writeValue(productId);
    }

    public int describeContents() {
        return 0;
    }

}
