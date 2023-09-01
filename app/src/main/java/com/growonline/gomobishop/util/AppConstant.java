package com.growonline.gomobishop.util;


import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;

public class AppConstant {
    public static final String SIGNED_IN_FROM = "SIGNED_IN_FROM";
    public static final String FACEBOOK = "FACEBOOK";
    public static final String LOGIN_MANUAL = "LOGIN_MANUAL";
    public static final String CATALOG_JSON = "CATALOG_JSON";
    public static final String COLOR_LIST_KEY = "COLOR_LIST_KEY";
    public static final String RETURN_URI = "returnUri";

    public static final String G_PLUS = "G_PLUS";
    public static final String MANUAL_SIGN_UP = "MANUAL_SIGN_UP";
    public static final String WIDGET_URI = "gfh://widget?id=%s";

    //intent variable
    public static final String INTENT_PRODUCT_ID = "paramProductId";
    public static final String INTENT_CATEGORY_ID = "paramCategoryId";
    public static final String INTENT_VENDOR = "paramVendor";
    public static final String INTENT_CREATE_SHORTCUT_BOOLEAN = "paramCreateShortCut";
    public static final String INTENT_PRODUCT = "paramProduct";
    public static final String INTENT_URL = "paramUrl";
    public static final String INTENT_TITLE = "paramTitle";
    public static final String INTENT_SHOW_CONTACT = "paramShowContact";
    public static final String INTENT_SHOW_FOLLOW_LINK = "paramShowFollowLink";
    public static final String INTENT_ORDER_ID = "paramOrderID";
    public static final String INTENT_WISH_LIST = "paramWishlist";
    public static final String INTENT_NOTIFICATION = "paramNotification";
    public static final String INTENT_ADDRESS = "paramAddress";
    public static final String INTENT_COUNTRY = "paramCountry";
    public static final String INTENT_MAXIMIZE_ANIMATION = "paramMaximizeAnimation";

    public static final int RETURN_HOME = 0;
    public static final int RETURN_SHIPPING = 1;
    public static final int RETURN_SPLASH = 2;
    public static final int RETURN_MAIN_SCREEN = 3;
    public static final int RETURN_PRODUCT = 4;
    public static final int RETURN_MY_ACCOUNT = 5;
    public static final int RETURN_CHANGE_PASSWORD = 6;
    public static final int RETURN_ORDER_LIST = 7;
    public static final int RETURN_WISH_LIST = 8;
    public static final int RETURN_NOTIFICATION = 9;
    public static final int RETURN_ADDRESS = 10;
    public static final int RETURN_REVIEWS = 11;
    public static final int RETURN_CHAT = 12;

    public static final int HOME_TAB_TITLE = R.string.home_tab_title;
    public static String BASE_DOMAIN = GoMobileApp.getContext().getString(R.string.BASE_DOMAIN);
    public static final int CODE_STRIPE_PAYMENT = -1;

