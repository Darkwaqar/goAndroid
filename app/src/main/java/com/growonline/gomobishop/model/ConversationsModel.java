package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConversationsModel implements Parcelable {

    public final static Parcelable.Creator<ConversationsModel> CREATOR = new Creator<ConversationsModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ConversationsModel createFromParcel(Parcel in) {
            return new ConversationsModel(in);
        }

        public ConversationsModel[] newArray(int size) {
            return (new ConversationsModel[size]);
        }

    };
    @SerializedName("FromCustomerId")
    @Expose
    private Integer fromCustomerId;
    @SerializedName("CustomerFromName")
    @Expose
    private String customerFromName;
    @SerializedName("AllowViewingFromProfile")
    @Expose
    private Boolean allowViewingFromProfile;
    @SerializedName("ToCustomerId")
    @Expose
    private Integer toCustomerId;
    @SerializedName("CustomerToName")
    @Expose
    private String customerToName;
    @SerializedName("AllowViewingToProfile")
    @Expose
    private Boolean allowViewingToProfile;
    @SerializedName("Subject")
    @Expose
    private Object subject;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("IsRead")
    @Expose
    private Boolean isRead;
    @SerializedName("AlignLeft")
    @Expose
    private Boolean alignLeft;
    @SerializedName("Deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("TotalUnread")
    @Expose
    private Integer totalUnread;
    @SerializedName("Id")
    @Expose
    private Integer id;

    protected ConversationsModel(Parcel in) {
        this.fromCustomerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customerFromName = ((String) in.readValue((String.class.getClassLoader())));
        this.allowViewingFromProfile = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.toCustomerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customerToName = ((String) in.readValue((String.class.getClassLoader())));
        this.allowViewingToProfile = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.subject = ((Object) in.readValue((Object.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.createdOn = ((String) in.readValue((String.class.getClassLoader())));
        this.isRead = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.alignLeft = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.deleted = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.count = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalUnread = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public ConversationsModel() {
    }

    public Integer getFromCustomerId() {
        return fromCustomerId;
    }

    public void setFromCustomerId(Integer fromCustomerId) {
        this.fromCustomerId = fromCustomerId;
    }

    public String getCustomerFromName() {
        return customerFromName;
    }

    public void setCustomerFromName(String customerFromName) {
        this.customerFromName = customerFromName;
    }

    public Boolean getAllowViewingFromProfile() {
        return allowViewingFromProfile;
    }

    public void setAllowViewingFromProfile(Boolean allowViewingFromProfile) {
        this.allowViewingFromProfile = allowViewingFromProfile;
    }

    public Integer getToCustomerId() {
        return toCustomerId;
    }

    public void setToCustomerId(Integer toCustomerId) {
        this.toCustomerId = toCustomerId;
    }

    public String getCustomerToName() {
        return customerToName;
    }

    public void setCustomerToName(String customerToName) {
        this.customerToName = customerToName;
    }

    public Boolean getAllowViewingToProfile() {
        return allowViewingToProfile;
    }

    public void setAllowViewingToProfile(Boolean allowViewingToProfile) {
        this.allowViewingToProfile = allowViewingToProfile;
    }

    public Object getSubject() {
        return subject;
    }

    public void setSubject(Object subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getAlignLeft() {
        return alignLeft;
    }

    public void setAlignLeft(Boolean alignLeft) {
        this.alignLeft = alignLeft;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotalUnread() {
        return totalUnread;
    }

    public void setTotalUnread(Integer totalUnread) {
        this.totalUnread = totalUnread;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(fromCustomerId);
        dest.writeValue(customerFromName);
        dest.writeValue(allowViewingFromProfile);
        dest.writeValue(toCustomerId);
        dest.writeValue(customerToName);
        dest.writeValue(allowViewingToProfile);
        dest.writeValue(subject);
        dest.writeValue(message);
        dest.writeValue(createdOn);
        dest.writeValue(isRead);
        dest.writeValue(alignLeft);
        dest.writeValue(deleted);
        dest.writeValue(count);
        dest.writeValue(totalUnread);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}