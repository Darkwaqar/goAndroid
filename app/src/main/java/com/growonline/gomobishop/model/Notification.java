package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 8/5/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification implements Parcelable {

    public final static Parcelable.Creator<Notification> CREATOR = new Creator<Notification>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        public Notification[] newArray(int size) {
            return (new Notification[size]);
        }

    };
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("FcmType")
    @Expose
    private Integer fcmType;
    @SerializedName("FcmApplicationType")
    @Expose
    private Integer fcmApplicationType;
    @SerializedName("ApplicationId")
    @Expose
    private Integer applicationId;
    @SerializedName("ActionId")
    @Expose
    private Integer actionId;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Image")
    @Expose
    private Integer image;
    @SerializedName("DirectImageLink")
    @Expose
    private String directImageLink;
    @SerializedName("GotoLink")
    @Expose
    private String gotoLink;
    @SerializedName("Icon")
    @Expose
    private Integer icon;
    @SerializedName("DirectIconLink")
    @Expose
    private String directIconLink;
    @SerializedName("IsReaded")
    @Expose
    private Boolean isReaded;
    @SerializedName("CreatedOnUtc")
    @Expose
    private String createdOnUtc;
    @SerializedName("UpdatedOnUtc")
    @Expose
    private String updatedOnUtc;
    @SerializedName("ReadedOnUtc")
    @Expose
    private Object readedOnUtc;
    @SerializedName("VendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("VendorImage")
    @Expose
    private String vendorImage;
    @SerializedName("Extra")
    @Expose
    private String extra;
    @SerializedName("FcmActionType")
    @Expose
    private Integer fcmActionType;

    protected Notification(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.fcmType = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.fcmApplicationType = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.applicationId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.actionId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.directImageLink = ((String) in.readValue((String.class.getClassLoader())));
        this.gotoLink = ((String) in.readValue((String.class.getClassLoader())));
        this.icon = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.directIconLink = ((String) in.readValue((String.class.getClassLoader())));
        this.isReaded = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.createdOnUtc = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedOnUtc = ((String) in.readValue((String.class.getClassLoader())));
        this.readedOnUtc = ((Object) in.readValue((Object.class.getClassLoader())));
        this.vendorId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vendorName = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorImage = ((String) in.readValue((String.class.getClassLoader())));
        this.extra = ((String) in.readValue((String.class.getClassLoader())));
        this.fcmActionType = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Notification() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFcmType() {
        return fcmType;
    }

    public void setFcmType(Integer fcmType) {
        this.fcmType = fcmType;
    }

    public Integer getFcmApplicationType() {
        return fcmApplicationType;
    }

    public void setFcmApplicationType(Integer fcmApplicationType) {
        this.fcmApplicationType = fcmApplicationType;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getDirectImageLink() {
        return directImageLink;
    }

    public void setDirectImageLink(String directImageLink) {
        this.directImageLink = directImageLink;
    }

    public String getGotoLink() {
        return gotoLink;
    }

    public void setGotoLink(String gotoLink) {
        this.gotoLink = gotoLink;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getDirectIconLink() {
        return directIconLink;
    }

    public void setDirectIconLink(String directIconLink) {
        this.directIconLink = directIconLink;
    }

    public Boolean getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(Boolean isReaded) {
        this.isReaded = isReaded;
    }

    public String getCreatedOnUtc() {
        return createdOnUtc;
    }

    public void setCreatedOnUtc(String createdOnUtc) {
        this.createdOnUtc = createdOnUtc;
    }

    public String getUpdatedOnUtc() {
        return updatedOnUtc;
    }

    public void setUpdatedOnUtc(String updatedOnUtc) {
        this.updatedOnUtc = updatedOnUtc;
    }

    public Object getReadedOnUtc() {
        return readedOnUtc;
    }

    public void setReadedOnUtc(Object readedOnUtc) {
        this.readedOnUtc = readedOnUtc;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorImage() {
        return vendorImage;
    }

    public void setVendorImage(String vendorImage) {
        this.vendorImage = vendorImage;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getFcmActionType() {
        return fcmActionType;
    }

    public void setFcmActionType(Integer fcmActionType) {
        this.fcmActionType = fcmActionType;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(fcmType);
        dest.writeValue(fcmApplicationType);
        dest.writeValue(applicationId);
        dest.writeValue(actionId);
        dest.writeValue(title);
        dest.writeValue(message);
        dest.writeValue(image);
        dest.writeValue(directImageLink);
        dest.writeValue(gotoLink);
        dest.writeValue(icon);
        dest.writeValue(directIconLink);
        dest.writeValue(isReaded);
        dest.writeValue(createdOnUtc);
        dest.writeValue(updatedOnUtc);
        dest.writeValue(readedOnUtc);
        dest.writeValue(vendorId);
        dest.writeValue(vendorName);
        dest.writeValue(vendorImage);
        dest.writeValue(extra);
        dest.writeValue(fcmActionType);
    }

    public int describeContents() {
        return 0;
    }

}
