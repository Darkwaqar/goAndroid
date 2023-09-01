package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 11/26/2018.
 */


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorList implements Parcelable {

    public final static Parcelable.Creator<VendorList> CREATOR = new Creator<VendorList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VendorList createFromParcel(Parcel in) {
            return new VendorList(in);
        }

        public VendorList[] newArray(int size) {
            return (new VendorList[size]);
        }

    };
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("VendorUrl")
    @Expose
    private String vendorUrl;
    @SerializedName("MpLogoUrl")
    @Expose
    private String mpLogoUrl;
    @SerializedName("LogoUrl")
    @Expose
    private String logoUrl;
    @SerializedName("Description")
    @Expose
    private String description;

    protected VendorList(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.mpLogoUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.logoUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
    }

    public VendorList() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVendorUrl() {
        return vendorUrl;
    }

    public void setVendorUrl(String vendorUrl) {
        this.vendorUrl = vendorUrl;
    }

    public String getMpLogoUrl() {
        return mpLogoUrl;
    }

    public void setMpLogoUrl(String mpLogoUrl) {
        this.mpLogoUrl = mpLogoUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(vendorUrl);
        dest.writeValue(mpLogoUrl);
        dest.writeValue(logoUrl);
        dest.writeValue(description);
    }

    public int describeContents() {
        return 0;
    }

}