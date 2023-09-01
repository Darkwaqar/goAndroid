package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.SearchModel;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class SearchVendorsAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<SearchModel>> {

    private AsyncTaskResultListener<SearchModel> mSearchResultListener;
    private String searchTerm;
    private int vendorId;
    private int categoryId;
    private int orderBy;
    private int pageNumber;
    private int pageSize;


    public SearchVendorsAsyncTask(String searchTerm, int vendorId, int categoryId, int orderBy, int pageNumber, int pageSize) {
        this.searchTerm = searchTerm;
        this.vendorId = vendorId;
        this.categoryId = categoryId;
        this.orderBy = orderBy;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.execute();
    }

    @Override
    protected AsyncTaskResult<SearchModel> doInBackground(Void... voids) {

        AsyncTaskResult<SearchModel> res;

        if (!this.isCancelled()) {
            try {

                SearchModel searchModel;
                String uri = AppConstant.SEARCH_ALL_URL;
                if (this.searchTerm != null) {
                    uri = AppConstant.SEARCH_ALL_URL + "?SearchTerm=" + this.searchTerm;
                }
                JSONObject parms = new JSONObject();
//                if (this.searchTerm != null)
//                    parms.put("SearchTerm", this.searchTerm);
                parms.put("CategoryId", this.categoryId);
                parms.put("VendorId", this.vendorId);
                parms.put("pageNumber", this.pageNumber);
                parms.put("pageSize", this.pageSize);
                parms.put("OrderBy", this.orderBy);


                String response = new GetPostSender().sendPostJSON(uri, parms.toString(), false);

                if (response != null) {

                    Gson gson = new Gson();
                    Type type = new TypeToken<SearchModel>() {
                    }.getType();

                    JSONObject obj = new JSONObject(response);
                    if (obj.getBoolean("status")) {
                        searchModel = gson.fromJson(obj.getJSONObject("model").toString(), type);
                        res = new AsyncTaskResult<>(null, searchModel);
                    } else {
                        res = new AsyncTaskResult<>(new Exception(obj.getString("message")), null);
                    }
                } else {
                    res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                res = new AsyncTaskResult<>(new Exception("Invalid response is coming from the server"), null);
            }
        } else {
            res = new AsyncTaskResult<>(new Exception("Cancelled"), null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<SearchModel> result) {
        super.onPostExecute(result);

        if (!this.isCancelled() && mSearchResultListener != null) {
            mSearchResultListener.response(result);
        }
    }

    public void addOnSearchResultsListener(AsyncTaskResultListener<SearchModel> listener) {
        mSearchResultListener = listener;
    }

}
