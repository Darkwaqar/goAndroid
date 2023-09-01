package com.growonline.gomobishop.asynctask;

import android.os.AsyncTask;


import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

/**
 * Created by asifrizvi on 4/2/2019.
 */

public class AddItemsToCartFromWishlist extends AsyncTask<Void, Void, AsyncTaskResult<Boolean>> {

    private AsyncTaskResultListener<Boolean> mResult;
    private String mProductId;

    public AddItemsToCartFromWishlist(String productId) {
        this.mProductId = productId;
    }

    @Override
    protected AsyncTaskResult<Boolean> doInBackground(Void... params) {
        AsyncTaskResult<Boolean> res;

        try {
            JSONObject mJsonRequest = new JSONObject();
            mJsonRequest.put("WishlistItems", mProductId);

            String response = new GetPostSender().sendPostJSON(AppConstant.ADD_ITEMS_TO_CART_FROM_WISHLIST, mJsonRequest.toString(), false);

            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.getBoolean("status")) {
                res = new AsyncTaskResult<>(null, true);
            } else {
                res = new AsyncTaskResult<>(new Exception(jsonResponse.getString("message")), false);
            }

        } catch (Exception ex) {
            res = new AsyncTaskResult<>(new Exception(AppConstant.SERVER_ERROR_MSG), false);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Boolean> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(booleanAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<Boolean> listener) {
        mResult = listener;
    }


}