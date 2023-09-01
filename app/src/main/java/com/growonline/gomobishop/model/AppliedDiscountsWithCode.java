package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 10/1/2018.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppliedDiscountsWithCode implements Parcelable
{

    @SerializedName("CouponCode")
    @Expose
    private String couponCode;
    @SerializedName("Id")
    @Expose
    private Integer id;
    public final static Parcelable.Creator<AppliedDiscountsWithCode> CREATOR = new Creator<AppliedDiscountsWithCode>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AppliedDiscountsWithCode createFromParcel(Parcel in) {
            return new AppliedDiscountsWithCode(in);
        }

        public AppliedDiscountsWithCode[] newArray(int size) {
            return (new AppliedDiscountsWithCode[size]);
        }

    }
            ;

    protected AppliedDiscountsWithCode(Parcel in) {
        this.couponCode = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public AppliedDiscountsWithCode() {
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }




    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(couponCode);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}
