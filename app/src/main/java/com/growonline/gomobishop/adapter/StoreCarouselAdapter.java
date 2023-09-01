package com.growonline.gomobishop.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class StoreCarouselAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public StoreCarouselAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragments.size();
    }

    public void addAll(List<Fragment> fragments) {
        this.mFragments.addAll(fragments);
        notifyDataSetChanged();
    }

}
