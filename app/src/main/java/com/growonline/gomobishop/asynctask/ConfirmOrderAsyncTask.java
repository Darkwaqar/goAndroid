package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.OrderConfirmModel;
import com.growonline.gomobishop.network.MultiPartSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;


public class ConfirmOrderAsyncTask extends AsyncTask<Void,Void,AsyncTaskResult<OrderConfirmModel>> {

    private AsyncTaskResultListener<OrderConfirmModel> mResult;
    private String mShippingMethodName, mShippingRateComputationMethodSystemName, mPaymentMethodName;
    private boolean mUseRewardPoints = false;
    private String mStripePaymentIntentId;
    private OrderConfirmModel mOrderConfirmModel;

    public ConfirmOrderAsyncTask(String shippingMethodName, String shippingRateComputationMethodSystemName, String paymentMethodName) {

        this.mShippingMethodName = shippingMethodName;
        this.mShippingRateComputationMethodSystemName = shippingRateComputationMethodSystemName;
        this.mPaymentMethodName = paymentMethodName;
    }

    public ConfirmOrderAsyncTask(String StripePaymentIntentId, String shippingMethodName,
                                 String shippingRateComputationMethodSystemName,
                                 String paymentMethodName,
                                 boolean useRewardPoints) {

        this.mStripePaymentIntentId = StripePaymentIntentId;
        this.mShippingMethodName = shippingMethodName;
        this.mShippingRateComputationMethodSystemName = shippingRateComputationMethodSystemName;
        this.mPaymentMethodName = paymentMethodName;
        this.mUseRewardPoints = useRewardPoints;
    }

    @Override
    protected AsyncTaskResult<OrderConfirmModel> doInBackground(Void... params) {

        AsyncTaskResult<OrderConfirmModel> res;
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("stripeToken", mStripePaymentIntentId);
            hashMap.put("shippingmethod", mShippingMethodName + "___" + mShippingRateComputationMethodSystemName);
            hashMap.put("paymentmethod", mPaymentMethodName);
            hashMap.put("useRewardPoints", String.valueOf(mUseRewardPoints));

//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("stripeToken",mStripePaymentIntentId);
//            jsonObject.put("shippingmethod", mShippingMethodName + "___" + mShippingRateComputationMethodSystemName);
//            jsonObject.put("paymentmethod", mPaymentMethodName);
//            jsonObject.put("useRewardPoints", mUseRewardPoints);

//            String response = new GetPostSender().sendPostJSON(AppConstant.CONFIRM_ORDER, jsonObject.toString(), false);
            String response = new MultiPartSender().sendPostStringUsingMultiPart(AppConstant.CONFIRM_ORDER, hashMap, false);
            if (response != null) {

                JSONObject jsonResponse = new JSONObject(response);
                if (jsonResponse.getBoolean("status")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<OrderConfirmModel>() {
                    }.getType();

                    mOrderConfirmModel = gson.fromJson(jsonResponse.getJSONObject("model").toString(), type);
                    res = new AsyncTaskResult<>(null, mOrderConfirmModel);
                } else {
                    res = new AsyncTaskResult<>(new  NetworkErrorException(jsonResponse.getString("message")), null);
                }
            } else {
                res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
            }

        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<OrderConfirmModel> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if(!this.isCancelled() && mResult != null)
            mResult.response(booleanAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<OrderConfirmModel> listener){
        mResult = listener;
    }
}
