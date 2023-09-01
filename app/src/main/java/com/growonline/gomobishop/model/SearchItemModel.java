package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 1/28/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchItemModel implements Parcelable {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("VendorImage")
    @Expose
    private String vendorImage;
    public final static Parcelable.Creator<SearchItemModel> CREATOR = new Creator<SearchItemModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SearchItemModel createFromParcel(Parcel in) {
            return new SearchItemModel(in);
        }

        public SearchItemModel[] newArray(int size) {
            return (new SearchItemModel[size]);
        }

    };

    protected SearchItemModel(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.categoryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vendorName = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorImage = ((String) in.readValue((String.class.getClassLoader())));
    }

    public SearchItemModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorImage() {
        return vendorImage;
    }

    public void setVendorImage(String vendorImage) {
        this.vendorImage = vendorImage;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(image);
        dest.writeValue(vendorId);
        dest.writeValue(categoryId);
        dest.writeValue(productId);
        dest.writeValue(vendorName);
        dest.writeValue(vendorImage);
    }

    public int describeContents() {
        return 0;
    }

}