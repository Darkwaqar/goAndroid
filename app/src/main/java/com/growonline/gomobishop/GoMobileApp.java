package com.growonline.gomobishop;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.growonline.gomobishop.control.FontEditText;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.ImageCacheManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GoMobileApp extends Application {

    private static int screenWidth, screenHeight, initStoreFrameWidth = 0, initStoreFrameHeight = 0;
    private static ImageCacheManager mCacheManager;
    private static SharedPreferences mPreferences;
    private static int mShoppingCartItemsCount;
    private static List<Vendor> downloadedVendors;
    private static Context mContext;
    private static Resources resources;

    private static List<Integer> VendorImageHeightRatio;

    public static void LogEvent(String logString) {
        if (AppConstant.isLogEnabled) {
            Log.e("Zuni:", logString);
        }
    }

    public static void Toast(String Msg) {
        Toast.makeText(mContext, Msg, Toast.LENGTH_SHORT).show();
    }

    public static void Toast(int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }

    public static String getText(TextView s) {
        return s.getText() == null ? "" : s.getText().toString().trim();
    }

    public static String getText(FontEditText s) {
        return s.getText() == null ? "" : s.getText().toString().trim();
    }

    public static boolean isNullOrEmpty(FontEditText s) {
        return s.getText() == null || s.getText().toString().trim().isEmpty();
    }

    public static boolean isNullOrEmpty(EditText s) {
        return s.getText() == null || s.getText().toString().trim().isEmpty();
    }

    public static boolean isNullOrEmpty(TextView s) {
        return s.getText() == null || s.getText().toString().trim().isEmpty();
    }

    public static ImageCacheManager getmCacheManager() {
        return mCacheManager;
    }

    public static void setmCacheManager(ImageCacheManager mCacheManager) {
        GoMobileApp.mCacheManager = mCacheManager;
    }

    public static String ConvertUtcToDate(String utc) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(utc.replaceAll("[^\\d.]", "")));
        return DateFormat.format("dd-MM-yyyy", cal).toString();
    }

    public static String ConvertUtcToTime(String utc) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(utc.replaceAll("[^\\d.]", "")));
        return DateFormat.format("HH:mm", cal).toString();
    }

    public static void showMsgDialog(Activity activity,
                                     String title, String msg, DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener negativeButton) {
        try {
            if (positiveButton == null) {
                positiveButton = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
            }

            new AlertDialog.Builder(activity, R.style.AppTheme_Dialog).setTitle(title).setMessage(msg).setCancelable(false)
                    .setPositiveButton(activity.getString(android.R.string.ok), positiveButton).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidEmail(String mEmail) {
        boolean isOK = Patterns.EMAIL_ADDRESS.matcher(mEmail).matches();
        ///verify tld length
        if (isOK) {
            int tldSeparatorIndex = mEmail.lastIndexOf(".");
            String tld = mEmail.substring(tldSeparatorIndex + 1);
            if (tld.length() < 2)
                isOK = false;
        }
        return isOK;
    }

    public static boolean validatePhoneNumber(String mNumber) {
        return Patterns.PHONE.matcher(mNumber).matches();
    }

    public static SharedPreferences getmAppPreferences() {
        return mPreferences;
    }

    public static void setmAppPreferences(SharedPreferences mPreferences) {
        GoMobileApp.mPreferences = mPreferences;
    }

    public static SharedPreferences.Editor getmAppPrefEditor() {
        return mPreferences.edit();
    }

    public static void addToSharedPrefs(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    public static Map<String, ?> getAll() {
        return mPreferences.getAll();
    }

    public static void removeSharedPrefs(String key) {
        mPreferences.edit().remove(key).apply();
    }

    public static String getStringPrefs(String key) {
        return mPreferences.getString(key, "");
    }

    public static boolean IsUserLogin() {
        return mPreferences.contains(AppConstant.IS_USER_LOGGED_IN);
    }

    public static boolean IsCookieAvailable() {
        return mPreferences.contains(AppConstant.COOKIE_HANDLER);
    }

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getInitStoreFrameHeight() {
        return initStoreFrameHeight;
    }

    public static void setInitStoreFrameHeight(int initStoreFrameHeight) {
        GoMobileApp.initStoreFrameHeight = initStoreFrameHeight;
    }

    public static int getInitStoreFrameWidth() {
        return initStoreFrameWidth;
    }

    public static void setInitStoreFrameWidth(int initStoreFrameWidth) {
        GoMobileApp.initStoreFrameWidth = initStoreFrameWidth;
    }

    public static int getShoppingCartItemsCount() {
        return mShoppingCartItemsCount;
    }

    public static void setShoppingCartItemsCount(int shoppingCartItemsCount) {
        GoMobileApp.mShoppingCartItemsCount = shoppingCartItemsCount;
    }

    public static int getStoreFromAndroidPlayStore() {

        if (mPreferences.contains(AppConstant.KEY_PLAY_STORE_VENDOR_ID)) {
            return mPreferences.getInt(AppConstant.KEY_PLAY_STORE_VENDOR_ID, 0);
        } else {
            return 0;
        }
    }

    public static int getProductFromAndroidPlayStore() {

        if (mPreferences.contains(AppConstant.KEY_PLAY_STORE_PRODUCT_ID)) {
            return mPreferences.getInt(AppConstant.KEY_PLAY_STORE_PRODUCT_ID, 0);
        } else {
            return 0;
        }
    }

    public static String getFcmToken() {

        if (mPreferences.contains(AppConstant.KEY_TOKEN_ID)) {
            return mPreferences.getString(AppConstant.KEY_TOKEN_ID, "");
        } else {
            return "";
        }
    }

    public static List<Vendor> getDownloadedVendors() {

        if (downloadedVendors == null)
            return downloadedVendors = new ArrayList<>();

        return downloadedVendors;
    }

    public static void setDownloadedVendors(List<Vendor> downloadedVendors) {
        GoMobileApp.downloadedVendors = downloadedVendors;
    }

    public static Context getContext() {
        return mContext;
    }

    public static float convertDpToPixel(float dp) {
        return dp * ((float) mContext.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private void GetScreenDimensions() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displaymetrics);

        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }

    ///Returns device RAM in MegaBytes
    private long GetDeviceRAM() {
        long totalMemory = 0;

        try {
            ActivityManager actManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
            actManager.getMemoryInfo(memInfo);
            totalMemory = memInfo.totalMem / 1048576L;
        } catch (Exception ex) {
            Log.e("Memory get error", ex.toString());
        }

        return totalMemory;
    }

    public static float convertPixelsToDp(float px) {
        return px / ((float) mContext.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static Resources getAppResources() {
        return resources;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getBaseContext();
        setmCacheManager(ImageCacheManager.Initialize(getApplicationContext()));
        setmAppPreferences(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        GetScreenDimensions();

        //create default directory
        File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/" + getPackageName() + "/");
        if (!dir.exists())
            dir.mkdirs();

        FacebookSdk.sdkInitialize(this);

        resources = getResources();

    }


    //Language
}