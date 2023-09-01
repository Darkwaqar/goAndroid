package com.growonline.gomobishop.asynctask;

import android.os.AsyncTask;

import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

/**
 * Created by asifrizvi on 3/10/2019.
 */

public class BackInStockPost extends AsyncTask<Void, Void, AsyncTaskResult<String>> {
    private AsyncTaskResultListener<String> mResult;
    private JSONObject params;

    public BackInStockPost(JSONObject params) {
        this.params = params;
    }

    @Override
    protected AsyncTaskResult<String> doInBackground(Void... voids) {
        AsyncTaskResult<String> res;
        try {

            String uri = AppConstant.SEND_NOTIFY_WHEN_AVAILABLE_POST;
            String response = new GetPostSender().sendPostJSON(uri, params.toString(), false);
            JSONObject obj = new JSONObject(response);
            res = new AsyncTaskResult<>(null, obj.getString("result"));
        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<String> listAsyncTaskResult) {
        super.onPostExecute(listAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(listAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<String> listener) {
        mResult = listener;
    }
}