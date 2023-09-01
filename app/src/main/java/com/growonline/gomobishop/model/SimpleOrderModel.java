package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 2/14/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimpleOrderModel implements Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("CustomOrderNumber")
    @Expose
    private String customOrderNumber;
    @SerializedName("OrderTotal")
    @Expose
    private String orderTotal;
    @SerializedName("IsReturnRequestAllowed")
    @Expose
    private Boolean isReturnRequestAllowed;
    @SerializedName("OrderStatusEnum")
    @Expose
    private Integer orderStatusEnum;
    @SerializedName("OrderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("PaymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("ShippingStatus")
    @Expose
    private String shippingStatus;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("Image")
    @Expose
    private String image;
    public final static Parcelable.Creator<SimpleOrderModel> CREATOR = new Creator<SimpleOrderModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SimpleOrderModel createFromParcel(Parcel in) {
            return new SimpleOrderModel(in);
        }

        public SimpleOrderModel[] newArray(int size) {
            return (new SimpleOrderModel[size]);
        }

    };

    protected SimpleOrderModel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customOrderNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.orderTotal = ((String) in.readValue((String.class.getClassLoader())));
        this.isReturnRequestAllowed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.orderStatusEnum = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.orderStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.createdOn = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    public SimpleOrderModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomOrderNumber() {
        return customOrderNumber;
    }

    public void setCustomOrderNumber(String customOrderNumber) {
        this.customOrderNumber = customOrderNumber;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Boolean getIsReturnRequestAllowed() {
        return isReturnRequestAllowed;
    }

    public void setIsReturnRequestAllowed(Boolean isReturnRequestAllowed) {
        this.isReturnRequestAllowed = isReturnRequestAllowed;
    }

    public Integer getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public void setOrderStatusEnum(Integer orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(customOrderNumber);
        dest.writeValue(orderTotal);
        dest.writeValue(isReturnRequestAllowed);
        dest.writeValue(orderStatusEnum);
        dest.writeValue(orderStatus);
        dest.writeValue(paymentStatus);
        dest.writeValue(shippingStatus);
        dest.writeValue(createdOn);
        dest.writeValue(image);
    }

    public int describeContents() {
        return 0;
    }

}