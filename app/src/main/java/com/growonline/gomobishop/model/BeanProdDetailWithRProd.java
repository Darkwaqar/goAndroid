package com.growonline.gomobishop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BeanProdDetailWithRProd implements Parcelable {

    @SerializedName("PageDetailModel")
    @Expose
    private BeanProductDetail pageDetailModel;
    @SerializedName("RelatedProducts")
    @Expose
    private ArrayList<Product> relatedProducts = null;
    @SerializedName("ProductPointers")
    @Expose
    private ArrayList<ProductPointer> productPointers = null;
    @SerializedName("ShoppingCartItemsCount")
    @Expose
    private Integer shoppingCartItemsCount;
    @SerializedName("WishListItemsCount")
    @Expose
    private Integer wishListItemsCount;
    public final static Parcelable.Creator<BeanProdDetailWithRProd> CREATOR = new Creator<BeanProdDetailWithRProd>() {


        @SuppressWarnings({
                "unchecked"
        })
        public BeanProdDetailWithRProd createFromParcel(Parcel in) {
            return new BeanProdDetailWithRProd(in);
        }

        public BeanProdDetailWithRProd[] newArray(int size) {
            return (new BeanProdDetailWithRProd[size]);
        }

    };

    protected BeanProdDetailWithRProd(Parcel in) {
        this.pageDetailModel = ((BeanProductDetail) in.readValue((BeanProductDetail.class.getClassLoader())));
        in.readList(this.relatedProducts, (Product.class.getClassLoader()));
        in.readList(this.productPointers, (ProductPointer.class.getClassLoader()));
        this.shoppingCartItemsCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.wishListItemsCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public BeanProdDetailWithRProd() {
    }

    public BeanProductDetail getPageDetailModel() {
        return pageDetailModel;
    }

    public void setPageDetailModel(BeanProductDetail pageDetailModel) {
        this.pageDetailModel = pageDetailModel;
    }

    public ArrayList<Product> getRelatedProducts() {
        return relatedProducts;
    }

    public void setRelatedProducts(ArrayList<Product> relatedProducts) {
        this.relatedProducts = relatedProducts;
    }

    public ArrayList<ProductPointer> getProductPointers() {
        return productPointers;
    }

    public void setProductPointers(ArrayList<ProductPointer> productPointers) {
        this.productPointers = productPointers;
    }

    public Integer getShoppingCartItemsCount() {
        return shoppingCartItemsCount;
    }

    public void setShoppingCartItemsCount(Integer shoppingCartItemsCount) {
        this.shoppingCartItemsCount = shoppingCartItemsCount;
    }

    public Integer getWishListItemsCount() {
        return wishListItemsCount;
    }

    public void setWishListItemsCount(Integer wishListItemsCount) {
        this.wishListItemsCount = wishListItemsCount;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pageDetailModel);
        dest.writeList(relatedProducts);
        dest.writeList(productPointers);
        dest.writeValue(shoppingCartItemsCount);
        dest.writeValue(wishListItemsCount);
    }

    public int describeContents() {
        return 0;
    }

}