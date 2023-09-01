package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by asifrizvi on 12/6/2018.
 */

public class AsyncTaskSubscribe extends BaseAsynctask {
    private String mEmail;
    private int mVendorId;
    private String message;

    public AsyncTaskSubscribe(Activity activity, boolean isProgressEnabled, String email, Integer id) {
        super(activity, isProgressEnabled);
        mEmail = email;
        mVendorId = id;
    }

    @Override
    protected void onComplete(String output) {
        if (mActivity instanceof StoreActivity) {
            AppHelper.showMsgDialog(mActivity, mActivity.getString(R.string.app_name), output, null, null);
        }

    }

    @Override
    protected String doInBackground(String... params) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",mEmail);
            jsonObject.put("vendorId",String.valueOf(mVendorId));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String response = new GetPostSender().sendPostJSON(AppConstant.SUBSCRIBE_URL,jsonObject.toString(), false);
        String checkPoint = onResponseReceived(response);
        if (checkPoint.equalsIgnoreCase("")) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                return jsonResponse.getString("Result");
            } catch (JSONException e) {
                e.printStackTrace();
                return e.toString();
            }
        } else {
            return checkPoint;
        }
    }
}