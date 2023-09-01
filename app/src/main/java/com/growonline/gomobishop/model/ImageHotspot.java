package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Basit on 5/13/2017.
 */

public class ImageHotspot implements Parcelable {

    private String name, description;
    private int x, y, viewId;

    public ImageHotspot() {
    }

    public ImageHotspot(Parcel parcel) {
        name = parcel.readString();
        description = parcel.readString();
        x = parcel.readInt();
        y = parcel.readInt();
        viewId = parcel.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeInt(viewId);
    }

    public static final Parcelable.Creator<ImageHotspot> CREATOR = new Creator<ImageHotspot>() {

        @Override
        public ImageHotspot createFromParcel(Parcel source) {
            return new ImageHotspot(source);
        }

        @Override
        public ImageHotspot[] newArray(int size) {
            return new ImageHotspot[size];
        }
    };

}
