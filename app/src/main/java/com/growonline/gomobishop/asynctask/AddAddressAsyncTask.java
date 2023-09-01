package com.growonline.gomobishop.asynctask;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.growonline.gomobishop.model.Address;
import com.growonline.gomobishop.network.GetPostSender;
import com.growonline.gomobishop.util.AppConstant;

import org.json.JSONObject;

public class AddAddressAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<Boolean>> {

    private AsyncTaskResultListener<Boolean> mResult;
    private Address mAddress;
    private AppConstant.AddressType mAddressType;

    public AddAddressAsyncTask(Address address, AppConstant.AddressType addressType) {
        this.mAddress = address;
        this.mAddressType = addressType;
    }

    @Override
    protected AsyncTaskResult<Boolean> doInBackground(Void... params) {
        AsyncTaskResult<Boolean> res;

        try {

            if (!this.isCancelled()) {

                JSONObject jsonAddressObject = new JSONObject();

                if (mAddress.getId() != null && mAddress.getId() > 0)
                    jsonAddressObject.put("Id", mAddress.getId());

                jsonAddressObject.put("FirstName", mAddress.getFirstName());
                jsonAddressObject.put("LastName", mAddress.getLastName());
                jsonAddressObject.put("Email", mAddress.getEmail());
                jsonAddressObject.put("CountryId", mAddress.getCountryId());
                jsonAddressObject.put("stateProvinceId", mAddress.getStateProvinceId());
                jsonAddressObject.put("City", mAddress.getCity());
                jsonAddressObject.put("Address1", mAddress.getAddress1());
                jsonAddressObject.put("ZipPostalCode", mAddress.getZipPostalCode());
                jsonAddressObject.put("PhoneNumber", mAddress.getPhoneNumber());

                String response;

                if (mAddressType == AppConstant.AddressType.SHIPPING) {
                    response = new GetPostSender().sendPostJSON(AppConstant.SAVE_SHIPPING_LOCATION, jsonAddressObject.toString(), false);
                } else {
                    response = new GetPostSender().sendPostJSON(AppConstant.SAVE_BILLING_LOCATION, jsonAddressObject.toString(), false);
                }

                if (response != null) {

                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getBoolean("status")) {
                        res = new AsyncTaskResult<>(null, true);
                    } else {
                        res = new AsyncTaskResult<>(new Exception(jsonResponse.getString("message")), false);
                    }
                } else {
                    res = new AsyncTaskResult<>(new NetworkErrorException(AppConstant.SERVER_ERROR_MSG), null);
                }

            } else {
                res = new AsyncTaskResult<>(new Exception("Cancelled"), null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncTaskResult<>(e, false);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Boolean> booleanAsyncTaskResult) {
        super.onPostExecute(booleanAsyncTaskResult);

        if (!this.isCancelled() && mResult != null) {
            mResult.response(booleanAsyncTaskResult);
        }
    }

    public void addOnResultListener(AsyncTaskResultListener<Boolean> listener) {
        this.mResult = listener;
    }
}
