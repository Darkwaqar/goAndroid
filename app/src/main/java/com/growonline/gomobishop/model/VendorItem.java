package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 12/9/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VendorItem implements Parcelable {

    public final static Parcelable.Creator<VendorItem> CREATOR = new Creator<VendorItem>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VendorItem createFromParcel(Parcel in) {
            return new VendorItem(in);
        }

        public VendorItem[] newArray(int size) {
            return (new VendorItem[size]);
        }

    };
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("ShippingCharges")
    @Expose
    private float shippingCharges;
    @SerializedName("ShippingChargesFormatted")
    @Expose
    private String shippingChargesFormatted;
    @SerializedName("Total")
    @Expose
    private float total;
    @SerializedName("TotalFormatted")
    @Expose
    private String totalFormatted;
    @SerializedName("Items")
    @Expose
    private List<ShoppingCartItem> items = new ArrayList<>();

    protected VendorItem(Parcel in) {
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vendorName = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingCharges = ((float) in.readValue((float.class.getClassLoader())));
        this.shippingChargesFormatted = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((float) in.readValue((float.class.getClassLoader())));
        this.totalFormatted = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.items, (ShoppingCartItem.class.getClassLoader()));
    }

    public VendorItem() {
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

    public float getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(Integer shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public String getShippingChargesFormatted() {
        return shippingChargesFormatted;
    }

    public void setShippingChargesFormatted(String shippingChargesFormated) {
        this.shippingChargesFormatted = shippingChargesFormated;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getTotalFormatted() {
        return totalFormatted;
    }

    public void setTotalFormatted(String totalFormated) {
        this.totalFormatted = totalFormated;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vendorId);
        dest.writeValue(vendorName);
        dest.writeValue(shippingCharges);
        dest.writeValue(shippingChargesFormatted);
        dest.writeValue(total);
        dest.writeValue(totalFormatted);
        dest.writeList(items);
    }

    public int describeContents() {
        return 0;
    }

}