    //    public static final String BASE_DOMAIN = "http://13.126.233.4/";
    public static final String FONTS_DIR = "fonts/";
    public static final String BASE_URL = BASE_DOMAIN + "Mservices/";
    public static final String PRIVACY_POLICY_PAGE_URL = BASE_DOMAIN + "/t-popup/conditionsofuse";
    public static final String FAQS_URL = BASE_DOMAIN + "t-popup/FAQ";
    public static final String ROAD_MAP_URL = BASE_DOMAIN + "t-popup/roadmap";
    public static final String ABOUT_GOMOBISHOP_URL = BASE_DOMAIN + "t-popup/aboutusgomobishop";
    public static final String BUILDER_URL = "https://info.gomobishop.com/builder/indexNew.php";
    public static final String T_POP_UP_URL = BASE_DOMAIN + "t-popup/";
    public static final String LOYALTY_PROGRAM_PAGE_URL = T_POP_UP_URL + "/loyaltyMobile";
    public static final String GET_SHARE_PRODUCT = BASE_DOMAIN + "share?sid=";
    public static final String GET_SHARE_STORE = BASE_DOMAIN + "share?sid=";
    public static final String USER_EMAIL_PREFS_KEY = "USER_EMAIL_KEY";
    public static final String USER_PASSWORD_PREFS_KEY = "USER_PASSWORD_KEY";
    public static final String USER_NAME_PREFS_KEY = "USER_NAME_KEY";
    public static final String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
    public static final String COOKIE_HANDLER = "COOKIE_HANDLER";
    public static final int COLLECTION_TAB_TITLE = R.string.collection_tab_title;
    public static final int PRODUCTS_TAB_TITLE = R.string.product_tab_title;
    public static final int LATEST_TAB_TITLE = R.string.latest_tab_title;
    public static final int SALE_TAB_TITLE = R.string.sale_tab_title;
    public static final int ABOUT_TAB_TITLE = R.string.about_tab_title;
    public static final int MY_ACCOUNT_TITLE = R.string.my_account_title;
    public static final int FEATURED_VENDOR_TITLE = R.string.featured_vendor_title;
    public static final int ALL_VENDOR_TITLE = R.string.all_vendor_title;
    public static final int BRANDS_TITLE = R.string.brand_title;
    public static final int DESIGNER_TITLE = R.string.designer_title;
    public static final int SHE_EARNS_TITLE = R.string.she_earn_title;
    public static final int MY_STORE_TITLE = R.string.my_store_title;
    public static final int CATEGORY_TITLE = R.string.category_title;
    public static final int CHAT = -40;
    public static final int LANGUAGE = -41;
    //Navigation TITLE
    public static final int Create_SHORTCUT_TITLE = R.string.create_shortcut_title;
    public static final int SHARE_STORE_TITLE = R.string.share_store_title;
    public static final int ALL_PRODUCTS_TITLE = R.string.all_product_title;
    public static final int CATEGORIES_TITLE = R.string.categories_title;
    public static final int SHOP_THE_LOOK_TITLE = R.string.shop_the_look;
    public static final int LOYALTY_PROGRAM_TITLE = R.string.loyality_program;
    public static final int USER_INFO_TITLE = R.string.profile;
    public static final int MY_ORDER_TITLE = R.string.order_history;
    public static final int CHANGE_PASSWORD_TITLE = R.string.change_password;
    public static final int LOGIN_TITLE = R.string.login_title;
    public static final int LOGOUT_TITLE = R.string.logout_title;
    public static final int WISH_LIST_TITLE = R.string.wish_list_title;
    public static final int FAQ_TITLE = R.string.faq_title;
    public static final int FOLLOW_TITLE = R.string.follow_title;
    public static final int CONTACT_TITLE = R.string.contact_title;
    public static final int HOW_TO_USE_TITLE = R.string.how_to_use_title;
    public static final int ABOUT_NAVIGATION_TITLE = R.string.about_navigation_title;
    public static final int SUBSCRIBE_TITLE = R.string.subscribe_title;
    public static final int SETTINGS_TITLE = R.string.setting_title;
    public static final int EXIT_STORE_TITLE = R.string.exit_store_title;
    public static final int SALES_TITLE = R.string.sales_title;
    public static final int NEW_TITLE = R.string.new_title;
    public static final int CURRENCY_TITLE = R.string.currency_title;
    public static final int RATE_TITLE = R.string.rate_title;
    public static final int FEEDBACK_TITLE = R.string.feedback_title;
    public static final int CREATE_APP_TITLE = R.string.create_app_title;
    public static final int NOTIFICATION_TITLE = R.string.notification_title;
    public static final int ROAD_MAP_TITLE = R.string.road_map_title;
    public static final int ABOUT_GOMOBISHOP_TITLE = R.string.about_mobishop_tile;
    public static final int REVIEW_CENTER_TITLE = R.string.review_center_title;
    public static final int SHARE_APP_TITLE = R.string.share_app_title;
    public static final int MY_ADDRESS_TITLE = R.string.my_address_title;
    public static final int TERM_AND_CONDITION_TITLE = R.string.term_and_condition_title;
    public static final int RETURN_AND_REFUND_TITLE = R.string.return_and_refund_title;
    public static final int DELIVERY_POLICY_TITLE = R.string.delivery_policy_title;
    public static final int SHIPPING_INFORMATION_TITLE = R.string.shipping_information_title;
    public static final int RETURN_AND_EXCHANGE_POLICY_TITLE = R.string.return_and_exchange_title;
    public static final int APPOINTMENT_TITLE = R.string.appointment_title;
    public static final int CHAT_TITLE = R.string.chat_title;
    public static final int MORE_TITLE = R.string.more_title;
    public static final int LANGUAGE_TITLE = R.string.language_title;
    public static final int MY_ACCOUNT_NAVIGATION_TITLE = R.string.title_my_account;
    //AttributeControlType
    public static final int CHECKBOX = 3;
    public static final int TAB_PRODUCT = R.string.tab_product_review_title;
    public static final int TAB_VENDOR = R.string.tab_vendor_review_title;
    public static final int TAB_PENDING = R.string.tab_pending_review_title;
    private static final String CONTROLLER_CATALOG = BASE_URL + "catalog/";
    //controller catalog
    public static final String SEARCH_ALL_URL = CONTROLLER_CATALOG + "AdvanceSearch";
    public static final String QUICK_SEARCH = CONTROLLER_CATALOG + "searchTermAutoComplete";
    public static final String SEARCH_PRODUCT_URL = CONTROLLER_CATALOG + "SearchVendorProductsByVendorId";
    public static final String GET_VENDOR_URL = CONTROLLER_CATALOG + "GetvendorById";
    public static final String GET_SELECTED_VENDOR_BY_IDS_URL = CONTROLLER_CATALOG + "GetSelectedVendorByIds";
    public static final String GET_FEATURES_ITEM_URL = CONTROLLER_CATALOG + "GetFeaturedProductsByVendorId";
    public static final String GET_FEATURED_VENDORS = CONTROLLER_CATALOG + "GetFeaturedVendors";
    public static final String GET_ALL_VENDORS = CONTROLLER_CATALOG + "VendorAll";
    public static final String GET_ALL_PRODUCTS_URL = CONTROLLER_CATALOG + "GetProductsByVendorId";
    public static final String GET_SHOP_THE_LOOK_URL = CONTROLLER_CATALOG + "GetShopTheLookByVendorId";
    public static final String GET_ALL_NEW_PRODUCTS_URL = CONTROLLER_CATALOG + "GetNewProductsByVendorId";
    public static final String GET_ALL_CATEGORY_PRODUCTS_URL = CONTROLLER_CATALOG + "GetProductsByCategoryId";
    public static final String GET_ALL_MARKET_CATEGORY_URL = CONTROLLER_CATALOG + "MarketCategories";
    public static final String GET_CATEGORY_PRODUCTS = CONTROLLER_CATALOG + "GetProductByCategoryAndVendorId";
    public static final String GET_SALES_PRODUCTS = CONTROLLER_CATALOG + "GetSaleProductsByVendorId";
    public static final String GET_CATEGORY_LIST = CONTROLLER_CATALOG + "GetCategoryByCategoryId";

