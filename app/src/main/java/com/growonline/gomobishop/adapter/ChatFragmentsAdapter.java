package com.growonline.gomobishop.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.growonline.gomobishop.control.HostFragment;
import com.growonline.gomobishop.fragment.InboxFragment;
import com.growonline.gomobishop.model.TabFragment;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class ChatFragmentsAdapter extends FragmentStateAdapter {

    private List<TabFragment> mFragments = new ArrayList<>();
    private Context mContext;

    public ChatFragmentsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context baseContext) {
        super(fragmentManager, lifecycle);
        mContext = baseContext;
        initializeFragments();
    }

    private void initializeFragments() {
        mFragments.add(new TabFragment(mContext.getResources().getString(AppConstant.COLLECTION_TAB_TITLE), HostFragment.newInstance(InboxFragment.newInstance())));
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

