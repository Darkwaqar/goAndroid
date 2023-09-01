package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryListModel implements Parcelable {

    public final static Parcelable.Creator<CategoryListModel> CREATOR = new Creator<CategoryListModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CategoryListModel createFromParcel(Parcel in) {
            return new CategoryListModel(in);
        }

        public CategoryListModel[] newArray(int size) {
            return (new CategoryListModel[size]);
        }

    };
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private Object description;
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
    @SerializedName("PagingFilteringContext")
    @Expose
    private PagingFilteringContext pagingFilteringContext;
    @SerializedName("DisplayCategoryBreadcrumb")
    @Expose
    private Boolean displayCategoryBreadcrumb;
    @SerializedName("CategoryBreadcrumb")
    @Expose
    private List<CategoryBreadcrumb> categoryBreadcrumb = null;
    @SerializedName("SubCategories")
    @Expose
    private List<SubCategory> subCategories = null;
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
    private List<Product> products = null;
    @SerializedName("SelectedStoreIds")
    @Expose
    private Object selectedStoreIds;
    @SerializedName("Id")
    @Expose
    private Integer id;

    protected CategoryListModel(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((Object) in.readValue((Object.class.getClassLoader())));
        this.metaTitle = ((Object) in.readValue((Object.class.getClassLoader())));
        this.seName = ((String) in.readValue((String.class.getClassLoader())));
        this.pictureModel = ((DefaultPictureModel) in.readValue((DefaultPictureModel.class.getClassLoader())));
        this.alternativePictureModel = ((DefaultPictureModel) in.readValue((DefaultPictureModel.class.getClassLoader())));
        this.pagingFilteringContext = ((PagingFilteringContext) in.readValue((PagingFilteringContext.class.getClassLoader())));
        this.displayCategoryBreadcrumb = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.categoryBreadcrumb, (CategoryBreadcrumb.class.getClassLoader()));
        in.readList(this.subCategories, (SubCategory.class.getClassLoader()));
        this.collection = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.collectionName = ((Object) in.readValue((Object.class.getClassLoader())));
        this.collectionDiscription = ((Object) in.readValue((Object.class.getClassLoader())));
        this.collectionPricture = ((Object) in.readValue((Object.class.getClassLoader())));
        in.readList(this.featuredProducts, (java.lang.Object.class.getClassLoader()));
        in.readList(this.products, (Product.class.getClassLoader()));
        this.selectedStoreIds = ((Object) in.readValue((Object.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public CategoryListModel() {
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

    public PagingFilteringContext getPagingFilteringContext() {
        return pagingFilteringContext;
    }

    public void setPagingFilteringContext(PagingFilteringContext pagingFilteringContext) {
        this.pagingFilteringContext = pagingFilteringContext;
    }

    public Boolean getDisplayCategoryBreadcrumb() {
        return displayCategoryBreadcrumb;
    }

    public void setDisplayCategoryBreadcrumb(Boolean displayCategoryBreadcrumb) {
        this.displayCategoryBreadcrumb = displayCategoryBreadcrumb;
    }

    public List<CategoryBreadcrumb> getCategoryBreadcrumb() {
        return categoryBreadcrumb;
    }

    public void setCategoryBreadcrumb(List<CategoryBreadcrumb> categoryBreadcrumb) {
        this.categoryBreadcrumb = categoryBreadcrumb;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
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
        dest.writeValue(metaTitle);
        dest.writeValue(seName);
        dest.writeValue(pictureModel);
        dest.writeValue(alternativePictureModel);
        dest.writeValue(pagingFilteringContext);
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