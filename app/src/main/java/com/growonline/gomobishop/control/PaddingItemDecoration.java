package com.growonline.gomobishop.control;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by asifrizvi on 12/19/2019.
 */

public class PaddingItemDecoration extends RecyclerView.ItemDecoration {
    private final int size;

    public PaddingItemDecoration(int size) {
        this.size = size;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Apply offset only to first item
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left += size;
        }
    }
}