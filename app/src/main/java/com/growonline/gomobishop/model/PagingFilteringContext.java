package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 9/14/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PagingFilteringContext implements Parcelable {

    public final static Parcelable.Creator<PagingFilteringContext> CREATOR = new Creator<PagingFilteringContext>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PagingFilteringContext createFromParcel(Parcel in) {
            return new PagingFilteringContext(in);
        }

        public PagingFilteringContext[] newArray(int size) {
            return (new PagingFilteringContext[size]);
        }

    };
    @SerializedName("HasNextPage")
    @Expose
    private Boolean hasNextPage;
    @SerializedName("PageIndex")
    @Expose
    private Integer pageIndex;
    @SerializedName("PageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("PageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("TotalItems")
    @Expose
    private Integer totalItems;
    @SerializedName("TotalPages")
    @Expose
    private Integer totalPages;

    protected PagingFilteringContext(Parcel in) {
        this.hasNextPage = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.pageIndex = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pageNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pageSize = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalItems = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public PagingFilteringContext() {
    }

    public Boolean getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(hasNextPage);
        dest.writeValue(pageIndex);
        dest.writeValue(pageNumber);
        dest.writeValue(pageSize);
        dest.writeValue(totalItems);
        dest.writeValue(totalPages);
    }

    public int describeContents() {
        return 0;
    }

}
