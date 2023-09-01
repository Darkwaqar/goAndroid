package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.VendorReview;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by asifrizvi on 4/7/2019.
 */

public class GetVendorReviews extends AsyncTask<Void, Void, AsyncTaskResult<List<VendorReview>>> {
    private AsyncTaskResultListener<List<VendorReview>> mResult;
    private List<VendorReview> mVendorReviews;

    public GetVendorReviews() {
    }

    @Override
    protected AsyncTaskResult<List<VendorReview>> doInBackground(Void... voids) {
        String uri = AppConstant.REVIEW_DATA_URL;
        AsyncTaskResult<List<VendorReview>> res;
        JSONObject params = new JSONObject();
        try {
            params.put("type", "vendor");
            String response = new GetPostSender().sendPostJSON(uri, params.toString(), true);
            if (response != null) {
                JSONObject jsonResponse = new JSONObject(response);
                Gson gson = new Gson();
                JSONObject message = jsonResponse.getJSONObject("model");
                mVendorReviews = gson.fromJson(message.getJSONArray("VendorReviews").toString(), new TypeToken<List<VendorReview>>() {
                }.getType());
                res = new AsyncTaskResult<>(null, mVendorReviews);

            } else {
                res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
            }
        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<List<VendorReview>> listAsyncTaskResult) {
        super.onPostExecute(listAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(listAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<List<VendorReview>> listener) {
        mResult = listener;
    }


}
