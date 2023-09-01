package com.growonline.gomobishop.asynctask;

import android.app.Activity;
import android.content.Intent;

import com.growonline.gomobishop.AddressActivity;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.LoginSignUpActivity;
import com.growonline.gomobishop.MainActivity;
import com.growonline.gomobishop.MyAccountActivity;
import com.growonline.gomobishop.NotificationActivity;
import com.growonline.gomobishop.OrderListingActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.ShopCartActivity;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.WishListDetailsActivity;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.network.MultiPartSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONObject;

import java.util.HashMap;

public class AsyncTaskRegisterUser extends BaseAsynctask {
    private String mSignInFrom;
    private int returnUri;
    private String mUserPassword;
    private String mUserEmail;
    private String mUserName;
    private int productId;

    public AsyncTaskRegisterUser(Activity activity, String mEmail,
                                 String mPassword, String username, String signInFrom, int returnUri, int productId) {
        super(activity, true);
        this.mUserEmail = mEmail;
        this.mUserPassword = mPassword;
        this.mUserName = username;
        this.mSignInFrom = signInFrom;
        this.returnUri = returnUri;
        this.productId = productId;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = SendRequest(AppConstant.REGISTER_USER);
        String checkPoint = onResponseReceived(response);
        if (checkPoint.equalsIgnoreCase("")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                AppHelper.LogEvent(response);
                if (jsonObject.getBoolean("status")) {
                    JSONObject model = jsonObject.getJSONObject("model");
                    GoMobileApp.addToSharedPrefs(AppConstant.USER_EMAIL_PREFS_KEY, mUserEmail);
                    GoMobileApp.addToSharedPrefs(AppConstant.IS_USER_LOGGED_IN, "true");
                    GoMobileApp.addToSharedPrefs(AppConstant.USER_PASSWORD_PREFS_KEY, mUserPassword);
                    GoMobileApp.addToSharedPrefs(AppConstant.USER_NAME_PREFS_KEY, mUserEmail.split("@")[0]);
                    GoMobileApp.addToSharedPrefs(AppConstant.SIGNED_IN_FROM, mSignInFrom);

                    GoMobileApp.setShoppingCartItemsCount(model.getInt("NumberOfCartItems"));
                    //if new user is registerd by facebook or google
                    response = SendRequest(AppConstant.LOGIN_AS_FACEBOOK);
                    checkPoint = onResponseReceived(response);
                    if (checkPoint.equalsIgnoreCase("")) {
                        try {
                            jsonObject = new JSONObject(response);
                            AppHelper.LogEvent(response);
                            if (jsonObject.getBoolean("status")) {
                                model = jsonObject.getJSONObject("model");
                                GoMobileApp.addToSharedPrefs(AppConstant.USER_EMAIL_PREFS_KEY, mUserEmail);
                                GoMobileApp.addToSharedPrefs(AppConstant.IS_USER_LOGGED_IN, "true");
                                GoMobileApp.addToSharedPrefs(AppConstant.USER_PASSWORD_PREFS_KEY, mUserPassword);
                                GoMobileApp.addToSharedPrefs(AppConstant.USER_NAME_PREFS_KEY, mUserEmail.split("@")[0]);
                                GoMobileApp.addToSharedPrefs(AppConstant.SIGNED_IN_FROM, mSignInFrom);

                                GoMobileApp.setShoppingCartItemsCount(model.getInt("NumberOfCartItems"));
                                return "";
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            return mActivity.getString(R.string.invalid_Response_comming_from_server);
                        }
                    }
                    return "";
                } else {
                    if (jsonObject.getString("message").equals("The specified email already exists") && !mSignInFrom.equalsIgnoreCase(AppConstant.MANUAL_SIGN_UP)) {
                        response = SendRequest(AppConstant.LOGIN_AS_FACEBOOK);
                        checkPoint = onResponseReceived(response);
                        if (checkPoint.equalsIgnoreCase("")) {
                            try {
                                jsonObject = new JSONObject(response);
                                AppHelper.LogEvent(response);
                                if (jsonObject.getBoolean("status")) {
                                    JSONObject model = jsonObject.getJSONObject("model");
                                    GoMobileApp.addToSharedPrefs(AppConstant.USER_EMAIL_PREFS_KEY, mUserEmail);
                                    GoMobileApp.addToSharedPrefs(AppConstant.IS_USER_LOGGED_IN, "true");
                                    GoMobileApp.addToSharedPrefs(AppConstant.USER_PASSWORD_PREFS_KEY, mUserPassword);
                                    GoMobileApp.addToSharedPrefs(AppConstant.USER_NAME_PREFS_KEY, mUserEmail.split("@")[0]);
                                    GoMobileApp.addToSharedPrefs(AppConstant.SIGNED_IN_FROM, mSignInFrom);

                                    GoMobileApp.setShoppingCartItemsCount(model.getInt("NumberOfCartItems"));
                                    return "";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                return mActivity.getString(R.string.invalid_Response_comming_from_server);
                            }
                        }
                    }
                    return jsonObject.getString("message");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return mActivity.getString(R.string.invalid_Response_comming_from_server);
            }
        } else {
            return checkPoint;
        }
    }

    private String SendRequest(String Url) {
        String response;
        HashMap<String, String> jsonObject = new HashMap<>();
        try {
            jsonObject.put("Email", mUserEmail);
            jsonObject.put("Password", mUserPassword);
            jsonObject.put("ConfirmPassword", mUserPassword);
            jsonObject.put("FirstName", mUserName);
            jsonObject.put("LastName", "");

            response = new MultiPartSender().sendPostStringUsingMultiPart(Url, jsonObject, true);
        } catch (Exception exception) {
            return "false";
        }
        return response;
    }


    @Override
    protected void onComplete(String output) {
        if (output.equalsIgnoreCase("")) {
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
            }

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            if (((LoginSignUpActivity) mActivity).mVendor != null)
                intent.putExtra(AppConstant.INTENT_VENDOR, ((LoginSignUpActivity) mActivity).mVendor);

            mActivity.startActivity(intent);
            mActivity.finish();
        } else {
            AppHelper.showMsgDialog(mActivity, mActivity.getString(R.string.app_name), output, null, null);
        }
    }
}