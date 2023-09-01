package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Chat implements Parcelable {

    public final static Parcelable.Creator<Chat> CREATOR = new Creator<Chat>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        public Chat[] newArray(int size) {
            return (new Chat[size]);
        }

    };
    @SerializedName("InboxModel")
    @Expose
    private List<InboxModel> inboxModel = null;
    @SerializedName("ConversationsModel")
    @Expose
    private List<ConversationsModel> conversationsModel = null;
    @SerializedName("NumberOfUnreadMessage")
    @Expose
    private Integer numberOfUnreadMessage;
    @SerializedName("FromCustomerId")
    @Expose
    private Integer fromCustomerId;
    @SerializedName("InboxPage")
    @Expose
    private Integer inboxPage;
    @SerializedName("SentItemsPage")
    @Expose
    private Integer sentItemsPage;
    @SerializedName("SentItemsTabSelected")
    @Expose
    private Boolean sentItemsTabSelected;
    @SerializedName("ToCustomerId")
    @Expose
    private Integer toCustomerId;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("Inbox")
    @Expose
    private Object inbox;
    @SerializedName("Conversations")
    @Expose
    private Object conversations;
    @SerializedName("IsGuest")
    @Expose
    private Boolean isGuest;

    protected Chat(Parcel in) {
        in.readList(this.inboxModel, (InboxModel.class.getClassLoader()));
        in.readList(this.conversationsModel, (ConversationsModel.class.getClassLoader()));
        this.numberOfUnreadMessage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.fromCustomerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.inboxPage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sentItemsPage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sentItemsTabSelected = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.toCustomerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((Object) in.readValue((Object.class.getClassLoader())));
        this.inbox = ((Object) in.readValue((Object.class.getClassLoader())));
        this.conversations = ((Object) in.readValue((Object.class.getClassLoader())));
        this.isGuest = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Chat() {
    }

    public List<InboxModel> getInboxModel() {
        return inboxModel;
    }

    public void setInboxModel(List<InboxModel> inboxModel) {
        this.inboxModel = inboxModel;
    }

    public List<ConversationsModel> getConversationsModel() {
        return conversationsModel;
    }

    public void setConversationsModel(List<ConversationsModel> conversationsModel) {
        this.conversationsModel = conversationsModel;
    }

    public Integer getNumberOfUnreadMessage() {
        return numberOfUnreadMessage;
    }

    public void setNumberOfUnreadMessage(Integer numberOfUnreadMessage) {
        this.numberOfUnreadMessage = numberOfUnreadMessage;
    }

    public Integer getFromCustomerId() {
        return fromCustomerId;
    }

    public void setFromCustomerId(Integer fromCustomerId) {
        this.fromCustomerId = fromCustomerId;
    }

    public Integer getInboxPage() {
        return inboxPage;
    }

    public void setInboxPage(Integer inboxPage) {
        this.inboxPage = inboxPage;
    }

    public Integer getSentItemsPage() {
        return sentItemsPage;
    }

    public void setSentItemsPage(Integer sentItemsPage) {
        this.sentItemsPage = sentItemsPage;
    }

    public Boolean getSentItemsTabSelected() {
        return sentItemsTabSelected;
    }

    public void setSentItemsTabSelected(Boolean sentItemsTabSelected) {
        this.sentItemsTabSelected = sentItemsTabSelected;
    }

    public Integer getToCustomerId() {
        return toCustomerId;
    }

    public void setToCustomerId(Integer toCustomerId) {
        this.toCustomerId = toCustomerId;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getInbox() {
        return inbox;
    }

    public void setInbox(Object inbox) {
        this.inbox = inbox;
    }

    public Object getConversations() {
        return conversations;
    }

    public void setConversations(Object conversations) {
        this.conversations = conversations;
    }

    public Boolean getIsGuest() {
        return isGuest;
    }

    public void setIsGuest(Boolean isGuest) {
        this.isGuest = isGuest;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(inboxModel);
        dest.writeList(conversationsModel);
        dest.writeValue(numberOfUnreadMessage);
        dest.writeValue(fromCustomerId);
        dest.writeValue(inboxPage);
        dest.writeValue(sentItemsPage);
        dest.writeValue(sentItemsTabSelected);
        dest.writeValue(toCustomerId);
        dest.writeValue(message);
        dest.writeValue(inbox);
        dest.writeValue(conversations);
        dest.writeValue(isGuest);
    }

    public int describeContents() {
        return 0;
    }

}