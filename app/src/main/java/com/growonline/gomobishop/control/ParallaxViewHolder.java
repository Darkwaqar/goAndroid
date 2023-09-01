package com.growonline.gomobishop.control;

import android.view.View;
import android.widget.ListView;

abstract class ParallaxViewHolder implements ParallaxImageView.ParallaxImageListener {

    public View itemView;
    private ParallaxImageView backgroundImage;

    public void setBackgroundImage(ParallaxImageView backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.backgroundImage.setListener(this);
    }

    private ParallaxImageView getBackgroundImage() {
        return backgroundImage;
    }

    @Override
    public int[] requireValuesForTranslate() {
        if (itemView.getParent() == null)
            return null;

        int[] itemPosition = new int[2];
        itemView.getLocationOnScreen(itemPosition);

        int[] recyclerPosition = new int[2];
        ((ListView) itemView.getParent()).getLocationOnScreen(recyclerPosition);
        return new int[]{itemView.getMeasuredHeight(), itemPosition[1], ((ListView) itemView.getParent()).getMeasuredHeight(), recyclerPosition[1]};
    }


    void animateImage() {
        getBackgroundImage().doTranslate();
    }

}
