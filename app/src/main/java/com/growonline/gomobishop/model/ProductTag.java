package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 3/3/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductTag implements Parcelable
{

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("SeName")
    @Expose
    private String seName;
    @SerializedName("ProductCount")
    @Expose
    private Integer productCount;
    @SerializedName("Id")
    @Expose
    private Integer id;

    public final static Parcelable.Creator<ProductTag> CREATOR = new Creator<ProductTag>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductTag createFromParcel(Parcel in) {
            return new ProductTag(in);
        }

        public ProductTag[] newArray(int size) {
            return (new ProductTag[size]);
        }

    }
            ;

    protected ProductTag(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.seName = ((String) in.readValue((String.class.getClassLoader())));
        this.productCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public ProductTag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeName() {
        return seName;
    }

    public void setSeName(String seName) {
        this.seName = seName;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(seName);
        dest.writeValue(productCount);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}
