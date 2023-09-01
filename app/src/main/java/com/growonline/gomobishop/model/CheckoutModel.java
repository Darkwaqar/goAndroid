package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CheckoutModel implements Parcelable {

    @SerializedName("ShippingLocation")
    @Expose
    private Address shippingLocation;
    @SerializedName("BillingAddress")
    @Expose
    private Address billingAddress;
    @SerializedName("Addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("ShopingCartItems")
    @Expose
    private List<ShoppingCartItem> shopingCartItems = null;
    @SerializedName("Total")
    @Expose
    private Total total;
    @SerializedName("ShippingMethod")
    @Expose
    private ShippingMethod shippingMethod;
    @SerializedName("PaymentMethods")
    @Expose
    private PaymentMethods paymentMethods;
    @SerializedName("AvalibleCountries")
    @Expose
    private ArrayList<AvalibleCountry> avalibleCountries = null;
    public final static Parcelable.Creator<CheckoutModel> CREATOR = new Creator<CheckoutModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CheckoutModel createFromParcel(Parcel in) {
            return new CheckoutModel(in);
        }

        public CheckoutModel[] newArray(int size) {
            return (new CheckoutModel[size]);
        }

    };

    protected CheckoutModel(Parcel in) {
        this.shippingLocation = ((Address) in.readValue((Address.class.getClassLoader())));
        this.billingAddress = ((Address) in.readValue((Address.class.getClassLoader())));
        in.readList(this.addresses, (com.growonline.gomobishop.model.Address.class.getClassLoader()));
        in.readList(this.shopingCartItems, (com.growonline.gomobishop.model.ShoppingCartItem.class.getClassLoader()));
        this.total = ((Total) in.readValue((Total.class.getClassLoader())));
        this.shippingMethod = ((ShippingMethod) in.readValue((ShippingMethod.class.getClassLoader())));
        this.paymentMethods = ((PaymentMethods) in.readValue((PaymentMethods.class.getClassLoader())));
        in.readList(this.avalibleCountries, (com.growonline.gomobishop.model.AvalibleCountry.class.getClassLoader()));
    }

    public CheckoutModel() {
    }

    public Address getShippingLocation() {
        return shippingLocation;
    }

    public void setShippingLocation(Address shippingLocation) {
        this.shippingLocation = shippingLocation;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shopingCartItems;
    }

    public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shopingCartItems = shoppingCartItems;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public PaymentMethods getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public ArrayList<AvalibleCountry> getAvalibleCountries() {
        return avalibleCountries;
    }

    public void setAvalibleCountries(ArrayList<AvalibleCountry> avalibleCountries) {
        this.avalibleCountries = avalibleCountries;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(shippingLocation);
        dest.writeValue(billingAddress);
        dest.writeList(addresses);
        dest.writeList(shopingCartItems);
        dest.writeValue(total);
        dest.writeValue(shippingMethod);
        dest.writeValue(paymentMethods);
        dest.writeList(avalibleCountries);
    }

    public int describeContents() {
        return 0;
    }

}
