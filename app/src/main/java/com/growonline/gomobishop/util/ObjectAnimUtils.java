package com.growonline.gomobishop.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by imac on 08/02/17.
 */
public class ObjectAnimUtils
{
    public static final String TRANSLATION_Y = "translationY";
    private static final int DURATION = 500;

    public static void slideDown(View v, Animator.AnimatorListener listener)
    {
        v.setVisibility(View.VISIBLE);

        ObjectAnimator mover = ObjectAnimator.ofFloat(v, TRANSLATION_Y, v.getTop(), v.getBottom());
        mover.setDuration(DURATION);

        if (listener != null)
            mover.addListener(listener);

        mover.start();
    }

    public static void fadeIn(View v, Animator.AnimatorListener listener) {
        v.setVisibility(View.VISIBLE);

        ObjectAnimator mover = ObjectAnimator.ofFloat(v, View.ALPHA, 0, 1);
        mover.setDuration(DURATION);

        if (listener != null)
            mover.addListener(listener);

        mover.start();
    }

    public static void fadeOut(View v, Animator.AnimatorListener listener) {
        v.setVisibility(View.VISIBLE);
        ObjectAnimator mover = ObjectAnimator.ofFloat(v, View.ALPHA, 1, 0);
        mover.setDuration(DURATION);

        if (listener != null)
            mover.addListener(listener);

        mover.start();
    }

    public static void customAnim(View v, Animator.AnimatorListener listener, String type, int start, int end) {
        v.setVisibility(View.VISIBLE);
        ObjectAnimator mover = ObjectAnimator.ofFloat(v, type, start, end);
        mover.setDuration(DURATION);

        if (listener != null)
            mover.addListener(listener);

        mover.start();
    }
}