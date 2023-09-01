package com.growonline.gomobishop.asynctask;

import android.os.AsyncTask;

import com.growonline.gomobishop.network.MultiPartSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by asifrizvi on 6/4/2018.
 */

public class ContactPretapakAsyncTask extends AsyncTask<Void,Void,AsyncTaskResult<Boolean>> {

    private AsyncTaskResultListener<Boolean> mResult;
    private String mUserEmail, mPhoneNumber ,mName;
    private String mUserEnquiry;

    public ContactPretapakAsyncTask(String name,String email,String enquiry,String phoneNumber) {
        mName=name;
        mUserEmail = email;
        mUserEnquiry = enquiry;
        mPhoneNumber=phoneNumber;
    }

    @Override
    protected AsyncTaskResult<Boolean> doInBackground(Void... voids) {


        AsyncTaskResult<Boolean> res;

        String response;
        HashMap<String, String> jsonObject = new HashMap<>();
        try {
            jsonObject.put("Name", mName);
            jsonObject.put("Email", mUserEmail);
            jsonObject.put("Enquiry", mUserEnquiry);
            jsonObject.put("Subject","Feedback from "+mName+" Contact number :"+mPhoneNumber);

            response = new MultiPartSender().sendPostStringUsingMultiPart(AppConstant.SEND_CONTACT_US, jsonObject, false);

            JSONObject jsonResponse = new JSONObject(response);
            if(jsonResponse.getBoolean("status")){
                res = new AsyncTaskResult<>(null, true);
            } else {
                res = new AsyncTaskResult<>(new Exception(jsonResponse.getString("message")), false);
            }

        } catch (Exception ex){
            res = new AsyncTaskResult<>(ex, false);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Boolean> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if(!this.isCancelled() && mResult != null){
            mResult.response(booleanAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<Boolean> listener) {
        mResult = listener;
    }


}
