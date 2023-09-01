package com.growonline.gomobishop.asynctask;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.growonline.gomobishop.db.DbHelper;
import com.growonline.gomobishop.db.VendorContract;
import com.growonline.gomobishop.model.Vendor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class UpdateStoreToDbAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<String>> {

    private List<Vendor> mListVendor = new ArrayList<>();
    private Context mContext;
    private AsyncTaskResultListener<String> mResult;

    public UpdateStoreToDbAsyncTask(Context context, List<Vendor> vendors) {
        this.mContext = context;
        this.mListVendor = vendors;
    }


    @Override
    protected AsyncTaskResult<String> doInBackground(Void... vendors) {

        DbHelper mDbHelper = new DbHelper(this.mContext);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        int count = mListVendor.size();

        for (int i = 0; i < count; i++) {

            Vendor tempVendor = mListVendor.get(i);

            ContentValues values = new ContentValues();
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_TITLE, tempVendor.getName());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_THEME_COLOR, tempVendor.getThemeColor());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_HAVE_SHOP_THE_LOOK, tempVendor.getHaveShoptheLook().toString());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ABOUT_US, tempVendor.getAboutUs());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_APPOINTMENT, tempVendor.getAppointmentPictureUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_LOGO_IMG_URL, tempVendor.getLogoUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_COVER_IMG_URL, tempVendor.getCoverPictureURL());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_URL, tempVendor.getUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ABOUT_US_URL, tempVendor.getAboutUsPageURL());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FACEBOOK_URL, tempVendor.getFacebookPageURL());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_LINKED_IN_URL, tempVendor.getLinkedInPageURL());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_BLOG_URL, tempVendor.getBlogUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FAQ_URL, tempVendor.getFaqUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_TERM_AND_CONDITION_URL, tempVendor.getTermAndConditionsUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RETURN_AND_REFUND_URL, tempVendor.getReturnAndRefundUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_DELIVERY_POLICY_URL, tempVendor.getDeliveryPolicyUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_SHIPPING_INFORMATION_URL, tempVendor.getShippingInformationUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RETURN_AND_EXCHANGE_POLICY_URL, tempVendor.getReturnAndExchangePolicyUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_PAYMENT_SECURITY_URL, tempVendor.getPaymentSecurityUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_MP_LOGO_IMG_URL, tempVendor.getMarketPlaceLogoURL());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CONTACT_DESCRIPTION, tempVendor.getContactDescription());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_MODIFIED_ON, timeStamp);

            Gson gson = new Gson();
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_APP_SETTINGS, gson.toJson(tempVendor.getMobileAppSetting()));
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_SOCIAL_LINKS, gson.toJson(tempVendor.getSocialLinks()));
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FEATURE_CATEGORIES, gson.toJson(tempVendor.getFeaturedCategories()));
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CATEGORIES, gson.toJson(tempVendor.getCategories()));
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RATING, gson.toJson(tempVendor.getRating()));

            // Insert the new row, returning the primary key value of the new row
            //long newRowId = db.insert(VendorContract.VendorEntry.TABLE_NAME, null, values);
            db.update(VendorContract.VendorEntry.TABLE_NAME, values,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ID + " = ?",
                    new String[]{String.valueOf(tempVendor.getId())});
        }

        return new AsyncTaskResult<>(null, "");
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
