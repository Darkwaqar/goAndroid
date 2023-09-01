package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartItem implements Parcelable {

    public final static Parcelable.Creator<ShoppingCartItem> CREATOR = new Creator<ShoppingCartItem>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ShoppingCartItem createFromParcel(Parcel in) {
            return new ShoppingCartItem(in);
        }

        public ShoppingCartItem[] newArray(int size) {
            return (new ShoppingCartItem[size]);
        }

    };
    @SerializedName("Sku")
    @Expose
    private Object sku;
    @SerializedName("Picture")
    @Expose
    private DefaultPictureModel picture;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("ProductSeName")
    @Expose
    private String productSeName;
    @SerializedName("UnitPrice")
    @Expose
    private String unitPrice;
    @SerializedName("SubTotal")
    @Expose
    private String subTotal;
    @SerializedName("Discount")
    @Expose
    private Object discount;
    @SerializedName("MaximumDiscountedQty")
    @Expose
    private Integer maximumDiscountedQty;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("AllowedQuantities")
    @Expose
    private List<Integer> allowedQuantities = null;
    @SerializedName("AttributeInfo")
    @Expose
    private String attributeInfo;
    @SerializedName("RecurringInfo")
    @Expose
    private Object recurringInfo;
    @SerializedName("RentalInfo")
    @Expose
    private Object rentalInfo;
    @SerializedName("AllowItemEditing")
    @Expose
    private Boolean allowItemEditing;
    @SerializedName("DisableRemoval")
    @Expose
    private Boolean disableRemoval;
    @SerializedName("Warnings")
    @Expose
    private List<String> warnings = new ArrayList<>();
    @SerializedName("AttributeInfoXml")
    @Expose
    private String attributeInfoXml;
    @SerializedName("ShortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("ProductAttributes")
    @Expose
    private List<ProductAttribute> productAttributes = null;
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("VendorSeName")
    @Expose
    private String vendorSeName;
    @SerializedName("Id")
    @Expose
    private Integer id;

    protected ShoppingCartItem(Parcel in) {
        this.sku = ((Object) in.readValue((Object.class.getClassLoader())));
        this.picture = ((DefaultPictureModel) in.readValue((DefaultPictureModel.class.getClassLoader())));
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.productSeName = ((String) in.readValue((Object.class.getClassLoader())));
        this.unitPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.subTotal = ((String) in.readValue((String.class.getClassLoader())));
        this.discount = ((Object) in.readValue((Object.class.getClassLoader())));
        this.maximumDiscountedQty = ((Integer) in.readValue((Object.class.getClassLoader())));
        this.quantity = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.allowedQuantities, (java.lang.Object.class.getClassLoader()));
        this.attributeInfo = ((String) in.readValue((String.class.getClassLoader())));
        this.recurringInfo = ((Object) in.readValue((Object.class.getClassLoader())));
        this.rentalInfo = ((Object) in.readValue((Object.class.getClassLoader())));
        this.allowItemEditing = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.disableRemoval = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.warnings, (java.lang.Object.class.getClassLoader()));
        this.attributeInfoXml = ((String) in.readValue((String.class.getClassLoader())));
        this.shortDescription = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.productAttributes, (ProductAttribute.class.getClassLoader()));
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vendorName = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorSeName = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public ShoppingCartItem() {
    }

    public Object getSku() {
        return sku;
    }

    public void setSku(Object sku) {
        this.sku = sku;
    }

    public DefaultPictureModel getPicture() {
        return picture;
    }

    public void setPicture(DefaultPictureModel picture) {
        this.picture = picture;
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

    public Object getProductSeName() {
        return productSeName;
    }

    public void setProductSeName(String productSeName) {
        this.productSeName = productSeName;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public Object getMaximumDiscountedQty() {
        return maximumDiscountedQty;
    }

    public void setMaximumDiscountedQty(Integer maximumDiscountedQty) {
        this.maximumDiscountedQty = maximumDiscountedQty;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<Integer> getAllowedQuantities() {
        return allowedQuantities;
    }

    public void setAllowedQuantities(List<Integer> allowedQuantities) {
        this.allowedQuantities = allowedQuantities;
    }

    public String getAttributeInfo() {
        return attributeInfo;
    }

    public void setAttributeInfo(String attributeInfo) {
        this.attributeInfo = attributeInfo;
    }

    public Object getRecurringInfo() {
        return recurringInfo;
    }

    public void setRecurringInfo(Object recurringInfo) {
        this.recurringInfo = recurringInfo;
    }

    public Object getRentalInfo() {
        return rentalInfo;
    }

    public void setRentalInfo(Object rentalInfo) {
        this.rentalInfo = rentalInfo;
    }

    public Boolean getAllowItemEditing() {
        return allowItemEditing;
    }

    public void setAllowItemEditing(Boolean allowItemEditing) {
        this.allowItemEditing = allowItemEditing;
    }

    public Boolean getDisableRemoval() {
        return disableRemoval;
    }

    public void setDisableRemoval(Boolean disableRemoval) {
        this.disableRemoval = disableRemoval;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public String getAttributeInfoXml() {
        return attributeInfoXml;
    }

    public void setAttributeInfoXml(String attributeInfoXml) {
        this.attributeInfoXml = attributeInfoXml;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public List<ProductAttribute> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(List<ProductAttribute> productAttributes) {
        this.productAttributes = productAttributes;
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

    public String getVendorSeName() {
        return vendorSeName;
    }

    public void setVendorSeName(String vendorSeName) {
        this.vendorSeName = vendorSeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sku);
        dest.writeValue(picture);
        dest.writeValue(productId);
        dest.writeValue(productName);
        dest.writeValue(productSeName);
        dest.writeValue(unitPrice);
        dest.writeValue(subTotal);
        dest.writeValue(discount);
        dest.writeValue(maximumDiscountedQty);
        dest.writeValue(quantity);
        dest.writeList(allowedQuantities);
        dest.writeValue(attributeInfo);
        dest.writeValue(recurringInfo);
        dest.writeValue(rentalInfo);
        dest.writeValue(allowItemEditing);
        dest.writeValue(disableRemoval);
        dest.writeList(warnings);
        dest.writeValue(attributeInfoXml);
        dest.writeValue(shortDescription);
        dest.writeList(productAttributes);
        dest.writeValue(vendorId);
        dest.writeValue(vendorName);
        dest.writeValue(vendorSeName);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}