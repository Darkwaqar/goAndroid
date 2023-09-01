package com.growonline.gomobishop.control.multisnaprecyclerview;

/**
 * Created by asifrizvi on 9/4/2019.
 */

/**
 * supported gravity
 **/
enum SnapGravity {

    /**
     * gravity to center
     */
    CENTER(0),

    /**
     * gravity to start (left or top)
     */
    START(1),

    /**
     * gravity to start (right or bottom)
     */
    END(2);

    private int value;

    SnapGravity(int value) {
        this.value = value;
    }

    public static SnapGravity valueOf(int value) {
        for (SnapGravity gravity : SnapGravity.values()) {
            if (gravity.getValue() == value) {
                return gravity;
            }
        }
        throw new IllegalArgumentException("no such enum object for the value: " + value);
    }

    public int getValue() {
        return this.value;
    }

}