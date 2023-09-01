package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.CheckoutModel;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

public class AsyncTaskGetCheckDetails extends AsyncTask<Void, Void, AsyncTaskResult<CheckoutModel>> {
    private AsyncTaskResultListener<CheckoutModel> mResult;

    @Override
    protected AsyncTaskResult<CheckoutModel> doInBackground(Void... voids) {
        AsyncTaskResult<CheckoutModel> res;

        if (!this.isCancelled()) {
            try {

                CheckoutModel listItems;
                String response = new GetPostSender().sendGet(AppConstant.GET_CHECKOUT_DETAILS, false);

                if (response != null) {
                    JSONObject obj = new JSONObject(response);

                    if (obj.getBoolean("status")) {
                        JSONObject model = obj.getJSONObject("model");
                        listItems = new Gson().fromJson(model.toString(), new TypeToken<CheckoutModel>() {
                        }.getType());
                        res = new AsyncTaskResult<>(null, listItems);
                    } else {
                        res = new AsyncTaskResult<>(new Exception(obj.getString("message")), null);
                    }
                } else {
                    res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
                }
            } catch (Exception exc) {
                exc.printStackTrace();
                res = new AsyncTaskResult<>(new Exception(AppConstant.SERVER_ERROR_MSG), null);
            }
        } else {
            res = new AsyncTaskResult<>(new Exception("Cancelled"), null);
        }
        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<CheckoutModel> listAsyncTaskResult) {
        super.onPostExecute(listAsyncTaskResult);

        if (!this.isCancelled() && this.mResult != null)
            this.mResult.response(listAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<CheckoutModel> listener) {
        this.mResult = listener;
    }
}