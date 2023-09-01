package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultPictureModel implements Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("ThumbImageUrl")
    @Expose
    private String thumbImageUrl;
    @SerializedName("FullSizeImageUrl")
    @Expose
    private String fullSizeImageUrl;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("AlternateText")
    @Expose
    private String alternateText;

    public final static Parcelable.Creator<DefaultPictureModel> CREATOR = new Creator<DefaultPictureModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DefaultPictureModel createFromParcel(Parcel in) {
            return new DefaultPictureModel(in);
        }

        public DefaultPictureModel[] newArray(int size) {
            return (new DefaultPictureModel[size]);
        }

    };

    protected DefaultPictureModel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbImageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.fullSizeImageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.alternateText = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DefaultPictureModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbImageUrl() {
        return thumbImageUrl;
    }

    public void setThumbImageUrl(String thumbImageUrl) {
        this.thumbImageUrl = thumbImageUrl;
    }

    public String getFullSizeImageUrl() {
        return fullSizeImageUrl;
    }

    public void setFullSizeImageUrl(String fullSizeImageUrl) {
        this.fullSizeImageUrl = fullSizeImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlternateText() {
        return alternateText;
    }

    public void setAlternateText(String alternateText) {
        this.alternateText = alternateText;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
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
