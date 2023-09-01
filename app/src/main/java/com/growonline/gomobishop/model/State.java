package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State implements Parcelable
{

    @SerializedName("ProvinceId")
    @Expose
    private Integer provinceId;
    @SerializedName("Name")
    @Expose
    private String name;
    public final static Parcelable.Creator<State> CREATOR = new Creator<State>() {


        @SuppressWarnings({
                "unchecked"
        })
        public State createFromParcel(Parcel in) {
            return new State(in);
        }

        public State[] newArray(int size) {
            return (new State[size]);
        }

    }
            ;

    protected State(Parcel in) {
        this.provinceId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public State() {
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(provinceId);
        dest.writeValue(name);
    }

    public int describeContents() {
        return 0;
    }

}