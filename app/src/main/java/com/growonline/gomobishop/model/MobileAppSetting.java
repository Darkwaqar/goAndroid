package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileAppSetting implements Parcelable
{

    public final static Parcelable.Creator<MobileAppSetting> CREATOR = new Creator<MobileAppSetting>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MobileAppSetting createFromParcel(Parcel in) {
            return new MobileAppSetting(in);
        }

        public MobileAppSetting[] newArray(int size) {
            return (new MobileAppSetting[size]);
        }

    };
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("TabAnimation")
    @Expose
    private Integer tabAnimation;
    @SerializedName("ShopTheLookEnabled")
    @Expose
    private Boolean shopTheLookEnabled;
    @SerializedName("CatalogEnabled")
    @Expose
    private Boolean catalogEnabled;
    @SerializedName("NewTabEnabled")
    @Expose
    private Boolean newTabEnabled;
    @SerializedName("SalesTabEnabled")
    @Expose
    private Boolean salesTabEnabled;
    @SerializedName("LoyalityEnabled")
    @Expose
    private Boolean loyalityEnabled;
    @SerializedName("ImageAspectRatio")
    @Expose
    private Double imageAspectRatio;
    @SerializedName("CallToOrderEnabled")
    @Expose
    private Boolean callToOrderEnabled;
    @SerializedName("ShortcutEnabled")
    @Expose
    private Boolean shortcutEnabled;
    @SerializedName("AboutUsEnabled")
    @Expose
    private Boolean aboutUsEnabled;
    @SerializedName("ShopTheLookType")
    @Expose
    private Integer shopTheLookType;
    @SerializedName("HomeTabType")
    @Expose
    private Integer homeTabType;
    @SerializedName("AppintmentEnable")
    @Expose
    private Boolean appintmentEnable;
    @SerializedName("Id")
    @Expose
    private Integer id;

    protected MobileAppSetting(Parcel in) {
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.tabAnimation = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.shopTheLookEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.catalogEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.newTabEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.salesTabEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.loyalityEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.imageAspectRatio = ((Double) in.readValue((Double.class.getClassLoader())));
        this.callToOrderEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.shortcutEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.aboutUsEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.shopTheLookType = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.homeTabType = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.appintmentEnable = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public MobileAppSetting() {
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getTabAnimation() {
        return tabAnimation;
    }

    public void setTabAnimation(Integer tabAnimation) {
        this.tabAnimation = tabAnimation;
    }

    public Boolean getShopTheLookEnabled() {
        return shopTheLookEnabled;
    }

    public void setShopTheLookEnabled(Boolean shopTheLookEnabled) {
        this.shopTheLookEnabled = shopTheLookEnabled;
    }

    public Boolean getCatalogEnabled() {
        return catalogEnabled;
    }

    public void setCatalogEnabled(Boolean catalogEnabled) {
        this.catalogEnabled = catalogEnabled;
    }

    public Boolean getNewTabEnabled() {
        return newTabEnabled;
    }

    public void setNewTabEnabled(Boolean newTabEnabled) {
        this.newTabEnabled = newTabEnabled;
    }

    public Boolean getSalesTabEnabled() {
        return salesTabEnabled;
    }

    public void setSalesTabEnabled(Boolean salesTabEnabled) {
        this.salesTabEnabled = salesTabEnabled;
    }

    public Boolean getLoyalityEnabled() {
        return loyalityEnabled;
    }

    public void setLoyalityEnabled(Boolean loyalityEnabled) {
        this.loyalityEnabled = loyalityEnabled;
    }

    public Double getImageAspectRatio() {
        return imageAspectRatio;
    }

    public void setImageAspectRatio(Double imageAspectRatio) {
        this.imageAspectRatio = imageAspectRatio;
    }

    public Boolean getCallToOrderEnabled() {
        return callToOrderEnabled;
    }

    public void setCallToOrderEnabled(Boolean callToOrderEnabled) {
        this.callToOrderEnabled = callToOrderEnabled;
    }

    public Boolean getAppintmentEnable() {
        return appintmentEnable;
    }

    public void setAppintmentEnable(Boolean appintmentEnable) {
        this.appintmentEnable = appintmentEnable;
    }

    public Boolean getAboutUsEnabled() {
        return aboutUsEnabled;
    }

    public void setAboutUsEnabled(Boolean aboutUsEnabled) {
        this.aboutUsEnabled = aboutUsEnabled;
    }

    public Integer getShopTheLookType() {
        return shopTheLookType;
    }

    public void setShopTheLookType(Integer shopTheLookType) {
        this.shopTheLookType = shopTheLookType;
    }

    public Integer getHomeTabType() {
        return homeTabType;
    }

    public void setHomeTabType(Integer homeTabType) {
        this.homeTabType = homeTabType;
    }


    public Boolean getShortcutEnabled() {
        return shortcutEnabled;
    }

    public void setShortcutEnabled(Boolean shortcutEnabled) {
        this.shortcutEnabled = shortcutEnabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vendorId);
        dest.writeValue(tabAnimation);
        dest.writeValue(shopTheLookEnabled);
        dest.writeValue(catalogEnabled);
        dest.writeValue(newTabEnabled);
        dest.writeValue(salesTabEnabled);
        dest.writeValue(loyalityEnabled);
        dest.writeValue(imageAspectRatio);
        dest.writeValue(callToOrderEnabled);
        dest.writeValue(shortcutEnabled);
        dest.writeValue(aboutUsEnabled);
        dest.writeValue(shopTheLookType);
        dest.writeValue(homeTabType);
        dest.writeValue(appintmentEnable);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}