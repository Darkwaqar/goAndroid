package com.growonline.gomobishop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;

public class ThankYouActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_thank_you);
        setToolBarTitle(getString(R.string.title_checkout_complete));
        initUi();
    }

    void initUi() {

        Button btn_ok = (Button) findViewById(R.id.btn_ok);
        setBackButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });
        hideShopCartBadge();
    }

    void goToHome() {
        GoMobileApp.setShoppingCartItemsCount(0);
        Intent i;
        if (BuildConfig.market)
            i = new Intent(ThankYouActivity.this, MainActivity.class);
        else
            i = new Intent(ThankYouActivity.this, StoreActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
        i.putExtra(AppConstant.INTENT_MAXIMIZE_ANIMATION, false);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        goToHome();
    }

}
