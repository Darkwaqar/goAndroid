package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class AsyncTaskChangePassword extends BaseAsynctask {

    private String mOLDPassword;
    private String mNEWPassword;
    private Activity mActivity;

    public AsyncTaskChangePassword(Activity activity, String mOld_psd, String mNew_psd) {
        super(activity, true);
        this.mOLDPassword = mOld_psd;
        this.mNEWPassword = mNew_psd;
        this.mActivity = activity;
    }

    @Override
    protected void onComplete(String output) {
        AppHelper.showMsgDialog(mActivity, "", output, null, null);
    }

    @Override
    protected String doInBackground(String... strings) {

        JSONObject jsonObject = new JSONObject();
        String response;
        try {
            jsonObject.put("OldPassword", mOLDPassword);
            jsonObject.put("NewPassword", mNEWPassword);
            response = new GetPostSender().sendPostJSON(AppConstant.Change_Password, jsonObject.toString(), false);


            String checkPoint = onResponseReceived(response);


            if (checkPoint.equalsIgnoreCase("")) //for network connection check
            {
                try {
                    JSONObject responseObject = new JSONObject(response);

                    String success = responseObject.getString("status");

                    if (success.equalsIgnoreCase("true")) {
                        return responseObject.getString("message");
                    } else {
                        return responseObject.getString("message");
                    }
                } catch (JSONException e) {
                    return mActivity.getString(R.string.invalid_Response_comming_from_server);
                }
            } else {
                return checkPoint;
            }

        } catch (Exception exception) {
            return mActivity.getString(R.string.invalid_Response_comming_from_server);
        }
    }
}
