package com.growonline.gomobishop.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.MainActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreDownloadActivity;
import com.growonline.gomobishop.control.DetectHtml;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.model.VendorList;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class FeaturedVendorListingAdapter extends RecyclerView.Adapter<FeaturedVendorListingAdapter.VendorViewHolder> implements SectionIndexer {

    private List<VendorList> mVendors = new ArrayList<>();
    private MainActivity mActivity;
    private ArrayList<Integer> mSectionPositions;
    private int type;


    public FeaturedVendorListingAdapter(List<VendorList> vendors, AppCompatActivity activity, int type) {
        this.mVendors = vendors;
        this.mActivity = (MainActivity) activity;
        this.type = type;
    }

    public static String ToSentenceCase(String str) {
        if (str == null) {
            return null;
        }
        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    @Override
    public FeaturedVendorListingAdapter.VendorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_featured_vendor_layout, parent, false);
        return new FeaturedVendorListingAdapter.VendorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeaturedVendorListingAdapter.VendorViewHolder holder, int position) {

        VendorList item = mVendors.get(position);

        holder.vid = item.getId();
        holder.vendorName = item.getTitle();


        holder.logo_url = item.getMpLogoUrl();
        GoMobileApp.getmCacheManager().loadImage(Uri.parse(item.getMpLogoUrl()), holder.imgVendor, null);
        if (DetectHtml.isHtml(item.getDescription())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.description.setText(Html.fromHtml(item.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.description.setText(Html.fromHtml(item.getDescription()));
            }
        } else {
            holder.description.setText(item.getDescription());
        }
        if (type == AppConstant.ALL_VENDOR) {
            holder.description.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mVendors.size();
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = mVendors.size(); i < size; i++) {
            String section = String.valueOf(mVendors.get(i).getTitle().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int position) {
        return mSectionPositions.get(position);
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position >= mVendors.size()) {
            position = mVendors.size() - 1;
        }
        return position;
    }

    private boolean isStoreAlreadyDownloaded(int vendorId) {

        boolean exist = false;
        if (GoMobileApp.getDownloadedVendors() != null && GoMobileApp.getDownloadedVendors().size() > 0) {

            List<Vendor> mDownloadedVendors = GoMobileApp.getDownloadedVendors();
            for (int i = 0; i < mDownloadedVendors.size(); i++) {
                Vendor cur = mDownloadedVendors.get(i);

                if (cur.getId() == vendorId) {
                    exist = true;
                    break;
                }
            }
        }

        return exist;
    }

    class VendorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int vid;
        String vendorName, logo_url;
        private ImageView imgVendor;
        private TextView description;

        VendorViewHolder(View itemView) {
            super(itemView);

            imgVendor = (ImageView) itemView.findViewById(R.id.img_vendor);
            description = (TextView) itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isStoreAlreadyDownloaded(vid)) {
                        mActivity.startStore(vid);
                    } else {
                        Intent i = new Intent(mActivity, StoreDownloadActivity.class);
                        i.putExtra("mStoreId", vid);
                        i.putExtra("mStoreTitle", vendorName);
                        i.putExtra("mStoreLogoUrl", logo_url);
                        mActivity.startActivityForResult(i, AppConstant.CODE_DOWNLOAD_ACTIVITY);
                        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            }, 500);
        }

    }


}
