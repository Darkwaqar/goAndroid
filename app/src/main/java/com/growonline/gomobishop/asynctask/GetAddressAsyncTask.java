package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.GetAddressModel;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class GetAddressAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<GetAddressModel>> {

    private AsyncTaskResultListener<GetAddressModel> mResult;

    @Override
    protected AsyncTaskResult<GetAddressModel> doInBackground(Void... params) {
        AsyncTaskResult<GetAddressModel> res;

        try {
            String response = new GetPostSender().sendGet(AppConstant.GET_SHIPPING_BILLING_ADDRESS_URL, false);
            if (response != null) {

                Gson gson = new Gson();
                Type type = new TypeToken<GetAddressModel>() {
                }.getType();

                JSONObject obj = new JSONObject(response);

                if (obj.getBoolean("status")) {
                    GetAddressModel address = gson.fromJson(obj.getJSONObject("model").toString(), type);
                    res = new AsyncTaskResult<>(null, address);
                } else {
                    res = new AsyncTaskResult<>(new Exception(obj.getString("message")), null);
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
    protected void onPostExecute(AsyncTaskResult<GetAddressModel> getAddressModelAsyncTaskResult) {
        super.onPostExecute(getAddressModelAsyncTaskResult);

        if (!this.isCancelled() && mResult != null)
            mResult.response(getAddressModelAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<GetAddressModel> listener) {
        this.mResult = listener;
    }

}
