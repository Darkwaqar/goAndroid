package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

public class AsyncTaskRecoverPassword extends AsyncTask<Void, Void, AsyncTaskResult<Boolean>> {

    private String mEmail;
    private AsyncTaskResultListener<Boolean> mResult;

    public AsyncTaskRecoverPassword(String email) {
        this.mEmail = email;
    }

    @Override
    protected AsyncTaskResult<Boolean> doInBackground(Void... params) {

        AsyncTaskResult<Boolean> tResult;

        if (!this.isCancelled()) {
            try {

                String uri = AppConstant.RECOVER_PASSWORD + this.mEmail;
                String response = new GetPostSender().sendGet(uri, false);

                if (response != null) {

                    JSONObject obj = new JSONObject(response);

                    if (obj.getBoolean("status")) {
                        tResult = new AsyncTaskResult<>(null, true);
                    } else {
                        tResult = new AsyncTaskResult<>(new Exception(obj.getString("message")), null);
                    }
                } else {
                    tResult = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                tResult = new AsyncTaskResult<>(new Exception("Invalid response is coming from the server"), null);
            }
        } else {
            tResult = new AsyncTaskResult<>(new Exception("Cancelled"), null);
        }

        return tResult;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Boolean> res) {
        super.onPostExecute(res);

        if (!this.isCancelled() && this.mResult != null)
            this.mResult.response(res);
    }

    public void addOnResultListener(AsyncTaskResultListener<Boolean> listener) {
        this.mResult = listener;
    }

}
