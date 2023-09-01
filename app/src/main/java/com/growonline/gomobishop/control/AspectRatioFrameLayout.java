package com.growonline.gomobishop.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.growonline.gomobishop.R;

public class AspectRatioFrameLayout extends FrameLayout {

    private float mHeightRatio = 1f;
    private float mWidthRatio = 0f;

    public AspectRatioFrameLayout(Context context) {
        super(context);
        init(context, null);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context context, AttributeSet attrs){

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioFrameLayout);
        if (ta != null) {
            mHeightRatio = ta.getFloat(R.styleable.AspectRatioFrameLayout_heightRatio, 1f);
            mWidthRatio = ta.getFloat(R.styleable.AspectRatioFrameLayout_widthRatio, 0f);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mWidthRatio > 0f) {

            int originalHeight = MeasureSpec.getSize(heightMeasureSpec);

            Double w = Math.floor(originalHeight * mWidthRatio);
            int calculatedWidth = w.intValue();

            super.onMeasure(MeasureSpec.makeMeasureSpec(calculatedWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(originalHeight, MeasureSpec.EXACTLY));

        } else {
            int originalWidth = MeasureSpec.getSize(widthMeasureSpec);

            Double h = Math.floor(originalWidth * mHeightRatio);
            int calculatedHeight = h.intValue();

            super.onMeasure(MeasureSpec.makeMeasureSpec(originalWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(calculatedHeight, MeasureSpec.EXACTLY));
        }
    }

}
