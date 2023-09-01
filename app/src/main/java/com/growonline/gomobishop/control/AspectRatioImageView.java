package com.growonline.gomobishop.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.growonline.gomobishop.R;

public class AspectRatioImageView extends ImageView {

    private float mHeightRatio = 1f;

    public AspectRatioImageView(Context context) {
        super(context);
        init(context, null);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context context, AttributeSet attrs){

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioFrameLayout);
        if (ta != null) {
            mHeightRatio = ta.getFloat(R.styleable.AspectRatioFrameLayout_heightRatio, 1f);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);

        Double h = Math.floor(originalWidth * mHeightRatio);
        int calculatedHeight = h.intValue();

        super.onMeasure(MeasureSpec.makeMeasureSpec(originalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(calculatedHeight, MeasureSpec.EXACTLY));
    }

    public void setHeightRatio(float mHeightRatio) {
        this.mHeightRatio = mHeightRatio;
    }
}
