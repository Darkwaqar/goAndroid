package com.growonline.gomobishop.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import com.growonline.gomobishop.AddressActivity;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.MainActivity;
import com.growonline.gomobishop.MyAccountActivity;
import com.growonline.gomobishop.NotificationActivity;
import com.growonline.gomobishop.OrderListingActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.ReviewsActivity;
import com.growonline.gomobishop.ShopCartActivity;
import com.growonline.gomobishop.SplashScreen;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.WishListDetailsActivity;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.network.MultiPartSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AsyncTaskLogin extends AsyncTask<String, Void, String> {

    protected Activity mActivity;
    private String mUserEmail;
    private String mUserPassword;
    private ProgressDialog mDialog;
    private Vendor mVendor;
    private int returnUri;
    private int productId;
    private boolean mSilently = false;
    private AddDeviceToServer backgroundTask;

    public AsyncTaskLogin(Activity activity, String email, String password, Vendor vendor, int returnUri, int productId) {
        mActivity = activity;
        this.mUserEmail = email;
        this.mUserPassword = password;
        this.mVendor = vendor;
        this.returnUri = returnUri;
        this.productId = productId;
    }

    public AsyncTaskLogin(Activity activity, String email, String password, Vendor vendor, int returnUri, boolean silent) {
        mActivity = activity;
        this.mUserEmail = email;
        this.mUserPassword = password;
        this.mVendor = vendor;
        this.returnUri = returnUri;
        this.mSilently = silent;
    }

    protected void onComplete(String output) {
        if (returnUri == AppConstant.RETURN_SPLASH) {
            ((SplashScreen) mActivity).startApp();
            return;
        }
        if (output.equalsIgnoreCase("")) {
            sendRegistrationToServer(GoMobileApp.getFcmToken());
            Intent intent = new Intent();
            if (returnUri == AppConstant.RETURN_HOME)
                intent = intent.setClass(mActivity, StoreActivity.class);
            else if (returnUri == AppConstant.RETURN_SHIPPING)
                intent = intent.setClass(mActivity, ShopCartActivity.class);
            else if (returnUri == AppConstant.RETURN_MAIN_SCREEN)
                intent = intent.setClass(mActivity, MainActivity.class);
            else if (returnUri == AppConstant.RETURN_PRODUCT) {
                intent = intent.setClass(mActivity, StoreActivity.class);
                intent.putExtra(AppConstant.INTENT_PRODUCT_ID, productId);
            } else if (returnUri == AppConstant.RETURN_MY_ACCOUNT) {
                intent = intent.setClass(mActivity, MyAccountActivity.class);
            } else if (returnUri == AppConstant.RETURN_CHANGE_PASSWORD) {
                intent = intent.setClass(mActivity, AsyncTaskChangePassword.class);
            } else if (returnUri == AppConstant.RETURN_ORDER_LIST) {
                intent = intent.setClass(mActivity, OrderListingActivity.class);
            } else if (returnUri == AppConstant.RETURN_WISH_LIST) {
                intent = intent.setClass(mActivity, WishListDetailsActivity.class);
            } else if (returnUri == AppConstant.RETURN_NOTIFICATION) {
                intent = intent.setClass(mActivity, NotificationActivity.class);
            } else if (returnUri == AppConstant.RETURN_ADDRESS) {
                intent = intent.setClass(mActivity, AddressActivity.class);
            } else if (returnUri == AppConstant.RETURN_REVIEWS) {
                intent = intent.setClass(mActivity, ReviewsActivity.class);
            }

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
            intent.putExtra("redirect", "AddressActivity");

            mActivity.startActivity(intent);
            mActivity.finish();

        } else {
            if (!mSilently)
                GoMobileApp.showMsgDialog(mActivity, mActivity.getString(R.string.app_name), output, null, null);
        }
    }

    @Override
    protected String doInBackground(String... strings) {

        String response = obtainResponseFromService();
        String checkPoint = onResponseReceived(response);
        GoMobileApp.LogEvent(response);

        if (checkPoint.equalsIgnoreCase("")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                AppHelper.LogEvent(response);
                if (jsonObject.getBoolean("status")) {
                    JSONObject model = jsonObject.getJSONObject("model");
                    GoMobileApp.addToSharedPrefs(AppConstant.IS_USER_LOGGED_IN, "true");
                    GoMobileApp.addToSharedPrefs(AppConstant.USER_EMAIL_PREFS_KEY, model.getString("Email"));
                    GoMobileApp.addToSharedPrefs(AppConstant.USER_PASSWORD_PREFS_KEY, mUserPassword);
                    GoMobileApp.addToSharedPrefs(AppConstant.SIGNED_IN_FROM, AppConstant.LOGIN_MANUAL);
                    GoMobileApp.addToSharedPrefs(AppConstant.IS_USER_LOGGED_IN, "true");
                    GoMobileApp.addToSharedPrefs(AppConstant.USER_NAME_PREFS_KEY, model.getString("FullName"));

                    GoMobileApp.setShoppingCartItemsCount(model.getInt("NumberOfCartItems"));
                    return "";
                } else {
                    String messageByServer = jsonObject.getString("message");
                    if (!messageByServer.trim().isEmpty())
                        return messageByServer;
                    else
                        return mActivity.getString(R.string.customer_not_found);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return mActivity.getString(R.string.invalid_Response_comming_from_server);
            }
        } else {
            return checkPoint;
        }
    }

    private String obtainResponseFromService() {
        String response;

        HashMap<String, String> hashMap = new HashMap<>();
        try {
            hashMap.put("Email", mUserEmail);
            hashMap.put("Password", mUserPassword);
            response = new MultiPartSender().sendPostStringUsingMultiPart(AppConstant.LOGIN_URL, hashMap, true);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (!mSilently)
            mDialog = ProgressDialog.show(mActivity, "", mActivity.getString(R.string.please_wait_message));
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            if (!this.isCancelled() && mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (!this.isCancelled())
            onComplete(result);
    }

    public String onResponseReceived(String response) {
        if (response == null)
            return mActivity.getString(R.string.no_response);

        if (response.equalsIgnoreCase("false"))
            return mActivity.getString(R.string.time_out);

        return "";
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        JSONObject params = new JSONObject();
        try {
            params.put("DeviceId", token);
            params.put("Package", "com.growonline.gomobishop");
            params.put("DeviceOS", "1");
            params.put("Brand", Build.BRAND);
            params.put("OSVersion", Build.VERSION.SDK);
            params.put("Carrier", Build.MANUFACTURER);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        backgroundTask = new AddDeviceToServer(params);
        backgroundTask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
            @Override
            public void response(AsyncTaskResult<Boolean> response) {
                if (!response.hasException()) {
                    if (AppConstant.isLogEnabled)
                        AppHelper.LogEvent("Device added");
                } else {
                    if (AppConstant.isLogEnabled)
                        AppHelper.LogEvent("Device failed");
                }
            }
        });
        backgroundTask.execute();

    }
}