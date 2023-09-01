package com.growonline.gomobishop;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.growonline.gomobishop.asynctask.AddStoreToDbAsyncTask;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.FetchAllVendorsFromDbAsyncTask;
import com.growonline.gomobishop.asynctask.GetVendorByIdAsyncTask;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;

import java.util.List;

public class StoreDownloadActivity extends AppCompatActivity {

    private final long mLoadingDuration = 5000;
    private Vendor mLoadedStore;
    private GetVendorByIdAsyncTask backgroundTask;
    private AddStoreToDbAsyncTask addStoreToDbBackgroundTask;
    private boolean userHasCancelledDownload = false;

    private int mStoreId;
    private String mStoreTitle, mStoreLogoUrl;
    private int mStartupProductId;

    private FetchAllVendorsFromDbAsyncTask FetchAllVendorsFromDbAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_store_download);

        Intent i = getIntent();
        if (i.hasExtra("mStore")) {
            Vendor mStore = i.getParcelableExtra("mStore");

            mStoreId = mStore.getId();
            mStoreTitle = mStore.getName();
            mStoreLogoUrl = mStore.getLogoUrl();

        } else {
            mStoreId = i.getIntExtra("mStoreId", 0);
            mStoreTitle = i.getStringExtra("mStoreTitle");
            mStoreLogoUrl = i.getStringExtra("mStoreLogoUrl");
            mStartupProductId=i.getIntExtra("mStartupProductId",0);
        }
        initUi();
    }

    void initUi() {
        TextView titleActivityTextView = (TextView) findViewById(R.id.lbl_app_title);
        ImageView btnBack = (ImageView) findViewById(R.id.btn_back);
        ImageView imgLogo = (ImageView) findViewById(R.id.img_logo_vendor);
        TextView txtTitleStore = (TextView) findViewById(R.id.txt_vendor_title);
        final ProgressBar pBar = (ProgressBar) findViewById(R.id.progress_bar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userHasCancelledDownload = true;
                closeActivity();
            }
        });

        GoMobileApp.getmCacheManager().loadImage(Uri.parse(mStoreLogoUrl), imgLogo);

        txtTitleStore.setText(mStoreTitle);
        AppHelper.applyRobotoRegularFont(txtTitleStore);
        AppHelper.applyRobotoLightFont(titleActivityTextView);

        loadStoreFromAPI(mStoreId);
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    void closeActivity() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    void loadStoreFromAPI(int vendorId) {
        backgroundTask = new GetVendorByIdAsyncTask(vendorId);
        backgroundTask.addOnResultsListener(new AsyncTaskResultListener<Vendor>() {
            @Override
            public void response(AsyncTaskResult<Vendor> response) {
                if (!response.hasException()) {
                    mLoadedStore = response.getResult();
                    if (mLoadedStore != null && !userHasCancelledDownload) {
                        addStoreToDbAndNavigate(mLoadedStore);
                    }
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        launchException(response.getException().getMessage(), response.getException(), false);
                    else
                        launchException(response.getException().getMessage(), response.getException(), true);
                    
                }
            }
        });
        backgroundTask.execute();
    }

    void addStoreToDbAndNavigate(Vendor vendor) {
        addStoreToDbBackgroundTask = new AddStoreToDbAsyncTask(this);
        addStoreToDbBackgroundTask.addOnResultsListener(new AsyncTaskResultListener<String>() {
            @Override
            public void response(AsyncTaskResult<String> response) {
                if (!response.hasException()) {
                    loadDataFromDatabase();

                }
                else {
                    if (response.getException() instanceof NetworkErrorException)
                        launchException(response.getException().getMessage(), response.getException(), false);
                    else
                        launchException(response.getException().getMessage(), response.getException(), true);

                }
            }
        });
        addStoreToDbBackgroundTask.execute(vendor);
    }


    private void loadDataFromDatabase() {
        FetchAllVendorsFromDbAsyncTask = new FetchAllVendorsFromDbAsyncTask(StoreDownloadActivity.this);
        FetchAllVendorsFromDbAsyncTask.addOnVendorsLoadedListener(new AsyncTaskResultListener<List<Vendor>>() {
            @Override
            public void response(AsyncTaskResult<List<Vendor>> response) {
                if (!response.hasException()) {
                    List<Vendor> tempList = response.getResult();
                    if (tempList != null && tempList.size() > 0) {
                        GoMobileApp.setDownloadedVendors(tempList);

                    } else {
                        GoMobileApp.setDownloadedVendors(tempList);
                    }
                    startNewStore(mLoadedStore);
                } else {
                    launchException("Database not valid!", response.getException(),true);

                }
            }
        });
        FetchAllVendorsFromDbAsyncTask.execute();
    }

    void startNewStore(Vendor vendor) {
        Intent i = new Intent();
        i.putExtra(AppConstant.INTENT_VENDOR, vendor);
        i.putExtra("newlyDownloadStore", true);
        i.putExtra("mStartupProductId",mStartupProductId);
        setResult(AppConstant.CODE_DOWNLOAD_ACTIVITY, i);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException(StoreDownloadActivity.this, message, exception);
        else
            AppHelper.showNetworkError(StoreDownloadActivity.this, message);


        final Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                finish();
                timerHandler.postDelayed(this, 1000);
            }
        };


    }

    @Override
    protected void onDestroy() {

        userHasCancelledDownload = true;

        if (backgroundTask != null && !backgroundTask.isCancelled())
            backgroundTask.cancel(true);

        if (addStoreToDbBackgroundTask != null && !addStoreToDbBackgroundTask.isCancelled())
            addStoreToDbBackgroundTask.cancel(true);

        super.onDestroy();
    }


}
