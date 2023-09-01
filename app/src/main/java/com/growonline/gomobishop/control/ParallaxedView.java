package com.growonline.gomobishop.control;

import android.annotation.SuppressLint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AbsListView;
import android.widget.TextView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

abstract class ParallaxedView {
    private static boolean isAPI11 = true;
    protected WeakReference<View> view;
    private WeakReference<TextView> textView;
    private int lastOffset;
    private List<Animation> animations;

    abstract protected void translatePreICS(View view, float offset);

    ParallaxedView(View view) {
        this.lastOffset = 0;
        this.animations = new ArrayList<>();
        this.view = new WeakReference<>(view);
        this.textView = new WeakReference<>((TextView) view.findViewById(R.id.lbl_category));
    }

    public boolean is(View v) {
        return (v != null && view != null && view.get() != null && view.get().equals(v));
    }

    @SuppressLint("NewApi")
    void setOffset(float offset) {
        View view = this.view.get();
        if (view != null)
            if (isAPI11) {
                view.setTranslationY(offset);
            } else {
                translatePreICS(view, offset);
            }
    }

    public void setAlpha(float alpha) {
        View view = this.view.get();
        if (view != null)
            if (isAPI11) {
                view.setAlpha(alpha);
            } else {
                alphaPreICS(alpha);
            }
    }

    void setScale(float scaleOffset, float min, float max) {

        Log.d("SET SCALE OFFSET", String.valueOf(scaleOffset));

        View view = this.view.get();
        if (view != null) {
            if (isAPI11) {
                int finalHeight = (int) min + (int) scaleOffset;
                if (finalHeight <= max) {
                    AbsListView.LayoutParams params = new AbsListView.LayoutParams(GoMobileApp.getScreenWidth(),
                            finalHeight);
                    view.setLayoutParams(params);
                }

                Log.d("EQUAL", String.valueOf(scaleOffset));

            } else {
                //alphaPreICS(view, alpha);
            }
        }
    }

    void setScaleMin(float scaleOffset, float min, float max) {

        Log.d("SET SCALE OFFSET", String.valueOf(scaleOffset));

        View view = this.view.get();
        if (view != null) {
            if (isAPI11) {

                //int finalHeight = view.getHeight() - (int) scaleOffset;
                int finalHeight = (int) max - (int) scaleOffset;
                if (finalHeight >= min) {
                    AbsListView.LayoutParams params = new AbsListView.LayoutParams(GoMobileApp.getScreenWidth(),
                            finalHeight);
                    view.setLayoutParams(params);
                }

            } else {
                //alphaPreICS(view, alpha);
            }
        }

    }


    void setTextScale(float scaleOffset, float min, float max) {

        TextView view = this.textView.get();
        if (view != null) {
            if (isAPI11) {

                float finalSize = min + scaleOffset;

                if (finalSize <= max)
                    view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, finalSize);
            }
        }
    }

    void setTextScaleMin(float scaleOffset, float min, float max) {

        TextView view = this.textView.get();
        if (view != null) {
            if (isAPI11) {

                float finalSize = max - scaleOffset;

                if (finalSize >= min)
                    view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, finalSize);
            }
        }
    }


    synchronized void addAnimation(Animation animation) {
        animations.add(animation);
    }

    private void alphaPreICS(float alpha) {
        addAnimation(new AlphaAnimation(alpha, alpha));
    }


    synchronized void animateNow() {
        View view = this.view.get();
        if (view != null) {
            AnimationSet set = new AnimationSet(true);
            for (Animation animation : animations)
                if (animation != null)
                    set.addAnimation(animation);
            set.setDuration(0);
            set.setFillAfter(true);
            view.setAnimation(set);
            set.start();
            animations.clear();
        }
    }

    public void setView(View view) {
        this.view = new WeakReference<>(view);
    }

    void setTextView(TextView view) {
        this.textView = new WeakReference<>(view);
    }
}

