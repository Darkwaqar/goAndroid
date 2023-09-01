package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddToCart implements Parcelable {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("EnteredQuantity")
    @Expose
    private Integer enteredQuantity;
    @SerializedName("MinimumQuantityNotification")
    @Expose
    private Object minimumQuantityNotification;
    @SerializedName("AllowedQuantities")
    @Expose
    private List<Object> allowedQuantities = null;
    @SerializedName("CustomerEntersPrice")
    @Expose
    private Boolean customerEntersPrice;
    @SerializedName("CustomerEnteredPrice")
    @Expose
    private Integer customerEnteredPrice;
    @SerializedName("CustomerEnteredPriceRange")
    @Expose
    private Object customerEnteredPriceRange;
    @SerializedName("DisableBuyButton")
    @Expose
    private Boolean disableBuyButton;
    @SerializedName("DisableWishlistButton")
    @Expose
    private Boolean disableWishlistButton;
    @SerializedName("IsRental")
    @Expose
    private Boolean isRental;
    @SerializedName("AvailableForPreOrder")
    @Expose
    private Boolean availableForPreOrder;
    @SerializedName("PreOrderAvailabilityStartDateTimeUtc")
    @Expose
    private Object preOrderAvailabilityStartDateTimeUtc;
    @SerializedName("UpdatedShoppingCartItemId")
    @Expose
    private Integer updatedShoppingCartItemId;
    @SerializedName("UpdateShoppingCartItemType")
    @Expose
    private Object updateShoppingCartItemType;

    public final static Parcelable.Creator<AddToCart> CREATOR = new Creator<AddToCart>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AddToCart createFromParcel(Parcel in) {
            return new AddToCart(in);
        }

        public AddToCart[] newArray(int size) {
            return (new AddToCart[size]);
        }

    };

    protected AddToCart(Parcel in) {
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.enteredQuantity = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.minimumQuantityNotification = ((Object) in.readValue((Object.class.getClassLoader())));
        in.readList(this.allowedQuantities, (java.lang.Object.class.getClassLoader()));
        this.customerEntersPrice = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.customerEnteredPrice = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customerEnteredPriceRange = ((Object) in.readValue((Object.class.getClassLoader())));
        this.disableBuyButton = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.disableWishlistButton = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isRental = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.availableForPreOrder = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.preOrderAvailabilityStartDateTimeUtc = ((Object) in.readValue((Object.class.getClassLoader())));
        this.updatedShoppingCartItemId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.updateShoppingCartItemType = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public AddToCart() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getEnteredQuantity() {
        return enteredQuantity;
    }

    public void setEnteredQuantity(Integer enteredQuantity) {
        this.enteredQuantity = enteredQuantity;
    }

    public Object getMinimumQuantityNotification() {
        return minimumQuantityNotification;
    }

    public void setMinimumQuantityNotification(Object minimumQuantityNotification) {
        this.minimumQuantityNotification = minimumQuantityNotification;
    }

    public List<Object> getAllowedQuantities() {
        return allowedQuantities;
    }

    public void setAllowedQuantities(List<Object> allowedQuantities) {
        this.allowedQuantities = allowedQuantities;
    }

    public Boolean getCustomerEntersPrice() {
        return customerEntersPrice;
    }

    public void setCustomerEntersPrice(Boolean customerEntersPrice) {
        this.customerEntersPrice = customerEntersPrice;
    }

    public Integer getCustomerEnteredPrice() {
        return customerEnteredPrice;
    }

    public void setCustomerEnteredPrice(Integer customerEnteredPrice) {
        this.customerEnteredPrice = customerEnteredPrice;
    }

    public Object getCustomerEnteredPriceRange() {
        return customerEnteredPriceRange;
    }

    public void setCustomerEnteredPriceRange(Object customerEnteredPriceRange) {
        this.customerEnteredPriceRange = customerEnteredPriceRange;
    }

    public Boolean getDisableBuyButton() {
        return disableBuyButton;
    }

    public void setDisableBuyButton(Boolean disableBuyButton) {
        this.disableBuyButton = disableBuyButton;
    }

    public Boolean getDisableWishlistButton() {
        return disableWishlistButton;
    }

    public void setDisableWishlistButton(Boolean disableWishlistButton) {
        this.disableWishlistButton = disableWishlistButton;
    }

    public Boolean getIsRental() {
        return isRental;
    }

    public void setIsRental(Boolean isRental) {
        this.isRental = isRental;
    }

    public Boolean getAvailableForPreOrder() {
        return availableForPreOrder;
    }

    public void setAvailableForPreOrder(Boolean availableForPreOrder) {
        this.availableForPreOrder = availableForPreOrder;
    }

    public Object getPreOrderAvailabilityStartDateTimeUtc() {
        return preOrderAvailabilityStartDateTimeUtc;
    }

    public void setPreOrderAvailabilityStartDateTimeUtc(Object preOrderAvailabilityStartDateTimeUtc) {
        this.preOrderAvailabilityStartDateTimeUtc = preOrderAvailabilityStartDateTimeUtc;
    }

    public Integer getUpdatedShoppingCartItemId() {
        return updatedShoppingCartItemId;
    }

    public void setUpdatedShoppingCartItemId(Integer updatedShoppingCartItemId) {
        this.updatedShoppingCartItemId = updatedShoppingCartItemId;
    }

    public Object getUpdateShoppingCartItemType() {
        return updateShoppingCartItemType;
    }

    public void setUpdateShoppingCartItemType(Object updateShoppingCartItemType) {
        this.updateShoppingCartItemType = updateShoppingCartItemType;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(productId);
        dest.writeValue(enteredQuantity);
        dest.writeValue(minimumQuantityNotification);
        dest.writeList(allowedQuantities);
        dest.writeValue(customerEntersPrice);
        dest.writeValue(customerEnteredPrice);
        dest.writeValue(customerEnteredPriceRange);
        dest.writeValue(disableBuyButton);
        dest.writeValue(disableWishlistButton);
        dest.writeValue(isRental);
        dest.writeValue(availableForPreOrder);
        dest.writeValue(preOrderAvailabilityStartDateTimeUtc);
        dest.writeValue(updatedShoppingCartItemId);
        dest.writeValue(updateShoppingCartItemType);
    }

    public int describeContents() {
        return 0;
    }

}
