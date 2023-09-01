package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GiftCard implements Parcelable {

    @SerializedName("IsGiftCard")
    @Expose
    private Boolean isGiftCard;
    @SerializedName("RecipientName")
    @Expose
    private Object recipientName;
    @SerializedName("RecipientEmail")
    @Expose
    private Object recipientEmail;
    @SerializedName("SenderName")
    @Expose
    private String senderName;
    @SerializedName("SenderEmail")
    @Expose
    private String senderEmail;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("GiftCardType")
    @Expose
    private Integer giftCardType;

    public final static Parcelable.Creator<GiftCard> CREATOR = new Parcelable.Creator<GiftCard>() {


        @SuppressWarnings({
                "unchecked"
        })
        public GiftCard createFromParcel(Parcel in) {
            return new GiftCard(in);
        }

        public GiftCard[] newArray(int size) {
            return (new GiftCard[size]);
        }

    };

    protected GiftCard(Parcel in) {
        this.isGiftCard = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.recipientName = ((Object) in.readValue((Object.class.getClassLoader())));
        this.recipientEmail = ((Object) in.readValue((Object.class.getClassLoader())));
        this.senderName = ((String) in.readValue((String.class.getClassLoader())));
        this.senderEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((Object) in.readValue((Object.class.getClassLoader())));
        this.giftCardType = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public GiftCard() {
    }

    public Boolean getIsGiftCard() {
        return isGiftCard;
    }

    public void setIsGiftCard(Boolean isGiftCard) {
        this.isGiftCard = isGiftCard;
    }

    public Object getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(Object recipientName) {
        this.recipientName = recipientName;
    }

    public Object getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(Object recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Integer getGiftCardType() {
        return giftCardType;
    }

    public void setGiftCardType(Integer giftCardType) {
        this.giftCardType = giftCardType;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(isGiftCard);
        dest.writeValue(recipientName);
        dest.writeValue(recipientEmail);
        dest.writeValue(senderName);
        dest.writeValue(senderEmail);
        dest.writeValue(message);
        dest.writeValue(giftCardType);
    }

    public int describeContents() {
        return 0;
    }

}