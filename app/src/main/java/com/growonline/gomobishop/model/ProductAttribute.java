package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 3/28/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductAttribute implements Parcelable {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("ProductAttributeId")
    @Expose
    private Integer productAttributeId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private Object description;
    @SerializedName("TextPrompt")
    @Expose
    private Object textPrompt;
    @SerializedName("IsRequired")
    @Expose
    private Boolean isRequired;
    @SerializedName("DefaultValue")
    @Expose
    private String defaultValue;
    @SerializedName("SelectedDay")
    @Expose
    private Object selectedDay;
    @SerializedName("SelectedMonth")
    @Expose
    private Object selectedMonth;
    @SerializedName("SelectedYear")
    @Expose
    private Object selectedYear;
    @SerializedName("HasCondition")
    @Expose
    private Boolean hasCondition;
    @SerializedName("AllowedFileExtensions")
    @Expose
    private List<Object> allowedFileExtensions = null;
    @SerializedName("AttributeControlType")
    @Expose
    private Integer attributeControlType;
    @SerializedName("Values")
    @Expose
    private List<AttributeValue> values = null;
    @SerializedName("Id")
    @Expose
    private Integer id;

    public final static Creator<ProductAttribute> CREATOR = new Creator<ProductAttribute>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductAttribute createFromParcel(Parcel in) {
            return new ProductAttribute(in);
        }

        public ProductAttribute[] newArray(int size) {
            return (new ProductAttribute[size]);
        }

    };

    protected ProductAttribute(Parcel in) {
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productAttributeId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((Object) in.readValue((Object.class.getClassLoader())));
        this.textPrompt = ((Object) in.readValue((Object.class.getClassLoader())));
        this.isRequired = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.defaultValue = ((String) in.readValue((String.class.getClassLoader())));
        this.selectedDay = ((Object) in.readValue((Object.class.getClassLoader())));
        this.selectedMonth = ((Object) in.readValue((Object.class.getClassLoader())));
        this.selectedYear = ((Object) in.readValue((Object.class.getClassLoader())));
        this.hasCondition = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.allowedFileExtensions, (Object.class.getClassLoader()));
        this.attributeControlType = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.values, (AttributeValue.class.getClassLoader()));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public ProductAttribute() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(Integer productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getTextPrompt() {
        return textPrompt;
    }

    public void setTextPrompt(Object textPrompt) {
        this.textPrompt = textPrompt;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(Object selectedDay) {
        this.selectedDay = selectedDay;
    }

    public Object getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(Object selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public Object getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(Object selectedYear) {
        this.selectedYear = selectedYear;
    }

    public Boolean getHasCondition() {
        return hasCondition;
    }

    public void setHasCondition(Boolean hasCondition) {
        this.hasCondition = hasCondition;
    }

    public List<Object> getAllowedFileExtensions() {
        return allowedFileExtensions;
    }

    public void setAllowedFileExtensions(List<Object> allowedFileExtensions) {
        this.allowedFileExtensions = allowedFileExtensions;
    }

    public Integer getAttributeControlType() {
        return attributeControlType;
    }

    public void setAttributeControlType(Integer attributeControlType) {
        this.attributeControlType = attributeControlType;
    }

    public List<AttributeValue> getValues() {
        return values;
    }

    public void setValues(List<AttributeValue> values) {
        this.values = values;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(productId);
        dest.writeValue(productAttributeId);
        dest.writeValue(name);
        dest.writeValue(description);
        dest.writeValue(textPrompt);
        dest.writeValue(isRequired);
        dest.writeValue(defaultValue);
        dest.writeValue(selectedDay);
        dest.writeValue(selectedMonth);
        dest.writeValue(selectedYear);
        dest.writeValue(hasCondition);
        dest.writeList(allowedFileExtensions);
        dest.writeValue(attributeControlType);
        dest.writeList(values);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}
