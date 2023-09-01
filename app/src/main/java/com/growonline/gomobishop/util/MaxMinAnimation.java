package com.growonline.gomobishop.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Basit on 9/18/2016.
 */
public class MaxMinAnimation extends Animation {

    private int startWidth;
    private int deltaWidth; // distance between start and end width
    private int startHeight;
    private int deltaHeight; // distance between start and end height
    private View view;

    /**
     * constructor, do not forget to use the setParams(int, int) method before
     * starting the animation
     * @param v
     */
    public MaxMinAnimation (View v) {
        this.view = v;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        view.getLayoutParams().width = (int) (startWidth + deltaWidth * interpolatedTime);
        view.getLayoutParams().height = (int) (startHeight + deltaHeight * interpolatedTime);
        view.requestLayout();
    }

    /**
     * set the starting and ending height for the resize animation
     * starting height is usually the views current height, the end height is the height
     * we want to reach after the animation is completed
     * @param startWidth in pixels
     * @param endWidth in pixels*
     * @param startHeight height in pixels
     * @param endHeight in pixels
     */
    public void setParams(int startWidth, int endWidth, int startHeight, int endHeight) {

        this.startWidth = startWidth;
        deltaWidth = endWidth - startWidth;

        this.startHeight = startHeight;
        deltaHeight = endHeight - startHeight;
    }

    /**
     * set the duration for the hideshowanimation
     */
    @Override
    public void setDuration(long durationMillis) {
        super.setDuration(durationMillis);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }


}
