package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VendorListModel implements Parcelable {

    public final static Parcelable.Creator<VendorListModel> CREATOR = new Creator<VendorListModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VendorListModel createFromParcel(Parcel in) {
            return new VendorListModel(in);
        }

        public VendorListModel[] newArray(int size) {
            return (new VendorListModel[size]);
        }

    };
    @SerializedName("All")
    @Expose
    private ArrayList<Vendor> all = new ArrayList<>();
    @SerializedName("Brand")
    @Expose
    private ArrayList<Vendor> brand = new ArrayList<>();
    @SerializedName("Designer")
    @Expose
    private ArrayList<Vendor> designer = new ArrayList<>();
    @SerializedName("SheEarns")
    @Expose
    private ArrayList<Vendor> sheEarns = new ArrayList<>();

    protected VendorListModel(Parcel in) {
        in.readList(this.all, (Vendor.class.getClassLoader()));
        in.readList(this.brand, (Vendor.class.getClassLoader()));
        in.readList(this.designer, (Vendor.class.getClassLoader()));
        in.readList(this.sheEarns, (Vendor.class.getClassLoader()));
    }

    public VendorListModel() {
    }

    public ArrayList<Vendor> getAll() {
        return all;
    }

    public void setAll(ArrayList<Vendor> all) {
        this.all = all;
    }

    public ArrayList<Vendor> getBrand() {
        return brand;
    }

    public void setBrand(ArrayList<Vendor> brand) {
        this.brand = brand;
    }

    public ArrayList<Vendor> getDesigner() {
        return designer;
    }

    public void setDesigner(ArrayList<Vendor> designer) {
        this.designer = designer;
    }

    public ArrayList<Vendor> getSheEarns() {
        return sheEarns;
    }

    public void setSheEarns(ArrayList<Vendor> sheEarns) {
        this.sheEarns = sheEarns;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(all);
        dest.writeList(brand);
        dest.writeList(designer);
        dest.writeList(sheEarns);
    }

    public int describeContents() {
        return 0;
    }

}
