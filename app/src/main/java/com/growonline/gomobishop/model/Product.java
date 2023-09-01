package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product implements Parcelable {

    public final static Parcelable.Creator<Product> CREATOR = new Creator<Product>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return (new Product[size]);
        }

    };
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ShortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("FullDescription")
    @Expose
    private String fullDescription;
    @SerializedName("SeName")
    @Expose
    private String seName;
    @SerializedName("Sku")
    @Expose
    private String sku;
    @SerializedName("ProductType")
    @Expose
    private Integer productType;
    @SerializedName("MarkAsNew")
    @Expose
    private Boolean markAsNew;
    @SerializedName("MarkAsSales")
    @Expose
    private Boolean markAsSales;
    @SerializedName("OutOfStock")
    @Expose
    private Boolean outOfStock;
    @SerializedName("ProductPrice")
    @Expose
    private ProductPrice productPrice;
    @SerializedName("DefaultPictureModel")
    @Expose
    private DefaultPictureModel defaultPictureModel;
    @SerializedName("SpecificationAttributeModels")
    @Expose
    private List<Object> specificationAttributeModels = null;
    @SerializedName("ReviewOverviewModel")
    @Expose
    private ReviewOverviewModel reviewOverviewModel;
    @SerializedName("Vendor")
    @Expose
    private Vendor vendor;
    @SerializedName("Id")
    @Expose
    private Integer id;

    protected Product(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.shortDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.fullDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.seName = ((String) in.readValue((String.class.getClassLoader())));
        this.sku = ((String) in.readValue((String.class.getClassLoader())));
        this.productType = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.markAsNew = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.markAsSales = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.outOfStock = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.productPrice = ((ProductPrice) in.readValue((ProductPrice.class.getClassLoader())));
        this.defaultPictureModel = ((DefaultPictureModel) in.readValue((DefaultPictureModel.class.getClassLoader())));
        in.readList(this.specificationAttributeModels, (java.lang.Object.class.getClassLoader()));
        this.reviewOverviewModel = ((ReviewOverviewModel) in.readValue((ReviewOverviewModel.class.getClassLoader())));
        this.vendor = ((Vendor) in.readValue((Vendor.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }


    public Product(int id, int vendorId, String title, String thumbUrl, String formattedPrice,
                   String formattedOldPrice, String formattedSpecialPrice, String formattedPriceWithDiscount,
                   String currencyCode, String vendorImage, String vendorName, float price, float oldPrice, float specialPrice, float priceWithDiscount) {
        this.id = id;
        if (getVendor() == null) vendor = new Vendor();
        this.getVendor().setId(vendorId);
        this.getVendor().setMarketPlaceLogoURL(vendorImage);
        this.getVendor().setName(vendorName);
        this.name = title;
        if (getDefaultPictureModel() == null) defaultPictureModel = new DefaultPictureModel();
        this.getDefaultPictureModel().setImageUrl(thumbUrl);
        if (getProductPrice() == null) productPrice = new ProductPrice();
        this.getProductPrice().setPrice(formattedPrice);
        this.getProductPrice().setOldPrice(formattedOldPrice);
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getSeName() {
        return seName;
    }

    public void setSeName(String seName) {
        this.seName = seName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Boolean getMarkAsNew() {
        return markAsNew;
    }

    public void setMarkAsNew(Boolean markAsNew) {
        this.markAsNew = markAsNew;
    }

    public Boolean getMarkAsSales() {
        return markAsSales;
    }

    public void setMarkAsSales(Boolean markAsSales) {
        this.markAsSales = markAsSales;
    }

    public Boolean getOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(Boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    public DefaultPictureModel getDefaultPictureModel() {
        return defaultPictureModel;
    }

    public void setDefaultPictureModel(DefaultPictureModel defaultPictureModel) {
        this.defaultPictureModel = defaultPictureModel;
    }

    public List<Object> getSpecificationAttributeModels() {
        return specificationAttributeModels;
    }

    public void setSpecificationAttributeModels(List<Object> specificationAttributeModels) {
        this.specificationAttributeModels = specificationAttributeModels;
    }

    public ReviewOverviewModel getReviewOverviewModel() {
        return reviewOverviewModel;
    }

    public void setReviewOverviewModel(ReviewOverviewModel reviewOverviewModel) {
        this.reviewOverviewModel = reviewOverviewModel;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(shortDescription);
        dest.writeValue(fullDescription);
        dest.writeValue(seName);
        dest.writeValue(sku);
        dest.writeValue(productType);
        dest.writeValue(markAsNew);
        dest.writeValue(markAsSales);
        dest.writeValue(outOfStock);
        dest.writeValue(productPrice);
        dest.writeValue(defaultPictureModel);
        dest.writeList(specificationAttributeModels);
        dest.writeValue(reviewOverviewModel);
        dest.writeValue(vendor);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}
