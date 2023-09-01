package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableTimeZone implements Parcelable {

    public final static Parcelable.Creator<AvailableTimeZone> CREATOR = new Creator<AvailableTimeZone>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AvailableTimeZone createFromParcel(Parcel in) {
            return new AvailableTimeZone(in);
        }

        public AvailableTimeZone[] newArray(int size) {
            return (new AvailableTimeZone[size]);
        }

    };
    @SerializedName("Disabled")
    @Expose
    private Boolean disabled;
    @SerializedName("Group")
    @Expose
    private Object group;
    @SerializedName("Selected")
    @Expose
    private Boolean selected;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Value")
    @Expose
    private String value;

    protected AvailableTimeZone(Parcel in) {
        this.disabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.group = ((Object) in.readValue((Object.class.getClassLoader())));
        this.selected = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AvailableTimeZone() {
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Object getGroup() {
        return group;
    }

    public void setGroup(Object group) {
        this.group = group;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(disabled);
        dest.writeValue(group);
        dest.writeValue(selected);
        dest.writeValue(text);
        dest.writeValue(value);
    }

    public int describeContents() {
        return 0;
    }

}