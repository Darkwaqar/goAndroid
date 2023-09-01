package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.OrderDetailActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.model.OrderDetail;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONObject;

public class AsyncTaskGetOrderDetails extends BaseAsynctask {

    private final String mId;
    private OrderDetail mOrderDetail;


    public AsyncTaskGetOrderDetails(Activity activity, String mId, boolean isProgressEnabled) {
        super(activity, isProgressEnabled);
        this.mId = mId;
    }

    @Override
    protected void onComplete(String output) {

        if (output.equalsIgnoreCase(""))
            ((OrderDetailActivity) mActivity).onDetailReceived(mOrderDetail);
        else
            AppHelper.showMsgDialog(mActivity, "", output, null, null);
    }

    @Override
    protected String doInBackground(String... strings) {
        String responseModel = new GetPostSender().sendGet(AppConstant.GET_ORDER_DETAILS + mId, false);
        String checkPoint = onResponseReceived(responseModel);
        if (responseModel != null)
            AppHelper.LogEvent(responseModel);

        if (checkPoint.equalsIgnoreCase("")) {
            try {
                Gson gson = new Gson();
                JSONObject obj = new JSONObject(responseModel);
                mOrderDetail = gson.fromJson(obj.getJSONObject("model").toString(), new TypeToken<OrderDetail>() {
                }.getType());

                return "";
            } catch (Exception e) {
                e.printStackTrace();
                return mActivity.getString(R.string.invalid_Response_comming_from_server);
            }
        } else {
            return checkPoint;
        }
    }
}
