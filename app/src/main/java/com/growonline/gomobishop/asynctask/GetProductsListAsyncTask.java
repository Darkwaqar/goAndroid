package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.CategoryListModel;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetProductsListAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<CategoryListModel>> {

    private AsyncTaskResultListener<CategoryListModel> mResult;
    private int mVendorId, mCategoryId, mPageNumber = 1, mPageSize = 6;
    private List<Integer> mFilterSpecs = new ArrayList<>();
    private AppConstant.ProductListFragmentMode mMode;
    private String mSearchTerm;
    private int mOrderBy;

    public GetProductsListAsyncTask(int vendorId, int categoryId, int pageNumber, int pageSize, int OrderBy,
                                    List<Integer> filterSpecs) {
        this.mVendorId = vendorId;
        this.mCategoryId = categoryId;
        this.mPageNumber = pageNumber;
        this.mPageSize = pageSize;
        this.mOrderBy = OrderBy;
        this.mMode = AppConstant.ProductListFragmentMode.SINGLE_CATEGORY;

        if (filterSpecs != null)
            this.mFilterSpecs = filterSpecs;
    }

    public GetProductsListAsyncTask(int vendorId, int pageNumber, int pageSize, int OrderBy, List<Integer> filterSpecs,
                                    AppConstant.ProductListFragmentMode mode) {
        this.mVendorId = vendorId;
        this.mPageNumber = pageNumber;
        this.mPageSize = pageSize;
        this.mOrderBy = OrderBy;
        this.mMode = mode;

        if (filterSpecs != null)
            this.mFilterSpecs = filterSpecs;
    }

    public GetProductsListAsyncTask(String mSearchTerm, Integer vendorId, int pageNumber, int pageSize, int OrderBy, ArrayList<Integer> filterSpecs, AppConstant.ProductListFragmentMode mode) {
        this.mSearchTerm = mSearchTerm;
        this.mVendorId = vendorId;
        this.mPageNumber = pageNumber;
        this.mPageSize = pageSize;
        this.mOrderBy = OrderBy;
        this.mMode = mode;

        if (filterSpecs != null)
            this.mFilterSpecs = filterSpecs;
    }

    @Override
    protected AsyncTaskResult<CategoryListModel> doInBackground(Void... params) {

        AsyncTaskResult<CategoryListModel> res;

        if (!this.isCancelled()) {
            try {

                CategoryListModel pagedProducts;
                String uri = AppConstant.GET_CATEGORY_LIST +
                        "?categoryId=" + this.mVendorId +
                        "&pageNumber=" + this.mPageNumber +
                        "&pageSize=" + this.mPageSize +
                        "&specs=" + commaSeparatedSpecs();


                String response = new GetPostSender().sendGet(uri, false);

                if (response != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<CategoryListModel>() {
                    }.getType();

                    JSONObject obj = new JSONObject(response);
                    if (obj.getBoolean("status")) {
                        pagedProducts = gson.fromJson(obj.getJSONObject("model").toString(), type);
                        res = new AsyncTaskResult<>(null, pagedProducts);
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
    protected void onPostExecute(AsyncTaskResult<CategoryListModel> listAsyncTaskResult) {
        super.onPostExecute(listAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(listAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<CategoryListModel> listener) {
        mResult = listener;
    }

    private String commaSeparatedSpecs() {
        String res = "";

        for (int i = 0; i < this.mFilterSpecs.size(); i++) {

            if (i == 0)
                res += this.mFilterSpecs.get(i).toString();
            else
                res += "," + this.mFilterSpecs.get(i).toString();

        }

        return res;
    }


}
