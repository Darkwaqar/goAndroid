package com.growonline.gomobishop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.growonline.gomobishop.adapter.ReviewFragmentAdapter;

public class ReviewsActivity extends BaseActivity {
    private ViewPager2 mainPager;
    private ReviewFragmentAdapter reviewFragmentAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        DisableSecondTitleRow();
        mTabLayout = (TabLayout) findViewById(R.id.review_tab_bar);
        mainPager = (ViewPager2) findViewById(R.id.review_fragment_container);

        reviewFragmentAdapter = new ReviewFragmentAdapter(getSupportFragmentManager(), getLifecycle(), getBaseContext(), mVendor);
        mainPager.setAdapter(reviewFragmentAdapter);
        mainPager.setOffscreenPageLimit(2);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mTabLayout, mainPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(reviewFragmentAdapter.getPageTitle(position));
            }
        });
        tabLayoutMediator.attach();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                finish();
                startActivity(getIntent());
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }//on
}
