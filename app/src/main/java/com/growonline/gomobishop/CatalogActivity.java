package com.growonline.gomobishop;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.adapter.CatalogAdapter;
import com.growonline.gomobishop.model.VendorCatalogList;
import com.growonline.gomobishop.util.AppConstant;

import java.util.List;

public class CatalogActivity extends BaseActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<VendorCatalogList> mCatalogList;
    private ImageView mLeftImageView;
    private ImageView mRightImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        setToolBarTitle(mVendor.getName());

        mTabLayout = (TabLayout) findViewById(R.id.app_tab_bar1);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_catalog);
        mLeftImageView = (ImageView) findViewById(R.id.imageView_catalog_left);
        mRightImageView = (ImageView) findViewById(R.id.imageView_catalog_right);
        mTabLayout.setBackgroundColor(Color.parseColor(mVendor.getThemeColor()));

        String catalogString = PreferenceManager.getDefaultSharedPreferences(CatalogActivity.this).getString(AppConstant.CATALOG_JSON, "{}");
        TypeToken<List<VendorCatalogList>> list = new TypeToken<List<VendorCatalogList>>() {
        };
        mCatalogList = new Gson().fromJson(catalogString, list.getType());

        createTabs();

        mRightImageView.setOnClickListener(this);
        mLeftImageView.setOnClickListener(this);

        DisableSecondTitleRow();

        mTabLayout.setOnTabSelectedListener(this);

        int pos = 0;
        if (getIntent().hasExtra("pos"))
            pos = getIntent().getIntExtra("pos", 0);

        if (pos != 0)
            mTabLayout.getTabAt(pos).select();
        else
            onTabSelected(mTabLayout.getTabAt(0));
    }

    private void createTabs() {

        for (int i = 0; i < mCatalogList.size(); i++)
            mTabLayout.addTab(mTabLayout.newTab().setText(mCatalogList.get(i).getmTitle()));
    }

    @Override
    public void onClick(View view) {

        if (view == mLeftImageView) {
            if (mViewPager.getCurrentItem() > -1)
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
        } else if (view == mRightImageView) {
            if (mViewPager.getCurrentItem() < (mViewPager.getAdapter().getCount() - 1))
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int pos = tab.getPosition();

        if (mCatalogList != null && mCatalogList.size() > pos) {
            if (mViewPager.getAdapter() == null) {
                mViewPager.setAdapter(new CatalogAdapter(getLayoutInflater(), mCatalogList.get(pos).getmCatalogPages()));
            } else {
                mViewPager.setAdapter(null);
                mViewPager.setAdapter(new CatalogAdapter(getLayoutInflater(), mCatalogList.get(pos).getmCatalogPages()));
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}