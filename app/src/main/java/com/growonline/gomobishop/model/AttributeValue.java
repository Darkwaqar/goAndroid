package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 3/28/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttributeValue implements Parcelable
{

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ColorSquaresRgb")
    @Expose
    private String colorSquaresRgb;
    @SerializedName("ImageSquaresPictureModel")
    @Expose
    private ImageSquaresPictureModel imageSquaresPictureModel;
    @SerializedName("PriceAdjustment")
    @Expose
    private String priceAdjustment;
    @SerializedName("PriceAdjustmentValue")
    @Expose
    private double priceAdjustmentValue;
    @SerializedName("IsPreSelected")
    @Expose
    private Boolean isPreSelected;
    @SerializedName("PictureId")
    @Expose
    private Integer pictureId;
    @SerializedName("CustomerEntersQty")
    @Expose
    private Boolean customerEntersQty;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("Id")
    @Expose
    private Integer id;
    public final static Creator<AttributeValue> CREATOR = new Creator<AttributeValue>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AttributeValue createFromParcel(Parcel in) {
            return new AttributeValue(in);
        }

        public AttributeValue[] newArray(int size) {
            return (new AttributeValue[size]);
        }

    }
            ;

    public AttributeValue(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    protected AttributeValue(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.colorSquaresRgb = ((String) in.readValue((String.class.getClassLoader())));
        this.imageSquaresPictureModel = ((ImageSquaresPictureModel) in.readValue((ImageSquaresPictureModel.class.getClassLoader())));
        this.priceAdjustment = ((String) in.readValue((String.class.getClassLoader())));
        this.priceAdjustmentValue = ((double) in.readValue((double.class.getClassLoader())));
        this.isPreSelected = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.pictureId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customerEntersQty = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.quantity = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public AttributeValue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorSquaresRgb() {
        return colorSquaresRgb;
    }

    public void setColorSquaresRgb(String colorSquaresRgb) {
        this.colorSquaresRgb = colorSquaresRgb;
    }

    public ImageSquaresPictureModel getImageSquaresPictureModel() {
        return imageSquaresPictureModel;
    }

    public void setImageSquaresPictureModel(ImageSquaresPictureModel imageSquaresPictureModel) {
        this.imageSquaresPictureModel = imageSquaresPictureModel;
    }

    public String getPriceAdjustment() {
        return priceAdjustment;
    }

    public void setPriceAdjustment(String priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public double getPriceAdjustmentValue() {
        return priceAdjustmentValue;
    }

    public void setPriceAdjustmentValue(double priceAdjustmentValue) {
        this.priceAdjustmentValue = priceAdjustmentValue;
    }

    public Boolean getIsPreSelected() {
        return isPreSelected;
    }

    public void setIsPreSelected(Boolean isPreSelected) {
        this.isPreSelected = isPreSelected;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public Boolean getCustomerEntersQty() {
        return customerEntersQty;
    }

    public void setCustomerEntersQty(Boolean customerEntersQty) {
        this.customerEntersQty = customerEntersQty;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(colorSquaresRgb);
        dest.writeValue(imageSquaresPictureModel);
        dest.writeValue(priceAdjustment);
        dest.writeValue(priceAdjustmentValue);
        dest.writeValue(isPreSelected);
        dest.writeValue(pictureId);
        dest.writeValue(customerEntersQty);
        dest.writeValue(quantity);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}
