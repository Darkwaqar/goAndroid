package com.growonline.gomobishop.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.control.HostFragment;
import com.growonline.gomobishop.fragment.AboutUsFragment;
import com.growonline.gomobishop.fragment.CollectionFragment;
import com.growonline.gomobishop.fragment.DiscoverFeatureProductListingFragment;
import com.growonline.gomobishop.fragment.DiscoverFragment;
import com.growonline.gomobishop.fragment.DiscoverFragmentProductsAndBanner;
import com.growonline.gomobishop.fragment.ProductListingFragment;
import com.growonline.gomobishop.model.TabFragment;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentsAdapter extends FragmentStateAdapter {

    private Vendor mVendor;
    private Context mContext;
    private List<TabFragment> mFragments = new ArrayList<>();

    public MainFragmentsAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle, Context baseContext, Vendor vendor) {
        super(fm, lifecycle);
        this.mVendor = vendor;
        this.mContext = baseContext;
        initializeFragments();
    }

    private void initializeFragments() {

        mFragments.add(new TabFragment(mContext.getResources().getString(R.string.collection_tab_title), HostFragment.newInstance(CollectionFragment.newInstance(mVendor))));
        if (mVendor.getMobileAppSetting().getTabAnimation() == AppConstant.BOX_ANIMATION) {
            mFragments.add(new TabFragment(mContext.getResources().getString(R.string.home_tab_title), HostFragment.newInstance(DiscoverFragment.newInstance(mVendor))));
        } else if (mVendor.getMobileAppSetting().getTabAnimation() == AppConstant.BANNER_ANIMATION) {
            mFragments.add(new TabFragment(mContext.getResources().getString(R.string.home_tab_title), HostFragment.newInstance(DiscoverFragmentProductsAndBanner.newInstance(mVendor))));
        } else {
            mFragments.add(new TabFragment(mContext.getResources().getString(R.string.home_tab_title), HostFragment.newInstance(DiscoverFeatureProductListingFragment.newInstance(mVendor))));
        }
        mFragments.add(new TabFragment(mContext.getResources().getString(R.string.product_tab_title), HostFragment.newInstance(ProductListingFragment.newInstance(mVendor, 0, AppConstant.ProductListFragmentMode.ALL_PRODUCT))));

        if (mVendor.getMobileAppSetting().getNewTabEnabled())
            mFragments.add(new TabFragment(mContext.getResources().getString(R.string.latest_tab_title), HostFragment.newInstance(ProductListingFragment.newInstance(mVendor, 0, AppConstant.ProductListFragmentMode.WHATS_NEW))));

        if (mVendor.getMobileAppSetting().getSalesTabEnabled())
            mFragments.add(new TabFragment(mContext.getResources().getString(R.string.sale_tab_title), HostFragment.newInstance(ProductListingFragment.newInstance(mVendor, 0, AppConstant.ProductListFragmentMode.SALE))));
        if (mVendor.getMobileAppSetting().getAboutUsEnabled())
            mFragments.add(new TabFragment(mContext.getResources().getString(R.string.about_tab_title), HostFragment.newInstance(AboutUsFragment.newInstance(mVendor))));
    }

    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
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
}
