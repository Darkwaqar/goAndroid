package com.growonline.gomobishop.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.MainActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.SearchActivity;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.control.TriangleLabelView;
import com.growonline.gomobishop.model.Product;
import com.growonline.gomobishop.model.SearchItemModel;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class ProductListingAdapter extends RecyclerView.Adapter<ProductListingAdapter.ProductListingViewHolder> {

    private String mColorAccent = "#000000";
    private List<Product> mProducts = new ArrayList<>();
    private List<Product> productToBeSend = new ArrayList<>();
    private AppCompatActivity mActivity;
    private Vendor mVendor;
    private SearchItemModel VendorToDownload;

    public ProductListingAdapter(List<Product> products, String colorAccent, AppCompatActivity activity, Vendor vendor) {
        this.mProducts = products;
        this.mColorAccent = colorAccent;
        this.mActivity = activity;
        this.mVendor = vendor;
    }

    @NonNull
    @Override
    public ProductListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_prd_item_layout, parent, false);

        return new ProductListingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductListingViewHolder holder, int position) {
        Product temp = mProducts.get(position);

        holder.pid = temp.getId();
        holder.vendorId = temp.getVendor().getId();
        holder.VendorName = temp.getVendor().getName();
        holder.VendorImage = temp.getVendor().getPictureUrl();


        if (mVendor != null && mVendor.getMobileAppSetting().getImageAspectRatio() == 1.0) {
            holder.thumb_img.setHeightRatio(1.0f);
        }
        GoMobileApp.getmCacheManager().loadImageAndFit(Uri.parse(temp.getDefaultPictureModel().getImageUrl()), holder.thumb_img, null);
        holder.lbl_title.setText(temp.getName());
        holder.Title = temp.getName();
        holder.ImageURL = temp.getDefaultPictureModel().getImageUrl();

        if (mActivity instanceof MainActivity || mActivity instanceof SearchActivity) {
            holder.lbl_vendor.setText(temp.getVendor().getName());
        } else
            holder.lbl_vendor.setVisibility(View.GONE);

        if (temp.getProductPrice().getDiscountPercentage() > 0) {
            holder.lbl_price.setText(temp.getProductPrice().getPriceWithDiscount());
            holder.lbl_price_strike_through.setVisibility(View.VISIBLE);
            holder.lbl_price_strike_through.setText(temp.getProductPrice().getPriceWithoutDiscount());
            holder.lbl_price_strike_through.setTextColor(Color.GRAY);
            holder.lbl_price_strike_through.setPaintFlags(holder.lbl_price_strike_through.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.lbl_price.setText(temp.getProductPrice().getPrice());
            holder.lbl_price_strike_through.setVisibility(View.GONE);
        }
        if (temp.getMarkAsSales() != null && temp.getMarkAsSales() && temp.getProductPrice().getDiscountPercentage() > 0) {
            holder.sales_label.setPrimaryText(String.format("-%.0f", temp.getProductPrice().getDiscountPercentage()) + "%");
            holder.sales_label.setVisibility(View.VISIBLE);

        } else if (temp.getMarkAsNew() != null && temp.getMarkAsNew())
            holder.new_label.setVisibility(View.VISIBLE);

        holder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public void addItems(List<Product> products) {
        mProducts.addAll(products);
    }

    public void addItem(Product product) {
        mProducts.add(product);
    }

    class ProductListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int pid, vendorId;
        AspectRatioImageView thumb_img;
        TextView lbl_title, lbl_vendor, lbl_price, lbl_price_strike_through;
        String Title, ImageURL;
        String VendorName, VendorImage;
        TriangleLabelView new_label, sales_label;


        ProductListingViewHolder(View itemView) {
            super(itemView);

            thumb_img = (AspectRatioImageView) itemView.findViewById(R.id.img_prd);
            lbl_title = (TextView) itemView.findViewById(R.id.txt_title);
            lbl_vendor = (TextView) itemView.findViewById(R.id.txt_vendor);
            lbl_price = (TextView) itemView.findViewById(R.id.txt_price);
            lbl_price_strike_through = (TextView) itemView.findViewById(R.id.txt_price_strike_through);
            new_label = (TriangleLabelView) itemView.findViewById(R.id.new_label);
            sales_label = (TriangleLabelView) itemView.findViewById(R.id.sales_label);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mActivity instanceof StoreActivity) {
                StoreActivity sActivity = ((StoreActivity) mActivity);
                if (getAdapterPosition() > 4 && mProducts.size() - getAdapterPosition() > 6) {
                    productToBeSend = mProducts.subList(getAdapterPosition() - 4, getAdapterPosition() + 6);
                } else {
                    productToBeSend = mProducts;
                }
                sActivity.openProductDetailFragment(pid, Title, productToBeSend);
            } else if (mActivity instanceof MainActivity) {
                MainActivity sActivity = ((MainActivity) mActivity);
                sActivity.StartOrLoadStore(vendorId, pid, VendorName, VendorImage);
            } else if (mActivity instanceof SearchActivity) {
                SearchActivity sActivity = ((SearchActivity) mActivity);
                Intent i = new Intent();
                VendorToDownload = new SearchItemModel();
                VendorToDownload.setVendorId(vendorId);
                VendorToDownload.setProductId(pid);
                VendorToDownload.setVendorName(VendorName);
                VendorToDownload.setVendorImage(VendorImage);
                i.putExtra(AppConstant.INTENT_VENDOR, VendorToDownload);
                sActivity.setResult(AppConstant.CODE_SEARCH_ACTIVITY, i);
                sActivity.finish();
                sActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }

    }

}
