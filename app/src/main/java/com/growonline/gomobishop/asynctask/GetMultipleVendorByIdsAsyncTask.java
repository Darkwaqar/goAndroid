package com.growonline.gomobishop.asynctask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class GetMultipleVendorByIdsAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<List<Vendor>>> {

    private List<Integer> mVendorIds;
    private AsyncTaskResultListener<List<Vendor>> mResultListener;
    private boolean saveTempUser;

    public GetMultipleVendorByIdsAsyncTask(List<Integer> vendorIds, boolean saveTempUser) {
        this.mVendorIds = vendorIds;
        this.saveTempUser = saveTempUser;
    }

    @Override
    protected AsyncTaskResult<List<Vendor>> doInBackground(Void... params) {

        AsyncTaskResult<List<Vendor>> res;

        if (!this.isCancelled()) {
            try {

                String requestedIds = "";
                for (int i = 0; i < mVendorIds.size(); i++) {
                    if (i == 0)
                        requestedIds += String.valueOf(mVendorIds.get(i));
                    else
                        requestedIds += "," + String.valueOf(mVendorIds.get(i));
                }

                String uri = AppConstant.GET_SELECTED_VENDOR_BY_IDS_URL + "?ids=" + requestedIds;
                String response = new GetPostSender().sendGet(uri, saveTempUser);

                if (response != null) {

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Vendor>>() {
                    }.getType();

                    JSONObject obj = new JSONObject(response);
                    if (obj.getBoolean("status")) {

                        List<Vendor> vendors = gson.fromJson(obj.getJSONArray("model").toString(), type);
                        res = new AsyncTaskResult<>(null, vendors);

                    } else {
                        res = new AsyncTaskResult<>(new Exception(obj.getString("message")), null);
                    }
                } else {
                    res = new AsyncTaskResult<>(new Exception(AppConstant.SERVER_ERROR_MSG), null);
                }

            } catch (Exception ex) {
                res = new AsyncTaskResult<>(ex, null);
            }
        } else {
            res = new AsyncTaskResult<>(new Exception("Cancelled"), null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<List<Vendor>> vendorAsyncTaskResult) {
        super.onPostExecute(vendorAsyncTaskResult);

        if (!this.isCancelled() && mResultListener != null) {
            mResultListener.response(vendorAsyncTaskResult);
        }
    }

    public void addOnResultsListener(AsyncTaskResultListener<List<Vendor>> listener) {
        mResultListener = listener;
    }

}
