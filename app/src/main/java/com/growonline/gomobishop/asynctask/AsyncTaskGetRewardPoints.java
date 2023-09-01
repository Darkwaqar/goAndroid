package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.google.gson.Gson;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.RewardPointsActivity;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.model.RewardPoint;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONObject;

public class AsyncTaskGetRewardPoints extends BaseAsynctask {

    private RewardPoint rewardPoint;


    public AsyncTaskGetRewardPoints(Activity activity, Boolean showLoading) {
        super(activity, showLoading);
    }

    @Override
    protected void onComplete(String output) {
        if (!output.equalsIgnoreCase("")) {
            AppHelper.showMsgDialog(mActivity, "", output, null, null);
        }

        if (mActivity instanceof RewardPointsActivity)
            ((RewardPointsActivity) mActivity).onRewardPointsObtained(rewardPoint);
    }

    @Override
    protected String doInBackground(String... params) {
        String responseModel = new GetPostSender().sendGet(AppConstant.GET_REWARD_POINTS, false);

        String checkPoint = onResponseReceived(responseModel);
        if (checkPoint.equalsIgnoreCase("")) {
            try {
                JSONObject jsonObject = new JSONObject(responseModel);
                String status = jsonObject.getString("status");
                if (status.equalsIgnoreCase("true")) {
                    rewardPoint = new Gson().fromJson(jsonObject.getJSONObject("model").toString(), RewardPoint.class);
                    return "";
                } else {
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

}
