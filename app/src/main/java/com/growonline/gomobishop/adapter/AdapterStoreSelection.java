package com.growonline.gomobishop.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreWidgetConfigActivity;
import com.growonline.gomobishop.model.Vendor;

import java.util.List;


public class AdapterStoreSelection extends RecyclerView.Adapter<AdapterStoreSelection.VendorViewHolder> {
    private final LayoutInflater mInflator;
    private StoreWidgetConfigActivity nActivity;
    private List<Vendor> mList;
    private WidgetConfigListener mListener;

    public AdapterStoreSelection(StoreWidgetConfigActivity storeWidgetConfigActivity,
                                 List<Vendor> tempList,
                                 AdapterStoreSelection.WidgetConfigListener listener) {
        nActivity = storeWidgetConfigActivity;
        mList = tempList;
        mListener = listener;
        mInflator = nActivity.getLayoutInflater();
    }

    @Override
    public VendorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VendorViewHolder(mInflator.inflate(R.layout.single_featured_vendor_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(VendorViewHolder holder, int position) {
        GoMobileApp.getmCacheManager().loadImage(Uri.parse(mList.get(position).getMarketPlaceLogoURL()), holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface WidgetConfigListener {
        void onItemSelected(int pos, Vendor v);
    }

    class VendorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mImageView;

        VendorViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.img_vendor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            mListener.onItemSelected(pos, mList.get(pos));
        }
    }
}
