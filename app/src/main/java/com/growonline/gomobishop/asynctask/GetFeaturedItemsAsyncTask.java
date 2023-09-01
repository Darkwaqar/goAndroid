package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.Product;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;


public class GetFeaturedItemsAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<List<Product>>> {

    private AsyncTaskResultListener<List<Product>> mResult;
    private int mVendorId;

    public GetFeaturedItemsAsyncTask(int vendorId) {
        this.mVendorId = vendorId;
    }

    @Override
    protected AsyncTaskResult<List<Product>> doInBackground(Void... voids) {

        AsyncTaskResult<List<Product>> res;

        if (!this.isCancelled()) {
            try {

                List<Product> listItems;
                String uri = AppConstant.GET_FEATURES_ITEM_URL + "?vendorid=" + this.mVendorId;
                String response = new GetPostSender().sendGet(uri, false);

                if (response != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Product>>() {
                    }.getType();

                    JSONObject obj = new JSONObject(response);

                    if (obj.getBoolean("status")) {
                        listItems = gson.fromJson(obj.getJSONArray("model").toString(), type);
                        res = new AsyncTaskResult<>(null, listItems);
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
    protected void onPostExecute(AsyncTaskResult<List<Product>> listAsyncTaskResult) {
        super.onPostExecute(listAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(listAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<List<Product>> listener) {
        mResult = listener;
    }

}
