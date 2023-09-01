package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 9/14/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableCategory implements Parcelable {

    public final static Parcelable.Creator<AvailableCategory> CREATOR = new Creator<AvailableCategory>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AvailableCategory createFromParcel(Parcel in) {
            return new AvailableCategory(in);
        }

        public AvailableCategory[] newArray(int size) {
            return (new AvailableCategory[size]);
        }

    };
    @SerializedName("Disabled")
    @Expose
    private Boolean disabled;
    @SerializedName("Selected")
    @Expose
    private Boolean selected;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Value")
    @Expose
    private int value;

    protected AvailableCategory(Parcel in) {
        this.disabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.selected = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((int) in.readValue((int.class.getClassLoader())));
    }

    public AvailableCategory() {
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }


    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(disabled);
        dest.writeValue(selected);
        dest.writeValue(text);
        dest.writeValue(value);
    }

    public int describeContents() {
        return 0;
    }

}