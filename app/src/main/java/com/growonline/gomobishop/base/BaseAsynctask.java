package com.growonline.gomobishop.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.growonline.gomobishop.R;


public abstract class BaseAsynctask extends AsyncTask<String, Void, String> {
    protected Activity mActivity;
    private ProgressDialog mDialog;
    private boolean mIsProgressEnabled;

    protected abstract void onComplete(String output);

    public BaseAsynctask(Activity activity, boolean isProgressEnabled) {
        this.mActivity = activity;
        this.mIsProgressEnabled = isProgressEnabled;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mIsProgressEnabled)
            mDialog = ProgressDialog.show(mActivity, "", mActivity.getString(R.string.progress_msg));
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            if (mIsProgressEnabled && mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        onComplete(result);
    }

    public String onResponseReceived(String response) {
        if (response == null)
            return mActivity.getString(R.string.no_response);

        if (response.equalsIgnoreCase("false"))
            return mActivity.getString(R.string.time_out);


        return "";
    }
}