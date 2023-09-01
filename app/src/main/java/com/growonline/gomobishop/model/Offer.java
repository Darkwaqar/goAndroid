package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer implements Parcelable
{

    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Discription")
    @Expose
    private String discription;
    @SerializedName("ButtonText")
    @Expose
    private String buttonText;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("Intent")
    @Expose
    private String intent;
    @SerializedName("LogoUrl")
    @Expose
    private String logoUrl;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    public final static Parcelable.Creator<Offer> CREATOR = new Creator<Offer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        public Offer[] newArray(int size) {
            return (new Offer[size]);
        }

    }
            ;

    protected Offer(Parcel in) {
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.discription = ((String) in.readValue((String.class.getClassLoader())));
        this.buttonText = ((String) in.readValue((String.class.getClassLoader())));
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.categoryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.intent = ((String) in.readValue((String.class.getClassLoader())));
        this.logoUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Offer() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(image);
        dest.writeValue(name);
        dest.writeValue(discription);
        dest.writeValue(buttonText);
        dest.writeValue(productId);
        dest.writeValue(categoryId);
        dest.writeValue(vendorId);
        dest.writeValue(intent);
        dest.writeValue(logoUrl);
        dest.writeValue(vendorName);
    }

    public int describeContents() {
        return 0;
    }

}