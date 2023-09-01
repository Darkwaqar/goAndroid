package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.growonline.gomobishop.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterTutorial extends PagerAdapter {

    private List<Integer> mTutorialResources = new ArrayList<>();
    private Context mContext;
    private boolean mStartupMode = false;

    public AdapterTutorial(Context context, List<Integer> tutorialResources) {
        mTutorialResources = tutorialResources;
        mContext = context;
        mStartupMode = false;
    }

    public AdapterTutorial(Context context, List<Integer> tutorialResources, boolean startUpMode) {
        mTutorialResources = tutorialResources;
        mContext = context;
        mStartupMode = startUpMode;
    }

    @Override
    public int getCount() {
        if (mStartupMode)
            return mTutorialResources.size() + 1;
        else
            return mTutorialResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (position < mTutorialResources.size()) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.single_tutorial_layout, container, false);

            ImageView img = (ImageView) layout.findViewById(R.id.single_image);
            img.setImageResource(mTutorialResources.get(position));
            container.addView(layout);
            return layout;
        } else {
            View v = new View(mContext);
            v.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            container.addView(v);
            return v;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }


}
