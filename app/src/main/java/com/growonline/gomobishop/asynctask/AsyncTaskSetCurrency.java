package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.growonline.gomobishop.CountrySelectorActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by asifrizvi on 7/14/2018.
 */

public class AsyncTaskSetCurrency extends BaseAsynctask {
    private int mMessage;
    private int mSelectedCurrencyId;

    public AsyncTaskSetCurrency(Activity activity, boolean isProgressEnabled, int SelectedCurrencyId) {
        super(activity, isProgressEnabled);
        mSelectedCurrencyId = SelectedCurrencyId;

    }

    @Override
    protected void onComplete(String output) {
        if (output.equalsIgnoreCase("")) {
            if (mActivity instanceof StoreActivity) {
                ((StoreActivity) mActivity).updatedCurrency(mMessage);
            } else if (mActivity instanceof CountrySelectorActivity) {
                ((CountrySelectorActivity) mActivity).updatedCurrency(mMessage);
            }
        } else {
            AppHelper.showMsgDialog(mActivity, mActivity.getString(R.string.app_name), output, null, null);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        JSONObject mParams = new JSONObject();
        try {
            mParams.put("customerCurrency", String.valueOf(mSelectedCurrencyId));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new GetPostSender().sendPostJSON(AppConstant.SET_CURRENCY, mParams.toString(), true);
        mMessage = R.string.currency_updated;
        return "";
    }
}