package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class GetVendorByIdAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<Vendor>> {

    private int mVendorId;
    private AsyncTaskResultListener<Vendor> mResultListener;


    public GetVendorByIdAsyncTask(int vendorId) {
        this.mVendorId = vendorId;
    }

    @Override
    protected AsyncTaskResult<Vendor> doInBackground(Void... voids) {
        AsyncTaskResult<Vendor> res;

        if (!this.isCancelled()) {
            try {

                String uri = AppConstant.GET_VENDOR_URL + "?vendorid=" + this.mVendorId;
                String response = new GetPostSender().sendGet(uri, false);

                if (response != null) {

                    Gson gson = new Gson();
                    Type type = new TypeToken<Vendor>() {
                    }.getType();

                    JSONObject obj = new JSONObject(response);
                    if (obj.getBoolean("status")) {
                        Vendor vendor = gson.fromJson(obj.getJSONObject("model").toString(), type);
                        res = new AsyncTaskResult<>(null, vendor);
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
    protected void onPostExecute(AsyncTaskResult<Vendor> vendorAsyncTaskResult) {
        super.onPostExecute(vendorAsyncTaskResult);

        if (!this.isCancelled() && mResultListener != null) {
            mResultListener.response(vendorAsyncTaskResult);
        }
    }

    public void addOnResultsListener(AsyncTaskResultListener<Vendor> listener) {
        mResultListener = listener;
    }

}
