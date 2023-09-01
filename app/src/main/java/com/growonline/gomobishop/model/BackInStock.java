package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 3/10/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BackInStock implements Parcelable
{

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("ProductSeName")
    @Expose
    private String productSeName;
    @SerializedName("IsCurrentCustomerRegistered")
    @Expose
    private Boolean isCurrentCustomerRegistered;
    @SerializedName("SubscriptionAllowed")
    @Expose
    private Boolean subscriptionAllowed;
    @SerializedName("AlreadySubscribed")
    @Expose
    private Boolean alreadySubscribed;
    @SerializedName("MaximumBackInStockSubscriptions")
    @Expose
    private Integer maximumBackInStockSubscriptions;
    @SerializedName("CurrentNumberOfBackInStockSubscriptions")
    @Expose
    private Integer currentNumberOfBackInStockSubscriptions;

    public final static Creator<BackInStock> CREATOR = new Creator<BackInStock>() {


        @SuppressWarnings({
                "unchecked"
        })
        public BackInStock createFromParcel(Parcel in) {
            return new BackInStock(in);
        }

        public BackInStock[] newArray(int size) {
            return (new BackInStock[size]);
        }

    }
            ;

    protected BackInStock(Parcel in) {
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.productSeName = ((String) in.readValue((String.class.getClassLoader())));
        this.isCurrentCustomerRegistered = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.subscriptionAllowed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.alreadySubscribed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.maximumBackInStockSubscriptions = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.currentNumberOfBackInStockSubscriptions = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public BackInStock() {
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

    public String getProductSeName() {
        return productSeName;
    }

    public void setProductSeName(String productSeName) {
        this.productSeName = productSeName;
    }

    public Boolean getIsCurrentCustomerRegistered() {
        return isCurrentCustomerRegistered;
    }

    public void setIsCurrentCustomerRegistered(Boolean isCurrentCustomerRegistered) {
        this.isCurrentCustomerRegistered = isCurrentCustomerRegistered;
    }

    public Boolean getSubscriptionAllowed() {
        return subscriptionAllowed;
    }

    public void setSubscriptionAllowed(Boolean subscriptionAllowed) {
        this.subscriptionAllowed = subscriptionAllowed;
    }

    public Boolean getAlreadySubscribed() {
        return alreadySubscribed;
    }

    public void setAlreadySubscribed(Boolean alreadySubscribed) {
        this.alreadySubscribed = alreadySubscribed;
    }

    public Integer getMaximumBackInStockSubscriptions() {
        return maximumBackInStockSubscriptions;
    }

    public void setMaximumBackInStockSubscriptions(Integer maximumBackInStockSubscriptions) {
        this.maximumBackInStockSubscriptions = maximumBackInStockSubscriptions;
    }

    public Integer getCurrentNumberOfBackInStockSubscriptions() {
        return currentNumberOfBackInStockSubscriptions;
    }

    public void setCurrentNumberOfBackInStockSubscriptions(Integer currentNumberOfBackInStockSubscriptions) {
        this.currentNumberOfBackInStockSubscriptions = currentNumberOfBackInStockSubscriptions;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(productId);
        dest.writeValue(productName);
        dest.writeValue(productSeName);
        dest.writeValue(isCurrentCustomerRegistered);
        dest.writeValue(subscriptionAllowed);
        dest.writeValue(alreadySubscribed);
        dest.writeValue(maximumBackInStockSubscriptions);
        dest.writeValue(currentNumberOfBackInStockSubscriptions);
    }

    public int describeContents() {
        return 0;
    }

}