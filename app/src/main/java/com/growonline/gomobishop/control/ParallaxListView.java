package com.growonline.gomobishop.control;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ParallaxListView extends LinearLayout implements GestureDetector.OnGestureListener {
    public static final String TAG = "CustomListView";
    private ParallaxBaseAdapter mBaseAdapter;

    private int mMaxHeight = 1500;
    private int mMinHeight = 500;

    private final int SWIPE_MIN_DISTANCE = 5;
    private final int SWIPE_THRESHOLD_VELOCITY = 20;

    private GestureDetector mGeastureDetector;
    private boolean mIsFlingCalled;
    private int mAnimDuration = 300;

    public ParallaxListView(Context context) {
        super(context);
        Init(null);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(attrs);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Init(attrs);

    }


    private void Init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = null;
            try {
                a = getContext().obtainStyledAttributes(attrs, com.growonline.gomobishop.R.styleable.ParallaxHeightListView);
                mMaxHeight = a.getDimensionPixelSize(com.growonline.gomobishop.R.styleable.ParallaxHeightListView_maximum_item_height, 350);

                mMinHeight = a.getDimensionPixelSize(com.growonline.gomobishop.R.styleable.ParallaxHeightListView_minimum_item_height, 200);
                mAnimDuration = a.getInt(com.growonline.gomobishop.R.styleable.ParallaxHeightListView_anim_duration, 300);
            } finally {
                if (a != null) {
                    a.recycle();
                }
            }
        }
        setOrientation(VERTICAL);
        mGeastureDetector = new GestureDetector(getContext(), this);
    }

    public void setAdapter(ParallaxBaseAdapter adapter) {
        removeAllViews();
        mBaseAdapter = adapter;
        if (adapter == null) {
            return;
        }
        reload();
    }

    public ParallaxBaseAdapter getAdapter() {
        return mBaseAdapter;
    }

    private void reload() {
        for (int i = 0; i < mBaseAdapter.getCount(); i++) {
            View layout = mBaseAdapter.getView(i, (i < getChildCount()) ? getChildAt(i) : null, ParallaxListView.this);

            ((ImageView) layout.findViewById(mBaseAdapter.getParallaxViewId(i))).setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (i == 0) {
                layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mMaxHeight));
            } else {
                layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mMinHeight));
            }
            addView(layout);
        }
    }

    private int mCurrentPage = 0;

    private boolean isRestrictedArea(View mNextView) {
        int mNextItemHeight = mNextView.getLayoutParams().height;
        float mNextScrollTop = mNextView.getTop();
        float mRestrictedArea = (2 * mNextScrollTop) - mNextItemHeight;
        return mRestrictedArea <= mMaxHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getChildAt(mCurrentPage) == null) return false;
        boolean isEventHndled = mGeastureDetector.onTouchEvent(event);

        if (!mIsFlingCalled && event.getAction() == MotionEvent.ACTION_UP) {
            mIsFlingCalled = false;
            Log.i(TAG, "MotionEvent.ACTION_UP is called");
            ResizeAnimation animation = new ResizeAnimation(
                    getChildAt(mCurrentPage),
                    1,
                    getChildAt(mCurrentPage).getLayoutParams().height,
                    getChildAt(mCurrentPage + 1),
                    (getChildAt(mCurrentPage + 1) == null) ? 0 : getChildAt(mCurrentPage + 1).getLayoutParams().height,
                    mMaxHeight,
                    mMaxHeight);
            animation.setDuration(mAnimDuration);
            getChildAt(mCurrentPage).startAnimation(animation);

            return true;
        }
        return true;
    }

    public void setMinItemHeight(int height) {
        mMinHeight = height;
    }

    public int getMinItemHeight() {
        return mMinHeight;
    }

    public void setMaxItemHeight(int height) {
        mMaxHeight = height;
    }

    public int getMaxItemHeight() {
        return mMaxHeight;
    }

    public void setAnimDuration(int mAnimDuration) {
        this.mAnimDuration = mAnimDuration;
    }

    public int getAnimDuration() {
        return mAnimDuration;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.i(TAG, "Called:" + motionEvent);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {

        Log.i(TAG, "onSingleTapUp:" + motionEvent);

        if (mListener != null)
            for (int i = 0; i < getChildCount(); i++) {
                if (isPointInsideView(motionEvent.getRawX(), motionEvent.getRawY(), getChildAt(i))) {
                    if (i == mCurrentPage) {
                        mListener.onTap(this, i, getChildAt(i));
                    } else {
                        moveToPosition(i);
                    }
                    return true;
                }
            }

        return true;
    }

    private void moveToPosition(int pos) {

        for (int i = mCurrentPage; i < pos; i++) {
            getChildAt(i).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        }
        mCurrentPage = pos;
        getChildAt(mCurrentPage).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mMaxHeight));
    }

    /**
     * Determines if given points are inside view
     *
     * @param x    - x coordinate of point
     * @param y    - y coordinate of point
     * @param view - view object to compare
     * @return true if the points are within view bounds, false otherwise
     */
    private boolean isPointInsideView(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if ((x > viewX && x < (viewX + view.getWidth())) &&
                (y > viewY && y < (viewY + view.getHeight()))) {
            return true;
        } else {
            return false;
        }
    }

    private TapListener mListener;

    public void setOnTapListener(TapListener li) {
        mListener = li;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            //!-- performing all the calculations
            View mCurrentView = getChildAt(mCurrentPage);
            float deltaY = e1.getY() - e2.getY();
            int mCurrentHeight = mCurrentView.getLayoutParams().height;

            //!-- Handling scrolling calculations according to the directions
            if (deltaY > 0) {
                Log.i("direction: ", "Down to Top");
                if (mCurrentHeight < 1) {
                    if (mCurrentPage + 1 != getChildCount() - 1)
                        mCurrentPage += 1;

                    return true;
                }

            } else {
                Log.i("direction: ", "Top to Down");
                if (mCurrentHeight >= mMaxHeight) {
                    if (mCurrentPage - 1 != -1) {
                        mCurrentPage -= 1;
                    }
                    return true;
                }
            }

            //!-- Applying changes on the current page
            mIsFlingCalled = false;
            Log.i(TAG, "mCurrentIndex: " + mCurrentPage + " mCurrentHeight: " + mCurrentHeight);
            int heightToBeSet = mCurrentHeight - Math.round(velocityY);
            mCurrentView.getLayoutParams().height = (heightToBeSet <= 0) ? 0 : heightToBeSet;


            //!-- Calculate and applying positions on the next view
            View mNextView = getChildAt(mCurrentPage + 1);
            if (mNextView == null) return true;

            int mNextHeight = mNextView.getLayoutParams().height;
            if (isRestrictedArea(mNextView)) {
                int heightToBeSetOnNextItem = mNextHeight + Math.round(velocityY);

                if (heightToBeSetOnNextItem > mMinHeight)
                    mNextView.getLayoutParams().height = (heightToBeSetOnNextItem <= mMaxHeight) ? Math.round(heightToBeSetOnNextItem) : mMaxHeight;
                else {
                    mNextView.getLayoutParams().height = mMinHeight;
                }
            }

            //!-- Reseting LayoutParams of the below views
            for (int i = mCurrentPage + 2; i < getChildCount(); i++) {
                getChildAt(i).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mMinHeight));
            }
            requestLayout();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public interface TapListener {
        public void onTap(ParallaxListView parallaxListView, int pos, View view);
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        mIsFlingCalled = true;
        //!-- performing all the calculations
        View mCurrentView = getChildAt(mCurrentPage);
        View mNextView = getChildAt(mCurrentPage + 1);
        float deltaY = e1.getY() - e2.getY();
        int mCurrentHeight = mCurrentView.getLayoutParams().height;

        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

            if (mNextView == null) return true;

            ResizeAnimation animation = new ResizeAnimation(
                    mCurrentView,
                    1,
                    mCurrentHeight,
                    mNextView,
                    (mNextView == null) ? 0 : mNextView.getLayoutParams().height,
                    mMaxHeight, mMaxHeight);

            animation.setDuration(mAnimDuration);
            mCurrentView.startAnimation(animation);
            return true;

        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            Log.i(TAG, "Top to down");
            if (mNextView == null) return true;

            ResizeAnimation animation = new ResizeAnimation(
                    mCurrentView,
                    mMaxHeight,
                    mCurrentHeight,
                    mNextView,
                    (mNextView == null) ? 0 : mNextView.getLayoutParams().height,
                    mMinHeight,
                    mMaxHeight);

            animation.setDuration(mAnimDuration);
            mCurrentView.startAnimation(animation);
            return true;
        }
        return false;
    }
}