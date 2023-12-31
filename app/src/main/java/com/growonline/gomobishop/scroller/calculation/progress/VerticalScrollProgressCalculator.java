package com.growonline.gomobishop.scroller.calculation.progress;

/**
 * Created by asifrizvi on 8/25/2018.
 */

import android.view.MotionEvent;

import com.growonline.gomobishop.scroller.calculation.VerticalScrollBoundsProvider;

/**
 * Basic scroll progress calculator used to calculate vertical scroll progress from a touch event
 */
public abstract class VerticalScrollProgressCalculator implements TouchableScrollProgressCalculator {

    private final VerticalScrollBoundsProvider mScrollBoundsProvider;

    public VerticalScrollProgressCalculator(VerticalScrollBoundsProvider scrollBoundsProvider) {
        mScrollBoundsProvider = scrollBoundsProvider;
    }

    @Override
    public float calculateScrollProgress(MotionEvent event) {
        float y = event.getY();

        if (y <= mScrollBoundsProvider.getMinimumScrollY()) {
            return 0;
        } else if (y >= mScrollBoundsProvider.getMaximumScrollY()) {
            return 1;
        } else {
            return y / mScrollBoundsProvider.getMaximumScrollY();
        }
    }
}