    public static final String SERVER_ERROR_MSG = "Server not found or Network is un-available";
    //CMS-Constants
    public static final Integer GROUPED_PRODUCT_TYPE = 10;

    public static final String APPOINTMENT_IMAGE_URL = "https://www.gomobishop.com/Content/Images/uploaded/Lagun/appointment.jpg";
    //Easy-Pay settings
    public static final String EasyPayRedirectUrl = BASE_DOMAIN + "PaymentEasyPay/RedirectHandlerMobile?orderId=";
    public static final String GET_OFFERS = BASE_DOMAIN + "content/offer.json";


    public static final int CODE_STORE_ACTIVITY = 1;
    public static final int CODE_DOWNLOAD_ACTIVITY = 2;
    public static final int CODE_SEARCH_ACTIVITY = 3;
    public static final int CODE_SEARCH_ACTIVITY_CATEGORY = 4;
    public static final int CODE_NOTIFICATION_CENTER = 5;
    public static final int CODE_ONE_PAGE_CHECKOUT = 6;
    public static final int CODE_REDIRECTION_CANCEL = 7;
    public static final int CODE_ADDRESS = 8;
    private static final String CONTROLLER_CUSTOMER = BASE_URL + "Customer/";
    public static final String KEY_PLAY_STORE_VENDOR_ID = "Play_Store_Vendor_Id";
    public static final String KEY_PLAY_STORE_PRODUCT_ID = "Play_Store_Product_Id";
    public static final String IS_FIRST_LAUNCH = "Is_First_Launch";
    public static final String KEY_TOKEN_ID = "Token_Id";
    //controller customer
    public static final String REVIEW_DATA_URL = CONTROLLER_CUSTOMER + "UserReviewsData";
    public static final String LOGIN_URL = CONTROLLER_CUSTOMER + "LoginUser";
    public static final String REGISTER_USER = CONTROLLER_CUSTOMER + "RegisterUser";
    public static final String LOGIN_AS_FACEBOOK = CONTROLLER_CUSTOMER + "LoginFacebookUser";
    public static final String RECOVER_PASSWORD = CONTROLLER_CUSTOMER + "RecoverPassword?email=";
    public static final String ADD_DEVICE = CONTROLLER_CUSTOMER + "AddDevice";

