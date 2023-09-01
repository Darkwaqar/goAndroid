package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Vendor implements Parcelable {

    public final static Parcelable.Creator<Vendor> CREATOR = new Creator<Vendor>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Vendor createFromParcel(Parcel in) {
            return new Vendor(in);
        }

        public Vendor[] newArray(int size) {
            return (new Vendor[size]);
        }

    };
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("AboutUsPageURL")
    @Expose
    private String aboutUsPageURL;
    @SerializedName("AppointmentPictureUrl")
    @Expose
    private String appointmentPictureUrl;
    @SerializedName("LogoUrl")
    @Expose
    private String logoUrl;
    @SerializedName("CoverPictureURL")
    @Expose
    private String coverPictureURL;
    @SerializedName("MarketPlaceLogoURL")
    @Expose
    private String marketPlaceLogoURL;
    @SerializedName("ContactDescription")
    @Expose
    private String contactDescription;
    @SerializedName("ContactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("AboutUsPageUrl")
    @Expose
    private String aboutUsPageUrl;
    @SerializedName("BlogUrl")
    @Expose
    private String blogUrl;
    @SerializedName("FacebookPageURL")
    @Expose
    private String facebookPageURL;
    @SerializedName("LinkedInPageURL")
    @Expose
    private String linkedInPageURL;
    @SerializedName("ThemeColor")
    @Expose
    private String themeColor;
    @SerializedName("MobileAppSetting")
    @Expose
    private MobileAppSetting mobileAppSetting;
    @SerializedName("SocialLinks")
    @Expose
    private SocialLinks socialLinks;
    @SerializedName("Banners")
    @Expose
    private List<Banner> banners = new ArrayList<>();
    @SerializedName("Rating")
    @Expose
    private Rating rating;
    @SerializedName("NumberOfCartItems")
    @Expose
    private Integer numberOfCartItems;
    @SerializedName("NumberOfWishlistItems")
    @Expose
    private Integer numberOfWishlistItems;
    @SerializedName("TermAndConditionsUrl")
    @Expose
    private String termAndConditionsUrl;
    @SerializedName("ReturnAndRefundUrl")
    @Expose
    private String returnAndRefundUrl;
    @SerializedName("DeliveryPolicyUrl")
    @Expose
    private String deliveryPolicyUrl;
    @SerializedName("ShippingInformationUrl")
    @Expose
    private String shippingInformationUrl;
    @SerializedName("ReturnAndExchangePolicyUrl")
    @Expose
    private String returnAndExchangePolicyUrl;
    @SerializedName("PaymentSecurityUrl")
    @Expose
    private String paymentSecurityUrl;
    @SerializedName("FaqUrl")
    @Expose
    private String faqUrl;
    @SerializedName("StandAloneApp")
    @Expose
    private Boolean standAloneApp;
    @SerializedName("StandAloneAppPackageIdAndroid")
    @Expose
    private String standAloneAppPackageIdAndroid;
    @SerializedName("StandAloneAppIos")
    @Expose
    private Boolean standAloneAppIos;
    @SerializedName("StandAloneAppPackageIdIos")
    @Expose
    private String standAloneAppPackageIdIos;
    @SerializedName("StandAloneAppUrlSchemesIos")
    @Expose
    private String standAloneAppUrlSchemesIos;
    @SerializedName("VendorNotes")
    @Expose
    private List<String> vendorNotes = new ArrayList<>();
    @SerializedName("FeaturedCategories")
    @Expose
    private List<Category> featuredCategories = new ArrayList<>();
    @SerializedName("Categories")
    @Expose
    private List<Category> categories = new ArrayList<>();
    @SerializedName("HaveShoptheLook")
    @Expose
    private Boolean haveShoptheLook;
    @SerializedName("AboutUs")
    @Expose
    private String aboutUs;
    @SerializedName("PictureUrl")
    @Expose
    private String pictureUrl;
    @SerializedName("Id")
    @Expose
    private Integer id;

    protected Vendor(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.aboutUsPageURL = ((String) in.readValue((Object.class.getClassLoader())));
        this.appointmentPictureUrl = ((String) in.readValue((Object.class.getClassLoader())));
        this.logoUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.coverPictureURL = ((String) in.readValue((String.class.getClassLoader())));
        this.marketPlaceLogoURL = ((String) in.readValue((String.class.getClassLoader())));
        this.contactDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.contactNumber = ((String) in.readValue((Object.class.getClassLoader())));
        this.url = ((String) in.readValue((Object.class.getClassLoader())));
        this.aboutUsPageUrl = ((String) in.readValue((Object.class.getClassLoader())));
        this.blogUrl = ((String) in.readValue((Object.class.getClassLoader())));
        this.facebookPageURL = ((String) in.readValue((String.class.getClassLoader())));
        this.linkedInPageURL = ((String) in.readValue((Object.class.getClassLoader())));
        this.themeColor = ((String) in.readValue((String.class.getClassLoader())));
        this.mobileAppSetting = ((MobileAppSetting) in.readValue((MobileAppSetting.class.getClassLoader())));
        this.socialLinks = ((SocialLinks) in.readValue((SocialLinks.class.getClassLoader())));
        in.readList(this.banners, (Banner.class.getClassLoader()));
        this.rating = ((Rating) in.readValue((Rating.class.getClassLoader())));
        this.numberOfCartItems = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.numberOfWishlistItems = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.termAndConditionsUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.returnAndRefundUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryPolicyUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingInformationUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.returnAndExchangePolicyUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentSecurityUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.faqUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.standAloneApp = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.standAloneAppPackageIdAndroid = ((String) in.readValue((String.class.getClassLoader())));
        this.standAloneAppIos = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.standAloneAppPackageIdIos = ((String) in.readValue((String.class.getClassLoader())));
        this.standAloneAppUrlSchemesIos = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.vendorNotes, (java.lang.String.class.getClassLoader()));
        in.readList(this.featuredCategories, (Category.class.getClassLoader()));
        in.readList(this.categories, (Category.class.getClassLoader()));
        this.haveShoptheLook = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.aboutUs = ((String) in.readValue((String.class.getClassLoader())));
        this.pictureUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Vendor() {
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

    public String getAboutUsPageURL() {
        return aboutUsPageURL;
    }

    public void setAboutUsPageURL(String aboutUsPageURL) {
        this.aboutUsPageURL = aboutUsPageURL;
    }

    public String getAppointmentPictureUrl() {
        return appointmentPictureUrl;
    }

    public void setAppointmentPictureUrl(String appointmentPictureUrl) {
        this.appointmentPictureUrl = appointmentPictureUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCoverPictureURL() {
        return coverPictureURL;
    }

    public void setCoverPictureURL(String coverPictureURL) {
        this.coverPictureURL = coverPictureURL;
    }

    public String getMarketPlaceLogoURL() {
        return marketPlaceLogoURL;
    }

    public void setMarketPlaceLogoURL(String marketPlaceLogoURL) {
        this.marketPlaceLogoURL = marketPlaceLogoURL;
    }

    public String getContactDescription() {
        return contactDescription;
    }

    public void setContactDescription(String contactDescription) {
        this.contactDescription = contactDescription;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAboutUsPageUrl() {
        return aboutUsPageUrl;
    }

    public void setAboutUsPageUrl(String aboutUsPageUrl) {
        this.aboutUsPageUrl = aboutUsPageUrl;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getFacebookPageURL() {
        return facebookPageURL;
    }

    public void setFacebookPageURL(String facebookPageURL) {
        this.facebookPageURL = facebookPageURL;
    }

    public String getLinkedInPageURL() {
        return linkedInPageURL;
    }

    public void setLinkedInPageURL(String linkedInPageURL) {
        this.linkedInPageURL = linkedInPageURL;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public MobileAppSetting getMobileAppSetting() {
        return mobileAppSetting;
    }

    public void setMobileAppSetting(MobileAppSetting mobileAppSetting) {
        this.mobileAppSetting = mobileAppSetting;
    }

    public SocialLinks getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(SocialLinks socialLinks) {
        this.socialLinks = socialLinks;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Integer getNumberOfCartItems() {
        return numberOfCartItems;
    }

    public void setNumberOfCartItems(Integer numberOfCartItems) {
        this.numberOfCartItems = numberOfCartItems;
    }

    public Integer getNumberOfWishlistItems() {
        return numberOfWishlistItems;
    }

    public void setNumberOfWishlistItems(Integer numberOfWishlistItems) {
        this.numberOfWishlistItems = numberOfWishlistItems;
    }

    public String getTermAndConditionsUrl() {
        return termAndConditionsUrl;
    }

    public void setTermAndConditionsUrl(String termAndConditionsUrl) {
        this.termAndConditionsUrl = termAndConditionsUrl;
    }

    public String getReturnAndRefundUrl() {
        return returnAndRefundUrl;
    }

    public void setReturnAndRefundUrl(String returnAndRefundUrl) {
        this.returnAndRefundUrl = returnAndRefundUrl;
    }

    public String getDeliveryPolicyUrl() {
        return deliveryPolicyUrl;
    }

    public void setDeliveryPolicyUrl(String deliveryPolicyUrl) {
        this.deliveryPolicyUrl = deliveryPolicyUrl;
    }

    public String getShippingInformationUrl() {
        return shippingInformationUrl;
    }

    public void setShippingInformationUrl(String shippingInformationUrl) {
        this.shippingInformationUrl = shippingInformationUrl;
    }

    public String getReturnAndExchangePolicyUrl() {
        return returnAndExchangePolicyUrl;
    }

    public void setReturnAndExchangePolicyUrl(String returnAndExchangePolicyUrl) {
        this.returnAndExchangePolicyUrl = returnAndExchangePolicyUrl;
    }

    public String getPaymentSecurityUrl() {
        return paymentSecurityUrl;
    }

    public void setPaymentSecurityUrl(String paymentSecurityUrl) {
        this.paymentSecurityUrl = paymentSecurityUrl;
    }

    public String getFaqUrl() {
        return faqUrl;
    }

    public void setFaqUrl(String faqUrl) {
        this.faqUrl = faqUrl;
    }

    public Boolean getStandAloneApp() {
        return standAloneApp;
    }

    public void setStandAloneApp(Boolean standAloneApp) {
        this.standAloneApp = standAloneApp;
    }

    public String getStandAloneAppPackageIdAndroid() {
        return standAloneAppPackageIdAndroid;
    }

    public void setStandAloneAppPackageIdAndroid(String standAloneAppPackageIdAndroid) {
        this.standAloneAppPackageIdAndroid = standAloneAppPackageIdAndroid;
    }

    public Boolean getStandAloneAppIos() {
        return standAloneAppIos;
    }

    public void setStandAloneAppIos(Boolean standAloneAppIos) {
        this.standAloneAppIos = standAloneAppIos;
    }

    public String getStandAloneAppPackageIdIos() {
        return standAloneAppPackageIdIos;
    }

    public void setStandAloneAppPackageIdIos(String standAloneAppPackageIdIos) {
        this.standAloneAppPackageIdIos = standAloneAppPackageIdIos;
    }

    public String getStandAloneAppUrlSchemesIos() {
        return standAloneAppUrlSchemesIos;
    }

    public void setStandAloneAppUrlSchemesIos(String standAloneAppUrlSchemesIos) {
        this.standAloneAppUrlSchemesIos = standAloneAppUrlSchemesIos;
    }

    public List<String> getVendorNotes() {
        return vendorNotes;
    }

    public void setVendorNotes(List<String> vendorNotes) {
        this.vendorNotes = vendorNotes;
    }


    public List<Category> getFeaturedCategories() {
        return featuredCategories;
    }

    public void setFeaturedCategories(List<Category> featuredCategories) {
        this.featuredCategories = featuredCategories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Boolean getHaveShoptheLook() {
        return haveShoptheLook;
    }

    public void setHaveShoptheLook(Boolean haveShoptheLook) {
        this.haveShoptheLook = haveShoptheLook;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
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
        dest.writeValue(aboutUsPageURL);
        dest.writeValue(appointmentPictureUrl);
        dest.writeValue(logoUrl);
        dest.writeValue(coverPictureURL);
        dest.writeValue(marketPlaceLogoURL);
        dest.writeValue(contactDescription);
        dest.writeValue(contactNumber);
        dest.writeValue(url);
        dest.writeValue(aboutUsPageUrl);
        dest.writeValue(blogUrl);
        dest.writeValue(facebookPageURL);
        dest.writeValue(linkedInPageURL);
        dest.writeValue(themeColor);
        dest.writeValue(mobileAppSetting);
        dest.writeValue(socialLinks);
        dest.writeList(banners);
        dest.writeValue(rating);
        dest.writeValue(numberOfCartItems);
        dest.writeValue(numberOfWishlistItems);
        dest.writeValue(termAndConditionsUrl);
        dest.writeValue(returnAndRefundUrl);
        dest.writeValue(deliveryPolicyUrl);
        dest.writeValue(shippingInformationUrl);
        dest.writeValue(returnAndExchangePolicyUrl);
        dest.writeValue(paymentSecurityUrl);
        dest.writeValue(faqUrl);
        dest.writeValue(standAloneApp);
        dest.writeValue(standAloneAppPackageIdAndroid);
        dest.writeValue(standAloneAppIos);
        dest.writeValue(standAloneAppPackageIdIos);
        dest.writeValue(standAloneAppUrlSchemesIos);
        dest.writeList(vendorNotes);
        dest.writeList(featuredCategories);
        dest.writeList(categories);
        dest.writeValue(haveShoptheLook);
        dest.writeValue(aboutUs);
        dest.writeValue(pictureUrl);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}