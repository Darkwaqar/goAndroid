package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Banner implements Parcelable
{

    public final static Parcelable.Creator<Banner> CREATOR = new Creator<Banner>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Banner createFromParcel(Parcel in) {
            return new Banner(in);
        }

        public Banner[] newArray(int size) {
            return (new Banner[size]);
        }

    }
            ;
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("ImageId")
    @Expose
    private Integer imageId;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("DisplayOrder")
    @Expose
    private Integer displayOrder;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Image")
    @Expose
    private String image;

    protected Banner(Parcel in) {
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.imageId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.displayOrder = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Banner() {
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vendorId);
        dest.writeValue(imageId);
        dest.writeValue(url);
        dest.writeValue(title);
        dest.writeValue(description);
        dest.writeValue(displayOrder);
        dest.writeValue(id);
        dest.writeValue(image);
    }

    public int describeContents() {
        return 0;
    }

}