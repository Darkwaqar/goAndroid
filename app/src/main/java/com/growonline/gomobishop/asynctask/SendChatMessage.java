package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.UpdateChat;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

public class SendChatMessage extends AsyncTask<Void, Void, AsyncTaskResult<UpdateChat>> {
    private AsyncTaskResultListener<UpdateChat> mResult;
    private UpdateChat chat;
    private String params;

    public SendChatMessage(String params) {
        this.params = params;
    }

    @Override
    protected AsyncTaskResult<UpdateChat> doInBackground(Void... voids) {
        AsyncTaskResult<UpdateChat> res;

        String response = new GetPostSender().sendPostJSON(AppConstant.SEND_PRIVATE_MESSAGE, params, false);
        try {
            if (response != null) {
                Gson gson = new Gson();
                chat = gson.fromJson(response, new TypeToken<UpdateChat>() {
                }.getType());
                res = new AsyncTaskResult<>(null, chat);

            } else {
                res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
            }
        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, null);
        }
        return res;
    }


    @Override
    protected void onPostExecute(AsyncTaskResult<UpdateChat> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if (!this.isCancelled() && mResult != null)
            mResult.response(booleanAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<UpdateChat> listener) {
        mResult = listener;
    }
}