    public static final String ORDER_PHONE_NUMBER = "+922134380438";
    public static final int FEATURED_VENDOR = 1;
    public static final int ALL_VENDOR = 2;
    public static final int BRANDS = 3;
    public static final int DESIGNER = 4;
    public static final int SHE_EARNS = 5;
    public static final String GET_REWARD_POINTS = CONTROLLER_CUSTOMER + "GetRewardPoints";
    public static final String EDIT_INFO = CONTROLLER_CUSTOMER + "EditInfo";
    public static final String GET_INFO = CONTROLLER_CUSTOMER + "Info";
    public static final String Change_Password = CONTROLLER_CUSTOMER + "ChangePassword";
    public static final String GET_NOTIFICATION_URL = CONTROLLER_CUSTOMER + "Notifications";
    public static final String GET_ADDRESS_LIST = CONTROLLER_CUSTOMER + "Addresses";
    public static final String ADDRESS_DELETE = CONTROLLER_CUSTOMER + "DeleteAddress";
    private static final String CONTROLLER_COMMON = BASE_URL + "common/";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    public static final int SIMPLE_NOTIFICATION = 1;
    public static final int IMAGE_NOTIFICATION = 2;
    public static final int WEB_NOTIFICATION = 3;
    public static final int NEWS_NOTIFICATION = 4;

    public static final int CLOSE_APP = 1;
    public static final int OPEN_APP = 2;
    public static final int OPEN_ANY_URL = 3;
    public static final int OPEN_MARKET = 4;
    public static final int OPEN_VENDOR_APP = 5;
    public static final int OPEN_PRODUCT = 6;

