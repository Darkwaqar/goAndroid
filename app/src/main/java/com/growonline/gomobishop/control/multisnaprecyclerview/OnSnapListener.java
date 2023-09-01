package com.growonline.gomobishop.control.multisnaprecyclerview;

/**
 * Created by asifrizvi on 9/4/2019.
 */

/**
 * Listener to notify snapping
 **/
public interface OnSnapListener {

    /**
     * Called when RecyclerView is snapped
     *
     * @param position snapped position
     */
    void snapped(int position);
}