package com.growonline.gomobishop.scroller.calculation;

/**
 * Created by asifrizvi on 8/25/2018.
 */

public class VerticalScrollBoundsProvider {

    private final float mMinimumScrollY;
    private final float mMaximumScrollY;

    public VerticalScrollBoundsProvider(float minimumScrollY, float maximumScrollY) {
        mMinimumScrollY = minimumScrollY;
        mMaximumScrollY = maximumScrollY;
    }

    public float getMinimumScrollY() {
        return mMinimumScrollY;
    }

    public float getMaximumScrollY() {
        return mMaximumScrollY;
    }
}