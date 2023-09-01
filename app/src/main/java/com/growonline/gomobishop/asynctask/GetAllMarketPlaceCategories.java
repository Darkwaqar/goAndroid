package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.CategorySimpleModel;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by asifrizvi on 1/29/2019.
 */

public class GetAllMarketPlaceCategories extends AsyncTask<Void, Void, AsyncTaskResult<List<CategorySimpleModel>>> {

    private AsyncTaskResultListener<List<CategorySimpleModel>> mResult;
    private int mCategoryId;

    public GetAllMarketPlaceCategories(int categoryId) {
        mCategoryId = categoryId;
    }

    @Override
    protected AsyncTaskResult<List<CategorySimpleModel>> doInBackground(Void... params) {

        AsyncTaskResult<List<CategorySimpleModel>> res;

        if (!this.isCancelled()) {
            try {
                List<CategorySimpleModel> categories;

                String uri = AppConstant.GET_ALL_MARKET_CATEGORY_URL + "?CategoryId=" + this.mCategoryId;
                String response = new GetPostSender().sendGet(uri, false);
                if (response != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<CategorySimpleModel>>() {
                    }.getType();
                    JSONObject obj = new JSONObject(response);
                    if (obj.getBoolean("status")) {
                        categories = gson.fromJson(obj.getJSONArray("model").toString(), type);
                        res = new AsyncTaskResult<>(null, categories);
                    } else {
                        res = new AsyncTaskResult<>(new Exception(obj.getString("message")), null);
                    }
                } else {
                    res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                res = new AsyncTaskResult<>(new Exception("Invalid response is coming from the server"), null);
            }
        } else {
            res = new AsyncTaskResult<>(new Exception("Cancelled"), null);
        }
        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<List<CategorySimpleModel>> listAsyncTaskResult) {
        super.onPostExecute(listAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(listAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<List<CategorySimpleModel>> listener) {
        mResult = listener;
    }


}