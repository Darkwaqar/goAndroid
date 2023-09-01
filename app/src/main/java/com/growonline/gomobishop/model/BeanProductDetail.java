package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BeanProductDetail implements Parcelable {

    @SerializedName("DefaultPictureZoomEnabled")
    @Expose
    private Boolean defaultPictureZoomEnabled;
    @SerializedName("DefaultPictureModel")
    @Expose
    private DefaultPictureModel defaultPictureModel;
    @SerializedName("PictureModels")
    @Expose
    private List<DefaultPictureModel> pictureModels = null;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ShortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("FullDescription")
    @Expose
    private String fullDescription;
    @SerializedName("MetaKeywords")
    @Expose
    private String metaKeywords;
    @SerializedName("MetaDescription")
    @Expose
    private String metaDescription;
    @SerializedName("MetaTitle")
    @Expose
    private Object metaTitle;
    @SerializedName("SeName")
    @Expose
    private String seName;
    @SerializedName("ProductType")
    @Expose
    private Integer productType;
    @SerializedName("ShowSku")
    @Expose
    private Boolean showSku;
    @SerializedName("Sku")
    @Expose
    private String sku;
    @SerializedName("ShowManufacturerPartNumber")
    @Expose
    private Boolean showManufacturerPartNumber;
    @SerializedName("ManufacturerPartNumber")
    @Expose
    private Object manufacturerPartNumber;
    @SerializedName("ShowGtin")
    @Expose
    private Boolean showGtin;
    @SerializedName("Gtin")
    @Expose
    private Object gtin;
    @SerializedName("ShowVendor")
    @Expose
    private Boolean showVendor;
    @SerializedName("VendorModel")
    @Expose
    private Vendor vendorModel;
    @SerializedName("HasSampleDownload")
    @Expose
    private Boolean hasSampleDownload;
    @SerializedName("GiftCard")
    @Expose
    private GiftCard giftCard;
    @SerializedName("IsShipEnabled")
    @Expose
    private Boolean isShipEnabled;
    @SerializedName("IsFreeShipping")
    @Expose
    private Boolean isFreeShipping;
    @SerializedName("FreeShippingNotificationEnabled")
    @Expose
    private Boolean freeShippingNotificationEnabled;
    @SerializedName("DeliveryDate")
    @Expose
    private String deliveryDate;
    @SerializedName("IsRental")
    @Expose
    private Boolean isRental;
    @SerializedName("RentalStartDate")
    @Expose
    private Object rentalStartDate;
    @SerializedName("RentalEndDate")
    @Expose
    private Object rentalEndDate;
    @SerializedName("ManageInventoryMethod")
    @Expose
    private Integer manageInventoryMethod;
    @SerializedName("StockAvailability")
    @Expose
    private String stockAvailability;
    @SerializedName("DisplayBackInStockSubscription")
    @Expose
    private Boolean displayBackInStockSubscription;
    @SerializedName("EmailAFriendEnabled")
    @Expose
    private Boolean emailAFriendEnabled;
    @SerializedName("CompareProductsEnabled")
    @Expose
    private Boolean compareProductsEnabled;
    @SerializedName("PageShareCode")
    @Expose
    private String pageShareCode;
    @SerializedName("ProductPrice")
    @Expose
    private ProductPrice productPrice;
    @SerializedName("AddToCart")
    @Expose
    private AddToCart addToCart;
    @SerializedName("ProductTags")
    @Expose
    private List<ProductTag> productTags = null;
    @SerializedName("ProductAttributes")
    @Expose
    private List<ProductAttribute> productAttributes = null;
    @SerializedName("ProductSpecifications")
    @Expose
    private List<ProductSpecification> productSpecifications = null;
    @SerializedName("ProductManufacturers")
    @Expose
    private List<Object> productManufacturers = null;
    @SerializedName("ProductReviewOverview")
    @Expose
    private ProductReviewOverview productReviewOverview;
    @SerializedName("TierPrices")
    @Expose
    private List<Object> tierPrices = null;
    @SerializedName("AssociatedProducts")
    @Expose
    private List<BeanProductDetail> associatedProducts = null;
    @SerializedName("DisplayDiscontinuedMessage")
    @Expose
    private Boolean displayDiscontinuedMessage;
    @SerializedName("CurrentStoreName")
    @Expose
    private String currentStoreName;
    @SerializedName("Id")
    @Expose
    private Integer id;
    public final static Parcelable.Creator<BeanProductDetail> CREATOR = new Creator<BeanProductDetail>() {


        @SuppressWarnings({
                "unchecked"
        })
        public BeanProductDetail createFromParcel(Parcel in) {
            return new BeanProductDetail(in);
        }

        public BeanProductDetail[] newArray(int size) {
            return (new BeanProductDetail[size]);
        }

    };

    protected BeanProductDetail(Parcel in) {
        this.defaultPictureZoomEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.defaultPictureModel = ((DefaultPictureModel) in.readValue((DefaultPictureModel.class.getClassLoader())));
        in.readList(this.pictureModels, (DefaultPictureModel.class.getClassLoader()));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.shortDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.fullDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.metaKeywords = ((String) in.readValue((String.class.getClassLoader())));
        this.metaDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.metaTitle = ((Object) in.readValue((Object.class.getClassLoader())));
        this.seName = ((String) in.readValue((String.class.getClassLoader())));
        this.productType = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.showSku = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.sku = ((String) in.readValue((String.class.getClassLoader())));
        this.showManufacturerPartNumber = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.manufacturerPartNumber = ((Object) in.readValue((Object.class.getClassLoader())));
        this.showGtin = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.gtin = ((Object) in.readValue((Object.class.getClassLoader())));
        this.showVendor = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.vendorModel = ((Vendor) in.readValue((Vendor.class.getClassLoader())));
        this.hasSampleDownload = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.giftCard = ((GiftCard) in.readValue((GiftCard.class.getClassLoader())));
        this.isShipEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isFreeShipping = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.freeShippingNotificationEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.deliveryDate = ((String) in.readValue((String.class.getClassLoader())));
        this.isRental = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.rentalStartDate = ((Object) in.readValue((Object.class.getClassLoader())));
        this.rentalEndDate = ((Object) in.readValue((Object.class.getClassLoader())));
        this.manageInventoryMethod = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.stockAvailability = ((String) in.readValue((String.class.getClassLoader())));
        this.displayBackInStockSubscription = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.emailAFriendEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.compareProductsEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.pageShareCode = ((String) in.readValue((String.class.getClassLoader())));
        this.productPrice = ((ProductPrice) in.readValue((ProductPrice.class.getClassLoader())));
        this.addToCart = ((AddToCart) in.readValue((AddToCart.class.getClassLoader())));
        in.readList(this.productTags, (ProductTag.class.getClassLoader()));
        in.readList(this.productAttributes, (ProductAttribute.class.getClassLoader()));
        in.readList(this.productSpecifications, (ProductSpecification.class.getClassLoader()));
        in.readList(this.productManufacturers, (java.lang.Object.class.getClassLoader()));
        this.productReviewOverview = ((ProductReviewOverview) in.readValue((ProductReviewOverview.class.getClassLoader())));
        in.readList(this.tierPrices, (java.lang.Object.class.getClassLoader()));
        in.readList(this.associatedProducts, (BeanProductDetail.class.getClassLoader()));
        this.displayDiscontinuedMessage = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.currentStoreName = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public BeanProductDetail() {
    }

    public Boolean getDefaultPictureZoomEnabled() {
        return defaultPictureZoomEnabled;
    }

    public void setDefaultPictureZoomEnabled(Boolean defaultPictureZoomEnabled) {
        this.defaultPictureZoomEnabled = defaultPictureZoomEnabled;
    }

    public DefaultPictureModel getDefaultPictureModel() {
        return defaultPictureModel;
    }

    public void setDefaultPictureModel(DefaultPictureModel defaultPictureModel) {
        this.defaultPictureModel = defaultPictureModel;
    }

    public List<DefaultPictureModel> getPictureModels() {
        return pictureModels;
    }

    public void setPictureModels(List<DefaultPictureModel> pictureModels) {
        this.pictureModels = pictureModels;
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

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
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

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Boolean getShowSku() {
        return showSku;
    }

    public void setShowSku(Boolean showSku) {
        this.showSku = showSku;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean getShowManufacturerPartNumber() {
        return showManufacturerPartNumber;
    }

    public void setShowManufacturerPartNumber(Boolean showManufacturerPartNumber) {
        this.showManufacturerPartNumber = showManufacturerPartNumber;
    }

    public Object getManufacturerPartNumber() {
        return manufacturerPartNumber;
    }

    public void setManufacturerPartNumber(Object manufacturerPartNumber) {
        this.manufacturerPartNumber = manufacturerPartNumber;
    }

    public Boolean getShowGtin() {
        return showGtin;
    }

    public void setShowGtin(Boolean showGtin) {
        this.showGtin = showGtin;
    }

    public Object getGtin() {
        return gtin;
    }

    public void setGtin(Object gtin) {
        this.gtin = gtin;
    }

    public Boolean getShowVendor() {
        return showVendor;
    }

    public void setShowVendor(Boolean showVendor) {
        this.showVendor = showVendor;
    }

    public Vendor getVendorModel() {
        return vendorModel;
    }

    public void setVendorModel(Vendor vendorModel) {
        this.vendorModel = vendorModel;
    }

    public Boolean getHasSampleDownload() {
        return hasSampleDownload;
    }

    public void setHasSampleDownload(Boolean hasSampleDownload) {
        this.hasSampleDownload = hasSampleDownload;
    }

    public GiftCard getGiftCard() {
        return giftCard;
    }

    public void setGiftCard(GiftCard giftCard) {
        this.giftCard = giftCard;
    }

    public Boolean getIsShipEnabled() {
        return isShipEnabled;
    }

    public void setIsShipEnabled(Boolean isShipEnabled) {
        this.isShipEnabled = isShipEnabled;
    }

    public Boolean getIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(Boolean isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public Boolean getFreeShippingNotificationEnabled() {
        return freeShippingNotificationEnabled;
    }

    public void setFreeShippingNotificationEnabled(Boolean freeShippingNotificationEnabled) {
        this.freeShippingNotificationEnabled = freeShippingNotificationEnabled;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Boolean getIsRental() {
        return isRental;
    }

    public void setIsRental(Boolean isRental) {
        this.isRental = isRental;
    }

    public Object getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(Object rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public Object getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(Object rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public Integer getManageInventoryMethod() {
        return manageInventoryMethod;
    }

    public void setManageInventoryMethod(Integer manageInventoryMethod) {
        this.manageInventoryMethod = manageInventoryMethod;
    }

    public String getStockAvailability() {
        return stockAvailability;
    }

    public void setStockAvailability(String stockAvailability) {
        this.stockAvailability = stockAvailability;
    }

    public Boolean getDisplayBackInStockSubscription() {
        return displayBackInStockSubscription;
    }

    public void setDisplayBackInStockSubscription(Boolean displayBackInStockSubscription) {
        this.displayBackInStockSubscription = displayBackInStockSubscription;
    }

    public Boolean getEmailAFriendEnabled() {
        return emailAFriendEnabled;
    }

    public void setEmailAFriendEnabled(Boolean emailAFriendEnabled) {
        this.emailAFriendEnabled = emailAFriendEnabled;
    }

    public Boolean getCompareProductsEnabled() {
        return compareProductsEnabled;
    }

    public void setCompareProductsEnabled(Boolean compareProductsEnabled) {
        this.compareProductsEnabled = compareProductsEnabled;
    }

    public String getPageShareCode() {
        return pageShareCode;
    }

    public void setPageShareCode(String pageShareCode) {
        this.pageShareCode = pageShareCode;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    public AddToCart getAddToCart() {
        return addToCart;
    }

    public void setAddToCart(AddToCart addToCart) {
        this.addToCart = addToCart;
    }


    public List<ProductTag> getProductTags() {
        return productTags;
    }

    public void setProductTags(List<ProductTag> productTags) {
        this.productTags = productTags;
    }

    public List<ProductAttribute> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(List<ProductAttribute> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public List<ProductSpecification> getProductSpecifications() {
        return productSpecifications;
    }

    public void setProductSpecifications(List<ProductSpecification> productSpecifications) {
        this.productSpecifications = productSpecifications;
    }

    public List<Object> getProductManufacturers() {
        return productManufacturers;
    }

    public void setProductManufacturers(List<Object> productManufacturers) {
        this.productManufacturers = productManufacturers;
    }

    public ProductReviewOverview getProductReviewOverview() {
        return productReviewOverview;
    }

    public void setProductReviewOverview(ProductReviewOverview productReviewOverview) {
        this.productReviewOverview = productReviewOverview;
    }

    public List<Object> getTierPrices() {
        return tierPrices;
    }

    public void setTierPrices(List<Object> tierPrices) {
        this.tierPrices = tierPrices;
    }

    public List<BeanProductDetail> getAssociatedProducts() {
        return associatedProducts;
    }

    public void setAssociatedProducts(List<BeanProductDetail> associatedProducts) {
        this.associatedProducts = associatedProducts;
    }

    public Boolean getDisplayDiscontinuedMessage() {
        return displayDiscontinuedMessage;
    }

    public void setDisplayDiscontinuedMessage(Boolean displayDiscontinuedMessage) {
        this.displayDiscontinuedMessage = displayDiscontinuedMessage;
    }

    public String getCurrentStoreName() {
        return currentStoreName;
    }

    public void setCurrentStoreName(String currentStoreName) {
        this.currentStoreName = currentStoreName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(defaultPictureZoomEnabled);
        dest.writeValue(defaultPictureModel);
        dest.writeList(pictureModels);
        dest.writeValue(name);
        dest.writeValue(shortDescription);
        dest.writeValue(fullDescription);
        dest.writeValue(metaKeywords);
        dest.writeValue(metaDescription);
        dest.writeValue(metaTitle);
        dest.writeValue(seName);
        dest.writeValue(productType);
        dest.writeValue(showSku);
        dest.writeValue(sku);
        dest.writeValue(showManufacturerPartNumber);
        dest.writeValue(manufacturerPartNumber);
        dest.writeValue(showGtin);
        dest.writeValue(gtin);
        dest.writeValue(showVendor);
        dest.writeValue(vendorModel);
        dest.writeValue(hasSampleDownload);
        dest.writeValue(giftCard);
        dest.writeValue(isShipEnabled);
        dest.writeValue(isFreeShipping);
        dest.writeValue(freeShippingNotificationEnabled);
        dest.writeValue(deliveryDate);
        dest.writeValue(isRental);
        dest.writeValue(rentalStartDate);
        dest.writeValue(rentalEndDate);
        dest.writeValue(manageInventoryMethod);
        dest.writeValue(stockAvailability);
        dest.writeValue(displayBackInStockSubscription);
        dest.writeValue(emailAFriendEnabled);
        dest.writeValue(compareProductsEnabled);
        dest.writeValue(pageShareCode);
        dest.writeValue(productPrice);
        dest.writeValue(addToCart);
        dest.writeList(productTags);
        dest.writeList(productAttributes);
        dest.writeList(productSpecifications);
        dest.writeList(productManufacturers);
        dest.writeValue(productReviewOverview);
        dest.writeList(tierPrices);
        dest.writeList(associatedProducts);
        dest.writeValue(displayDiscontinuedMessage);
        dest.writeValue(currentStoreName);
        dest.writeValue(id);

    }

    public int describeContents() {
        return 0;
    }

}