package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 2/1/2020.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag implements Parcelable {

    public final static Parcelable.Creator<Tag> CREATOR = new Creator<Tag>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        public Tag[] newArray(int size) {
            return (new Tag[size]);
        }

    };
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

    protected Tag(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.seName = ((String) in.readValue((String.class.getClassLoader())));
        this.productCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Tag() {
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