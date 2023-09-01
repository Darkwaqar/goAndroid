package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.AvalibleCountry;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asifrizvi on 10/9/2019.
 */

public class GetAvailableCountriesAndStates extends AsyncTask<Void, Void, AsyncTaskResult<ArrayList<AvalibleCountry>>> {

    private AsyncTaskResultListener<ArrayList<AvalibleCountry>> mResult;

    public GetAvailableCountriesAndStates(AsyncTaskResultListener<ArrayList<AvalibleCountry>> mResult) {
        this.mResult = mResult;
    }

    @Override
    protected AsyncTaskResult<ArrayList<AvalibleCountry>> doInBackground(Void... params) {
        AsyncTaskResult<ArrayList<AvalibleCountry>> res;

        try {
            String response = new GetPostSender().sendGet(AppConstant.Get_AVAILABLE_SATES_AND_COUNTRY, false);

            if (response != null) {

                Gson gson = new Gson();
                Type type = new TypeToken<List<AvalibleCountry>>() {
                }.getType();

                JSONObject obj = new JSONObject(response);

                if (obj.getBoolean("status")) {
                    ArrayList<AvalibleCountry> address = gson.fromJson(obj.getJSONObject("model").getJSONArray("Countries").toString(), type);
                    res = new AsyncTaskResult<>(null, address);
                } else {
                    res = new AsyncTaskResult<>(new Exception(obj.getString("message")), null);
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
    protected void onPostExecute(AsyncTaskResult<ArrayList<AvalibleCountry>> getAddressModelAsyncTaskResult) {
        super.onPostExecute(getAddressModelAsyncTaskResult);

        if (!this.isCancelled() && mResult != null)
            mResult.response(getAddressModelAsyncTaskResult);
    }

    public void addOnResultListener(AsyncTaskResultListener<ArrayList<AvalibleCountry>> listener) {
        this.mResult = listener;
    }

}
