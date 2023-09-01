package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.InboxModel;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.util.List;

public class GetAllVendorForChat extends AsyncTask<Void, Void, AsyncTaskResult<List<InboxModel>>> {
    private AsyncTaskResultListener<List<InboxModel>> mResult;
    private List<InboxModel> chat;

    public GetAllVendorForChat() {
    }

    @Override
    protected AsyncTaskResult<List<InboxModel>> doInBackground(Void... voids) {
        AsyncTaskResult<List<InboxModel>> res;
        String response = new GetPostSender().sendGet(AppConstant.GET_ALL_VENDOR_FOR_CHAT, false);
        try {

            if (response != null) {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("status");
                if (success.equalsIgnoreCase("true")) {
                    Gson gson = new Gson();
                    chat = gson.fromJson(jsonObject.get("model").toString(), new TypeToken<List<InboxModel>>() {
                    }.getType());
                    res = new AsyncTaskResult<>(null, chat);
                } else {
                    res = new AsyncTaskResult<>(new NetworkErrorException(jsonObject.getString("message")), null);
                }
            } else {
                res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
            }
        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, null);
        }
        return res;
    }


    @Override
    protected void onPostExecute(AsyncTaskResult<List<InboxModel>> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if (!this.isCancelled() && mResult != null)
            mResult.response(booleanAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<List<InboxModel>> listener) {
        mResult = listener;
    }
}
