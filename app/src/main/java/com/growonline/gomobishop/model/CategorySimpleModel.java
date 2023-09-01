package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 1/29/2019.
 */


import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategorySimpleModel implements Parcelable
{

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
    @SerializedName("HasSubCategory")
    @Expose
    private Boolean hasSubCategory;
    public final static Parcelable.Creator<CategorySimpleModel> CREATOR = new Creator<CategorySimpleModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CategorySimpleModel createFromParcel(Parcel in) {
            return new CategorySimpleModel(in);
        }

        public CategorySimpleModel[] newArray(int size) {
            return (new CategorySimpleModel[size]);
        }

    }
            ;

    protected CategorySimpleModel(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.categoryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.hasSubCategory = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public CategorySimpleModel() {
    }
    public String getName() {
        return name;
    }

    public CategorySimpleModel(String name, String image, Integer vendorId) {
        this.name = name;
        this.image = image;
        this.vendorId = vendorId;
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

    public Boolean getHasSubCategory() {
        return hasSubCategory;
    }

    public void setHasSubCategory(Boolean hasSubCategory) {
        this.hasSubCategory = hasSubCategory;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(image);
        dest.writeValue(vendorId);
        dest.writeValue(categoryId);
        dest.writeValue(productId);
        dest.writeValue(hasSubCategory);
    }

    public int describeContents() {
        return 0;
    }

}