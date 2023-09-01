package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.model.BeanGetAllCarts;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class GetCartDetailAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<BeanGetAllCarts>> {

    private AsyncTaskResultListener<BeanGetAllCarts> mResult;

    public GetCartDetailAsyncTask() {
    }

    @Override
    protected AsyncTaskResult<BeanGetAllCarts> doInBackground(Void... params) {

        AsyncTaskResult<BeanGetAllCarts> res;

        if (!this.isCancelled()) {
            try {
                String response = new GetPostSender().sendGet(AppConstant.GET_CART_DETAILS, false);
                if (response != null) {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.getBoolean("status")) {

                        Gson gson = new Gson();
                        Type type = new TypeToken<BeanGetAllCarts>() {
                        }.getType();

                        JSONObject model = jsonResponse.getJSONObject("model");
                        BeanGetAllCarts cart = gson.fromJson(model.toString(), type);
                        GoMobileApp.setShoppingCartItemsCount(model.getInt("ShoppingCartItemsCount"));

                        res = new AsyncTaskResult<>(null, cart);
                    } else {
                        res = new AsyncTaskResult<>(new Exception(jsonResponse.getString("message")), null);
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
    protected void onPostExecute(AsyncTaskResult<BeanGetAllCarts> beanGetAllCartsAsyncTaskResult) {
        super.onPostExecute(beanGetAllCartsAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(beanGetAllCartsAsyncTaskResult);
        }
    }

    public void addOnResultsListener(AsyncTaskResultListener<BeanGetAllCarts> listener) {
        mResult = listener;
    }

}
