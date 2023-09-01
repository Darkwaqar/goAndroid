package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPrice implements Parcelable
{

    public final static Parcelable.Creator<ProductPrice> CREATOR = new Creator<ProductPrice>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductPrice createFromParcel(Parcel in) {
            return new ProductPrice(in);
        }

        public ProductPrice[] newArray(int size) {
            return (new ProductPrice[size]);
        }

    };
    @SerializedName("OldPrice")
    @Expose
    private String oldPrice;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("PriceWithDiscount")
    @Expose
    private String priceWithDiscount;
    @SerializedName("PriceWithoutDiscount")
    @Expose
    private String priceWithoutDiscount;
    @SerializedName("DiscountPercentage")
    @Expose
    private double discountPercentage;
    @SerializedName("PriceValue")
    @Expose
    private double priceValue;
    @SerializedName("BasePricePAngV")
    @Expose
    private Object basePricePAngV;
    @SerializedName("DisableBuyButton")
    @Expose
    private Boolean disableBuyButton;
    @SerializedName("DisableWishlistButton")
    @Expose
    private Boolean disableWishlistButton;
    @SerializedName("DisableAddToCompareListButton")
    @Expose
    private Boolean disableAddToCompareListButton;
    @SerializedName("AvailableForPreOrder")
    @Expose
    private Boolean availableForPreOrder;
    @SerializedName("PreOrderAvailabilityStartDateTimeUtc")
    @Expose
    private String preOrderAvailabilityStartDateTimeUtc;
    @SerializedName("IsRental")
    @Expose
    private Boolean isRental;
    @SerializedName("ForceRedirectionAfterAddingToCart")
    @Expose
    private Boolean forceRedirectionAfterAddingToCart;
    @SerializedName("DisplayTaxShippingInfo")
    @Expose
    private Boolean displayTaxShippingInfo;
    @SerializedName("CallForPrice")
    @Expose
    private Boolean callForPrice;

    protected ProductPrice(Parcel in) {
        this.oldPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.priceWithDiscount = ((String) in.readValue((String.class.getClassLoader())));
        this.priceWithoutDiscount = ((String) in.readValue((String.class.getClassLoader())));
        this.discountPercentage = ((double) in.readValue((double.class.getClassLoader())));
        this.priceValue = ((double) in.readValue((double.class.getClassLoader())));
        this.basePricePAngV = ((Object) in.readValue((Object.class.getClassLoader())));
        this.disableBuyButton = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.disableWishlistButton = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.disableAddToCompareListButton = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.availableForPreOrder = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.preOrderAvailabilityStartDateTimeUtc = ((String) in.readValue((String.class.getClassLoader())));
        this.isRental = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.forceRedirectionAfterAddingToCart = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.displayTaxShippingInfo = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.callForPrice = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public ProductPrice() {
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(String priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public String getPriceWithoutDiscount() {
        return priceWithoutDiscount;
    }

    public void setPriceWithoutDiscount(String priceWithoutDiscount) {
        this.priceWithoutDiscount = priceWithoutDiscount;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Integer priceValue) {
        this.priceValue = priceValue;
    }

    public Object getBasePricePAngV() {
        return basePricePAngV;
    }

    public void setBasePricePAngV(Object basePricePAngV) {
        this.basePricePAngV = basePricePAngV;
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

    public Boolean getDisableAddToCompareListButton() {
        return disableAddToCompareListButton;
    }

    public void setDisableAddToCompareListButton(Boolean disableAddToCompareListButton) {
        this.disableAddToCompareListButton = disableAddToCompareListButton;
    }

    public Boolean getAvailableForPreOrder() {
        return availableForPreOrder;
    }

    public void setAvailableForPreOrder(Boolean availableForPreOrder) {
        this.availableForPreOrder = availableForPreOrder;
    }

    public String getPreOrderAvailabilityStartDateTimeUtc() {
        return preOrderAvailabilityStartDateTimeUtc;
    }

    public void setPreOrderAvailabilityStartDateTimeUtc(String preOrderAvailabilityStartDateTimeUtc) {
        this.preOrderAvailabilityStartDateTimeUtc = preOrderAvailabilityStartDateTimeUtc;
    }

    public Boolean getIsRental() {
        return isRental;
    }

    public void setIsRental(Boolean isRental) {
        this.isRental = isRental;
    }

    public Boolean getForceRedirectionAfterAddingToCart() {
        return forceRedirectionAfterAddingToCart;
    }

    public void setForceRedirectionAfterAddingToCart(Boolean forceRedirectionAfterAddingToCart) {
        this.forceRedirectionAfterAddingToCart = forceRedirectionAfterAddingToCart;
    }

    public Boolean getDisplayTaxShippingInfo() {
        return displayTaxShippingInfo;
    }

    public void setDisplayTaxShippingInfo(Boolean displayTaxShippingInfo) {
        this.displayTaxShippingInfo = displayTaxShippingInfo;
    }

    public Boolean getCallForPrice() {
        return callForPrice;
    }

    public void setCallForPrice(Boolean callForPrice) {
        this.callForPrice = callForPrice;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(oldPrice);
        dest.writeValue(price);
        dest.writeValue(priceWithDiscount);
        dest.writeValue(priceWithoutDiscount);
        dest.writeValue(discountPercentage);
        dest.writeValue(priceValue);
        dest.writeValue(basePricePAngV);
        dest.writeValue(disableBuyButton);
        dest.writeValue(disableWishlistButton);
        dest.writeValue(disableAddToCompareListButton);
        dest.writeValue(availableForPreOrder);
        dest.writeValue(preOrderAvailabilityStartDateTimeUtc);
        dest.writeValue(isRental);
        dest.writeValue(forceRedirectionAfterAddingToCart);
        dest.writeValue(displayTaxShippingInfo);
        dest.writeValue(callForPrice);
    }

    public int describeContents() {
        return 0;
    }

}