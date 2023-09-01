package com.growonline.gomobishop.asynctask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.BackInStock;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by asifrizvi on 3/10/2019.
 */

public class NotifyWhenAvailable extends AsyncTask<Void, Void, AsyncTaskResult<BackInStock>> {
    private AsyncTaskResultListener<BackInStock> mResult;
    private JSONObject params;

    public NotifyWhenAvailable(JSONObject params) {
        this.params = params;
    }

    @Override
    protected AsyncTaskResult<BackInStock> doInBackground(Void... voids) {
        AsyncTaskResult<BackInStock> res;
        try {

            String uri = AppConstant.SEND_NOTIFY_WHEN_AVAILABLE;
            String response = new GetPostSender().sendPostJSON(uri, params.toString(), false);

            Gson gson = new Gson();
            Type type = new TypeToken<BackInStock>() {
            }.getType();

            JSONObject obj = new JSONObject(response);
            if (obj.getBoolean("status")) {
                BackInStock backInStock = gson.fromJson(obj.getJSONObject("model").toString(), type);
                res = new AsyncTaskResult<>(null, backInStock);
            } else {
                res = new AsyncTaskResult<>(new Exception(obj.getString("message")), null);
            }
        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<BackInStock> listAsyncTaskResult) {
        super.onPostExecute(listAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(listAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<BackInStock> listener) {
        mResult = listener;
    }
}