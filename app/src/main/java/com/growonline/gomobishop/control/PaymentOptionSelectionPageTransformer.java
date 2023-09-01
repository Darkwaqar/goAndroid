package com.growonline.gomobishop.control;

import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.growonline.gomobishop.R;

public class PaymentOptionSelectionPageTransformer implements ViewPager.PageTransformer {

    private View mWaterMark;

    public PaymentOptionSelectionPageTransformer() {

    }

    public PaymentOptionSelectionPageTransformer(View waterMark) {
        mWaterMark = waterMark;
    }

    @Override
    public void transformPage(View page, float position) {

        ViewPager parent = (ViewPager) page.getParent();
        position -= parent.getPaddingLeft() / (float) page.getWidth();

        float checkMarkScale = 1 - Math.abs(position);
        ImageView checkMark = (ImageView) page.findViewById(R.id.img_option_check);
        checkMark.setPivotX(checkMark.getWidth() / 2);
        checkMark.setPivotY(checkMark.getHeight() / 2);
        checkMark.setScaleX(checkMarkScale);
        checkMark.setScaleY(checkMarkScale);


        if(mWaterMark != null){
            mWaterMark.setPivotX(mWaterMark.getWidth() / 2);
            mWaterMark.setPivotY(mWaterMark.getHeight() / 2);
            mWaterMark.setScaleX(checkMarkScale);
            mWaterMark.setScaleY(checkMarkScale);
            mWaterMark.setAlpha(position);
        }
    }


}
