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
import java.util.Calendar;

public class AddStoreToDbAsyncTask extends AsyncTask<Vendor, Void, AsyncTaskResult<String>> {

    private Context mContext;
    private AsyncTaskResultListener<String> mResult;

    public AddStoreToDbAsyncTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected AsyncTaskResult<String> doInBackground(Vendor... vendors) {

        DbHelper mDbHelper = new DbHelper(this.mContext);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        for (Vendor vendor : vendors) {
            Gson gson = new Gson();

            ContentValues values = new ContentValues();
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ID, vendor.getId());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_TITLE, vendor.getName());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_THEME_COLOR, vendor.getThemeColor());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_HAVE_SHOP_THE_LOOK, vendor.getHaveShoptheLook().toString());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ABOUT_US, vendor.getAboutUs());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_APPOINTMENT, vendor.getAppointmentPictureUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_LOGO_IMG_URL, vendor.getLogoUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_COVER_IMG_URL, vendor.getCoverPictureURL());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_URL, vendor.getUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ABOUT_US_URL, vendor.getAboutUsPageURL());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FACEBOOK_URL, vendor.getFacebookPageURL());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_BLOG_URL, vendor.getBlogUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FAQ_URL, vendor.getFaqUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_TERM_AND_CONDITION_URL, vendor.getTermAndConditionsUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RETURN_AND_REFUND_URL, vendor.getReturnAndRefundUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_DELIVERY_POLICY_URL, vendor.getDeliveryPolicyUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_SHIPPING_INFORMATION_URL, vendor.getShippingInformationUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RETURN_AND_EXCHANGE_POLICY_URL, vendor.getReturnAndExchangePolicyUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_PAYMENT_SECURITY_URL, vendor.getPaymentSecurityUrl());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_LINKED_IN_URL, vendor.getLinkedInPageURL());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_MP_LOGO_IMG_URL, vendor.getMarketPlaceLogoURL());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_BANNERS_IMAGES_URL, gson.toJson(vendor.getBanners()));
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_NOTES, gson.toJson(vendor.getVendorNotes()));
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CONTACT_DESCRIPTION, vendor.getContactDescription());
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_APP_SETTINGS, gson.toJson(vendor.getMobileAppSetting()));
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_SOCIAL_LINKS, gson.toJson(vendor.getSocialLinks()));
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FEATURE_CATEGORIES, gson.toJson(vendor.getFeaturedCategories()));
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CATEGORIES, gson.toJson(vendor.getCategories()));
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RATING, gson.toJson(vendor.getRating()));

            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CREATED_ON, timeStamp);
            values.put(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_MODIFIED_ON, timeStamp);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(VendorContract.VendorEntry.TABLE_NAME, null, values);

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