    public static final int LIST_ANIMATION = 1;
    public static final int BOX_ANIMATION = 2;
    public static final int BANNER_ANIMATION = 3;
    //Navigation Constant
    public static final int Create_SHORTCUT = -1;
    public static final int SHARE_STORE = -2;
    public static final int ALL_PRODUCTS = -3;
    public static final int CATEGORIES = -4;
    public static final int SHOP_THE_LOOK = -5;
    public static final int LOYALTY_PROGRAM = -6;
    public static final int USER_INFO = -7;
    public static final int MY_ORDER = -8;
    public static final int CHANGE_PASSWORD = -9;
    public static final int LOGIN = -10;
    public static final int LOGOUT = -11;
    public static final int WISH_LIST = -12;
    public static final int FAQ = -13;
    public static final int FOLLOW = -14;
    public static final int CONTACT = -15;
    public static final int HOW_TO_USE = -16;
    public static final int ABOUT = -17;
    public static final int SUBSCRIBE = -18;
    public static final int SETTINGS = -19;
    public static final int EXIT_STORE = -20;
    public static final int SALES = -21;
    public static final int NEW = -22;
    public static final int HOME_TAB = -23;
    public static final int BLOG = -24;
    public static final int CURRENCY = -25;
    public static final int RATE = -26;
    public static final int FEEDBACK = -27;
    public static final int CREATE_APP = -28;
    public static final int NOTIFICATION = -29;
    public static final int ROAD_MAP = -30;
    public static final int REVIEW_CENTER = -31;
    public static final int SHARE_APP = -32;
    public static final int MY_ADDRESS = -33;
    public static final int TERM_AND_CONDITION = -34;
    public static final int RETURN_AND_REFUND = -35;
    public static final int DELIVERY_POLICY = -36;
    public static final int SHIPPING_INFORMATION = -37;
    public static final int RETURN_AND_EXCHANGE_POLICY = -38;
    public static final int APPOINTMENT = -39;
    //controller common
    public static final String SEND_ENDUIRY_URL = CONTROLLER_COMMON + "SendEnquiryToVendor";
    public static final String SEND_CONTACT_US = CONTROLLER_COMMON + "ContactUsSend";
    public static final String SET_CURRENCY = CONTROLLER_COMMON + "setcurrency";
    public static final String GET_CURRENCY = CONTROLLER_COMMON + "getcurrency";
    public static final String SEND_NOTIFY_WHEN_AVAILABLE = CONTROLLER_COMMON + "SubscribePopup";
    public static final String SEND_NOTIFY_WHEN_AVAILABLE_POST = CONTROLLER_COMMON + "SubscribePopupPOST";
    public static final String Get_AVAILABLE_SATES_AND_COUNTRY = CONTROLLER_COMMON + "GetAvailableCountriesAndStates";
    public static final String GET_BANNER = CONTROLLER_COMMON + "GetBanner?SearchBannerName=Mobishop";
    private static final String CONTROLLER_PRODUCT = BASE_URL + "Product/";
    //controller product
    public static final String ADD_PRODUCT_REVIEW = CONTROLLER_PRODUCT + "ProductReviewsAdd";
    public static final String GET_PRODUCT_WITH_RELATED_PRODUCTS = CONTROLLER_PRODUCT + "GetProductDetailsByProductId";
    public static final String GET_PRODUCT_RATING = CONTROLLER_PRODUCT + "ProductRating?productId=";
    private static final String CONTROLLER_SHOPPING = BASE_URL + "ShoppingCart/";
    //controller shopping
    public static final String ADD_ITEMS_TO_CART_FROM_WISHLIST = CONTROLLER_SHOPPING + "AddItemsToCartFromWishlist";
    public static final String GET_CART_DETAILS = CONTROLLER_SHOPPING + "ShopCart";
    public static final String ADD_TO_CART = CONTROLLER_SHOPPING + "AddProductToCart_Details";
    public static final String DELETE_FROM_CART = CONTROLLER_SHOPPING + "RemoveCartProduct";
    public static final String UPDATE_CART = CONTROLLER_SHOPPING + "UpdateCartProduct";
    public static final String CALCULATE_CART_TOTAL = CONTROLLER_SHOPPING + "OrderTotals";
    public static final String GET_WISHLIST_DETAILS = CONTROLLER_SHOPPING + "Wishlist";
    public static final String APPLY_GIFT_VOUCHER = CONTROLLER_SHOPPING + "ApplyDiscountAndGiftCardCoupon";
    public static final String REMOVE_GIFT_VOUCHER = CONTROLLER_SHOPPING + "RemoveDiscountAndGiftCardCoupon";
    public static final String DELETE_FROM_WISHLIST = CONTROLLER_SHOPPING + "RemoveWishlistItem";
    private static final String CONTROLLER_CHECKOUT = BASE_URL + "Checkout/";
    //controller checkout
    public static final String GET_SHIPPING_BILLING_ADDRESS_URL = CONTROLLER_CHECKOUT + "GetShippingAndBillingAddress";
    public static final String GET_CHECKOUT_DETAILS = CONTROLLER_CHECKOUT + "GetAllDetailsForCheckOut";
    public static final String CONFIRM_ORDER = CONTROLLER_CHECKOUT + "ConfirmOrder";
    public static final String SAVE_SHIPPING_LOCATION = CONTROLLER_CHECKOUT + "GfhAddAddress";
    public static final String SAVE_BILLING_LOCATION = CONTROLLER_CHECKOUT + "GfhAddBillingAddress";
    public static final String GET_SHIPPING_LOCATIONS = CONTROLLER_CHECKOUT + "ShippingAddress";
    public static final String CANCEL_REDIRECTION = CONTROLLER_CHECKOUT + "CancelRedirection";
    public static final String USE_REWARD_POINT = CONTROLLER_CHECKOUT + "UseRewardPoints";
    private static final String CONTROLLER_VENDOR = BASE_URL + "vendor/";
    //controller vendor
    public static final String ADD_VENDOR_REVIEW = CONTROLLER_VENDOR + "VendorReviewsAdd";
    public static final String SUBSCRIBE_URL = CONTROLLER_VENDOR + "SubscribeVendorNewsletter";
    public static final String GET_VENDOR_RATING = CONTROLLER_VENDOR + "VendorRating?vendorId=";
    private static final String CONTROLLER_ORDER = BASE_URL + "Order/";
    //controller order
    public static final String GET_ORDER_lIST = CONTROLLER_ORDER + "Orders";
    public static final String GET_ORDER_DETAILS = CONTROLLER_ORDER + "OrderDetails?orderid=";
    private static final String CONTROLLER_PRIVATE_MESSAGES = BASE_URL + "PrivateMessages/";
    //controller PrivateChat
    public static final String GET_PRIVATE_CHAT_INDEX = CONTROLLER_PRIVATE_MESSAGES + "Index";
    public static final String GET_CONVERSATIONS = CONTROLLER_PRIVATE_MESSAGES + "Conversations";
    public static final String GET_ALL_VENDOR_FOR_CHAT = CONTROLLER_PRIVATE_MESSAGES + "Vendors";
    public static final String DELETE_PRIVATE_MESSAGE = CONTROLLER_PRIVATE_MESSAGES + "DeleteMessage";
    public static final String VIEW_PRIVATE_MESSAGE = CONTROLLER_PRIVATE_MESSAGES + "ViewMessage";
    public static final int TEXT_INPUT = 4;
    public static final int TEXT_MULTI_LINE_INPUT = 10;
    public static final String SEND_PRIVATE_MESSAGE = CONTROLLER_PRIVATE_MESSAGES + "SendPM";
    public static final String READ_ALL_PRIVATE_MESSAGE = CONTROLLER_PRIVATE_MESSAGES + "ReadAllMessageFromCustomerId";

