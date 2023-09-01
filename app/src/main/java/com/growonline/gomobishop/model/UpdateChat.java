package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateChat implements Parcelable {

    public final static Parcelable.Creator<UpdateChat> CREATOR = new Creator<UpdateChat>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UpdateChat createFromParcel(Parcel in) {
            return new UpdateChat(in);
        }

        public UpdateChat[] newArray(int size) {
            return (new UpdateChat[size]);
        }

    };
    @SerializedName("updateinboxsection")
    @Expose
    private Boolean updateinboxsection;
    @SerializedName("updateinboxsectionhtml")
    @Expose
    private List<InboxModel> updateinboxsectionhtml = null;
    @SerializedName("updateCustomerId")
    @Expose
    private Boolean updateCustomerId;
    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("updateconversationsection")
    @Expose
    private Boolean updateconversationsection;
    @SerializedName("updateconversationsectionhtml")
    @Expose
    private List<ConversationsModel> updateconversationsectionhtml = null;
    @SerializedName("updatetotalsection")
    @Expose
    private Boolean updatetotalsection;
    @SerializedName("totalsectionhtml")
    @Expose
    private Integer totalsectionhtml;
    @SerializedName("sendcustomerupdate")
    @Expose
    private Boolean sendcustomerupdate;
    @SerializedName("scrollToBottom")
    @Expose
    private Boolean scrollToBottom;

    protected UpdateChat(Parcel in) {
        this.updateinboxsection = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.updateinboxsectionhtml, (InboxModel.class.getClassLoader()));
        this.updateCustomerId = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.customerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.updateconversationsection = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.updateconversationsectionhtml, (ConversationsModel.class.getClassLoader()));
        this.updatetotalsection = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.totalsectionhtml = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sendcustomerupdate = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.scrollToBottom = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public UpdateChat() {
    }

    public Boolean getUpdateinboxsection() {
        return updateinboxsection;
    }

    public void setUpdateinboxsection(Boolean updateinboxsection) {
        this.updateinboxsection = updateinboxsection;
    }

    public List<InboxModel> getUpdateinboxsectionhtml() {
        return updateinboxsectionhtml;
    }

    public void setUpdateinboxsectionhtml(List<InboxModel> updateinboxsectionhtml) {
        this.updateinboxsectionhtml = updateinboxsectionhtml;
    }

    public Boolean getUpdateCustomerId() {
        return updateCustomerId;
    }

    public void setUpdateCustomerId(Boolean updateCustomerId) {
        this.updateCustomerId = updateCustomerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Boolean getUpdateconversationsection() {
        return updateconversationsection;
    }

    public void setUpdateconversationsection(Boolean updateconversationsection) {
        this.updateconversationsection = updateconversationsection;
    }

    public List<ConversationsModel> getUpdateconversationsectionhtml() {
        return updateconversationsectionhtml;
    }

    public void setUpdateconversationsectionhtml(List<ConversationsModel> updateconversationsectionhtml) {
        this.updateconversationsectionhtml = updateconversationsectionhtml;
    }

    public Boolean getUpdatetotalsection() {
        return updatetotalsection;
    }

    public void setUpdatetotalsection(Boolean updatetotalsection) {
        this.updatetotalsection = updatetotalsection;
    }

    public Integer getTotalsectionhtml() {
        return totalsectionhtml;
    }

    public void setTotalsectionhtml(Integer totalsectionhtml) {
        this.totalsectionhtml = totalsectionhtml;
    }

    public Boolean getSendcustomerupdate() {
        return sendcustomerupdate;
    }

    public void setSendcustomerupdate(Boolean sendcustomerupdate) {
        this.sendcustomerupdate = sendcustomerupdate;
    }

    public Boolean getScrollToBottom() {
        return scrollToBottom;
    }

    public void setScrollToBottom(Boolean scrollToBottom) {
        this.scrollToBottom = scrollToBottom;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(updateinboxsection);
        dest.writeList(updateinboxsectionhtml);
        dest.writeValue(updateCustomerId);
        dest.writeValue(customerId);
        dest.writeValue(updateconversationsection);
        dest.writeList(updateconversationsectionhtml);
        dest.writeValue(updatetotalsection);
        dest.writeValue(totalsectionhtml);
        dest.writeValue(sendcustomerupdate);
        dest.writeValue(scrollToBottom);
    }

    public int describeContents() {
        return 0;
    }

}
