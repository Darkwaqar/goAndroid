package com.growonline.gomobishop.asynctask;

import android.os.AsyncTask;

import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

public class ApplyCouponCodeAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<Boolean>> {

    private AsyncTaskResultListener<Boolean> mResult;
    private String mGiftOrDiscountCode;

    public ApplyCouponCodeAsyncTask(String couponCode) {
        this.mGiftOrDiscountCode = couponCode;
    }

    @Override
    protected AsyncTaskResult<Boolean> doInBackground(Void... params) {

        AsyncTaskResult<Boolean> res;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("couponcode", mGiftOrDiscountCode);

            String response = new GetPostSender().sendPostJSON(AppConstant.APPLY_GIFT_VOUCHER, jsonObject.toString(), false);

            JSONObject jsonResponse = new JSONObject(response);
            JSONObject model = jsonResponse.getJSONObject("model");
            String discountMessageDiscountBox = model.getJSONObject("DiscountBox").getString("Message");
            boolean DiscountBoxApplied = model.getJSONObject("DiscountBox").getBoolean("IsApplied");
            String discountMessageGiftCardBox = model.getJSONObject("GiftCardBox").getString("Message");
            boolean GiftCardBoxApplied = model.getJSONObject("DiscountBox").getBoolean("IsApplied");
            if (DiscountBoxApplied || GiftCardBoxApplied) {
                res = new AsyncTaskResult<>(null, true);
            } else if (!discountMessageDiscountBox.equals("null")) {
                res = new AsyncTaskResult<>(new Exception(discountMessageDiscountBox), false);
            } else if (!discountMessageGiftCardBox.equals("null")) {
                res = new AsyncTaskResult<>(new Exception(discountMessageGiftCardBox), false);
            } else
                res = new AsyncTaskResult<>(new Exception(jsonResponse.getString("message")), false);
        } catch (Exception ex) {
            res = new AsyncTaskResult<>(new Exception(AppConstant.SERVER_ERROR_MSG), false);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Boolean> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if (!this.isCancelled() && mResult != null)
            mResult.response(booleanAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<Boolean> listener) {
        mResult = listener;
    }


}
