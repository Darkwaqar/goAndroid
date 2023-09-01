
package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxRate implements Parcelable {

    public final static Parcelable.Creator<TaxRate> CREATOR = new Creator<TaxRate>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TaxRate createFromParcel(Parcel in) {
            return new TaxRate(in);
        }

        public TaxRate[] newArray(int size) {
            return (new TaxRate[size]);
        }

    };
    @SerializedName("Rate")
    @Expose
    private String rate;
    @SerializedName("Value")
    @Expose
    private String value;

    protected TaxRate(Parcel in) {
        this.rate = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public TaxRate() {
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rate);
        dest.writeValue(value);
    }

    public int describeContents() {
        return 0;
    }

}