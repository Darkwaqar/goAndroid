package com.growonline.gomobishop;

import android.Manifest;
import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.AsyncTaskLogin;
import com.growonline.gomobishop.asynctask.GetVendorByIdAsyncTask;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    private final int PERMISSION_REQUEST_STORAGE = 1;
    private final int delayInMilliSeconds = 1000;
    private final int delayInMilliSeconds1 = 3000;

    private final int updateProcessPending = 0;
    private final int updateProcessInit = 1;
    private final int updateProcessInProgress = 2;
    //deeplink variables
    int _deepLink_store_id = 9, _deepLink_product_id = -1;
    private int mUpdateProcessStatus;
    private GetVendorByIdAsyncTask backgroundTaskLoadPlayStoreVendor;
    private AsyncTaskLogin backgroundTaskLogin;
    private Handler delayHandler;
    private Runnable delayRunnable;
    private Boolean createShortCut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_splash_screen);

        mUpdateProcessStatus = updateProcessPending;
        TextView lblVersion = (TextView) findViewById(R.id.lbl_version);
        lblVersion.setText("v " + BuildConfig.VERSION_NAME);

        if (permissionsAllowedCompat()) {
            if (GoMobileApp.getmAppPreferences().getString(AppConstant.IS_USER_LOGGED_IN, "false").equalsIgnoreCase("true"))
                authenticateValidation();
            else
                startApp();

        } else {
            askForPermissions();
        }
    }


    private void authenticateValidation() {
        SharedPreferences prefs = GoMobileApp.getmAppPreferences();

        backgroundTaskLogin = new AsyncTaskLogin(SplashScreen.this,
                prefs.getString(AppConstant.USER_EMAIL_PREFS_KEY, ""),
                prefs.getString(AppConstant.USER_PASSWORD_PREFS_KEY, ""),
                null, AppConstant.RETURN_SPLASH, true);

        backgroundTaskLogin.execute();
    }

    public void startApp() {

        if (GoMobileApp.getStoreFromAndroidPlayStore() == 0) {
            //refresh all stores from API
            mUpdateProcessStatus = updateProcessInit;

        }

        delayHandler = new Handler();
        delayRunnable = new Runnable() {
            @Override
            public void run() {

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
                    else
                        loadStoreFromAPI(_deepLink_store_id);
                }
            }
        };

        delayHandler.postDelayed(delayRunnable, delayInMilliSeconds);
    }

    boolean permissionsAllowedCompat() {

        boolean res = true;

        int phoneCallPermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);


        if (phoneCallPermissionCheck != PackageManager.PERMISSION_GRANTED)
            res = false;

        return res;
    }

    void askForPermissions() {

        List<String> permissionToAsk = new ArrayList<>();

        int phoneCallPermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);

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
                    Toast.makeText(this, R.string.permissionDenied, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (delayHandler != null && delayRunnable != null)
            delayHandler.removeCallbacks(delayRunnable);


        if (backgroundTaskLoadPlayStoreVendor != null && backgroundTaskLoadPlayStoreVendor.isCancelled())
            backgroundTaskLoadPlayStoreVendor.cancel(true);


        if (backgroundTaskLogin != null && backgroundTaskLogin.isCancelled())
            backgroundTaskLogin.cancel(true);


        super.onDestroy();
    }


    void launchException(String message, Throwable exception) {
        AppHelper.showException(this, message, exception);
    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException(SplashScreen.this, message, exception);
        else {
            AppHelper.showNetworkError(SplashScreen.this, message);
            delayRunnable = new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            };
            delayHandler.postDelayed(delayRunnable, delayInMilliSeconds1);
        }

    }

    void loadStoreFromAPI(int vendorId) {
        backgroundTaskLoadPlayStoreVendor = new GetVendorByIdAsyncTask(vendorId);
        backgroundTaskLoadPlayStoreVendor.addOnResultsListener(new AsyncTaskResultListener<Vendor>() {
            @Override
            public void response(AsyncTaskResult<Vendor> response) {
                if (!response.hasException()) {
                    redirectToMainActivity(response.getResult());
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        launchException(response.getException().getMessage(), response.getException(), false);
                    else
                        launchException(response.getException().getMessage(), response.getException(), true);
                }
            }
        });
        backgroundTaskLoadPlayStoreVendor.execute();
    }

    void redirectToMainActivity(Vendor mVendor) {
        Intent intent = new Intent(SplashScreen.this, StoreActivity.class);
        if (mVendor != null) {
            intent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
            intent.putExtra(AppConstant.INTENT_CREATE_SHORTCUT_BOOLEAN, createShortCut);

            if (_deepLink_product_id > 0)
                intent.putExtra(AppConstant.INTENT_PRODUCT_ID, _deepLink_product_id);
        }

        startActivity(intent);
        overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        finish();
    }

    // Add code to print out the key hash
    void getAppHashCode() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.growonline.sadaf",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {

        }
    }


    void handleDeepLinking(Uri data) {

        String host = data.getHost();
        switch (host) {
            case "store": {

                try {
                    _deepLink_store_id = Integer.valueOf(data.getPathSegments().get(0));
                    createShortCut = true;
                } catch (Exception ignored) {

                }

                loadStoreForDeeplink();
            }
            break;
            case "product": {
                try {
                    _deepLink_store_id = Integer.valueOf(data.getPathSegments().get(0));
                    _deepLink_product_id = Integer.valueOf(data.getPathSegments().get(1));
                    createShortCut = true;
                } catch (Exception ignored) {

                }

                loadStoreForDeeplink();
            }
            break;
            case "www.sadafamir.com": {

                String path = data.getPathSegments().get(0);

                if (path.contentEquals("share")) {
                    try {
                        _deepLink_store_id = Integer.valueOf(data.getQueryParameter("sid"));
                        _deepLink_product_id = Integer.valueOf(data.getQueryParameter("pid"));
                        createShortCut = false;
                    } catch (Exception ignored) {

                    }

                    loadStoreForDeeplink();
                }
            }
            break;
            case "sadafamir.com": {

                String path = data.getPathSegments().get(0);

                if (path.contentEquals("share")) {
                    try {
                        _deepLink_store_id = Integer.valueOf(data.getQueryParameter("sid"));
                        _deepLink_product_id = Integer.valueOf(data.getQueryParameter("pid"));
                        createShortCut = false;
                    } catch (Exception ignored) {

                    }

                    loadStoreForDeeplink();
                }
            }
            break;
            case "widget": {
                try {
                    Map<String, String> a = splitQuery(data);
                    _deepLink_store_id = Integer.valueOf(a.get("id"));
                    createShortCut = false;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                loadStoreForDeeplink();
            }
            break;
        }
    }

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

    void loadStoreForDeeplink() {
        if (_deepLink_store_id > 0)
            loadStoreFromAPI(_deepLink_store_id);
        else
            redirectToMainActivity(null);
    }


}
