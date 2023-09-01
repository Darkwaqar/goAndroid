package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.PaymentInfoModel;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

public class GetPaymentInfo extends AsyncTask<Void, Void, AsyncTaskResult<PaymentInfoModel>> {
    private AsyncTaskResultListener<PaymentInfoModel> mResult;
    private PaymentInfoModel paymentInfoModel;

    public GetPaymentInfo() {
    }

    @Override
    protected AsyncTaskResult<PaymentInfoModel> doInBackground(Void... voids) {
        AsyncTaskResult<PaymentInfoModel> res;
        String response = new GetPostSender().sendGet(AppConstant.GET_PAYMENT_INFO_STRIPE, false);
        try {

            if (response != null) {
                Gson gson = new Gson();
                paymentInfoModel = gson.fromJson(response, new TypeToken<PaymentInfoModel>() {
                }.getType());
                res = new AsyncTaskResult<>(null, paymentInfoModel);

            } else {
                res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
            }

        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, null);
        }
        return res;
    }


    @Override
    protected void onPostExecute(AsyncTaskResult<PaymentInfoModel> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if (!this.isCancelled() && mResult != null)
            mResult.response(booleanAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<PaymentInfoModel> listener) {
        mResult = listener;
    }
}
