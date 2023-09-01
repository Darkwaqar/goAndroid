package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.growonline.gomobishop.model.BeanProdDetailWithRProd;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

public class GetProductDetailAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<BeanProdDetailWithRProd>> {

    private AsyncTaskResultListener<BeanProdDetailWithRProd> mResult;
    private int mProductId;


    public GetProductDetailAsyncTask(int productId) {
        mProductId = productId;
    }

    @Override
    protected AsyncTaskResult<BeanProdDetailWithRProd> doInBackground(Void... params) {
        AsyncTaskResult<BeanProdDetailWithRProd> res;

        try {
            BeanProdDetailWithRProd temp;
            String uri = AppConstant.GET_PRODUCT_WITH_RELATED_PRODUCTS + "?productId=" + this.mProductId;
            String response = new GetPostSender().sendGet(uri, false);


            if (response != null) {
                JSONObject jsonResponse = new JSONObject(response);
                if (!jsonResponse.has("status")) {
                    Gson gson = new Gson();
                    temp = gson.fromJson(response, BeanProdDetailWithRProd.class);
                    res = new AsyncTaskResult<>(null, temp);
                } else
                    res = new AsyncTaskResult<>(new Exception(jsonResponse.getString("message")), null);

            } else {
                res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
            }

        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<BeanProdDetailWithRProd> beanProductDetailAsyncTaskResult) {
        super.onPostExecute(beanProductDetailAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(beanProductDetailAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<BeanProdDetailWithRProd> listener) {
        mResult = listener;
    }


}
