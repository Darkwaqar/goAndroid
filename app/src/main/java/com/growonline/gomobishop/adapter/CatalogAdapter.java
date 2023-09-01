package com.growonline.gomobishop.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.CatalogPageModel;

import java.util.ArrayList;
import java.util.List;

public class CatalogAdapter extends PagerAdapter {
    private LayoutInflater mInflater;
    private List<CatalogPageModel> mCatalogList;

    public CatalogAdapter(LayoutInflater inflater, List<CatalogPageModel> mCatalogList) {
        mInflater = inflater;
        this.mCatalogList = mCatalogList;
    }

    @Override
    public int getCount() {
        return mCatalogList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = mInflater.inflate(R.layout.adapter_catalog, collection, false);
        ImageView mCatalogImageView = (ImageView) view.findViewById(R.id.viewpager_catalog_imgview);
        GoMobileApp.getmCacheManager().loadImageWithCenterCrop(Uri.parse(mCatalogList.get(position).getTitle()), mCatalogImageView, null);
        collection.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void updateCatalog(ArrayList<CatalogPageModel> catalogPageModels) {
        mCatalogList = catalogPageModels;
        notifyDataSetChanged();
    }
}
