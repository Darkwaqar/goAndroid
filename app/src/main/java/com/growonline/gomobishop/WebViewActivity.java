package com.growonline.gomobishop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;

public class WebViewActivity extends BaseActivity {

    private String mUrl, mTitle;
    private Vendor mVendor;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        init();
    }

    void init() {
        Intent i = getIntent();

        if (i.hasExtra(AppConstant.INTENT_URL)) {
            mVendor = i.getParcelableExtra(AppConstant.INTENT_VENDOR);
            mUrl = i.getStringExtra(AppConstant.INTENT_URL);
            if (i.hasExtra(AppConstant.INTENT_TITLE)) {
                mTitle = i.getStringExtra(AppConstant.INTENT_TITLE);
            }

            mWebView = (WebView) findViewById(R.id.webView);
            mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

            setToolBarTitle(mTitle);

            initWebView();
            startLoadingUrl();
        }
    }

    private void initWebView() {
        WebViewClient mWebViewClient = new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
                if (url.contains("EasyPayReceiptMobile") || url.contains("EasyPayReceipt") || url.contains("completed")) {
                    thankYouActivity();
                }

                if (url.equals("https://www.gomobishop.com/")) {
                    onBackPressed();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
                CookieSyncManager.getInstance().sync();
            }


        };
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        };

        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(webChromeClient);

        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.setScrollbarFadingEnabled(false);

        CookieManager.setAcceptFileSchemeCookies(true);
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

    }

    void startLoadingUrl() {
        mWebView.loadUrl(mUrl);
    }


    void thankYouActivity() {
        Intent i = new Intent();
        setResult(AppConstant.CODE_ONE_PAGE_CHECKOUT, i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent();
        setResult(AppConstant.CODE_REDIRECTION_CANCEL, i);
        finish();
    }
}
