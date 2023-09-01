package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.MainActivity;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.model.BannerPicture;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by asifrizvi on 9/28/2018.
 */

public class AsyncTaskGetOffers extends BaseAsynctask {
    private List<BannerPicture> offerList = new ArrayList<>();

    public AsyncTaskGetOffers(Activity activity, boolean isProgressEnabled) {
        super(activity, isProgressEnabled);
    }

    @Override
    protected void onComplete(String output) {
        if (mActivity instanceof MainActivity) {
            ((MainActivity) mActivity).LoadOffers(offerList);
        }

    }

    @Override
    protected String doInBackground(String... params) {
        String response = "";
        if (mActivity instanceof MainActivity) {
            response = new GetPostSender().sendGet(AppConstant.GET_BANNER, false);
        }
        String checkPoint = onResponseReceived(response);

        if (checkPoint.equalsIgnoreCase("")) {
            try {
                AppHelper.LogEvent(response);
                JSONArray json = new JSONObject(response).getJSONArray("model");
                JSONObject firstArray = (JSONObject) json.get(0);
                offerList.addAll((Collection<? extends BannerPicture>) new Gson().fromJson(firstArray.getJSONArray("PictureModels").toString(), new TypeToken<List<BannerPicture>>() {
                }.getType()));
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