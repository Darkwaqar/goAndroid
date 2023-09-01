package com.growonline.gomobishop.control;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class AdapterLinearLayout extends LinearLayout {
    private BaseAdapter mAdapter;

    public AdapterLinearLayout(Context context) {
        super(context);
    }

    public AdapterLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdapterLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AdapterLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAdapter(BaseAdapter adapter) {
        if (adapter == null) return;

        this.mAdapter = adapter;

        removeAllViews();

        for (int i = 0; i < adapter.getCount(); i++) {
            addView(adapter.getView(i, null, this));
        }
    }

    public BaseAdapter getmAdapter() {
        return mAdapter;
    }
}