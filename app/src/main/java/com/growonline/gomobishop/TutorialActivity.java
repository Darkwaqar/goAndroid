package com.growonline.gomobishop;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.growonline.gomobishop.adapter.AdapterTutorial;

import java.util.ArrayList;
import java.util.List;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Integer> mTutorialResources;
    private boolean mStartUpMode = false;
    private TextView lbl_counter;
    private TextView lbl_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Intent i = getIntent();
        mStartUpMode = i.getBooleanExtra("startUpMode", false);

        initUi();
    }

    void initUi() {
        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        lbl_counter = (TextView) findViewById(R.id.lbl_pager_counter);
        lbl_skip = (TextView) findViewById(R.id.lbl_pager_skip);

        mTutorialResources = new ArrayList<>();
        mTutorialResources.add(R.drawable.help_two);
        mTutorialResources.add(R.drawable.help_three);
        mTutorialResources.add(R.drawable.help_four);
        mTutorialResources.add(R.drawable.help_one);




        lbl_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartUpMode) {
                    launchSplashActivity();
                } else {
                    finish();
                }
            }
        });
        AdapterTutorial adapter = new AdapterTutorial(this, mTutorialResources, mStartUpMode);
        mViewPager.setAdapter(adapter);

        createPageSelector(mTutorialResources.size(), 0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mStartUpMode) {

                    if (position == mTutorialResources.size()) {
                        launchSplashActivity();
                    }
                }

                if (position < mTutorialResources.size())
                    createPageSelector(mTutorialResources.size(), position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void launchSplashActivity() {
        Intent currentIntent = getIntent();
        Intent i = new Intent(TutorialActivity.this, SplashScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (currentIntent.getData() != null) {
            i.setData(currentIntent.getData());
        }
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    void createPageSelector(int pagesCount, int selectedPage) {
        selectedPage = selectedPage + 1;
        String pagesText = "";
        for (int i = 1; i <= pagesCount; i++) {
            pagesText += String.valueOf(i) + " ";
        }
        pagesText = pagesText.trim();
        int selectedPageIndexInFinalString = pagesText.indexOf(String.valueOf(selectedPage));
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(pagesText);
        sBuilder.setSpan(new StyleSpan(Typeface.BOLD),
                selectedPageIndexInFinalString,
                selectedPageIndexInFinalString + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        lbl_counter.setText(sBuilder);
    }

}
