package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.growonline.gomobishop.AddressActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONObject;

/**
 * Created by asifrizvi on 6/15/2019.
 */

public class DeleteAddressAsyncTask extends BaseAsynctask {
    private int mAddressId;

    /**
     * @param mActivity  Activity From where Request Come
     * @param mAddressId Address Id
     */
    public DeleteAddressAsyncTask(Activity mActivity, int mAddressId) {
        super(mActivity, true);
        this.mAddressId = mAddressId;
    }

    @Override
    protected void onComplete(String output) {
        if (output.equalsIgnoreCase("")) {
            if (mActivity instanceof AddressActivity) {
                AppHelper.showMsgDialog(mActivity, "", mActivity.getString(R.string.delete_address_msg), null, null);
                ((AddressActivity) mActivity).LoadAddress();
            }
        } else {
            AppHelper.showMsgDialog(mActivity, "", output, null, null);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String response = getResponseFromService();
        String checkPoint = onResponseReceived(response);

        if (checkPoint.equalsIgnoreCase("")) {
            return "";
        } else {
            return checkPoint;
        }
    }

    private String getResponseFromService() {
        JSONObject jsonObject = new JSONObject();
        String response = "";
        try {
            jsonObject.put("addressId", mAddressId);
            response = new GetPostSender().sendPostJSON(AppConstant.ADDRESS_DELETE, jsonObject.toString(), false);
            AppHelper.LogEvent(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response = "";
        }
        return response;
    }
}