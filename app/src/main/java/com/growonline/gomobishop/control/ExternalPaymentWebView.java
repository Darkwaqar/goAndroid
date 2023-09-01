package com.growonline.gomobishop.control;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;


public class ExternalPaymentWebView extends WebView {

    public ExternalPaymentWebView(Context context) {
        super(context);
        init();

    }

    public ExternalPaymentWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExternalPaymentWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(21)
    public ExternalPaymentWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public boolean canGoBackOrForward(int steps) {
        return false;
    }



    void init(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.getSettings().setAllowFileAccessFromFileURLs(true);
            this.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        this.getSettings().setAllowContentAccess(true);
        this.getSettings().setAllowFileAccess(true);
        this.getSettings().setUseWideViewPort(true);
        this.getSettings().setDisplayZoomControls (false);
        this.getSettings().setLoadWithOverviewMode(true);
        this.getSettings().setAppCacheEnabled(false);
        this.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        this.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        this.setVerticalScrollBarEnabled(false);
        this.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        this.getSettings().setJavaScriptEnabled(true);
        this.setScrollbarFadingEnabled(false);
    }

}
