package com.growonline.gomobishop.control;

/**
 * Created by asifrizvi on 10/22/2019.
 */

public enum Align {


    LEFT(1),
    RIGHT(-1),
    TOP(1),
    BOTTOM(-1);

    int layoutDirection;

    Align(int sign) {
        this.layoutDirection = sign;
    }
}