package com.growonline.gomobishop.control;

import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.growonline.gomobishop.R;


class ParallaxScrollListener implements AbsListView.OnScrollListener {


    private static final float DEFAULT_ALPHA_FACTOR = -1F;
    private static final float DEFAULT_PARALLAX_FACTOR = 1.9F;
    private static final boolean DEFAULT_IS_CIRCULAR = true;
    private float parallaxFactor = DEFAULT_PARALLAX_FACTOR;
    private float alphaFactor = DEFAULT_ALPHA_FACTOR;
    private ParallaxedView parallaxedView;
    private ParallaxedView parallaxedViewSecond;
    private boolean isCircular;
    private AbsListView.OnScrollListener listener = null;
    private ListView listView;
    private int min_height, max_height, txt_min_size, txt_max_size;

    ParallaxScrollListener(ListView listView) {
        init(listView);
    }

    protected void init(ListView listView) {
        this.listView = listView;
        this.parallaxFactor = DEFAULT_PARALLAX_FACTOR;
        this.alphaFactor = DEFAULT_ALPHA_FACTOR;
        this.isCircular = DEFAULT_IS_CIRCULAR;

        min_height = listView.getResources().getDimensionPixelSize(R.dimen.singleCategoryMinHeight);
        max_height = listView.getResources().getDimensionPixelSize(R.dimen.singleCategoryMaxHeight);
        txt_min_size = 24;
        txt_max_size = 40;
    }

    void setOnScrollListener(AbsListView.OnScrollListener l) {
        this.listener = l;
    }

    protected void addParallaxedHeaderView(View v) {
        addParallaxedView(v);
    }

    protected void addParallaxedHeaderView(View v, Object data, boolean isSelectable) {
        addParallaxedView(v);
    }

    private void addParallaxedView(View v) {
        this.parallaxedView = new ListViewParallaxedItem(v);
    }

    private void parallaxScroll() {
        if (isCircular)
            circularParallax();
        else
            headerParallax();
    }

    private void circularParallax() {
        if (listView.getChildCount() > 0) {
            int top = -listView.getChildAt(0).getTop();
            if (top >= 0) {
                fillParallaxedViews();
                setFilters(top);
            }
        }
    }

    private void headerParallax() {
        if (parallaxedView != null) {
            if (listView.getChildCount() > 0) {
                int top = -listView.getChildAt(0).getTop();
                if (top >= 0) {
                    setFilters(top);
                }
            }
        }
    }

    private void setFilters(int top) {
        //parallaxedView.setOffset((float)top / parallaxFactor);

        parallaxedView.setScaleMin((float) top * 2f, min_height, max_height);
        parallaxedView.setTextScaleMin(top / 4, txt_min_size, txt_max_size);

        if (alphaFactor != DEFAULT_ALPHA_FACTOR) {
            float alpha = (top <= 0) ? 1 : (100 / ((float) top * alphaFactor));
            parallaxedView.setAlpha(alpha);
        }
        parallaxedView.animateNow();


        parallaxedViewSecond.setScale((float) top * 2f, min_height, max_height);
        parallaxedViewSecond.setTextScale((float) top / 4, txt_min_size, txt_max_size);

        if (alphaFactor != DEFAULT_ALPHA_FACTOR) {
            float alpha = (top <= 0) ? 1 : (100 / ((float) top * alphaFactor));
            parallaxedViewSecond.setAlpha(alpha);
        }
        parallaxedViewSecond.animateNow();
    }

    private void fillParallaxedViews() {
        if (parallaxedView == null || !parallaxedView.is(listView.getChildAt(0))) {
            if (parallaxedView != null) {
                resetFilters();
                parallaxedView.setView(listView.getChildAt(0));
                parallaxedView.setTextView((TextView) listView.getChildAt(0).findViewById(R.id.lbl_category));
            } else {
                parallaxedView = new ListViewParallaxedItem(listView.getChildAt(0));
            }
        }


        if (parallaxedViewSecond == null || !parallaxedViewSecond.is(listView.getChildAt(1))) {
            if (parallaxedViewSecond != null) {
                resetFilters();
                if (listView.getChildAt(1) != null) {
                    parallaxedViewSecond.setView(listView.getChildAt(1));
                    parallaxedViewSecond.setTextView((TextView) listView.getChildAt(1).findViewById(R.id.lbl_category));
                }
            } else {
                parallaxedViewSecond = new ListViewParallaxedItem(listView.getChildAt(1));
            }
        }
    }

    private void resetFilters() {
        parallaxedView.setOffset(0);
        if (alphaFactor != DEFAULT_ALPHA_FACTOR)
            parallaxedView.setAlpha(1F);
        parallaxedView.animateNow();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        parallaxScroll();

        for (int i = 0; i < view.getChildCount(); i++) {

            if (view.getChildAt(i).getTag() != null) {
                ((ParallaxViewHolder) view.getChildAt(i).getTag()).animateImage();
            }
        }

        if (this.listener != null)
            this.listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (this.listener != null)
            this.listener.onScrollStateChanged(view, scrollState);
    }

    private class ListViewParallaxedItem extends ParallaxedView {


        ListViewParallaxedItem(View view) {
            super(view);
        }

        @Override
        protected void translatePreICS(View view, float offset) {
            addAnimation(new TranslateAnimation(0, 0, offset, offset));
        }
    }
}
