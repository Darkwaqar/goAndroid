package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asifrizvi on 2/1/2020.
 */

public class Tags implements Parcelable {

    public final static Parcelable.Creator<Tags> CREATOR = new Creator<Tags>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Tags createFromParcel(Parcel in) {
            return new Tags(in);
        }

        public Tags[] newArray(int size) {
            return (new Tags[size]);
        }

    };
    @SerializedName("TotalTags")
    @Expose
    private Integer totalTags;
    @SerializedName("Tags")
    @Expose
    private List<Tag> tags = null;

    protected Tags(Parcel in) {
        this.totalTags = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.tags, (Tag.class.getClassLoader()));
    }

    public Tags() {
    }

    public Integer getTotalTags() {
        return totalTags;
    }

    public void setTotalTags(Integer totalTags) {
        this.totalTags = totalTags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalTags);
        dest.writeList(tags);
    }

    public int describeContents() {
        return 0;
    }

}