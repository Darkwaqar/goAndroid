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

public class UpdateCartQuantityAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<BeanGetAllCarts>> {

    private AsyncTaskResultListener<BeanGetAllCarts> mResult;
    private int mProductId, mCartItemId, mUpdateQuantity;

    public UpdateCartQuantityAsyncTask(int productId, int cartItemId, int updateQuantity) {
        this.mProductId = productId;
        this.mCartItemId = cartItemId;
        this.mUpdateQuantity = updateQuantity;
    }

    @Override
    protected AsyncTaskResult<BeanGetAllCarts> doInBackground(Void... params) {
        AsyncTaskResult<BeanGetAllCarts> res = null;

        try {

            JSONObject jsonObject = new JSONObject();
            String response;

            if (mUpdateQuantity == 0) {
                jsonObject.put("removefromcart", mCartItemId);
                response = new GetPostSender().sendPostJSON(AppConstant.DELETE_FROM_CART, jsonObject.toString(), false);
            } else {
                jsonObject.put("productId", mProductId);
                jsonObject.put("cartitemid", mCartItemId);
                jsonObject.put("itemquantity", String.valueOf(mUpdateQuantity));

                response = new GetPostSender().sendPostJSON(AppConstant.UPDATE_CART, jsonObject.toString(), false);
            }

            if (response != null) {

                JSONObject jsonResponse = new JSONObject(response);
                if (jsonResponse.getBoolean("status")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BeanGetAllCarts>() {
                    }.getType();

                    JSONObject model = jsonResponse.getJSONObject("model");
                    BeanGetAllCarts cart = (BeanGetAllCarts) gson.fromJson(model.toString(), type);
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

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<BeanGetAllCarts> cartAsyncTaskResult) {
        super.onPostExecute(cartAsyncTaskResult);

        if (!this.isCancelled() && mResult != null)
            mResult.response(cartAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<BeanGetAllCarts> listener) {
        mResult = listener;
    }


}
