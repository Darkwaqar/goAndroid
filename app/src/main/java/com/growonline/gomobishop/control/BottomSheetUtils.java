package com.growonline.gomobishop.control;

/**
 * Created by asifrizvi on 9/27/2018.
 */

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager2.widget.ViewPager2;

public final class BottomSheetUtils {

    public static void setupViewPager(ViewPager2 viewPager) {
        final View bottomSheetParent = findBottomSheetParent(viewPager);
        if (bottomSheetParent != null) {
            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    viewPager.post(new Runnable() {
                        @Override
                        public void run() {
                            ViewPagerBottomSheetBehavior.from(bottomSheetParent).invalidateScrollingChild();
                        }
                    });
                }
            });
        }
    }

    private static View findBottomSheetParent(final View view) {
        View current = view;
        while (current != null) {
            final ViewGroup.LayoutParams params = current.getLayoutParams();
            if (params instanceof CoordinatorLayout.LayoutParams && ((CoordinatorLayout.LayoutParams) params).getBehavior() instanceof ViewPagerBottomSheetBehavior) {
                return current;
            }
            final ViewParent parent = current.getParent();
            current = parent == null || !(parent instanceof View) ? null : (View) parent;
        }
        return null;
    }

}
