package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.ConversationsModel;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GetConversation extends AsyncTask<Void, Void, AsyncTaskResult<List<ConversationsModel>>> {
    private AsyncTaskResultListener<List<ConversationsModel>> mResult;
    private List<ConversationsModel> chat;
    private Integer toCustomerId;

    public GetConversation(Integer toCustomerId) {
        this.toCustomerId = toCustomerId;
    }

    @Override
    protected AsyncTaskResult<List<ConversationsModel>> doInBackground(Void... voids) {
        AsyncTaskResult<List<ConversationsModel>> res;
        JSONObject params = new JSONObject();
        try {
            params.put("toCustomerId", toCustomerId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String response = new GetPostSender().sendPostJSON(AppConstant.GET_CONVERSATIONS, params.toString(), false);
        try {
            if (response != null) {
                JSONObject jsonObject = new JSONObject(response);
                Gson gson = new Gson();
                chat = gson.fromJson(jsonObject.get("updateconversationsectionhtml").toString(), new TypeToken<List<ConversationsModel>>() {
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
    protected void onPostExecute(AsyncTaskResult<List<ConversationsModel>> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if (!this.isCancelled() && mResult != null)
            mResult.response(booleanAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<List<ConversationsModel>> listener) {
        mResult = listener;
    }
}
