package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategory implements Parcelable {

    public final static Parcelable.Creator<SubCategory> CREATOR = new Creator<SubCategory>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SubCategory createFromParcel(Parcel in) {
            return new SubCategory(in);
        }

        public SubCategory[] newArray(int size) {
            return (new SubCategory[size]);
        }

    };
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("SeName")
    @Expose
    private String seName;
    @SerializedName("Description")
    @Expose
    private Object description;
    @SerializedName("PictureModel")
    @Expose
    private DefaultPictureModel pictureModel;
    @SerializedName("Id")
    @Expose
    private Integer id;

    protected SubCategory(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.seName = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((Object) in.readValue((Object.class.getClassLoader())));
        this.pictureModel = ((DefaultPictureModel) in.readValue((DefaultPictureModel.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public SubCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeName() {
        return seName;
    }

    public void setSeName(String seName) {
        this.seName = seName;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public DefaultPictureModel getPictureModel() {
        return pictureModel;
    }

    public void setPictureModel(DefaultPictureModel pictureModel) {
        this.pictureModel = pictureModel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(seName);
        dest.writeValue(description);
        dest.writeValue(pictureModel);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}
