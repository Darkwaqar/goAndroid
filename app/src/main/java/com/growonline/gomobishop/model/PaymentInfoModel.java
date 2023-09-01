package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentInfoModel implements Parcelable {

    public final static Parcelable.Creator<PaymentInfoModel> CREATOR = new Creator<PaymentInfoModel>() {

        @SuppressWarnings({
                "unchecked"
        })
        public PaymentInfoModel createFromParcel(Parcel in) {
            return new PaymentInfoModel(in);
        }

        public PaymentInfoModel[] newArray(int size) {
            return (new PaymentInfoModel[size]);
        }

    };
    @SerializedName("IsGuest")
    @Expose
    private Boolean isGuest;
    @SerializedName("Errors")
    @Expose
    private Object errors;
    @SerializedName("StripeToken")
    @Expose
    private Object stripeToken;
    @SerializedName("PostalCode")
    @Expose
    private String postalCode;
    @SerializedName("PublishableKey")
    @Expose
    private String publishableKey;
    @SerializedName("OnePageCheckoutEnabled")
    @Expose
    private Boolean onePageCheckoutEnabled;
    @SerializedName("ClientSecret")
    @Expose
    private String clientSecret;

    protected PaymentInfoModel(Parcel in) {
        this.isGuest = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.errors = ((Object) in.readValue((Object.class.getClassLoader())));
        this.stripeToken = ((Object) in.readValue((Object.class.getClassLoader())));
        this.postalCode = ((String) in.readValue((String.class.getClassLoader())));
        this.publishableKey = ((String) in.readValue((String.class.getClassLoader())));
        this.onePageCheckoutEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.clientSecret = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PaymentInfoModel() {
    }

    public Boolean getIsGuest() {
        return isGuest;
    }

    public void setIsGuest(Boolean isGuest) {
        this.isGuest = isGuest;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public Object getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(Object stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPublishableKey() {
        return publishableKey;
    }

    public void setPublishableKey(String publishableKey) {
        this.publishableKey = publishableKey;
    }

    public Boolean getOnePageCheckoutEnabled() {
        return onePageCheckoutEnabled;
    }

    public void setOnePageCheckoutEnabled(Boolean onePageCheckoutEnabled) {
        this.onePageCheckoutEnabled = onePageCheckoutEnabled;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(isGuest);
        dest.writeValue(errors);
        dest.writeValue(stripeToken);
        dest.writeValue(postalCode);
        dest.writeValue(publishableKey);
        dest.writeValue(onePageCheckoutEnabled);
        dest.writeValue(clientSecret);
    }

    public int describeContents() {
        return 0;
    }

}
