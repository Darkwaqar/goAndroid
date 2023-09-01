package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 6/10/2020.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerPicture implements Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("FullSizeImageUrl")
    @Expose
    private String fullSizeImageUrl;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("AlternateText")
    @Expose
    private String alternateText;
    public final static Parcelable.Creator<BannerPicture> CREATOR = new Creator<BannerPicture>() {


        @SuppressWarnings({
                "unchecked"
        })
        public BannerPicture createFromParcel(Parcel in) {
            return new BannerPicture(in);
        }

        public BannerPicture[] newArray(int size) {
            return (new BannerPicture[size]);
        }

    };
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("URL")
    @Expose
    private Object uRL;
    @SerializedName("Height")
    @Expose
    private Integer height;
    @SerializedName("Width")
    @Expose
    private Integer width;

    protected BannerPicture(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.fullSizeImageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.alternateText = ((String) in.readValue((String.class.getClassLoader())));
        this.uRL = ((Object) in.readValue((Object.class.getClassLoader())));
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.categoryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public BannerPicture() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFullSizeImageUrl() {
        return fullSizeImageUrl;
    }

    public void setFullSizeImageUrl(String fullSizeImageUrl) {
        this.fullSizeImageUrl = fullSizeImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlternateText() {
        return alternateText;
    }

    public void setAlternateText(String alternateText) {
        this.alternateText = alternateText;
    }

    public Object getURL() {
        return uRL;
    }

    public void setURL(Object uRL) {
        this.uRL = uRL;
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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(imageUrl);
        dest.writeValue(fullSizeImageUrl);
        dest.writeValue(title);
        dest.writeValue(alternateText);
        dest.writeValue(uRL);
        dest.writeValue(vendorId);
        dest.writeValue(categoryId);
        dest.writeValue(productId);
        dest.writeValue(height);
        dest.writeValue(width);
    }

    public int describeContents() {
        return 0;
    }

}
