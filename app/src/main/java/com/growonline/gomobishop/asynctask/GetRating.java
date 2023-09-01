package com.growonline.gomobishop.asynctask;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.RatingActivity;
import com.growonline.gomobishop.base.BaseAsynctask;
import com.growonline.gomobishop.model.ReviewSummaryModel;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

/**
 * Created by asifrizvi on 2/20/2019.
 */

public class GetRating extends BaseAsynctask {
    private ReviewSummaryModel reviewSummaryModel;
    private int Type;
    private int Id;


    public GetRating(Activity activity, Boolean isProgressEnable, int Type, int Id) {
        super(activity, isProgressEnable);
        this.Type = Type;
        this.Id = Id;
    }

    @Override
    protected void onComplete(String output) {
        if (mActivity instanceof RatingActivity) {
            ((RatingActivity) mActivity).LoadRating(reviewSummaryModel);
        }

    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        if (Type == AppConstant.VENDOR_REVIEW) {
            response = new GetPostSender().sendGet(AppConstant.GET_VENDOR_RATING + Id, true);
        } else {
            response = new GetPostSender().sendGet(AppConstant.GET_PRODUCT_RATING + Id, true);
        }

        String checkpoint = onResponseReceived(response);

        if (checkpoint.equalsIgnoreCase("")) {
            try {
                JSONObject json = new JSONObject(response).getJSONObject("model");
                reviewSummaryModel = new Gson().fromJson(json.toString(), new TypeToken<ReviewSummaryModel>() {
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
