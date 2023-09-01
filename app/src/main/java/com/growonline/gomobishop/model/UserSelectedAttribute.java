package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sony on 12/14/2016.
 */

public class UserSelectedAttribute implements Parcelable {

    private String mAttributeId, mAttributeName, mAttributeValue, mType, mValueText;

    public UserSelectedAttribute(String ID, String Value) {
        mAttributeId = ID;
        mAttributeValue = Value;
    }

    public UserSelectedAttribute(Parcel in) {

        mAttributeId = in.readString();
        mAttributeName = in.readString();
        mAttributeValue = in.readString();
        mType = in.readString();
        mValueText = in.readString();

    }

    public UserSelectedAttribute(String ID, String Value, String type, String valueText, String attributeName) {
        mAttributeId = ID;
        mAttributeValue = Value;
        mType = type;
        mValueText = valueText;
        mAttributeName = attributeName;
    }

    public void setAttributeID(String AttributeID) {
        mAttributeId = AttributeID;
    }

    public void setAttributeValue(String AttributeValue) {
        mAttributeValue = AttributeValue;
    }

    public String getAttributeId() {
        return mAttributeId;
    }

    public String getAttributeValue() {
        return mAttributeValue;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getValueText() {
        return mValueText;
    }

    public void setValueText(String valueText) {
        this.mValueText = mValueText;
    }

    public String getAttributeName() {
        return mAttributeName;
    }

    public void setAttributeName(String attributeName) {
        this.mAttributeName = attributeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(mAttributeId);
        parcel.writeString(mAttributeName);
        parcel.writeString(mAttributeValue);
        parcel.writeString(mValueText);
        parcel.writeString(mType);
    }

    public static final Parcelable.Creator<UserSelectedAttribute> CREATOR =
            new Parcelable.Creator<UserSelectedAttribute>() {

                @Override
                public UserSelectedAttribute createFromParcel(Parcel parcel) {
                    return new UserSelectedAttribute(parcel);
                }

                @Override
                public UserSelectedAttribute[] newArray(int i) {
                    return new UserSelectedAttribute[i];
                }
            };
}
