package com.growonline.gomobishop.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.growonline.gomobishop.R;

public class CenterLineTextView extends AppCompatTextView {

    private final Rect mBounds = new Rect();
    private final Paint mPaint = new Paint();

    private int mStroke;

    public CenterLineTextView(Context context) {
        super(context);
    }

    public CenterLineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(mStroke);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        setGravity(Gravity.CENTER);
        mStroke = getContext().getResources().getDimensionPixelSize(R.dimen.divider);
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextColor(getResources().getColor(R.color.colorPrimary));
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), mBounds);
        canvas.drawLine(0, (float) getHeight() / 2, (float) (getWidth() - mBounds.right) / 2, (float) getHeight() / 2, mPaint);
        canvas.drawLine((float) (getWidth() + mBounds.right) / 2, (float) getHeight() / 2, getWidth(), (float) getHeight() / 2, mPaint);
    }
}