package com.growonline.gomobishop.control.ExpandingPager;

/**
 * Created by asifrizvi on 4/12/2019.
 */

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.lang.ref.WeakReference;

public abstract class ExpandingViewPagerAdapter extends FragmentStatePagerAdapter {

    WeakReference<Fragment> currentFragmentReference;

    public ExpandingViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getCurrentFragment() {
        if(currentFragmentReference != null){
            return currentFragmentReference.get();
        }
        return null;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (getCurrentFragment() != object) {
            currentFragmentReference = new WeakReference<>((Fragment) object);
        }
    }


}
