package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.Chat;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

public class GetPrivateMessageIndex extends AsyncTask<Void, Void, AsyncTaskResult<Chat>> {
    private AsyncTaskResultListener<Chat> mResult;
    private Chat chat;

    public GetPrivateMessageIndex() {
    }

    @Override
    protected AsyncTaskResult<Chat> doInBackground(Void... voids) {
        AsyncTaskResult<Chat> res;
        String response = new GetPostSender().sendGet(AppConstant.GET_PRIVATE_CHAT_INDEX, false);
        try {

            if (response != null) {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("status");
                if (success.equalsIgnoreCase("true")) {
                    Gson gson = new Gson();
                    chat = gson.fromJson(jsonObject.get("model").toString(), new TypeToken<Chat>() {
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
    protected void onPostExecute(AsyncTaskResult<Chat> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if (!this.isCancelled() && mResult != null)
            mResult.response(booleanAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<Chat> listener) {
        mResult = listener;
    }
}