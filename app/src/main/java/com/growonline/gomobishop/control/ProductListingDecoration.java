package com.growonline.gomobishop.control;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ProductListingDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public ProductListingDecoration(int space) {
        if (space % 2 == 0)
            this.mSpace = space;
        else
            this.mSpace = space + 1;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);

        if (position % 2 == 0) {
            outRect.right = mSpace / 2;
        } else {
            outRect.left = mSpace / 2;
        }

        outRect.bottom = mSpace;
    }


}
