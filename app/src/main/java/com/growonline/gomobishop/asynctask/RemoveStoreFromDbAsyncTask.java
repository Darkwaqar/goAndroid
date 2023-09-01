package com.growonline.gomobishop.asynctask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.growonline.gomobishop.db.DbHelper;
import com.growonline.gomobishop.db.VendorContract;

public class RemoveStoreFromDbAsyncTask extends AsyncTask<Integer, Void, AsyncTaskResult<String>> {

    private Context mContext;
    private AsyncTaskResultListener<String> mResult;

    public RemoveStoreFromDbAsyncTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected AsyncTaskResult<String> doInBackground(Integer... integers) {
        AsyncTaskResult<String> res = null;

        DbHelper mDbHelper = new DbHelper(this.mContext);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        for (Integer integer : integers) {

            String whereClause = VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ID + " = " + integer;
            db.delete(VendorContract.VendorEntry.TABLE_NAME, whereClause, null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<String> result) {
        super.onPostExecute(result);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(result);
        }
    }

    public void addOnResultsListener(AsyncTaskResultListener<String> listener) {
        mResult = listener;
    }


}
