package com.growonline.gomobishop.asynctask;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.db.DbHelper;
import com.growonline.gomobishop.db.VendorContract;
import com.growonline.gomobishop.model.Banner;
import com.growonline.gomobishop.model.Category;
import com.growonline.gomobishop.model.MobileAppSetting;
import com.growonline.gomobishop.model.Rating;
import com.growonline.gomobishop.model.SocialLinks;
import com.growonline.gomobishop.model.Vendor;

import java.util.List;


public class FetchVendorFromDbAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<Vendor>> {

    private AsyncTaskResultListener<Vendor> mAsyncTaskResultListener;
    private Context mContext;
    private int mVendorId = 0;

    public FetchVendorFromDbAsyncTask(Context context, int vendorId) {
        this.mContext = context;
        this.mVendorId = vendorId;
    }

    @Override
    protected AsyncTaskResult<Vendor> doInBackground(Void... params) {

        AsyncTaskResult<Vendor> res;

        try {

            String[] columns = {
                    VendorContract.VendorEntry._ID,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ID,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_TITLE,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_THEME_COLOR,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_HAVE_SHOP_THE_LOOK,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ABOUT_US,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_APPOINTMENT,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ABOUT_US_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CONTACT_DESCRIPTION,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_LOGO_IMG_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_COVER_IMG_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FACEBOOK_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_LINKED_IN_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_BLOG_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FAQ_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_TERM_AND_CONDITION_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RETURN_AND_REFUND_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_DELIVERY_POLICY_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_SHIPPING_INFORMATION_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RETURN_AND_EXCHANGE_POLICY_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_PAYMENT_SECURITY_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_MP_LOGO_IMG_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_BANNERS_IMAGES_URL,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_NOTES,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_APP_SETTINGS,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_SOCIAL_LINKS,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FEATURE_CATEGORIES,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CATEGORIES,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RATING
            };

            String sortOrder = "datetime(" + VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CREATED_ON + ") desc";

            DbHelper mDbHelper = new DbHelper(this.mContext);
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            Cursor cursor = db.query(
                    VendorContract.VendorEntry.TABLE_NAME,
                    columns,
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ID + "=?",
                    new String[]{String.valueOf(this.mVendorId)},
                    null,
                    null,
                    sortOrder
            );

            Vendor vendor = null;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    vendor = new Vendor();

                    vendor.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ID))));
                    vendor.setName(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_TITLE)));
                    vendor.setThemeColor(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_THEME_COLOR)));
                    vendor.setHaveShoptheLook(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_HAVE_SHOP_THE_LOOK)).equals("true"));
                    vendor.setAboutUs(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ABOUT_US)));
                    vendor.setAppointmentPictureUrl(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_APPOINTMENT)));
                    vendor.setLogoUrl(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_LOGO_IMG_URL)));
                    vendor.setAboutUsPageURL(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ABOUT_US_URL)));
                    vendor.setContactDescription(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CONTACT_DESCRIPTION)));
                    vendor.setCoverPictureURL(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_COVER_IMG_URL)));
                    vendor.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_LOGO_IMG_URL)));
                    vendor.setFacebookPageURL(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FACEBOOK_URL)));
                    vendor.setLinkedInPageURL(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_LINKED_IN_URL)));
                    vendor.setBlogUrl(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_BLOG_URL)));
                    vendor.setFaqUrl(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FAQ_URL)));
                    vendor.setTermAndConditionsUrl(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_TERM_AND_CONDITION_URL)));
                    vendor.setReturnAndRefundUrl(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RETURN_AND_REFUND_URL)));
                    vendor.setDeliveryPolicyUrl(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_DELIVERY_POLICY_URL)));
                    vendor.setShippingInformationUrl(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_SHIPPING_INFORMATION_URL)));
                    vendor.setReturnAndExchangePolicyUrl(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RETURN_AND_EXCHANGE_POLICY_URL)));
                    vendor.setPaymentSecurityUrl(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_PAYMENT_SECURITY_URL)));
                    vendor.setMarketPlaceLogoURL(cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_MP_LOGO_IMG_URL)));

                    String vendorBannersJson = cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_BANNERS_IMAGES_URL));
                    String vendorNotesJson = cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_NOTES));
                    String appSettingsJson = cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_APP_SETTINGS));
                    String socialLinks = cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_SOCIAL_LINKS));
                    String featuredCategories = cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FEATURE_CATEGORIES));
                    String categories = cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CATEGORIES));
                    String appRatingsJson = cursor.getString(cursor.getColumnIndexOrThrow(VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RATING));

                    Gson gson = new Gson();
                    vendor.setMobileAppSetting((MobileAppSetting) gson.fromJson(appSettingsJson, new TypeToken<MobileAppSetting>() {
                    }.getType()));
                    vendor.setSocialLinks((SocialLinks) gson.fromJson(socialLinks, new TypeToken<SocialLinks>() {
                    }.getType()));
                    vendor.setRating((Rating) gson.fromJson(appRatingsJson, new TypeToken<Rating>() {
                    }.getType()));
                    vendor.setBanners((List<Banner>) gson.fromJson(vendorBannersJson, new TypeToken<List<Banner>>() {
                    }.getType()));
                    vendor.setFeaturedCategories((List<Category>) gson.fromJson(featuredCategories, new TypeToken<List<Category>>() {
                    }.getType()));
                    vendor.setCategories((List<Category>) gson.fromJson(categories, new TypeToken<List<Category>>() {
                    }.getType()));
                    vendor.setVendorNotes((List<String>) gson.fromJson(vendorNotesJson, new TypeToken<List<String>>() {
                    }.getType()));
                }

                if (!cursor.isClosed())
                    cursor.close();
            }

            res = new AsyncTaskResult<>(null, vendor);

        } catch (Exception ex) {
            res = new AsyncTaskResult<>(ex, null);
        }

        return res;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Vendor> result) {
        super.onPostExecute(result);

        if (!this.isCancelled() && mAsyncTaskResultListener != null) {
            mAsyncTaskResultListener.response(result);
        }
    }

    public void addOnVendorLoadedListener(AsyncTaskResultListener<Vendor> listener) {
        mAsyncTaskResultListener = listener;
    }


}
