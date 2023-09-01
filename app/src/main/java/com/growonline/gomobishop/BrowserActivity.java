package com.growonline.gomobishop;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.growonline.gomobishop.control.ExternalPaymentWebView;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

public class BrowserActivity extends AppCompatActivity {

    private Vendor mVendor;
    private ImageView mLogo;
    private View mLogoShadow;
    private LinearLayout mTitleBar;
    private TextView mTitle;
    private int redirectCounter = 0;
    private boolean quit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        init();
    }


    void init() {

        mTitleBar = (LinearLayout) findViewById(R.id.title_bar);
        mTitle = (TextView) findViewById(R.id.txt_title);

        mLogo = (ImageView) findViewById(R.id.logo);
        Animation floatingAnimation = AnimationUtils.loadAnimation(this, R.anim.float_anim);
        mLogo.startAnimation(floatingAnimation);

        mLogoShadow = findViewById(R.id.shadow_logo);
        Animation floatingAnimationshadow = AnimationUtils.loadAnimation(this, R.anim.float_anim_shadow);
        mLogoShadow.startAnimation(floatingAnimationshadow);


        Intent intent = getIntent();
        String redirectUrl = intent.getStringExtra("url");
        mVendor = intent.getParcelableExtra(AppConstant.INTENT_VENDOR);

        ExternalPaymentWebView mWebView = (ExternalPaymentWebView) findViewById(R.id.web_view);
        mWebView.loadUrl(redirectUrl);
        mWebView.setVisibility(View.GONE);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (url.contains("EasyPayReceiptMobile")) {
                    thankYouActivity();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                redirectCounter += 1;

                if (redirectCounter == 2) {
                    view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                AppHelper.LogEvent(error.toString());
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                AppHelper.LogEvent(errorResponse.toString());
            }

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
//
                AlertDialog.Builder builder = new AlertDialog.Builder(BrowserActivity.this, R.style.AppTheme_Dialog);
                AlertDialog alertDialog = builder.create();
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }

                message += " Do you want to continue anyway?";
                alertDialog.setTitle("Pay With Easy Paisa");
                alertDialog.setMessage("Do you want to continue?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ignore SSL certificate errors
                        handler.proceed();
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        handler.cancel();
                    }
                });
                alertDialog.show();

            }


        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptCookie(true);
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        }

    }

    void thankYouActivity() {
        Intent i = new Intent(BrowserActivity.this, ThankYouActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        if (quit) {
            super.onBackPressed();
        } else {
            quit = true;
            GoMobileApp.Toast(R.string.quit);
        }
    }

}
