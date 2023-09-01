package com.growonline.gomobishop.asynctask;

import com.growonline.gomobishop.MyAccountActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;


public class AsyncTaskPostCustomerInfo extends BaseAsynctask {
    private JSONObject body = new JSONObject();

    public AsyncTaskPostCustomerInfo(MyAccountActivity myAccountActivity, JSONObject body) {
        super(myAccountActivity, true);
        this.body = body;
    }

    @Override
    protected void onComplete(String output) {
        AppHelper.showMsgDialog(mActivity, "", output, null, null);
    }

    @Override
    protected String doInBackground(String... strings) {
        String response;
        response = new GetPostSender().sendPostJSON(AppConstant.EDIT_INFO, body.toString(), false);
        String checkPoint = onResponseReceived(response);
        if (checkPoint.equalsIgnoreCase("")) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                String success = jsonResponse.getString("status");
                if (success.equalsIgnoreCase("true")) {
                    return mActivity.getString(R.string.data_successfully_updated);
                } else {
                    return jsonResponse.getString("message");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return e.toString();
            }
        } else {
            return checkPoint;
        }
    }
}

