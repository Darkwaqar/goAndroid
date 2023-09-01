package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

public class UseRewardPointAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<Boolean>> {
    private AsyncTaskResultListener<Boolean> mResult;
    private boolean useRewardPoint;

    public UseRewardPointAsyncTask(Boolean useRewardPoint) {
        this.useRewardPoint = useRewardPoint;
    }

    @Override
    protected AsyncTaskResult<Boolean> doInBackground(Void... voids) {
        AsyncTaskResult<Boolean> res;
        try {
            JSONObject params = new JSONObject();
            params.put("useRewardPoints", useRewardPoint);
            String response = new GetPostSender().sendPostJSON(AppConstant.USE_REWARD_POINT, params.toString(), false);
            if (response != null) {

                JSONObject jsonResponse = new JSONObject(response);
                if (jsonResponse.getBoolean("status")) {
                    res = new AsyncTaskResult<>(null, true);
                } else {
                    res = new AsyncTaskResult<>(new NetworkErrorException(jsonResponse.getString("message")), false);
                }
            } else {
                res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), false);
            }

        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Boolean> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if (!this.isCancelled() && mResult != null)
            mResult.response(booleanAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<Boolean> listener) {
        mResult = listener;
    }

}
