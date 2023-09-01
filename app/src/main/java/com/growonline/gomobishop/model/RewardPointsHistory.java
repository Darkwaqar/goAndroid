package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 10/1/2018.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardPointsHistory implements Parcelable {

    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("StoreId")
    @Expose
    private Integer storeId;
    @SerializedName("Points")
    @Expose
    private Integer points;
    @SerializedName("PointsBalance")
    @Expose
    private Integer pointsBalance;
    @SerializedName("UsedAmount")
    @Expose
    private Double usedAmount;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("CreatedOnUtc")
    @Expose
    private String createdOnUtc;
    @SerializedName("UsedWithOrder")
    @Expose
    private Object usedWithOrder;
    @SerializedName("Customer")
    @Expose
    private Object customer;
    @SerializedName("Id")
    @Expose
    private Integer id;
    public final static Parcelable.Creator<RewardPointsHistory> CREATOR = new Creator<RewardPointsHistory>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RewardPointsHistory createFromParcel(Parcel in) {
            return new RewardPointsHistory(in);
        }

        public RewardPointsHistory[] newArray(int size) {
            return (new RewardPointsHistory[size]);
        }

    };

    protected RewardPointsHistory(Parcel in) {
        this.customerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.storeId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.points = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pointsBalance = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.usedAmount = ((double) in.readValue((Integer.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.createdOnUtc = ((String) in.readValue((String.class.getClassLoader())));
        this.usedWithOrder = ((Object) in.readValue((Object.class.getClassLoader())));
        this.customer = ((Object) in.readValue((Object.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public RewardPointsHistory() {
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPointsBalance() {
        return pointsBalance;
    }

    public void setPointsBalance(Integer pointsBalance) {
        this.pointsBalance = pointsBalance;
    }

    public double getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(double usedAmount) {
        this.usedAmount = usedAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedOnUtc() {
        return createdOnUtc;
    }

    public void setCreatedOnUtc(String createdOnUtc) {
        this.createdOnUtc = createdOnUtc;
    }

    public Object getUsedWithOrder() {
        return usedWithOrder;
    }

    public void setUsedWithOrder(Object usedWithOrder) {
        this.usedWithOrder = usedWithOrder;
    }

    public Object getCustomer() {
        return customer;
    }

    public void setCustomer(Object customer) {
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(customerId);
        dest.writeValue(storeId);
        dest.writeValue(points);
        dest.writeValue(pointsBalance);
        dest.writeValue(usedAmount);
        dest.writeValue(message);
        dest.writeValue(createdOnUtc);
        dest.writeValue(usedWithOrder);
        dest.writeValue(customer);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}