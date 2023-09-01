package com.growonline.gomobishop.asynctask;

import android.os.AsyncTask;

import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

public class RemoveCouponCodeAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<Boolean>> {

    private AsyncTaskResultListener<Boolean> mResult;
    private String mGiftOrDiscountCode;

    public RemoveCouponCodeAsyncTask(String couponCode) {
        this.mGiftOrDiscountCode = couponCode;
    }

    @Override
    protected AsyncTaskResult<Boolean> doInBackground(Void... params) {

        AsyncTaskResult<Boolean> res;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("couponcode", mGiftOrDiscountCode);

            String response = new GetPostSender().sendPostJSON(AppConstant.REMOVE_GIFT_VOUCHER, jsonObject.toString(), false);

            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.getBoolean("status")) {
                res = new AsyncTaskResult<>(null, true);
            } else {
                res = new AsyncTaskResult<>(new Exception(jsonResponse.getString("message")), false);
            }
        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, false);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Boolean> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);


        //it determines the activity reference
        if (!this.isCancelled() && mResult != null)
            mResult.response(booleanAsyncTaskResult);
    }

    //it assigns the value
    public void addOnResultListener(AsyncTaskResultListener<Boolean> listener) {
        mResult = listener;
    }


}

