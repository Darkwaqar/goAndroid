package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.WishListDetailsActivity;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.model.ShoppingCartItem;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AsyncTaskGetWishList extends BaseAsynctask {
    private ArrayList<ShoppingCartItem> mWishListModels;
    private String mCustomerGuId;

    public AsyncTaskGetWishList(Activity activity, boolean isProgressEnabled) {
        super(activity, isProgressEnabled);
    }

    @Override
    protected void onComplete(String output) {
        if (output.equalsIgnoreCase("")) {
            if (mActivity instanceof WishListDetailsActivity)
                ((WishListDetailsActivity) mActivity).onWishListObtained(mWishListModels, mCustomerGuId);
        } else {
            AppHelper.showMsgDialog(mActivity, "", output, null, null);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String response = new GetPostSender().sendGet(AppConstant.GET_WISHLIST_DETAILS, false);
        String checkPoint = onResponseReceived(response);


        if (checkPoint.equalsIgnoreCase("")) {
            try {
                JSONObject completeObj = new JSONObject(response);
                JSONObject model = completeObj.getJSONObject("model");

                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<ShoppingCartItem>>() {
                }.getType();
                mWishListModels = gson.fromJson(model.getString("Items"), type);
                mCustomerGuId = new JSONObject(response).optString("CustomerGuid");
            } catch (Exception exce) {
                exce.printStackTrace();
                return mActivity.getString(R.string.invalid_Response_comming_from_server);
            }
        } else {
            return checkPoint;
        }
        return "";
    }
}
