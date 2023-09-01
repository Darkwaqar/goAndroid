package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AvalibleCountry implements Parcelable
{

    public final static Parcelable.Creator<AvalibleCountry> CREATOR = new Creator<AvalibleCountry>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AvalibleCountry createFromParcel(Parcel in) {
            return new AvalibleCountry(in);
        }

        public AvalibleCountry[] newArray(int size) {
            return (new AvalibleCountry[size]);
        }

    }
            ;
    @SerializedName("CountryId")
    @Expose
    private Integer countryId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("States")
    @Expose
    private List<State> states = new ArrayList<>();

    protected AvalibleCountry(Parcel in) {
        this.countryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.states, (com.growonline.gomobishop.model.State.class.getClassLoader()));
    }

    public AvalibleCountry() {
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(countryId);
        dest.writeValue(name);
        dest.writeList(states);
    }

    public int describeContents() {
        return 0;
    }

}
