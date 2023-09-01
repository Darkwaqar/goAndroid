package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.Address;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by asifrizvi on 6/15/2019.
 */

public class GetAddressesAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<List<Address>>> {
    private List<Address> mAddressList;
    private AsyncTaskResultListener<List<Address>> mResultListener;


    public GetAddressesAsyncTask() {

    }

    @Override
    protected AsyncTaskResult<List<Address>> doInBackground(Void... voids) {
        AsyncTaskResult<List<Address>> res;

        if (!this.isCancelled()) {
            try {
                String response = new GetPostSender().sendGet(AppConstant.GET_ADDRESS_LIST, false);
                if (response != null) {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getBoolean("status")) {
                        JSONArray json = new JSONObject(response).getJSONObject("model").getJSONArray("Addresses");
                        mAddressList = new Gson().fromJson(json.toString(), new TypeToken<List<Address>>() {
                        }.getType());
                        res = new AsyncTaskResult<>(null, mAddressList);
                    } else {
                        res = new AsyncTaskResult<>(new Exception(obj.getString("message")), null);
                    }
                } else {
                    res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
                }

            } catch (Exception ex) {
                res = new AsyncTaskResult<>(ex, null);
            }
        } else {
            res = new AsyncTaskResult<>(new Exception("Cancelled"), null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<List<Address>> vendorAsyncTaskResult) {
        super.onPostExecute(vendorAsyncTaskResult);

        if (!this.isCancelled() && mResultListener != null) {
            mResultListener.response(vendorAsyncTaskResult);
        }
    }

    public void addOnResultsListener(AsyncTaskResultListener<List<Address>> listener) {
        mResultListener = listener;
    }
}