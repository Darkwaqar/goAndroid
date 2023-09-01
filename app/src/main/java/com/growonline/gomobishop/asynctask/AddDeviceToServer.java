package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.growonline.gomobishop.model.VendorCatalogList;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by asifrizvi on 1/22/2019.
 */

public class AddDeviceToServer extends AsyncTask<Void, Void, AsyncTaskResult<Boolean>> {
    private AsyncTaskResultListener<Boolean> mResult;
    private JSONObject params;

    public AddDeviceToServer(JSONObject params) {
        this.params = params;
    }

    @Override
    protected AsyncTaskResult<Boolean> doInBackground(Void... voids) {
        AsyncTaskResult<Boolean> res;
        try {

            String uri = AppConstant.ADD_DEVICE;
            String response = new GetPostSender().sendPostJSON(uri, params.toString(), false);

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
