package com.growonline.gomobishop.control.multisnaprecyclerview;

/**
 * Created by asifrizvi on 9/4/2019.
 */

import android.view.View;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * {@link SnapHelperDelegator} for end gravity
 **/
class EndSnapHelperDelegator extends SnapHelperDelegator {

    /**
     * Constructor
     *
     * @param snapCount the number of items to scroll over
     */
    EndSnapHelperDelegator(int snapCount) {
        super(snapCount);
    }

    @Override
    int getDistance(RecyclerView.LayoutManager layoutManager, View targetView,
                    OrientationHelper helper) {
        final int childEnd = getChildPosition(targetView, helper);
        final int containerEnd = getContainerPosition(layoutManager, helper);
        return childEnd - containerEnd;
    }

    @Override
    int getContainerPosition(RecyclerView.LayoutManager layoutManager,
                             OrientationHelper helper) {
        return layoutManager.getClipToPadding() ? helper.getStartAfterPadding() + helper.getTotalSpace()
                : helper.getEnd() - helper.getEndPadding();
    }

    @Override
    int getChildPosition(View targetView, OrientationHelper helper) {
        return helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView);
    }

    @Override
    boolean shouldSkipTarget(View targetView, RecyclerView.LayoutManager layoutManager,
                             OrientationHelper helper, boolean forwardDirection) {
        return forwardDirection ? getDistance(layoutManager, targetView, helper) < 0
                : getDistance(layoutManager, targetView, helper) > 0;
    }
}