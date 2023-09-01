package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 2/9/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating implements Parcelable {

    @SerializedName("TotalRatings")
    @Expose
    private Integer totalRatings;
    @SerializedName("AverageRating")
    @Expose
    private Integer averageRating;
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("VendorSeName")
    @Expose
    private String vendorSeName;

    public final static Parcelable.Creator<Rating> CREATOR = new Creator<Rating>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        public Rating[] newArray(int size) {
            return (new Rating[size]);
        }

    };

    protected Rating(Parcel in) {
        this.totalRatings = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.averageRating = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vendorSeName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Rating() {
    }

    public Integer getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Integer totalRatings) {
        this.totalRatings = totalRatings;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorSeName() {
        return vendorSeName;
    }

    public void setVendorSeName(String vendorSeName) {
        this.vendorSeName = vendorSeName;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalRatings);
        dest.writeValue(averageRating);
        dest.writeValue(vendorId);
        dest.writeValue(vendorSeName);
    }

    public int describeContents() {
        return 0;
    }

}