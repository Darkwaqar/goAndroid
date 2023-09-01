package com.growonline.gomobishop;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.adapter.RewardAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskGetRewardPoints;
import com.growonline.gomobishop.model.RewardPoint;
import com.growonline.gomobishop.model.RewardPointsHistory;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;

import java.util.ArrayList;
import java.util.List;

public class RewardPointsActivity extends BaseActivity {

    private WebView mWebView;
    private String mUrl = "";
    private RecyclerView rewardPointRecyclerView;
    private RewardAdapter mAdapter;
    private List<RewardPointsHistory> rewardPointsHistory = new ArrayList<>();
    private TextView rewardPointsBalance;
    private TextView rewardPointsAmount;
    private TextView minimumRewardPointsToUse;
    private TextView minimumRewardPointsToUseAmount;
    private LinearLayout summeryLayout;
    private TextView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_reward_points);
        InitUI();
        setToolBarTitle(getString(R.string.title_reward));
        new AsyncTaskGetRewardPoints(RewardPointsActivity.this, false).execute();
        setLoadingAnimation(true);
    }

    private void InitUI() {
        mWebView = (WebView) findViewById(R.id.web_view);
        rewardPointRecyclerView = (RecyclerView) findViewById(R.id.reward_point_RecyclerView);
        rewardPointsBalance = (TextView) findViewById(R.id.RewardPointsBalance);
        rewardPointsAmount = (TextView) findViewById(R.id.rewardPointsAmount);
        minimumRewardPointsToUse = (TextView) findViewById(R.id.MinimumRewardPointsToUse);
        minimumRewardPointsToUseAmount = (TextView) findViewById(R.id.MinimumRewardPointsToUseAmount);
        summeryLayout = (LinearLayout) findViewById(R.id.reward_point_summery);
        history = (TextView) findViewById(R.id.reward_history);

        mUrl = AppConstant.LOYALTY_PROGRAM_PAGE_URL;
        mWebView.loadUrl(mUrl);
        initWebView();
        rewardPointsBalance.setText(R.string.rewardPointsBalance);
        rewardPointsAmount.setText(R.string.rewardPointsAmount);
        minimumRewardPointsToUse.setText(R.string.minimumRewardPointsToUse);
        minimumRewardPointsToUseAmount.setText(R.string.minimumRewardPointsToUseAmount);

        mAdapter = new RewardAdapter(rewardPointsHistory);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rewardPointRecyclerView.setLayoutManager(mLayoutManager);
        rewardPointRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rewardPointRecyclerView.setAdapter(mAdapter);


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                summeryLayout.setVisibility(View.GONE);
                rewardPointRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onRewardPointsObtained(RewardPoint rewardPoint) {
        setLoadingAnimation(false);
        if (rewardPoint != null) {
            rewardPointsHistory.clear();
            rewardPointsHistory.addAll(rewardPoint.getRewardPointsHistory());
            mAdapter.notifyDataSetChanged();
            rewardPointsBalance.setText(getString(R.string.rewardPointsBalance).concat(" : ").concat(String.valueOf(rewardPoint.getRewardPointsBalance())));
            rewardPointsAmount.setText(getString(R.string.rewardPointsAmount).concat(" : ").concat(rewardPoint.getRewardPointsAmount()));
            minimumRewardPointsToUse.setText(getString(R.string.minimumRewardPointsToUse).concat(" : ").concat(rewardPoint.getMinimumRewardPointsToUse().toString()));
            minimumRewardPointsToUseAmount.setText(getString(R.string.minimumRewardPointsToUseAmount).concat(" : ").concat(rewardPoint.getMinimumRewardPointsToUseAmount()));
        } else {
            rewardPointsBalance.setText(getString(R.string.rewardPointsBalance).concat(" : ").concat("0"));
            rewardPointsAmount.setText(getString(R.string.rewardPointsAmount).concat(" : ").concat("0"));
            minimumRewardPointsToUse.setText(getString(R.string.minimumRewardPointsToUse).concat(" : ").concat("0"));
            minimumRewardPointsToUseAmount.setText(getString(R.string.minimumRewardPointsToUseAmount).concat(" : ").concat("0"));
            history.setVisibility(View.GONE);
        }
    }


    private void initWebView() {
        WebViewClient mWebViewClient = new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (mUrl.equalsIgnoreCase(url)) {
                    view.loadUrl(mUrl);
                }
                return true;
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return false;
            }
        };
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        };
        mWebView.setEnabled(false);

        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(webChromeClient);

        mWebView.getSettings().setAllowContentAccess(false);
        mWebView.getSettings().setAllowFileAccess(false);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollbarFadingEnabled(false);
    }

    @Override
    public void onBackPressed() {
        if (rewardPointRecyclerView.getVisibility() == View.VISIBLE) {
            summeryLayout.setVisibility(View.VISIBLE);
            rewardPointRecyclerView.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}
