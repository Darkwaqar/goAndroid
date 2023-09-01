package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.NotificationActivity;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.model.Notification;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by asifrizvi on 8/2/2019.
 */

public class GetNotificationsAsyncTask extends BaseAsynctask {
    private List<Notification> offerList;

    public GetNotificationsAsyncTask(Activity activity, boolean isProgressEnabled) {
        super(activity, isProgressEnabled);
    }

    @Override
    protected void onComplete(String output) {
        ((NotificationActivity) mActivity).LoadOffers(offerList);
    }

    @Override
    protected String doInBackground(String... params) {
        String response = new GetPostSender().sendGet(AppConstant.GET_NOTIFICATION_URL, false);
        String checkPoint = onResponseReceived(response);
        JSONObject completeObj = null;
        if (checkPoint.equalsIgnoreCase("")) {
            try {
                completeObj = new JSONObject(response);
                response = completeObj.getJSONArray("model").toString();
                offerList = new Gson().fromJson(response, new TypeToken<List<Notification>>() {
                }.getType());
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