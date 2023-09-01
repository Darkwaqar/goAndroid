package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 4/18/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPointer implements Parcelable {

    @SerializedName("X")
    @Expose
    private Double x;
    @SerializedName("Y")
    @Expose
    private Double y;
    @SerializedName("TaggedProductId")
    @Expose
    private Integer taggedProductId;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("PictureId")
    @Expose
    private Integer pictureId;
    @SerializedName("Id")
    @Expose
    private Integer id;
    public final static Parcelable.Creator<ProductPointer> CREATOR = new Creator<ProductPointer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductPointer createFromParcel(Parcel in) {
            return new ProductPointer(in);
        }

        public ProductPointer[] newArray(int size) {
            return (new ProductPointer[size]);
        }

    };

    protected ProductPointer(Parcel in) {
        this.x = ((Double) in.readValue((Double.class.getClassLoader())));
        this.y = ((Double) in.readValue((Double.class.getClassLoader())));
        this.taggedProductId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pictureId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public ProductPointer() {
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Integer getTaggedProductId() {
        return taggedProductId;
    }

    public void setTaggedProductId(Integer taggedProductId) {
        this.taggedProductId = taggedProductId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(x);
        dest.writeValue(y);
        dest.writeValue(taggedProductId);
        dest.writeValue(productId);
        dest.writeValue(pictureId);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}