package com.growonline.gomobishop.asynctask;

import android.os.AsyncTask;

import com.growonline.gomobishop.network.MultiPartSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.util.HashMap;

public class ContactUsAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<Boolean>> {

    HashMap<String, String> mJsonObject = new HashMap<>();
    private AsyncTaskResultListener<Boolean> mResult;

    public ContactUsAsyncTask(HashMap<String, String> jsonObject) {
        mJsonObject = jsonObject;
    }

    @Override
    protected AsyncTaskResult<Boolean> doInBackground(Void... voids) {


        AsyncTaskResult<Boolean> res;

        String response;
        try {
            response = new MultiPartSender().sendPostStringUsingMultiPart(AppConstant.SEND_ENDUIRY_URL, mJsonObject, false);

            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.getBoolean("status")) {
                res = new AsyncTaskResult<>(null, true);
            } else {
                res = new AsyncTaskResult<>(new Exception(jsonResponse.getString("message")), false);
            }

        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, false);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Boolean> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(booleanAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<Boolean> listener) {
        mResult = listener;
    }


}
