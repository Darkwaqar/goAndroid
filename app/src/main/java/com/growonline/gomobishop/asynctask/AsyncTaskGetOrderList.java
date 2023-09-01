package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.OrderListingActivity;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.model.OrderListing;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

public class AsyncTaskGetOrderList extends BaseAsynctask {
    private OrderListing OrderListing;
    private int pageNumber = 1;


    public AsyncTaskGetOrderList(Activity activity, Boolean isProgressEnable, int pageNumber) {
        super(activity, isProgressEnable);
        this.pageNumber = pageNumber;
    }

    @Override
    protected void onComplete(String output) {
        if (mActivity instanceof OrderListingActivity)
            ((OrderListingActivity) mActivity).onListObtained(OrderListing);
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = new GetPostSender().sendGet(AppConstant.GET_ORDER_lIST + "?pageNumber=" + pageNumber, false);
        String checkpoint = onResponseReceived(response);

        if (checkpoint.equalsIgnoreCase("")) {
            try {
                JSONObject json = new JSONObject(response).getJSONObject("model");
                OrderListing = new Gson().fromJson(json.toString(), new TypeToken<OrderListing>() {
                }.getType());
            } catch (Exception exe) {
                exe.printStackTrace();
                return "Invalid response is coming from the server";
            }
        } else {
            return checkpoint;
        }
        return "";
    }
}

