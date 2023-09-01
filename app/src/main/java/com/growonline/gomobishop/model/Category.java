package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Category implements Parcelable {

    public final static Parcelable.Creator<Category> CREATOR = new Creator<Category>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return (new Category[size]);
        }

    };
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ParentId")
    @Expose
    private int parentId;
    @SerializedName("PictureModel")
    @Expose
    private DefaultPictureModel pictureModel;
    @SerializedName("IsVisible")
    @Expose
    private Boolean isVisible = true;
    @SerializedName("HasSubCategory")
    @Expose
    private Boolean hasSubCategory;
    @SerializedName("SubCategories")
    @Expose
    private List<Category> subCategories = new ArrayList<>();
    @SerializedName("Id")
    @Expose
    private Integer id;

    protected Category(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.parentId = ((int) in.readValue((int.class.getClassLoader())));
        this.pictureModel = ((DefaultPictureModel) in.readValue((DefaultPictureModel.class.getClassLoader())));
        this.isVisible = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.hasSubCategory = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.subCategories, (Category.class.getClassLoader()));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int seName) {
        this.parentId = seName;
    }

    public DefaultPictureModel getPictureModel() {
        return pictureModel;
    }

    public void setPictureModel(DefaultPictureModel pictureModel) {
        this.pictureModel = pictureModel;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean getHasSubCategory() {
        return hasSubCategory;
    }

    public void setHasSubCategory(Boolean hasSubCategory) {
        this.hasSubCategory = hasSubCategory;
    }


    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(parentId);
        dest.writeValue(pictureModel);
        dest.writeValue(isVisible);
        dest.writeValue(hasSubCategory);
        dest.writeList(subCategories);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}