package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

/**
 * Created by asifrizvi on 4/3/2019.
 */

public class AddReviewAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<String>> {
    private AsyncTaskResultListener<String> mResult;
    private JSONObject params;
    private int Type;

    public AddReviewAsyncTask(JSONObject params, int type) {
        this.params = params;
        this.Type = type;
    }

    @Override
    protected AsyncTaskResult<String> doInBackground(Void... voids) {
        AsyncTaskResult<String> res;
        try {
            String uri = "";
            String message = "";
            if (Type == AppConstant.VENDOR_REVIEW)
                uri = AppConstant.ADD_VENDOR_REVIEW;
            else
                uri = AppConstant.ADD_PRODUCT_REVIEW;

            String response = new GetPostSender().sendPostJSON(uri, params.toString(), true);

            if (response != null) {
                JSONObject jsonResponse = new JSONObject(response);
                if (jsonResponse.getBoolean("status")){
                    message = jsonResponse.getString("message");
                    res = new AsyncTaskResult<>(null, message);
                }else
                    res = new AsyncTaskResult<>(new Exception(jsonResponse.getString("message")), null);

            } else {
                res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
            }
        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, ex.getMessage());
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