    public static final int TYPE_PRODUCT = 1;
    public static final int TYPE_VENDOR = 2;
    public static final int TYPE_PENDING = 3;
    public static final int TYPE_CATEGORY = 4;
    public static final int VENDOR_REVIEW = 4;
    public static final int PRODUCT_REVIEW = 5;
    public static final String SHARED_ELEMENT_IMAGE_KEY = "SHARED_ELEMENT_IMAGE_KEY";
    public static final String IMAGE_FILE_KEY = "IMAGE_FILE_KEY";
    public static final String PENDING_REVIEW_INTENT = "pendingReview";
    public static final int CODE_PENDING_REVIEW_ACTIVITY = 1;
    //for sadaf amir
    public static final String LAGAN_URL = BASE_DOMAIN + "t-popup/lagun/true";
    public static final String BESPOKE_TAB_TITLE = "Bespoke";
    public static final String LAGAN_TAB_TITLE = "Lagan";
    public static final String ABOUT_SADAF_URL = BASE_DOMAIN + "t-popup/sadafamiraboutus/true";
    public static final String ABOUT_MOAZZAM_URL = BASE_DOMAIN + "t-popup/thedesignwebmoazzam/true";


    public static final String SHOP_THE_LOOK_IMAGE_URL = BASE_DOMAIN + "Content/Images/uploaded/moazzam/app/image_01.jpg";
    public static final String FIRST_BANNER_IMAGE_URL = BASE_DOMAIN + "Content/Images/uploaded/moazzam/app/image_01.jpg";
    public static final String SECOND_BANNER_IMAGE_URL = BASE_DOMAIN + "Content/Images/uploaded/moazzam/app/image_02.jpg";
    public static final String THIRD_BANNER_IMAGE_URL = BASE_DOMAIN + "Content/Images/uploaded/moazzam/app/image_03.jpg";
    public static final String FOURTH_BANNER_IMAGE_URL = BASE_DOMAIN + "Content/Images/uploaded/moazzam/app/image_04.jpg";
    public static final String VIDEO_URL1 = BASE_DOMAIN + "Themes/moazzamKhanCo/Content/video/MKBKK2NDCUT13.mp4";


    public static boolean isLogEnabled = true;
    public static final String GET_PAYMENT_INFO_STRIPE = BASE_DOMAIN + "Plugins/Stripe/PaymentInfo?mobile=true";


    public enum ProductListFragmentMode {ALL_PRODUCT, SINGLE_CATEGORY, SALE, WHATS_NEW, SHOP_THE_LOOK, MARKET_PRODUCT, SEARCH_PRODUCT}

    public enum ProductSortingEnum {
        Position(0), NameAsc(5), NameDesc(6), PriceAsc(10), PriceDesc(11), CreatedOn(15);
        private final int value;

        ProductSortingEnum(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    public enum MainTabs {HOME, CATEGORIES, PRODUCTS, LATEST, SALE, ABOUT}

    public enum AddressType {SHIPPING, BILLING}

}