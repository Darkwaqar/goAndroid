package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethod implements Parcelable {

    @SerializedName("PaymentMethodSystemName")
    @Expose
    private String paymentMethodSystemName;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Fee")
    @Expose
    private String fee;
    @SerializedName("Selected")
    @Expose
    private Boolean selected;
    @SerializedName("LogoUrl")
    @Expose
    private String logoUrl;
    public final static Parcelable.Creator<PaymentMethod> CREATOR = new Creator<PaymentMethod>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PaymentMethod createFromParcel(Parcel in) {
            return new PaymentMethod(in);
        }

        public PaymentMethod[] newArray(int size) {
            return (new PaymentMethod[size]);
        }

    };
    @SerializedName("ImageId")
    @Expose
    private int imageId;
    @SerializedName("Id")
    @Expose
    private int id;

    protected PaymentMethod(Parcel in) {
        this.paymentMethodSystemName = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.fee = ((String) in.readValue((String.class.getClassLoader())));
        this.selected = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.logoUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.imageId = ((int) in.readValue((int.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
    }

    public PaymentMethod() {
    }

    public PaymentMethod(int id, String name, int imageId) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
    }

    public String getPaymentMethodSystemName() {
        return paymentMethodSystemName;
    }

    public void setPaymentMethodSystemName(String paymentMethodSystemName) {
        this.paymentMethodSystemName = paymentMethodSystemName;
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

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int imageId) {
        this.id = imageId;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(paymentMethodSystemName);
        dest.writeValue(name);
        dest.writeValue(description);
        dest.writeValue(fee);
        dest.writeValue(selected);
        dest.writeValue(logoUrl);
        dest.writeValue(imageId);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}
