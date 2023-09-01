package com.growonline.gomobishop.control;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.growonline.gomobishop.R;

public class StoreCarouselPageTransformer implements ViewPager.PageTransformer {

    private static final float MAX_ALPHA = 0.5f;
    private static final float MIN_ALPHA = 0.8f;
    private static final float MIN_SCALE = 0.9f;
    private int mVerticalStoreFocusOutDistance;

    public StoreCarouselPageTransformer(int verticalStoreFocusOutDistance) {
        mVerticalStoreFocusOutDistance = verticalStoreFocusOutDistance;
    }

    public StoreCarouselPageTransformer() {

    }

    @Override
    public void transformPage(View view, float position) {

        //view.setLayerType(View.LAYER_TYPE_NONE,null);

        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        ViewPager parent = (ViewPager) view.getParent();
        position -= parent.getPaddingLeft() / (float) pageWidth;


        //float alpha = min(position < 0 ? 1f - position * -1 : 1f - position, MIN_ALPHA);
        float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));

        /*View header = view.findViewById(R.id.header);
        header.setTranslationX(position > 0 ? position * pageWidth / 2 : position * pageWidth);
        header.setAlpha(alpha);*/

        RelativeLayout panel = (RelativeLayout) view.findViewById(R.id.layout_root);
        panel.setPivotX(pageWidth / 2);
        panel.setPivotY(pageHeight / 2);
        if (scaleFactor != Float.NEGATIVE_INFINITY) {
            panel.setScaleX(scaleFactor);
            panel.setScaleY(scaleFactor);
        }
        //panel.setTranslationY(Math.abs(position * mVerticalStoreFocusOutDistance));

    }

    private float min(float val, float min) {
        return val < min ? min : val;
    }

    private float max(float val, float max) {
        return val > max ? max : val;
    }
}
