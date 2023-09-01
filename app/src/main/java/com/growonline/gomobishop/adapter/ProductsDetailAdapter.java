package com.growonline.gomobishop.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.growonline.gomobishop.fragment.SingleProductDetailFragment;
import com.growonline.gomobishop.fragment.SingleProductDetailFragmentPush;
import com.growonline.gomobishop.model.Vendor;

import java.util.ArrayList;
import java.util.List;

public class ProductsDetailAdapter extends FragmentStatePagerAdapter {

    private List<Integer> mProductId = new ArrayList<>();
    private Vendor mVendor;
    private FragmentManager fragmentManager;
    private Fragment mCurrentFragment;

    public ProductsDetailAdapter(FragmentManager fm, List<Integer> productIds, Vendor vendor) {
        super(fm);
        fragmentManager = fm;
        mProductId = productIds;
        mVendor = vendor;
    }

    @Override
    public Fragment getItem(int position) {
        if (mVendor.getMobileAppSetting().getShopTheLookType() == 1)
            return SingleProductDetailFragmentPush.newInstance(mProductId.get(position), mVendor);
        else
            return SingleProductDetailFragment.newInstance(mProductId.get(position), mVendor);

    }

    @Override
    public int getCount() {
        return mProductId.size();
    }


    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    //...
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }
}
