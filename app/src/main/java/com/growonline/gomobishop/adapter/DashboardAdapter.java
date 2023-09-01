package com.growonline.gomobishop.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.growonline.gomobishop.control.HostFragment;
import com.growonline.gomobishop.fragment.FeaturedVendorFragment;
import com.growonline.gomobishop.fragment.MarketPlaceCategoryFragment;
import com.growonline.gomobishop.fragment.MyAccountFragment;
import com.growonline.gomobishop.fragment.MyStore;
import com.growonline.gomobishop.model.TabFragment;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asifrizvi on 9/23/2018.
 */

public class DashboardAdapter extends FragmentStateAdapter {
    private Context mContext;

    private List<TabFragment> mFragments = new ArrayList<>();

    public DashboardAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context baseContext, Vendor startUpStore, Boolean openStoreTab) {
        super(fragmentManager, lifecycle);
        mContext = baseContext;
//        mFragments.add(new TabFragment(AppConstant.FEATURED_VENDOR_TITLE, FeaturedVendorFragment.newInstance(AppConstant.FEATURED_VENDOR)));
        mFragments.add(new TabFragment(mContext.getResources().getString(AppConstant.ALL_VENDOR_TITLE), FeaturedVendorFragment.newInstance(AppConstant.ALL_VENDOR)));
//        mFragments.add(new TabFragment(AppConstant.SHEEARNS_TITLE, FeaturedVendorFragment.newInstance(AppConstant.SHE_EARNS)));
        mFragments.add(new TabFragment(mContext.getResources().getString(AppConstant.CATEGORY_TITLE), HostFragment.newInstance(MarketPlaceCategoryFragment.newInstance(0, AppConstant.TYPE_CATEGORY))));
        mFragments.add(new TabFragment(mContext.getResources().getString(AppConstant.BRANDS_TITLE), FeaturedVendorFragment.newInstance(AppConstant.BRANDS)));
        mFragments.add(new TabFragment(mContext.getResources().getString(AppConstant.DESIGNER_TITLE), FeaturedVendorFragment.newInstance(AppConstant.DESIGNER)));
        mFragments.add(new TabFragment(mContext.getResources().getString(AppConstant.MY_STORE_TITLE), MyStore.newInstance(startUpStore, openStoreTab)));
        mFragments.add(new TabFragment(mContext.getResources().getString(AppConstant.MY_ACCOUNT_TITLE), MyAccountFragment.newInstance()));
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position).getFragment();
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }

    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }

}
