package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagedProducts implements Parcelable {

    public final static Parcelable.Creator<PagedProducts> CREATOR = new Creator<PagedProducts>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PagedProducts createFromParcel(Parcel in) {
            return new PagedProducts(in);
        }

        public PagedProducts[] newArray(int size) {
            return (new PagedProducts[size]);
        }

    };
    @SerializedName("PageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("TotalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("Products")
    @Expose
    private List<Product> products = null;
    @SerializedName("SpecList")
    @Expose
    private List<SpecificationAttribute> specList = null;
    @SerializedName("PriceMax")
    @Expose
    private double priceMax;
    @SerializedName("PriceMin")
    @Expose
    private double priceMin;

    protected PagedProducts(Parcel in) {
        this.pageNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.products, (com.growonline.gomobishop.model.Product.class.getClassLoader()));
        in.readList(this.specList, (com.growonline.gomobishop.model.SpecificationAttribute.class.getClassLoader()));
        this.priceMax = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.priceMin = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public PagedProducts() {
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<SpecificationAttribute> getSpecList() {
        return specList;
    }

    public void setSpecList(List<SpecificationAttribute> specList) {
        this.specList = specList;
    }

    public double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(double priceMax) {
        this.priceMax = priceMax;
    }

    public double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(double priceMin) {
        this.priceMin = priceMin;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pageNumber);
        dest.writeValue(totalPages);
        dest.writeList(products);
        dest.writeList(specList);
        dest.writeValue(priceMax);
        dest.writeValue(priceMin);
    }

    public int describeContents() {
        return 0;
    }

}
