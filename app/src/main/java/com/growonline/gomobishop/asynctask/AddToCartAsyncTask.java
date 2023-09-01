package com.growonline.gomobishop.asynctask;

import android.os.AsyncTask;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

public class AddToCartAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<Boolean>> {

    private AsyncTaskResultListener<Boolean> mResult;
    private int mProductId, mQuantity, mShoppingCartTypeId;
    private String mPrice;
    private JSONObject mAttributesJson;

    public AddToCartAsyncTask(int productId, int quantity, int shoppingCartTypeId,
                              String price) {

        this.mProductId = productId;
        this.mQuantity = quantity;
        this.mShoppingCartTypeId = shoppingCartTypeId;
        this.mPrice = price;
    }

    public AddToCartAsyncTask(int productId, int quantity, int shoppingCartTypeId,
                              String price, JSONObject attributesJson) {

        this.mProductId = productId;
        this.mQuantity = quantity;
        this.mShoppingCartTypeId = shoppingCartTypeId;
        this.mPrice = price;
        this.mAttributesJson = attributesJson;

    }

    @Override
    protected AsyncTaskResult<Boolean> doInBackground(Void... params) {
        AsyncTaskResult<Boolean> res;

        try {

            JSONObject mJsonRequest = new JSONObject();
            mJsonRequest.put("sQuantity", mQuantity);
            mJsonRequest.put("productId", mProductId);
            mJsonRequest.put("sPrice", mPrice);
            mJsonRequest.put("shoppingCartTypeId", mShoppingCartTypeId);
            mJsonRequest.put("sUpdatedItemId", 1);

            if (mAttributesJson == null)
                mAttributesJson = new JSONObject();


            mAttributesJson.put("addtocart_" + mProductId + ".EnteredQuantity", mQuantity);
            mAttributesJson.put("addtocart_" + mProductId + ".CustomerEnteredPrice", mPrice);
            mAttributesJson.put("addtocart_" + mProductId + ".UpdatedShoppingCartItemId", 1);

            mJsonRequest.put("attributesJson", mAttributesJson.toString());


            String response = new GetPostSender().sendPostJSON(AppConstant.ADD_TO_CART, mJsonRequest.toString(), !GoMobileApp.IsUserLogin());

            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.getBoolean("status")) {

                JSONObject model = jsonResponse.getJSONObject("model");
                GoMobileApp.setShoppingCartItemsCount(model.getInt("ShoppingCartItemsCount"));

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
