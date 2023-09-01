package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 3/3/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductSpecification implements Parcelable
{

    @SerializedName("SpecificationAttributeId")
    @Expose
    private Integer specificationAttributeId;
    @SerializedName("SpecificationAttributeName")
    @Expose
    private String specificationAttributeName;
    @SerializedName("ValueRaw")
    @Expose
    private String valueRaw;
    @SerializedName("ColorSquaresRgb")
    @Expose
    private Object colorSquaresRgb;
    public final static Parcelable.Creator<ProductSpecification> CREATOR = new Creator<ProductSpecification>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductSpecification createFromParcel(Parcel in) {
            return new ProductSpecification(in);
        }

        public ProductSpecification[] newArray(int size) {
            return (new ProductSpecification[size]);
        }

    }
            ;

    protected ProductSpecification(Parcel in) {
        this.specificationAttributeId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.specificationAttributeName = ((String) in.readValue((String.class.getClassLoader())));
        this.valueRaw = ((String) in.readValue((String.class.getClassLoader())));
        this.colorSquaresRgb = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public ProductSpecification() {
    }

    public Integer getSpecificationAttributeId() {
        return specificationAttributeId;
    }

    public void setSpecificationAttributeId(Integer specificationAttributeId) {
        this.specificationAttributeId = specificationAttributeId;
    }

    public String getSpecificationAttributeName() {
        return specificationAttributeName;
    }

    public void setSpecificationAttributeName(String specificationAttributeName) {
        this.specificationAttributeName = specificationAttributeName;
    }

    public String getValueRaw() {
        return valueRaw;
    }

    public void setValueRaw(String valueRaw) {
        this.valueRaw = valueRaw;
    }

    public Object getColorSquaresRgb() {
        return colorSquaresRgb;
    }

    public void setColorSquaresRgb(Object colorSquaresRgb) {
        this.colorSquaresRgb = colorSquaresRgb;
    }



    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(specificationAttributeId);
        dest.writeValue(specificationAttributeName);
        dest.writeValue(valueRaw);
        dest.writeValue(colorSquaresRgb);
    }

    public int describeContents() {
        return 0;
    }

}