package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.ProductReview;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by asifrizvi on 4/7/2019.
 */

public class GetProductReviews extends AsyncTask<Void, Void, AsyncTaskResult<List<ProductReview>>> {
    private AsyncTaskResultListener<List<ProductReview>> mResult;
    private List<ProductReview> mProductReviews;

    public GetProductReviews() {
    }

    @Override
    protected AsyncTaskResult<List<ProductReview>> doInBackground(Void... voids) {
        String uri = AppConstant.REVIEW_DATA_URL;
        AsyncTaskResult<List<ProductReview>> res;
        JSONObject params = new JSONObject();
        try {
            params.put("type", "product");
            String response = new GetPostSender().sendPostJSON(uri, params.toString(), true);
            if (response != null) {
                JSONObject jsonResponse = new JSONObject(response);
                Gson gson = new Gson();

                JSONArray message = jsonResponse.getJSONArray("model");
                mProductReviews = gson.fromJson(message.toString(), new TypeToken<List<ProductReview>>() {
                }.getType());
                res = new AsyncTaskResult<>(null, mProductReviews);

            } else {
                res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
            }
        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<List<ProductReview>> listAsyncTaskResult) {
        super.onPostExecute(listAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(listAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<List<ProductReview>> listener) {
        mResult = listener;
    }


}
