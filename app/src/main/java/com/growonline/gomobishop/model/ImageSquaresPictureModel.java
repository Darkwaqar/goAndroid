package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 3/28/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageSquaresPictureModel implements Parcelable
{

    @SerializedName("ImageUrl")
    @Expose
    private Object imageUrl;
    @SerializedName("ThumbImageUrl")
    @Expose
    private Object thumbImageUrl;
    @SerializedName("FullSizeImageUrl")
    @Expose
    private Object fullSizeImageUrl;
    @SerializedName("Title")
    @Expose
    private Object title;
    @SerializedName("AlternateText")
    @Expose
    private Object alternateText;
    @SerializedName("CustomProperties")

    public final static Creator<ImageSquaresPictureModel> CREATOR = new Creator<ImageSquaresPictureModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ImageSquaresPictureModel createFromParcel(Parcel in) {
            return new ImageSquaresPictureModel(in);
        }

        public ImageSquaresPictureModel[] newArray(int size) {
            return (new ImageSquaresPictureModel[size]);
        }

    }
            ;

    protected ImageSquaresPictureModel(Parcel in) {
        this.imageUrl = ((Object) in.readValue((Object.class.getClassLoader())));
        this.thumbImageUrl = ((Object) in.readValue((Object.class.getClassLoader())));
        this.fullSizeImageUrl = ((Object) in.readValue((Object.class.getClassLoader())));
        this.title = ((Object) in.readValue((Object.class.getClassLoader())));
        this.alternateText = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public ImageSquaresPictureModel() {
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getThumbImageUrl() {
        return thumbImageUrl;
    }

    public void setThumbImageUrl(Object thumbImageUrl) {
        this.thumbImageUrl = thumbImageUrl;
    }

    public Object getFullSizeImageUrl() {
        return fullSizeImageUrl;
    }

    public void setFullSizeImageUrl(Object fullSizeImageUrl) {
        this.fullSizeImageUrl = fullSizeImageUrl;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getAlternateText() {
        return alternateText;
    }

    public void setAlternateText(Object alternateText) {
        this.alternateText = alternateText;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(imageUrl);
        dest.writeValue(thumbImageUrl);
        dest.writeValue(fullSizeImageUrl);
        dest.writeValue(title);
        dest.writeValue(alternateText);
    }

    public int describeContents() {
        return 0;
    }

}