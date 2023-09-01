package com.growonline.gomobishop.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.growonline.gomobishop.control.HostFragment;
import com.growonline.gomobishop.fragment.AboutUsFragment;
import com.growonline.gomobishop.fragment.CollectionFragment;
import com.growonline.gomobishop.fragment.DiscoverFeatureProductListingFragment;
import com.growonline.gomobishop.fragment.DiscoverFragment;
import com.growonline.gomobishop.fragment.DiscoverFragmentDoubleList;
import com.growonline.gomobishop.fragment.DiscoverFragmentProductsAndBanner;
import com.growonline.gomobishop.fragment.ProductListingFragment;
import com.growonline.gomobishop.model.TabFragment;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentsAdapter extends FragmentPagerAdapter {

    private Vendor mVendor;
    private List<TabFragment> mFragments = new ArrayList<>();

    public MainFragmentsAdapter(FragmentManager fm, Vendor vendor) {
        super(fm);
        this.mVendor = vendor;
        initializeFragments();
    }

    private void initializeFragments() {

        mFragments.add(new TabFragment(AppConstant.COLLECTION_TAB_TITLE, HostFragment.newInstance(DiscoverFragmentProductsAndBanner.newInstance(mVendor))));
        mFragments.add(new TabFragment(AppConstant.HOME_TAB_TITLE, HostFragment.newInstance(DiscoverFragmentDoubleList.newInstance(mVendor))));
        mFragments.add(new TabFragment(AppConstant.PRODUCTS_TAB_TITLE, HostFragment.newInstance(ProductListingFragment.newInstance(mVendor,0, AppConstant.ProductListFragmentMode.ALL_PRODUCT))));

        if (mVendor.getMobileAppSetting().getNewTabEnabled())
            mFragments.add(new TabFragment(AppConstant.LATEST_TAB_TITLE, HostFragment.newInstance(ProductListingFragment.newInstance(mVendor,0, AppConstant.ProductListFragmentMode.WHATS_NEW))));

        if (mVendor.getMobileAppSetting().getSalesTabEnabled())
            mFragments.add(new TabFragment(AppConstant.SALE_TAB_TITLE, HostFragment.newInstance(ProductListingFragment.newInstance(mVendor,0, AppConstant.ProductListFragmentMode.SALE))));

//        mFragments.add(new TabFragment(AppConstant.LAGAN_TAB_TITLE, HostFragment.newInstance(AboutUsFragment.newInstance(mVendor ,AppConstant.LAGAN_URL))));
        mFragments.add(new TabFragment(AppConstant.ABOUT_TAB_TITLE, HostFragment.newInstance(AboutUsFragment.newInstance(mVendor,AppConstant.ABOUT_MOAZZAM_URL))));
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }

}
