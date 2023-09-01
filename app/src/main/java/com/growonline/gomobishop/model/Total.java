package com.growonline.gomobishop.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Total implements Parcelable
{

    @SerializedName("IsEditable")
    @Expose
    private Boolean isEditable;
    @SerializedName("SubTotal")
    @Expose
    private String subTotal;
    @SerializedName("SubTotalDiscount")
    @Expose
    private Object subTotalDiscount;
    @SerializedName("Shipping")
    @Expose
    private String shipping;
    @SerializedName("RequiresShipping")
    @Expose
    private Boolean requiresShipping;
    @SerializedName("SelectedShippingMethod")
    @Expose
    private String selectedShippingMethod;
    @SerializedName("HideShippingTotal")
    @Expose
    private Boolean hideShippingTotal;
    @SerializedName("PaymentMethodAdditionalFee")
    @Expose
    private String paymentMethodAdditionalFee;
    @SerializedName("Tax")
    @Expose
    private String tax;
    @SerializedName("TaxRates")
    @Expose
    private List<TaxRate> taxRates = null;
    @SerializedName("DisplayTax")
    @Expose
    private Boolean displayTax;
    @SerializedName("DisplayTaxRates")
    @Expose
    private Boolean displayTaxRates;
    @SerializedName("GiftCards")
    @Expose
    private List<Object> giftCards = null;
    @SerializedName("OrderTotalDiscount")
    @Expose
    private String orderTotalDiscount;
    @SerializedName("RedeemedRewardPoints")
    @Expose
    private Integer redeemedRewardPoints;
    @SerializedName("RedeemedRewardPointsAmount")
    @Expose
    private String redeemedRewardPointsAmount;
    @SerializedName("WillEarnRewardPoints")
    @Expose
    private Integer willEarnRewardPoints;
    @SerializedName("OrderTotal")
    @Expose
    private String orderTotal;
    @SerializedName("CustomProperties")
    @Expose
    public final static Parcelable.Creator<Total> CREATOR = new Creator<Total>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Total createFromParcel(Parcel in) {
            return new Total(in);
        }

        public Total[] newArray(int size) {
            return (new Total[size]);
        }

    }
            ;

    protected Total(Parcel in) {
        this.isEditable = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.subTotal = ((String) in.readValue((String.class.getClassLoader())));
        this.subTotalDiscount = ((Object) in.readValue((Object.class.getClassLoader())));
        this.shipping = ((String) in.readValue((String.class.getClassLoader())));
        this.requiresShipping = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.selectedShippingMethod = ((String) in.readValue((String.class.getClassLoader())));
        this.hideShippingTotal = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.paymentMethodAdditionalFee = ((String) in.readValue((Object.class.getClassLoader())));
        this.tax = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.taxRates, (com.growonline.gomobishop.model.TaxRate.class.getClassLoader()));
        this.displayTax = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.displayTaxRates = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.giftCards, (java.lang.Object.class.getClassLoader()));
        this.orderTotalDiscount = ((String) in.readValue((Object.class.getClassLoader())));
        this.redeemedRewardPoints = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.redeemedRewardPointsAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.willEarnRewardPoints = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.orderTotal = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Total() {
    }

    public Boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public Object getSubTotalDiscount() {
        return subTotalDiscount;
    }

    public void setSubTotalDiscount(Object subTotalDiscount) {
        this.subTotalDiscount = subTotalDiscount;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public Boolean getRequiresShipping() {
        return requiresShipping;
    }

    public void setRequiresShipping(Boolean requiresShipping) {
        this.requiresShipping = requiresShipping;
    }

    public String getSelectedShippingMethod() {
        return selectedShippingMethod;
    }

    public void setSelectedShippingMethod(String selectedShippingMethod) {
        this.selectedShippingMethod = selectedShippingMethod;
    }

    public Boolean getHideShippingTotal() {
        return hideShippingTotal;
    }

    public void setHideShippingTotal(Boolean hideShippingTotal) {
        this.hideShippingTotal = hideShippingTotal;
    }

    public Object getPaymentMethodAdditionalFee() {
        return paymentMethodAdditionalFee;
    }

    public void setPaymentMethodAdditionalFee(String paymentMethodAdditionalFee) {
        this.paymentMethodAdditionalFee = paymentMethodAdditionalFee;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public List<TaxRate> getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(List<TaxRate> taxRates) {
        this.taxRates = taxRates;
    }

    public Boolean getDisplayTax() {
        return displayTax;
    }

    public void setDisplayTax(Boolean displayTax) {
        this.displayTax = displayTax;
    }

    public Boolean getDisplayTaxRates() {
        return displayTaxRates;
    }

    public void setDisplayTaxRates(Boolean displayTaxRates) {
        this.displayTaxRates = displayTaxRates;
    }

    public List<Object> getGiftCards() {
        return giftCards;
    }

    public void setGiftCards(List<Object> giftCards) {
        this.giftCards = giftCards;
    }

    public String getOrderTotalDiscount() {
        return orderTotalDiscount;
    }

    public void setOrderTotalDiscount(String orderTotalDiscount) {
        this.orderTotalDiscount = orderTotalDiscount;
    }

    public Integer getRedeemedRewardPoints() {
        return redeemedRewardPoints;
    }

    public void setRedeemedRewardPoints(Integer redeemedRewardPoints) {
        this.redeemedRewardPoints = redeemedRewardPoints;
    }

    public String getRedeemedRewardPointsAmount() {
        return redeemedRewardPointsAmount;
    }

    public void setRedeemedRewardPointsAmount(String redeemedRewardPointsAmount) {
        this.redeemedRewardPointsAmount = redeemedRewardPointsAmount;
    }

    public Integer getWillEarnRewardPoints() {
        return willEarnRewardPoints;
    }

    public void setWillEarnRewardPoints(Integer willEarnRewardPoints) {
        this.willEarnRewardPoints = willEarnRewardPoints;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(isEditable);
        dest.writeValue(subTotal);
        dest.writeValue(subTotalDiscount);
        dest.writeValue(shipping);
        dest.writeValue(requiresShipping);
        dest.writeValue(selectedShippingMethod);
        dest.writeValue(hideShippingTotal);
        dest.writeValue(paymentMethodAdditionalFee);
        dest.writeValue(tax);
        dest.writeList(taxRates);
        dest.writeValue(displayTax);
        dest.writeValue(displayTaxRates);
        dest.writeList(giftCards);
        dest.writeValue(orderTotalDiscount);
        dest.writeValue(redeemedRewardPoints);
        dest.writeValue(redeemedRewardPointsAmount);
        dest.writeValue(willEarnRewardPoints);
        dest.writeValue(orderTotal);
    }

    public int describeContents() {
        return 0;
    }

}