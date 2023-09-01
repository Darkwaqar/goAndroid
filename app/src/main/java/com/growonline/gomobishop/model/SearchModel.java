package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 1/28/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchModel implements Parcelable {

    public final static Parcelable.Creator<SearchModel> CREATOR = new Creator<SearchModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SearchModel createFromParcel(Parcel in) {
            return new SearchModel(in);
        }

        public SearchModel[] newArray(int size) {
            return (new SearchModel[size]);
        }

    };
    @SerializedName("Warning")
    @Expose
    private Object warning;
    @SerializedName("NoResults")
    @Expose
    private Boolean noResults;
    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("cid")
    @Expose
    private Integer cid;
    @SerializedName("vid")
    @Expose
    private Integer vid;
    @SerializedName("AvailableCategories")
    @Expose
    private List<AvailableCategory> availableCategories = new ArrayList<>();
    @SerializedName("AvailableVendors")
    @Expose
    private List<AvailableCategory> availableVendors = new ArrayList<>();
    @SerializedName("Products")
    @Expose
    private List<Product> products = new ArrayList<>();
    @SerializedName("PagingFilteringContext")
    @Expose
    private PagingFilteringContext pagingFilteringContext;

    protected SearchModel(Parcel in) {
        this.warning = ((Object) in.readValue((Object.class.getClassLoader())));
        this.noResults = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.q = ((String) in.readValue((String.class.getClassLoader())));
        this.cid = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vid = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.availableCategories, (com.growonline.gomobishop.model.AvailableCategory.class.getClassLoader()));
        in.readList(this.availableVendors, (com.growonline.gomobishop.model.AvailableCategory.class.getClassLoader()));
        in.readList(this.products, (com.growonline.gomobishop.model.Product.class.getClassLoader()));
        this.pagingFilteringContext = ((PagingFilteringContext) in.readValue((PagingFilteringContext.class.getClassLoader())));
    }

    public SearchModel() {
    }

    public Object getWarning() {
        return warning;
    }

    public void setWarning(Object warning) {
        this.warning = warning;
    }

    public Boolean getNoResults() {
        return noResults;
    }

    public void setNoResults(Boolean noResults) {
        this.noResults = noResults;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public List<AvailableCategory> getAvailableCategories() {
        return availableCategories;
    }

    public void setAvailableCategories(List<AvailableCategory> availableCategories) {
        this.availableCategories = availableCategories;
    }

    public List<AvailableCategory> getAvailableVendors() {
        return availableVendors;
    }

    public void setAvailableVendors(List<AvailableCategory> availableVendors) {
        this.availableVendors = availableVendors;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public PagingFilteringContext getPagingFilteringContext() {
        return pagingFilteringContext;
    }

    public void setPagingFilteringContext(PagingFilteringContext pagingFilteringContext) {
        this.pagingFilteringContext = pagingFilteringContext;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(warning);
        dest.writeValue(noResults);
        dest.writeValue(q);
        dest.writeValue(cid);
        dest.writeValue(vid);
        dest.writeList(availableCategories);
        dest.writeList(availableVendors);
        dest.writeList(products);
        dest.writeValue(pagingFilteringContext);
    }

    public int describeContents() {
        return 0;
    }

}