package com.growonline.gomobishop.fragment;


import android.animation.Animator;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;

public class DialogWebViewFragment extends DialogFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mData;
    private String mTitle;
    private Boolean mIsUrl;

    private ProgressBar mProgressBar;
    private WebView mWebView;
    private ImageView mBtnClose;
    private View layoutRoot;
    private TextView title;


    public DialogWebViewFragment() {
        // Required empty public constructor
    }

    /**
     * @param data  Url or Html data
     * @param title Title to be Show on Dialog
     * @param isUrl Is data url
     * @return new Instance of DialogWebView
     */
    public static DialogWebViewFragment newInstance(String data, String title, boolean isUrl) {
        DialogWebViewFragment fragment = new DialogWebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, data);
        args.putString(ARG_PARAM3, title);
        args.putBoolean(ARG_PARAM2, isUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        if (getArguments() != null) {
            mData = getArguments().getString(ARG_PARAM1);
            mTitle = getArguments().getString(ARG_PARAM3);
            mIsUrl = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutRoot = inflater.inflate(R.layout.dialog_fragment_web_view, container, false);
        init(layoutRoot);
        return layoutRoot;
    }

    void init(View view) {

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mWebView = (WebView) view.findViewById(R.id.webView);
        mBtnClose = (ImageView) view.findViewById(R.id.btn_close);
        title = (TextView) view.findViewById(R.id.associate_title);

        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        title.setText(mTitle.toUpperCase());
        initWebView();

        if (mIsUrl) {
            startLoadingUrl(mData);
        } else {
            startLoadingHtml(mData);
        }
    }

    private void initWebView() {
        WebViewClient mWebViewClient = new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
            }
        };
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        };

        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(webChromeClient);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
            mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollbarFadingEnabled(false);

    }

    void startLoadingUrl(String pageUrl) {
        mWebView.loadUrl(pageUrl);
    }

    void startLoadingHtml(String htmlText) {
        final String mimeType = "text/html";
        final String encoding = "UTF-8";

        mWebView.loadDataWithBaseURL("", htmlText, mimeType, encoding, "");
    }

    private void CircularUnreveal() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            int cx = GoMobileApp.getScreenWidth();
            int cy = GoMobileApp.getScreenHeight();

            float finalRadius = (float) Math.sqrt(cx * cx + cy * cy);

            Animator anim = ViewAnimationUtils.createCircularReveal(layoutRoot, cx, cy, finalRadius, 0);
            anim.setDuration(350);
            anim.start();

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    getDialog().dismiss();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {
            getDialog().dismiss();
        }
    }


}
