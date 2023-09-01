package com.growonline.gomobishop.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.ContactActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;

import static com.growonline.gomobishop.util.AppConstant.BASE_DOMAIN;
import static com.growonline.gomobishop.util.AppConstant.INTENT_VENDOR;

public class AboutUsFragment extends Fragment {
    private Vendor mVendor;
    private ProgressBar mProgressBar;
    private WebView mWebView;
    private String url;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    public static AboutUsFragment newInstance(Vendor vendor) {
        AboutUsFragment fragment = new AboutUsFragment();
        Bundle args = new Bundle();
        args.putParcelable(INTENT_VENDOR, vendor);
        fragment.setArguments(args);
        return fragment;
    }

    public static AboutUsFragment newInstance(Vendor vendor, String url) {
        AboutUsFragment fragment = new AboutUsFragment();
        Bundle args = new Bundle();
        args.putParcelable(INTENT_VENDOR, vendor);
        args.putString(AppConstant.INTENT_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVendor = getArguments().getParcelable(INTENT_VENDOR);
            url = getArguments().getString(AppConstant.INTENT_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);
        init(v);
        return v;
    }

    void init(View view) {

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mWebView = (WebView) view.findViewById(R.id.webView);

        initWebView();
        if (url == null) {
            getAboutUsPageHtml();
        } else {
            mWebView.loadUrl(url);
        }

    }


    private void initWebView() {
        WebViewClient mWebViewClient = new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("https://www.sadafamir.com/contactus")) {
                    Intent contactScreenIntent = new Intent(getActivity(), ContactActivity.class);
                    contactScreenIntent.putExtra(INTENT_VENDOR, mVendor);
                    contactScreenIntent.putExtra(AppConstant.INTENT_SHOW_CONTACT, true);
                    startActivity(contactScreenIntent);
                }
                if (url.contains("https://www.sadafamir.com/lagan-3")) {
                    ((StoreActivity) getActivity()).setStoreTab(1973);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
                view.loadUrl("javascript:(function() { " +
                        "document.getElementById('ph-title').style.display='none'; " +
                        "})()");
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
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(webChromeClient);
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCacheEnabled(false);
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);


        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

    }


    private void getAboutUsPageHtml() {
        startLoadingHtmlText(mVendor.getAboutUs().replaceAll("/Content", BASE_DOMAIN + "/Content"));
    }


    void startLoadingHtmlText(String htmlText) {
        try {
            String decodedHtml;

            if (Build.VERSION.SDK_INT >= 24)
                decodedHtml = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY).toString();
            else
                decodedHtml = Html.fromHtml(htmlText).toString();

            mWebView.loadData(decodedHtml, "text/html; charset=utf-8", "UTF-8");

        } catch (Exception ignored) {

        }
    }


    @Override
    public void onDestroy() {
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }
}
