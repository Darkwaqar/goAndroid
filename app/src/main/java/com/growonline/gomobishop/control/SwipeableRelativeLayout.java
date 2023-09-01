package com.growonline.gomobishop.control;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.core.view.GestureDetectorCompat;


public class SwipeableRelativeLayout extends RelativeLayout implements GestureDetector.OnGestureListener {

    private final String TAG = SwipeableRelativeLayout.class.getSimpleName();
    private static int mTouchSlop;
    private static final float SCROLL_COMPLETION_LIMIT = 0.6f;
    private ViewConfiguration vc = ViewConfiguration.get(this.getRootView().getContext());

    private float mPosition;
    private Activity mContext;

    int mLeft = 0, mRight = 1, mTop = 2, mBottom = 3, mDirection;
    boolean isScrolling, mEnableSliding = true;
    //private boolean mEnableSlideShow = false;

    public boolean isEnableSwipe() {
        return mEnableSliding;
    }

    public void setEnableSwipe(boolean mEnableSliding) {
        this.mEnableSliding = mEnableSliding;
    }

    /*public boolean isEnableSlideShow() {
        return mEnableSlideShow;
    }

    public void setEnableSlideShow(boolean enableSlideShow) {
        this.mEnableSlideShow = enableSlideShow;

        if(enableSlideShow){
            startSlideShowTimer();
        } else {
            resetSlideShowTimer();
        }
    }*/

    private SwipeableRelativeLayoutListener mSwipeableRelativeLayoutListener;
    private GestureDetectorCompat mGestureDetector;

    void init() {
        mTouchSlop = vc.getScaledTouchSlop();
    }

    public SwipeableRelativeLayout(Context context) {
        super(context);
        mContext = (Activity) context;
        mGestureDetector = new GestureDetectorCompat(context, this);
        init();
    }

    public SwipeableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = (Activity) context;
        mGestureDetector = new GestureDetectorCompat(context, this);
        init();
    }

    public SwipeableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = (Activity) context;
        mGestureDetector = new GestureDetectorCompat(context, this);
        init();
    }

    public void addOnSwipeListener(SwipeableRelativeLayoutListener listener) {
        mSwipeableRelativeLayoutListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mEnableSliding) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (isScrolling) {
                        updatePosition(mPosition, 1, false);
                    }

                    /*if (mEnableSlideShow) {
                        startSlideShowTimer();
                    }*/

                    if (mSwipeableRelativeLayoutListener != null)
                        mSwipeableRelativeLayoutListener.onRelease();

                    break;
                case MotionEvent.ACTION_MOVE:
                    drag(event);
                    break;
            }
            return mGestureDetector.onTouchEvent(event);

        } else {
            return false;
        }
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.e("DOWN EVENT", String.valueOf(e.getX()));
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        updatePosition(mPosition, 0, true);
        return true;
    }

    void updatePosition(float startValue, float endValue, final boolean isScrollCompleted) {

        isScrolling = true;
        ValueAnimator va = ValueAnimator.ofFloat(startValue, endValue);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (mSwipeableRelativeLayoutListener != null) {
                    mPosition = (Float) animation.getAnimatedValue();

                    if (mDirection == mRight) {
                        mSwipeableRelativeLayoutListener.onSlideRight(mPosition);
                    } else if (mDirection == mLeft) {
                        mSwipeableRelativeLayoutListener.onSlideLeft(mPosition);
                    } else if (mDirection == mTop) {
                        mSwipeableRelativeLayoutListener.onSlideUp(mPosition);
                    } else if (mDirection == mBottom) {
                        mSwipeableRelativeLayoutListener.onSlideDown(mPosition);
                    }
                }
            }
        });
        va.start();

        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isScrolling = false;

                if (isScrollCompleted) {
                    if (mSwipeableRelativeLayoutListener != null) {
                        mSwipeableRelativeLayoutListener.onScrollCompleted();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public interface SwipeableRelativeLayoutListener {

        void onSlideRight(float position);

        void onSlideLeft(float position);

        void onSlideUp(float position);

        void onSlideDown(float position);

        void onPress();

        void onRelease();

        void onScrollCompleted();
    }

    private float mLastMotionX, mLastMotionY, mLastX, mStartY, mStartX, mLastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mEnableSliding) {
            // In general, we don't want to intercept touch events. They should be
            // handled by the child view.
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    /*if (mEnableSlideShow) {
                        resetSlideShowTimer();
                    }*/

                    if (mSwipeableRelativeLayoutListener != null)
                        mSwipeableRelativeLayoutListener.onPress();

                    mLastMotionX = event.getX();
                    mLastMotionY = event.getY();
                    mStartX = mLastX;
                    mStartY = mLastY;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    isScrolling = false;

                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = event.getX();
                    float y = event.getY();

                    float deltaY = y - mLastMotionY;
                    float deltaX = x - mLastMotionX;

                    if (Math.abs(deltaX) > Math.abs(deltaY)) {
                        if (Math.abs(deltaX) > mTouchSlop) {
                            mStartX = x;
                            mStartY = y;
                            return true;
                        }
                    } else {
                        if (Math.abs(deltaY) > mTouchSlop) {
                            isScrolling = true;
                            mStartX = x;
                            mStartY = y;
                            return true;
                        }
                    }
                    break;
            }

            return false;
        } else {
            return false;
        }
    }

    void drag(MotionEvent e2) {
        try {
            isScrolling = true;

            float deltaX = e2.getX() - mLastMotionX;
            float deltaY = e2.getY() - mLastMotionY;

            Log.e("MLAST Y", String.valueOf(mLastMotionY));

            Log.e("DELTA X", String.valueOf(deltaX));
            Log.e("DELTA Y", String.valueOf(deltaY));

            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (Math.abs(deltaX) > mTouchSlop) {

                    float startX = mLastMotionX;
                    float scrollingX = e2.getX();
                    float startWidth = this.getWidth();

                    float distanceMeasuredX = Math.abs(scrollingX - startX);

                    mPosition = 1 - ((distanceMeasuredX / startWidth * 100) / 100);

                    if (deltaX > 0) {

                        mDirection = mRight;

                        if (mSwipeableRelativeLayoutListener != null)
                            mSwipeableRelativeLayoutListener.onSlideRight(mPosition);

                    } else {

                        mDirection = mLeft;

                        if (mSwipeableRelativeLayoutListener != null)
                            mSwipeableRelativeLayoutListener.onSlideLeft(mPosition);
                    }
                }
            } else {
                if (Math.abs(deltaY) > mTouchSlop) {

                    float startY = mLastMotionY;
                    float scrollingY = e2.getY();
                    float startHeight = this.getHeight();

                    float distanceMeasuredY = Math.abs(scrollingY - startY);
                    Log.e("DISTANCE Y", String.valueOf(distanceMeasuredY));

                    mPosition = 1 - ((distanceMeasuredY / startHeight * 100) / 100);

                    if (deltaY > 0) {

                        mDirection = mBottom;

                        if (mSwipeableRelativeLayoutListener != null)
                            mSwipeableRelativeLayoutListener.onSlideDown(mPosition);
                    } else {

                        mDirection = mTop;

                        if (mSwipeableRelativeLayoutListener != null)
                            mSwipeableRelativeLayoutListener.onSlideUp(mPosition);
                    }
                }
            }
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage());
        }
    }
}