package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.model.PagedProducts;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetProductsAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<PagedProducts>> {

    private AsyncTaskResultListener<PagedProducts> mResult;
    private int mVendorId, mCategoryId, mPageNumber = 1, mPageSize = 6;
    private List<Integer> mFilterSpecs = new ArrayList<>();
    private AppConstant.ProductListFragmentMode mMode;
    private String mSearchTerm;
    private int mOrderBy;

    public GetProductsAsyncTask(int vendorId, int categoryId, int pageNumber, int pageSize, int OrderBy,
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

    public GetProductsAsyncTask(int vendorId, int pageNumber, int pageSize, int OrderBy, List<Integer> filterSpecs,
                                AppConstant.ProductListFragmentMode mode) {
        this.mVendorId = vendorId;
        this.mPageNumber = pageNumber;
        this.mPageSize = pageSize;
        this.mOrderBy = OrderBy;
        this.mMode = mode;

        if (filterSpecs != null)
            this.mFilterSpecs = filterSpecs;
    }

    public GetProductsAsyncTask(String mSearchTerm, Integer vendorId, int pageNumber, int pageSize, int OrderBy, ArrayList<Integer> filterSpecs, AppConstant.ProductListFragmentMode mode) {
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
    protected AsyncTaskResult<PagedProducts> doInBackground(Void... params) {

        AsyncTaskResult<PagedProducts> res;

        if (!this.isCancelled()) {
            try {

                PagedProducts pagedProducts;
                String uri = "";

                switch (mMode) {
                    case SINGLE_CATEGORY:
                        uri = AppConstant.GET_CATEGORY_PRODUCTS +
                                "?vendorid=" + this.mVendorId +
                                "&categoryId=" + this.mCategoryId +
                                "&pageNumber=" + this.mPageNumber +
                                "&pageSize=" + this.mPageSize +
                                "&OrderBy=" + this.mOrderBy +
                                "&specs=" + commaSeparatedSpecs();
                        break;
                    case ALL_PRODUCT:
                        uri = AppConstant.GET_ALL_PRODUCTS_URL +
                                "?vendorid=" + this.mVendorId +
                                "&pageNumber=" + this.mPageNumber +
                                "&pageSize=" + this.mPageSize +
                                "&OrderBy=" + this.mOrderBy +
                                "&specs=" + commaSeparatedSpecs();
                        break;
                    case SHOP_THE_LOOK:
                        uri = AppConstant.GET_SHOP_THE_LOOK_URL +
                                "?vendorid=" + this.mVendorId +
                                "&pageNumber=" + this.mPageNumber +
                                "&pageSize=" + this.mPageSize +
                                "&OrderBy=" + this.mOrderBy +
                                "&specs=" + commaSeparatedSpecs();
                        break;
                    case SALE:
                        //dateUtc param can be passed to get sale according to custom time
                        uri = AppConstant.GET_SALES_PRODUCTS +
                                "?vendorid=" + this.mVendorId +
                                "&pageNumber=" + this.mPageNumber +
                                "&pageSize=" + this.mPageSize +
                                "&OrderBy=" + this.mOrderBy +
                                "&specs=" + commaSeparatedSpecs();
                        break;
                    case WHATS_NEW:
                        uri = AppConstant.GET_ALL_NEW_PRODUCTS_URL +
                                "?vendorid=" + this.mVendorId +
                                "&pageNumber=" + this.mPageNumber +
                                "&pageSize=" + this.mPageSize +
                                "&OrderBy=" + this.mOrderBy +
                                "&specs=" + commaSeparatedSpecs();
                        break;
                    case MARKET_PRODUCT:
                        uri = AppConstant.GET_ALL_CATEGORY_PRODUCTS_URL +
                                "?categoryId=" + this.mVendorId +
                                "&pageNumber=" + this.mPageNumber +
                                "&pageSize=" + this.mPageSize +
                                "&specs=" + commaSeparatedSpecs();
                        break;
                    case SEARCH_PRODUCT:
                        uri = AppConstant.SEARCH_PRODUCT_URL +
                                "?vendorid=" + this.mVendorId +
                                "&pageNumber=" + this.mPageNumber +
                                "&pageSize=" + this.mPageSize +
                                "&specs=" + commaSeparatedSpecs() +
                                "&OrderBy=" + this.mOrderBy +
                                "&searchTerm=" + this.mSearchTerm;
                        break;
                    default:
                        break;
                }

                String response = new GetPostSender().sendGet(uri, false);

                if (response != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<PagedProducts>() {
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
    protected void onPostExecute(AsyncTaskResult<PagedProducts> listAsyncTaskResult) {
        super.onPostExecute(listAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(listAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<PagedProducts> listener) {
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
