package com.growonline.gomobishop.scroller;

/**
 * Created by asifrizvi on 8/25/2018.
 */

import android.content.Context;
import android.util.AttributeSet;

import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.scroller.sectionindicator.title.SectionTitleIndicator;

/**
 * Indicator for sections of type {@link Vendor}
 */
public class VendorTitleIndicator extends SectionTitleIndicator<Vendor> {

    public VendorTitleIndicator(Context context) {
        super(context);
    }

    public VendorTitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VendorTitleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSection(Vendor vendor) {
        // Example of using a single character
        setTitleText(vendor.getName().charAt(0) + "");
    }
}
