package com.growonline.gomobishop.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.growonline.gomobishop.control.HostFragment;
import com.growonline.gomobishop.fragment.ReviewFragment;
import com.growonline.gomobishop.model.TabFragment;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asifrizvi on 4/7/2019.
 */

public class ReviewFragmentAdapter extends FragmentStateAdapter {

    private List<TabFragment> mFragments = new ArrayList<>();
    private Context mContext;

    public ReviewFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context baseContext, Vendor mVendor) {
        super(fragmentManager, lifecycle);

        initializeFragments(mVendor);
    }

    private void initializeFragments(Vendor mVendor) {
        mFragments.add(new TabFragment(mContext.getResources().getString(AppConstant.TAB_PRODUCT), HostFragment.newInstance(ReviewFragment.newInstance(AppConstant.TYPE_PRODUCT, mVendor))));
        mFragments.add(new TabFragment(mContext.getResources().getString(AppConstant.TAB_VENDOR), HostFragment.newInstance(ReviewFragment.newInstance(AppConstant.TYPE_VENDOR, mVendor))));
        mFragments.add(new TabFragment(mContext.getResources().getString(AppConstant.TAB_PENDING), HostFragment.newInstance(ReviewFragment.newInstance(AppConstant.TYPE_PENDING, mVendor))));
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
