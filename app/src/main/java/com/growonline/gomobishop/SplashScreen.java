package com.growonline.gomobishop;

import android.Manifest;
import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.growonline.gomobishop.asynctask.AddStoreToDbAsyncTask;
import com.growonline.gomobishop.asynctask.AsyncTaskLogin;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.FetchAllVendorsFromDbAsyncTask;
import com.growonline.gomobishop.asynctask.FetchVendorFromDbAsyncTask;
import com.growonline.gomobishop.asynctask.GetMultipleVendorByIdsAsyncTask;
import com.growonline.gomobishop.asynctask.GetVendorByIdAsyncTask;
import com.growonline.gomobishop.asynctask.UpdateStoreToDbAsyncTask;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    private final int PERMISSION_REQUEST_STORAGE = 1;
    private final int delayInMilliSeconds = 5000;

    private final int updateProcessPending = 0;
    private final int updateProcessInit = 1;
    private final int updateProcessInProgress = 2;
    private final int updateProcessFinished = 3;
    private final int updateProcessFailed = 4;
    private final int updateProcessNotRequired = 5;
    ImageView splashImgView;
    //deeplink variables
    int _deepLink_store_id = BuildConfig.vendorId, _deepLink_product_id = -1;
    private int mUpdateProcessStatus;
    private GetMultipleVendorByIdsAsyncTask backgroundTaskGetFromAPI;
    private FetchAllVendorsFromDbAsyncTask backgroundTaskFetchFromDb;
    private UpdateStoreToDbAsyncTask backgroundTaskUpdateToDb;
    private GetVendorByIdAsyncTask backgroundTaskLoadPlayStoreVendor;
    private AddStoreToDbAsyncTask backgroundTaskAddStoreToDb;
    private AsyncTaskLogin backgroundTaskLogin;
    private Handler delayHandler;
    private Runnable delayRunnable;
    private Boolean createShortCut;

    public static Map<String, String> splitQuery(Uri url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_splash_screen);

        //launch tutorial for first time
        if (!GoMobileApp.getmAppPreferences().contains(AppConstant.IS_FIRST_LAUNCH) && BuildConfig.market) {
            SharedPreferences.Editor editor = GoMobileApp.getmAppPrefEditor();
            editor.putBoolean(AppConstant.IS_FIRST_LAUNCH, true);
            editor.commit();
            launchCountrySelector();
        } else {

            mUpdateProcessStatus = updateProcessPending;
            TextView lblVersion = (TextView) findViewById(R.id.lbl_version);
            lblVersion.setText("v " + BuildConfig.VERSION_NAME);

            if (permissionsAllowedCompat()) {
                if (GoMobileApp.IsUserLogin())
                    authenticateValidation();
                else
                    startApp();
            } else {
                askForPermissions();
            }
        }
    }


    private void authenticateValidation() {
        backgroundTaskLogin = new AsyncTaskLogin(SplashScreen.this,
                GoMobileApp.getStringPrefs(AppConstant.USER_EMAIL_PREFS_KEY),
                GoMobileApp.getStringPrefs(AppConstant.USER_PASSWORD_PREFS_KEY),
                null, AppConstant.RETURN_SPLASH, true);

        backgroundTaskLogin.execute();
    }

    public void startApp() {

        if (GoMobileApp.getStoreFromAndroidPlayStore() == 0) {
            //refresh all stores from API
            if (GoMobileApp.isNetworkAvailable(SplashScreen.this)) {
                mUpdateProcessStatus = updateProcessInit;
                startUpdateProcess();
            }
        }

        delayHandler = new Handler();
        delayRunnable = new Runnable() {
            @Override
            public void run() {

                if (mUpdateProcessStatus == updateProcessInProgress) {

                    if (backgroundTaskFetchFromDb != null && backgroundTaskFetchFromDb.isCancelled())
                        backgroundTaskFetchFromDb.cancel(true);

                    if (backgroundTaskGetFromAPI != null && backgroundTaskGetFromAPI.isCancelled())
                        backgroundTaskGetFromAPI.cancel(true);

                    if (backgroundTaskUpdateToDb != null && backgroundTaskUpdateToDb.isCancelled())
                        backgroundTaskUpdateToDb.cancel(true);
                }

                if (GoMobileApp.getStoreFromAndroidPlayStore() > 0) {

                    //first install by play-store with referrer vendor id
                    _deepLink_store_id = GoMobileApp.getStoreFromAndroidPlayStore();

                    int startWithProductId = GoMobileApp.getProductFromAndroidPlayStore();
                    if (startWithProductId > 0) {
                        _deepLink_product_id = startWithProductId;
                    }
                    createShortCut = true;
                    loadStoreFromAPI(_deepLink_store_id);

                    SharedPreferences.Editor editor = GoMobileApp.getmAppPrefEditor();
                    editor.remove(AppConstant.KEY_PLAY_STORE_VENDOR_ID);
                    if (startWithProductId > 0) {
                        editor.remove(AppConstant.KEY_PLAY_STORE_PRODUCT_ID);
                    }
                    editor.commit();

                } else {
                    if (getIntent().getData() != null)
                        handleDeepLinking(getIntent().getData());
                    else {
                        if (_deepLink_store_id > 0)
                            loadStoreFromAPI(_deepLink_store_id);
                        else
                            redirectToMainActivity(null);
                    }

                }
            }
        };

        delayHandler.postDelayed(delayRunnable, delayInMilliSeconds);
    }

    boolean permissionsAllowedCompat() {

        boolean res = true;

        int readStoragePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeStoragePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int phoneCallPermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);

        if (readStoragePermissionCheck != PackageManager.PERMISSION_GRANTED)
            res = false;

        if (writeStoragePermissionCheck != PackageManager.PERMISSION_GRANTED)
            res = false;

        if (phoneCallPermissionCheck != PackageManager.PERMISSION_GRANTED)
            res = false;

        return res;
    }

    void askForPermissions() {

        List<String> permissionToAsk = new ArrayList<>();

        int readStoragePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeStoragePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int phoneCallPermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);

        if (readStoragePermissionCheck != PackageManager.PERMISSION_GRANTED)
            permissionToAsk.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writeStoragePermissionCheck != PackageManager.PERMISSION_GRANTED)
            permissionToAsk.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (phoneCallPermissionCheck != PackageManager.PERMISSION_GRANTED)
            permissionToAsk.add(Manifest.permission.CALL_PHONE);


        String[] permissions = new String[permissionToAsk.size()];
        permissions = permissionToAsk.toArray(permissions);
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startApp();
                } else {
                    GoMobileApp.Toast(R.string.permissionDenied);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (delayHandler != null && delayRunnable != null)
            delayHandler.removeCallbacks(delayRunnable);

        if (backgroundTaskFetchFromDb != null && backgroundTaskFetchFromDb.isCancelled())
            backgroundTaskFetchFromDb.cancel(true);

        if (backgroundTaskGetFromAPI != null && backgroundTaskGetFromAPI.isCancelled())
            backgroundTaskGetFromAPI.cancel(true);

        if (backgroundTaskUpdateToDb != null && backgroundTaskUpdateToDb.isCancelled())
            backgroundTaskUpdateToDb.cancel(true);

        if (backgroundTaskLoadPlayStoreVendor != null && backgroundTaskLoadPlayStoreVendor.isCancelled())
            backgroundTaskLoadPlayStoreVendor.cancel(true);

        if (backgroundTaskAddStoreToDb != null && backgroundTaskAddStoreToDb.isCancelled())
            backgroundTaskAddStoreToDb.cancel(true);

        if (backgroundTaskLogin != null && backgroundTaskLogin.isCancelled())
            backgroundTaskLogin.cancel(true);


        super.onDestroy();
    }

    private void refreshAllDownloadedStores(List<Integer> vids) {
        backgroundTaskGetFromAPI = new GetMultipleVendorByIdsAsyncTask(vids, !GoMobileApp.IsUserLogin());
        backgroundTaskGetFromAPI.addOnResultsListener(new AsyncTaskResultListener<List<Vendor>>() {
            @Override
            public void response(AsyncTaskResult<List<Vendor>> response) {
                if (!response.hasException()) {
                    updateStoresToDB(response.getResult());
                } else {
                    mUpdateProcessStatus = updateProcessFailed;
                }
            }
        });
        backgroundTaskGetFromAPI.execute();
    }

    void startUpdateProcess() {
        mUpdateProcessStatus = updateProcessInProgress;
        backgroundTaskFetchFromDb = new FetchAllVendorsFromDbAsyncTask(this);
        backgroundTaskFetchFromDb.addOnVendorsLoadedListener(new AsyncTaskResultListener<List<Vendor>>() {
            @Override
            public void response(AsyncTaskResult<List<Vendor>> response) {

                if (!response.hasException()) {

                    List<Vendor> tempList = response.getResult();
                    GoMobileApp.setDownloadedVendors(tempList);
                    if (tempList != null && tempList.size() > 0) {
                        List<Integer> ids = new ArrayList<>();
                        for (int i = 0; i < tempList.size(); i++) {
                            ids.add(tempList.get(i).getId());
                        }
                        refreshAllDownloadedStores(ids);
                    } else {
                        mUpdateProcessStatus = updateProcessNotRequired;
                    }

                } else {
                    mUpdateProcessStatus = updateProcessFailed;
                }

            }
        });

        backgroundTaskFetchFromDb.execute();
    }

    void updateStoresToDB(List<Vendor> vendors) {
        backgroundTaskUpdateToDb = new UpdateStoreToDbAsyncTask(this, vendors);
        backgroundTaskUpdateToDb.addOnResultsListener(new AsyncTaskResultListener<String>() {
            @Override
            public void response(AsyncTaskResult<String> response) {
                if (!response.hasException()) {
                    mUpdateProcessStatus = updateProcessFinished;
                } else {
                    mUpdateProcessStatus = updateProcessFailed;
                }
            }
        });
        backgroundTaskUpdateToDb.execute();
    }

    void launchException(String message, Throwable exception) {
        AppHelper.showException(this, message, exception);
    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException(SplashScreen.this, message, exception);
        else
            AppHelper.showNetworkError(SplashScreen.this, message);
    }

    void loadStoreFromAPI(int vendorId) {
        backgroundTaskLoadPlayStoreVendor = new GetVendorByIdAsyncTask(vendorId);
        backgroundTaskLoadPlayStoreVendor.addOnResultsListener(new AsyncTaskResultListener<Vendor>() {
            @Override
            public void response(AsyncTaskResult<Vendor> response) {
                if (!response.hasException()) {
                    addStoreToDbAndNavigate(response.getResult());
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        launchException(response.getException().getMessage(), response.getException(), false);
                    else
                        launchException(response.getException().getMessage(), response.getException(), true);

                    redirectToMainActivity(null);
                }
            }
        });
        backgroundTaskLoadPlayStoreVendor.execute();
    }

    void addStoreToDbAndNavigate(final Vendor vendor) {
        backgroundTaskAddStoreToDb = new AddStoreToDbAsyncTask(this);
        backgroundTaskAddStoreToDb.addOnResultsListener(new AsyncTaskResultListener<String>() {
            @Override
            public void response(AsyncTaskResult<String> response) {
                if (!response.hasException()) {
                    redirectToMainActivity(vendor);
                }
            }
        });
        backgroundTaskAddStoreToDb.execute(vendor);
    }

    void redirectToMainActivity(Vendor mVendor) {
        Intent intent;
        if (BuildConfig.market)
            intent = new Intent(SplashScreen.this, MainActivity.class);
        else {
            intent = new Intent(SplashScreen.this, StoreActivity.class);
            intent.putExtra(AppConstant.INTENT_MAXIMIZE_ANIMATION, false);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (mVendor != null) {
            intent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
            intent.putExtra(AppConstant.INTENT_CREATE_SHORTCUT_BOOLEAN, createShortCut);

            if (_deepLink_product_id > 0)
                intent.putExtra(AppConstant.INTENT_PRODUCT_ID, _deepLink_product_id);
        }

        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    void handleDeepLinking(Uri data) {

        String host = data.getHost();
        switch (host) {
            case "store": {

                try {
                    _deepLink_store_id = Integer.parseInt(data.getPathSegments().get(0));
                    createShortCut = true;
                } catch (Exception ignored) {

                }

                loadStoreForDeepLink();
            }
            break;
            case "product": {
                try {
                    _deepLink_store_id = Integer.parseInt(data.getPathSegments().get(0));
                    _deepLink_product_id = Integer.parseInt(data.getPathSegments().get(1));
                    createShortCut = true;
                } catch (Exception ignored) {

                }

                loadStoreForDeepLink();
            }
            break;
            case "www.gomobishop.com":
            case "pretapak.com":
            case "share.gomobishop.com": {

                String path = data.getPathSegments().get(0);

                if (path.contentEquals("share")) {
                    try {
                        _deepLink_store_id = Integer.parseInt(data.getQueryParameter("sid"));
                        _deepLink_product_id = Integer.parseInt(data.getQueryParameter("pid"));
                        createShortCut = false;
                    } catch (Exception ignored) {

                    }

                    loadStoreForDeepLink();
                }
            }
            break;
            case "widget": {
                try {
                    Map<String, String> a = splitQuery(data);
                    _deepLink_store_id = Integer.parseInt(a.get("id"));
                    createShortCut = false;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                loadStoreForDeepLink();
            }
            break;
        }
    }

    void loadStoreForDeepLink() {
        FetchVendorFromDbAsyncTask fetchVendorTask = new FetchVendorFromDbAsyncTask(this, _deepLink_store_id);
        fetchVendorTask.addOnVendorLoadedListener(new AsyncTaskResultListener<Vendor>() {
            @Override
            public void response(AsyncTaskResult<Vendor> response) {
                if (!response.hasException() && response.getResult() != null) {
                    redirectToMainActivity(response.getResult());
                } else {
                    if (_deepLink_store_id > 0)
                        loadStoreFromAPI(_deepLink_store_id);
                    else
                        redirectToMainActivity(null);
                }
            }
        });
        fetchVendorTask.execute();
    }

    void launchTutorial() {

        Intent currentIntent = getIntent();
        Intent i = new Intent(SplashScreen.this, TutorialActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("startUpMode", true);

        if (currentIntent.getData() != null) {
            i.setData(currentIntent.getData());
        }

        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void launchCountrySelector() {
        Intent currentIntent = getIntent();
        Intent i = new Intent(SplashScreen.this, CountrySelectorActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("startUpMode", true);
        if (currentIntent.getData() != null) {
            i.setData(currentIntent.getData());
        }
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
