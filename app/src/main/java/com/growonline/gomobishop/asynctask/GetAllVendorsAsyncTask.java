package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.VendorList;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by asifrizvi on 8/25/2018.
 */

public class GetAllVendorsAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<List<VendorList>>> {

    private AsyncTaskResultListener<List<VendorList>> mResult;
    private int type;

    public GetAllVendorsAsyncTask(int type) {
        this.type = type;

    }

    @Override
    protected AsyncTaskResult<List<VendorList>> doInBackground(Void... voids) {

        AsyncTaskResult<List<VendorList>> res;

        if (!this.isCancelled()) {
            try {

                List<VendorList> listItems;
                String uri = "";
                if (type == AppConstant.FEATURED_VENDOR) {
                    uri = AppConstant.GET_FEATURED_VENDORS;
                }
                if (type == AppConstant.ALL_VENDOR) {
                    uri = AppConstant.GET_ALL_VENDORS;
                }
                if (type == AppConstant.BRANDS) {
                    uri = AppConstant.GET_ALL_VENDORS.concat("?Brand=true");
                }
                if (type == AppConstant.DESIGNER) {
                    uri = AppConstant.GET_ALL_VENDORS.concat("?Designer=true");
                }
                if (type == AppConstant.SHE_EARNS) {
                    uri = AppConstant.GET_ALL_VENDORS.concat("?SheEarns=true");
                }

                String response = new GetPostSender().sendGet(uri, false);

                if (response != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<VendorList>>() {
                    }.getType();

                    JSONObject obj = new JSONObject(response);

                    if (obj.getBoolean("status")) {
                        listItems = gson.fromJson(obj.getJSONArray("model").toString(), type);
                        res = new AsyncTaskResult<>(null, listItems);
                    } else {
                        res = new AsyncTaskResult<>(new Exception(obj.getString("message")), null);
                    }
                } else {
                    res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
                }

            } catch (Exception exce) {
                exce.printStackTrace();
                res = new AsyncTaskResult<>(new Exception("Invalid response is coming from the server"), null);
            }
        } else {
            res = new AsyncTaskResult<>(new Exception("Cancelled"), null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<List<VendorList>> listAsyncTaskResult) {
        super.onPostExecute(listAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(listAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<List<VendorList>> listener) {
        mResult = listener;
    }

}
