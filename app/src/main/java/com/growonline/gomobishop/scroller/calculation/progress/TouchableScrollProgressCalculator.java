package com.growonline.gomobishop.scroller.calculation.progress;

/**
 * Created by asifrizvi on 8/25/2018.
 */

import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Assists in calculating the amount of scroll progress for a {@link RecyclerView} based on a {@link MotionEvent}
 */
public interface TouchableScrollProgressCalculator extends ScrollProgressCalculator {

    /**
     * Calculates the scroll progress of a RecyclerView based on a motion event from a scroller
     * @param event for which to calculate scroll progress
     * @return fraction from [0 to 1] representing the scroll progress
     */
    public float calculateScrollProgress(MotionEvent event);

}