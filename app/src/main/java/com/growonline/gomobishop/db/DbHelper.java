package com.growonline.gomobishop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "gomobiApp.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    //store
    private static final String SQL_CREATE_STORE =
            "CREATE TABLE " + VendorContract.VendorEntry.TABLE_NAME + " (" +
                    VendorContract.VendorEntry._ID + " INTEGER PRIMARY KEY," +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ID + INTEGER_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_TITLE + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_THEME_COLOR + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_HAVE_SHOP_THE_LOOK + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ABOUT_US + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_APPOINTMENT + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_ABOUT_US_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_COVER_IMG_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_LOGO_IMG_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CONTACT_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FACEBOOK_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_LINKED_IN_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_BLOG_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FAQ_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_TERM_AND_CONDITION_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RETURN_AND_REFUND_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_DELIVERY_POLICY_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_SHIPPING_INFORMATION_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RETURN_AND_EXCHANGE_POLICY_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_PAYMENT_SECURITY_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_MP_LOGO_IMG_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_BANNERS_IMAGES_URL + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_NOTES + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CREATED_ON + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_MODIFIED_ON + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_APP_SETTINGS + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_SOCIAL_LINKS + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_FEATURE_CATEGORIES + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_CATEGORIES + TEXT_TYPE + COMMA_SEP +
                    VendorContract.VendorEntry.COLUMN_NAME_VENDOR_RATING + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_STORE =
            "DROP TABLE IF EXISTS " + VendorContract.VendorEntry.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_STORE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
