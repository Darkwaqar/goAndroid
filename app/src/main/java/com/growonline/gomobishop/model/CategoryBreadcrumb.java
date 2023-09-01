package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryBreadcrumb implements Parcelable {

    public final static Parcelable.Creator<CategoryBreadcrumb> CREATOR = new Creator<CategoryBreadcrumb>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CategoryBreadcrumb createFromParcel(Parcel in) {
            return new CategoryBreadcrumb(in);
        }

        public CategoryBreadcrumb[] newArray(int size) {
            return (new CategoryBreadcrumb[size]);
        }

    };
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private Object description;
    @SerializedName("MetaKeywords")
    @Expose
    private Object metaKeywords;
    @SerializedName("MetaDescription")
    @Expose
    private Object metaDescription;
    @SerializedName("MetaTitle")
    @Expose
    private Object metaTitle;
    @SerializedName("SeName")
    @Expose
    private String seName;
    @SerializedName("PictureModel")
    @Expose
    private DefaultPictureModel pictureModel;
    @SerializedName("AlternativePictureModel")
    @Expose
    private DefaultPictureModel alternativePictureModel;
    @SerializedName("DisplayCategoryBreadcrumb")
    @Expose
    private Boolean displayCategoryBreadcrumb;
    @SerializedName("CategoryBreadcrumb")
    @Expose
    private List<Object> categoryBreadcrumb = null;
    @SerializedName("SubCategories")
    @Expose
    private List<Object> subCategories = null;
    @SerializedName("Collection")
    @Expose
    private Boolean collection;
    @SerializedName("CollectionName")
    @Expose
    private Object collectionName;
    @SerializedName("CollectionDiscription")
    @Expose
    private Object collectionDiscription;
    @SerializedName("CollectionPricture")
    @Expose
    private Object collectionPricture;
    @SerializedName("FeaturedProducts")
    @Expose
    private List<Object> featuredProducts = null;
    @SerializedName("Products")
    @Expose
    private List<Object> products = null;
    @SerializedName("SelectedStoreIds")
    @Expose
    private Object selectedStoreIds;
    @SerializedName("Id")
    @Expose
    private Integer id;

    protected CategoryBreadcrumb(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((Object) in.readValue((Object.class.getClassLoader())));
        this.metaKeywords = ((Object) in.readValue((Object.class.getClassLoader())));
        this.metaDescription = ((Object) in.readValue((Object.class.getClassLoader())));
        this.metaTitle = ((Object) in.readValue((Object.class.getClassLoader())));
        this.seName = ((String) in.readValue((String.class.getClassLoader())));
        this.pictureModel = ((DefaultPictureModel) in.readValue((DefaultPictureModel.class.getClassLoader())));
        this.alternativePictureModel = ((DefaultPictureModel) in.readValue((DefaultPictureModel.class.getClassLoader())));
        this.displayCategoryBreadcrumb = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.categoryBreadcrumb, (java.lang.Object.class.getClassLoader()));
        in.readList(this.subCategories, (java.lang.Object.class.getClassLoader()));
        this.collection = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.collectionName = ((Object) in.readValue((Object.class.getClassLoader())));
        this.collectionDiscription = ((Object) in.readValue((Object.class.getClassLoader())));
        this.collectionPricture = ((Object) in.readValue((Object.class.getClassLoader())));
        in.readList(this.featuredProducts, (java.lang.Object.class.getClassLoader()));
        in.readList(this.products, (java.lang.Object.class.getClassLoader()));
        this.selectedStoreIds = ((Object) in.readValue((Object.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public CategoryBreadcrumb() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(Object metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public Object getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(Object metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Object getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(Object metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getSeName() {
        return seName;
    }

    public void setSeName(String seName) {
        this.seName = seName;
    }

    public DefaultPictureModel getPictureModel() {
        return pictureModel;
    }

    public void setPictureModel(DefaultPictureModel pictureModel) {
        this.pictureModel = pictureModel;
    }

    public DefaultPictureModel getAlternativePictureModel() {
        return alternativePictureModel;
    }

    public void setAlternativePictureModel(DefaultPictureModel alternativePictureModel) {
        this.alternativePictureModel = alternativePictureModel;
    }

    public Boolean getDisplayCategoryBreadcrumb() {
        return displayCategoryBreadcrumb;
    }

    public void setDisplayCategoryBreadcrumb(Boolean displayCategoryBreadcrumb) {
        this.displayCategoryBreadcrumb = displayCategoryBreadcrumb;
    }

    public List<Object> getCategoryBreadcrumb() {
        return categoryBreadcrumb;
    }

    public void setCategoryBreadcrumb(List<Object> categoryBreadcrumb) {
        this.categoryBreadcrumb = categoryBreadcrumb;
    }

    public List<Object> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Object> subCategories) {
        this.subCategories = subCategories;
    }

    public Boolean getCollection() {
        return collection;
    }

    public void setCollection(Boolean collection) {
        this.collection = collection;
    }

    public Object getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(Object collectionName) {
        this.collectionName = collectionName;
    }

    public Object getCollectionDiscription() {
        return collectionDiscription;
    }

    public void setCollectionDiscription(Object collectionDiscription) {
        this.collectionDiscription = collectionDiscription;
    }

    public Object getCollectionPricture() {
        return collectionPricture;
    }

    public void setCollectionPricture(Object collectionPricture) {
        this.collectionPricture = collectionPricture;
    }

    public List<Object> getFeaturedProducts() {
        return featuredProducts;
    }

    public void setFeaturedProducts(List<Object> featuredProducts) {
        this.featuredProducts = featuredProducts;
    }

    public List<Object> getProducts() {
        return products;
    }

    public void setProducts(List<Object> products) {
        this.products = products;
    }

    public Object getSelectedStoreIds() {
        return selectedStoreIds;
    }

    public void setSelectedStoreIds(Object selectedStoreIds) {
        this.selectedStoreIds = selectedStoreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(description);
        dest.writeValue(metaKeywords);
        dest.writeValue(metaDescription);
        dest.writeValue(metaTitle);
        dest.writeValue(seName);
        dest.writeValue(pictureModel);
        dest.writeValue(alternativePictureModel);
        dest.writeValue(displayCategoryBreadcrumb);
        dest.writeList(categoryBreadcrumb);
        dest.writeList(subCategories);
        dest.writeValue(collection);
        dest.writeValue(collectionName);
        dest.writeValue(collectionDiscription);
        dest.writeValue(collectionPricture);
        dest.writeList(featuredProducts);
        dest.writeList(products);
        dest.writeValue(selectedStoreIds);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}
