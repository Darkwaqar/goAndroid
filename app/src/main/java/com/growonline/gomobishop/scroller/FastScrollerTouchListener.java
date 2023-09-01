package com.growonline.gomobishop.scroller;

/**
 * Created by asifrizvi on 8/25/2018.
 */

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import androidx.annotation.Nullable;

import com.growonline.gomobishop.scroller.sectionindicator.SectionIndicator;

/**
 * Touch listener that will move a {@link AbsRecyclerViewFastScroller}'s handle to a specified offset along the scroll bar
 */
class FastScrollerTouchListener implements OnTouchListener {

    private final AbsRecyclerViewFastScroller mFastScroller;

    public FastScrollerTouchListener(AbsRecyclerViewFastScroller fastScroller) {
        mFastScroller = fastScroller;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        SectionIndicator sectionIndicator = mFastScroller.getSectionIndicator();
        showOrHideIndicator(sectionIndicator, event);

        float scrollProgress = mFastScroller.getScrollProgress(event);
        mFastScroller.scrollTo(scrollProgress, true);
        mFastScroller.moveHandleToPosition(scrollProgress);
        return true;
    }

    private void showOrHideIndicator(@Nullable SectionIndicator sectionIndicator, MotionEvent event) {
        if (sectionIndicator == null) {
            return;
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                sectionIndicator.animateAlpha(1f);
                return;
            case MotionEvent.ACTION_UP:
                sectionIndicator.animateAlpha(0f);
        }
    }

}