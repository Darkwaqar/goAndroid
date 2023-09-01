package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

/**
 * Created by asifrizvi on 8/10/2019.
 */

public class CancelRedirectionAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<Boolean>> {
    private AsyncTaskResultListener<Boolean> mResult;

    public CancelRedirectionAsyncTask() {
    }

    @Override
    protected AsyncTaskResult<Boolean> doInBackground(Void... voids) {
        AsyncTaskResult<Boolean> res;
        try {

            String uri = AppConstant.CANCEL_REDIRECTION;
            String response = new GetPostSender().sendGet(uri, false);

            if (response != null) {
                JSONObject jsonResponse = new JSONObject(response);
                res = new AsyncTaskResult<>(null, jsonResponse.getBoolean("status"));
            } else {
                res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), false);
            }
        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, false);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Boolean> listAsyncTaskResult) {
        super.onPostExecute(listAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(listAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<Boolean> listener) {
        mResult = listener;
    }
}
