package com.growonline.gomobishop.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.growonline.gomobishop.control.ExpandingPager.ExpandingViewPagerAdapter;
import com.growonline.gomobishop.fragment.ShopTheLookFragment;
import com.growonline.gomobishop.model.BeanProductDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asifrizvi on 4/12/2019.
 */

public class ShopTheLookViewPagerAdapter extends ExpandingViewPagerAdapter {

    private List<BeanProductDetail> Products;

    public ShopTheLookViewPagerAdapter(FragmentManager fm) {
        super(fm);
        Products = new ArrayList<>();
    }

    public void addAll(List<BeanProductDetail> Products) {
        this.Products.addAll(Products);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return ShopTheLookFragment.newInstance(Products.get(position));
    }

    @Override
    public int getCount() {
        return Products.size();
    }

}