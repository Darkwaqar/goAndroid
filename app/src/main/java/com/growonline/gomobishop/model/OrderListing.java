package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 10/9/2019.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderListing implements Parcelable {

    public final static Parcelable.Creator<OrderListing> CREATOR = new Creator<OrderListing>() {


        @SuppressWarnings({
                "unchecked"
        })
        public OrderListing createFromParcel(Parcel in) {
            return new OrderListing(in);
        }

        public OrderListing[] newArray(int size) {
            return (new OrderListing[size]);
        }

    };
    @SerializedName("PageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("TotalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("Orders")
    @Expose
    private List<SimpleOrderModel> orders = new ArrayList<>();

    protected OrderListing(Parcel in) {
        this.pageNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.orders, (SimpleOrderModel.class.getClassLoader()));
    }

    public OrderListing() {
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

    public List<SimpleOrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<SimpleOrderModel> orders) {
        this.orders = orders;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pageNumber);
        dest.writeValue(totalPages);
        dest.writeList(orders);
    }

    public int describeContents() {
        return 0;
    }

}
