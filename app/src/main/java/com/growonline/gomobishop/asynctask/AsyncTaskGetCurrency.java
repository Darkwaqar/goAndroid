package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.google.gson.Gson;
import com.growonline.gomobishop.CountrySelectorActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.model.AllAvailableCurrencies;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONObject;

/**
 * Created by asifrizvi on 7/14/2018.
 */

public class AsyncTaskGetCurrency extends BaseAsynctask {
    private AllAvailableCurrencies mAllAvailableCurrencies;

    public AsyncTaskGetCurrency(Activity activity, boolean isProgressEnabled) {
        super(activity, isProgressEnabled);
    }

    @Override
    protected void onComplete(String output) {
        if (output.equalsIgnoreCase("")) {
            if (mActivity instanceof StoreActivity) {
                ((StoreActivity) mActivity).updateCurrency(mAllAvailableCurrencies);
            } else if (mActivity instanceof CountrySelectorActivity) {
                ((CountrySelectorActivity) mActivity).updateCurrency(mAllAvailableCurrencies);
            }
        } else {
            AppHelper.showMsgDialog(mActivity, mActivity.getString(R.string.app_name), output, null, null);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String response = new GetPostSender().sendGet(AppConstant.GET_CURRENCY, false);
        String checkPoint = onResponseReceived(response);

        if (checkPoint.equalsIgnoreCase("")) {
            try {
                AppHelper.LogEvent(response);
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("status");
                if (success.equalsIgnoreCase("true")) {
                    mAllAvailableCurrencies = new Gson().fromJson(jsonObject.getJSONObject("model").toString(), AllAvailableCurrencies.class);
                } else {
                    return jsonObject.getString("message");
                }
            } catch (Exception exc) {
                exc.printStackTrace();
                return exc.toString();
            }
        } else {
            return checkPoint;
        }
        return "";
    }
}