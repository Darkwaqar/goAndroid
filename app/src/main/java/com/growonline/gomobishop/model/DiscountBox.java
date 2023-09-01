package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 10/1/2018.
 */

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscountBox implements Parcelable
{

    @SerializedName("AppliedDiscountsWithCodes")
    @Expose
    private List<AppliedDiscountsWithCode> appliedDiscountsWithCodes = null;
    @SerializedName("Display")
    @Expose
    private Boolean display;
    @SerializedName("Messages")
    @Expose
    private List<Object> messages = null;
    @SerializedName("IsApplied")
    @Expose
    private Boolean isApplied;

    public final static Parcelable.Creator<DiscountBox> CREATOR = new Creator<DiscountBox>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DiscountBox createFromParcel(Parcel in) {
            return new DiscountBox(in);
        }

        public DiscountBox[] newArray(int size) {
            return (new DiscountBox[size]);
        }

    }
            ;

    protected DiscountBox(Parcel in) {
        in.readList(this.appliedDiscountsWithCodes, (com.growonline.gomobishop.model.AppliedDiscountsWithCode.class.getClassLoader()));
        this.display = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.messages, (java.lang.Object.class.getClassLoader()));
        this.isApplied = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public DiscountBox() {
    }

    public List<AppliedDiscountsWithCode> getAppliedDiscountsWithCodes() {
        return appliedDiscountsWithCodes;
    }

    public void setAppliedDiscountsWithCodes(List<AppliedDiscountsWithCode> appliedDiscountsWithCodes) {
        this.appliedDiscountsWithCodes = appliedDiscountsWithCodes;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public List<Object> getMessages() {
        return messages;
    }

    public void setMessages(List<Object> messages) {
        this.messages = messages;
    }

    public Boolean getIsApplied() {
        return isApplied;
    }

    public void setIsApplied(Boolean isApplied) {
        this.isApplied = isApplied;
    }



    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(appliedDiscountsWithCodes);
        dest.writeValue(display);
        dest.writeList(messages);
        dest.writeValue(isApplied);
    }

    public int describeContents() {
        return 0;
    }

}