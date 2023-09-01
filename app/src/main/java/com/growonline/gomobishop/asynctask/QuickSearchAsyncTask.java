package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.Tag;
import com.growonline.gomobishop.model.Tags;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by asifrizvi on 2/1/2020.
 */

public class QuickSearchAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<ArrayList<String>>> {

    private AsyncTaskResultListener<ArrayList<String>> mSearchResultListener;
    private ArrayList<String> result = new ArrayList<>();


    public QuickSearchAsyncTask() {
        this.execute();
    }

    @Override
    protected AsyncTaskResult<ArrayList<String>> doInBackground(Void... voids) {

        AsyncTaskResult<ArrayList<String>> res;

        if (!this.isCancelled()) {
            try {
                Tags searchModel;
                String response = new GetPostSender().sendGet(AppConstant.QUICK_SEARCH, false);

                if (response != null) {

                    Gson gson = new Gson();
                    Type type = new TypeToken<Tags>() {
                    }.getType();

                    JSONObject obj = new JSONObject(response);
                    if (obj.getBoolean("status")) {
                        searchModel = gson.fromJson(obj.getJSONObject("model").toString(), type);
                        if (searchModel.getTags().size() > 0) {
                            for (Tag tag : searchModel.getTags()) {
                                result.add(tag.getName());
                            }
                        }
                        res = new AsyncTaskResult<>(null, result);
                    } else {
                        res = new AsyncTaskResult<>(new Exception(obj.getString("message")), null);
                    }
                } else {
                    res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                res = new AsyncTaskResult<>(new Exception("Invalid response is coming from the server"), null);
            }
        } else {
            res = new AsyncTaskResult<>(new Exception("Cancelled"), null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<ArrayList<String>> result) {
        super.onPostExecute(result);

        if (!this.isCancelled() && mSearchResultListener != null) {
            mSearchResultListener.response(result);
        }
    }

    public void addOnSearchResultsListener(AsyncTaskResultListener<ArrayList<String>> listener) {
        mSearchResultListener = listener;
    }

}
