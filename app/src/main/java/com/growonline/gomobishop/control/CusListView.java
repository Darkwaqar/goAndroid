package com.growonline.gomobishop.control;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by imac on 22/10/16.
 */
public class CusListView extends LinearLayout implements GestureDetector.OnGestureListener{
    public static final String TAG = "CustomListView";
    private GestureDetector mGeastureDetector;
    private ParallaxBaseAdapter mBaseAdapter;
    private int mMaxHeight = 500;
    private int mMinHeight = 300;

    private final int SWIPE_MIN_DISTANCE = 5;
    private final int SWIPE_THRESHOLD_VELOCITY = 20;
    private float mStartY, mLastMotionY, mLastMotionX;
    private float mStartX;
    private float mLastX;
    private float mLastY;
    private int mCurrentPage;

    public CusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    private void Init() {
        setOrientation(VERTICAL); mGeastureDetector = new GestureDetector(getContext(), this);
    }

    public void setAdapter(ParallaxBaseAdapter adapter) {
        removeAllViews();
        mBaseAdapter = adapter;
        if (adapter == null) {
            return;
        }
        reload();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {


        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("asd","asd");

        switch (event.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_DOWN:
                break;
        }
        mGeastureDetector.onTouchEvent(event);
        return true;//super.onTouchEvent(event);
    }

    private void setChildHeight(int pos, int height) {
        getChildAt(pos).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
    }

    private int getChildHeight(int pos) {
        Log.e(TAG, "height:" + getChildAt(pos).getLayoutParams().height);
        return getChildAt(pos).getLayoutParams().height;
    }

    private void reload() {
        for (int i = 0; i < mBaseAdapter.getCount(); i++) {
            View layout = mBaseAdapter.getView(i, (i < getChildCount()) ? getChildAt(i) : null, CusListView.this);

            ((ImageView) layout.findViewById(mBaseAdapter.getParallaxViewId(i))).setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (i == 0) {
                layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mMaxHeight));
            } else {
                layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mMinHeight));
            }
            addView(layout);
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    private boolean isRestrictedArea(View mNextView) {
        int mNextItemHeight = mNextView.getLayoutParams().height;
        float mNextScrollTop = mNextView.getTop();
        float mRestrictedArea = (2 * mNextScrollTop) - mNextItemHeight;
        return mRestrictedArea <= mMaxHeight;
    }

    private int mFirstItem, mSecItem;

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY) {

//        float deltaY = motionEvent.getY() - motionEvent1.getY();
//        Log.e("deltaY: ", deltaY + "");
//        if (deltaY > 0)
//        {
//            Log.e("direction: ", "Down to Top");
//            if (mFirstItem < getChildCount())
//            {
//                //!-- not a last item
//                Log.e("getChildHeight(mFirstItem): ", "" + getChildHeight(mFirstItem));
//                int nextHeight = getChildHeight(mFirstItem) - Math.round(velocityY);
//
//                if (getChildHeight(mFirstItem) > 0 && nextHeight > 0)
//                {
//                    setChildHeight(mFirstItem, nextHeight);
//
//                    //!-- Handling next item
//                    View nextView = getChildAt(mFirstItem + 1);
//                    if (nextView != null)
//                    {
//                        int mNextHeight = getChildHeight(mFirstItem + 1);
//                        if (isRestrictedArea(nextView)) {
//                            int heightToBeSetOnNextItem = mNextHeight + Math.round(velocityY);
//                            setChildHeight(mFirstItem + 1, (heightToBeSetOnNextItem <= mMaxHeight) ? Math.round(heightToBeSetOnNextItem) : mMaxHeight);
//                        }
//                    }
//                }
//                else
//                {
//                    setChildHeight(mFirstItem, 0);
//                    mFirstItem++;
//                }
//            }
//        }
//        else
//        {
//            Log.e("direction: ", "Top to Down");
//        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}