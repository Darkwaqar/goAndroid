package com.growonline.gomobishop.control.ExpandingPager;

/**
 * Created by asifrizvi on 4/12/2019.
 */

import android.app.Activity;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.growonline.gomobishop.fragment.ExpandingFragment;

public class ExpandingPagerFactory {


    public static ExpandingFragment getCurrentFragment(ViewPager viewPager) {
        if (viewPager.getAdapter() instanceof ExpandingViewPagerAdapter) {
            ExpandingViewPagerAdapter adapter = (ExpandingViewPagerAdapter) viewPager.getAdapter();
            Fragment fragment = adapter.getCurrentFragment();
            if (fragment instanceof ExpandingFragment) {
                return (ExpandingFragment) fragment;
            }
        }
        return null;
    }

    public static void setupViewPager(final ViewPager viewPager) {
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        layoutParams.width = ((Activity) viewPager.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 7 * 5;
        layoutParams.height = (int) ((layoutParams.width / 0.75));

        viewPager.setOffscreenPageLimit(2);

        if (viewPager.getParent() instanceof ViewGroup) {
            ViewGroup viewParent = ((ViewGroup) viewPager.getParent());
            viewParent.setClipChildren(false);
            viewPager.setClipChildren(false);
        }

        viewPager.setPageTransformer(true, new ExpandingViewPagerTransformer());

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                ExpandingFragment expandingFragment = getCurrentFragment(viewPager);
//                if(expandingFragment != null && expandingFragment.isOpenend()){
//                    expandingFragment.close();
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    public static boolean onBackPressed(ViewPager viewPager) {
        ExpandingFragment expandingFragment = getCurrentFragment(viewPager);
        if (expandingFragment != null && expandingFragment.isOpenend()) {
            expandingFragment.close();
            return true;
        }
        return false;
    }
}
