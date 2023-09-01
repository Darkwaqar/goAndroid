package com.growonline.gomobishop.db;

import android.provider.BaseColumns;

public final class VendorContract {
    private VendorContract() {
    }

    public static class VendorEntry implements BaseColumns {
        public static final String TABLE_NAME = "vendor";
        public static final String COLUMN_NAME_VENDOR_ID = "vendor_id";
        public static final String COLUMN_NAME_VENDOR_TITLE = "title";
        public static final String COLUMN_NAME_VENDOR_THEME_COLOR = "theme_color";
        public static final String COLUMN_NAME_VENDOR_HAVE_SHOP_THE_LOOK = "shop_the_look";
        public static final String COLUMN_NAME_VENDOR_ABOUT_US = "about_us";
        public static final String COLUMN_NAME_VENDOR_APPOINTMENT = "appointment";
        public static final String COLUMN_NAME_VENDOR_URL = "url";
        public static final String COLUMN_NAME_VENDOR_ABOUT_US_URL = "aboutUsPage";
        public static final String COLUMN_NAME_VENDOR_LOGO_IMG_URL = "logo_url";
        public static final String COLUMN_NAME_VENDOR_COVER_IMG_URL = "cover_url";
        public static final String COLUMN_NAME_VENDOR_FACEBOOK_URL = "facebook_url";
        public static final String COLUMN_NAME_VENDOR_LINKED_IN_URL = "linkedin_url";
        public static final String COLUMN_NAME_VENDOR_BLOG_URL = "blog_url";
        public static final String COLUMN_NAME_VENDOR_FAQ_URL = "faq_url";
        public static final String COLUMN_NAME_VENDOR_TERM_AND_CONDITION_URL = "term_and_condition_url";
        public static final String COLUMN_NAME_VENDOR_RETURN_AND_REFUND_URL = "return_and_refund_url";
        public static final String COLUMN_NAME_VENDOR_DELIVERY_POLICY_URL = "delivery_policy_url";
        public static final String COLUMN_NAME_VENDOR_SHIPPING_INFORMATION_URL = "shipping_policy_url";
        public static final String COLUMN_NAME_VENDOR_RETURN_AND_EXCHANGE_POLICY_URL = "return_exchange_policy_url";
        public static final String COLUMN_NAME_VENDOR_PAYMENT_SECURITY_URL = "payment_security_url";
        public static final String COLUMN_NAME_VENDOR_MP_LOGO_IMG_URL = "mp_logo_url";
        public static final String COLUMN_NAME_VENDOR_CONTACT_DESCRIPTION = "contact_desc";
        public static final String COLUMN_NAME_VENDOR_BANNERS_IMAGES_URL = "banner_images_url";
        public static final String COLUMN_NAME_VENDOR_NOTES = "notes";
        public static final String COLUMN_NAME_VENDOR_CREATED_ON = "created_on";
        public static final String COLUMN_NAME_VENDOR_MODIFIED_ON = "modified_on";
        public static final String COLUMN_NAME_VENDOR_APP_SETTINGS = "app_settings";
        public static final String COLUMN_NAME_VENDOR_SOCIAL_LINKS = "social";
        public static final String COLUMN_NAME_VENDOR_FEATURE_CATEGORIES = "featured_categories";
        public static final String COLUMN_NAME_VENDOR_CATEGORIES = "categories";
        public static final String COLUMN_NAME_VENDOR_RATING = "rating";
    }

}