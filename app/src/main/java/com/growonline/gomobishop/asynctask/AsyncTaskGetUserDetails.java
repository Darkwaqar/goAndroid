package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.google.gson.Gson;
import com.growonline.gomobishop.MyAccountActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.model.InfoModel;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONObject;


public class AsyncTaskGetUserDetails extends BaseAsynctask {
    private InfoModel mBeanUserDetails;

    public AsyncTaskGetUserDetails(Activity activity, boolean isProgressEnabled) {
        super(activity, isProgressEnabled);
    }

    @Override
    protected void onComplete(String output) {
        if (output.equalsIgnoreCase("")) {
            if (mActivity instanceof MyAccountActivity) {
                ((MyAccountActivity) mActivity).updateDetails(mBeanUserDetails);
            }
        } else {
            AppHelper.showMsgDialog(mActivity, mActivity.getString(R.string.app_name), output, null, null);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String response = new GetPostSender().sendGet(AppConstant.GET_INFO, false);
        String checkPoint = onResponseReceived(response);

        if (checkPoint.equalsIgnoreCase("")) {
            try {
                AppHelper.LogEvent(response);
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("status");
                if (success.equalsIgnoreCase("true")) {
                    mBeanUserDetails = new Gson().fromJson(jsonObject.getString("model"), InfoModel.class);
                } else {
                    return jsonObject.getString("message");
                }
            } catch (Exception exce) {
                exce.printStackTrace();
                return exce.toString();
            }
        } else {
            return checkPoint;
        }
        return "";
    }
}