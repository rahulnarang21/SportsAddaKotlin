package goforfit.com.sportsaddakotlin.helper

object AppConfig{
    //---- URLS------
    private const val WEBSITE_BASE_URL = "https://sportsadda.in/"
    const val APP_BASE_URL = "https://api.sportsadda.in/"
    //public static final String APP_BASE_URL1 = "http://sagedesigns.in/SportsAddaNew/";

    // Some common strings
    //var LIST_TYPE = 0 // 0 - categories list, 1 - packages list




    // side nav strings
    const val DOCUMENTS_URL = WEBSITE_BASE_URL + "documents"
    const val SHIPPING_POLICY_URL = WEBSITE_BASE_URL + "shipping-policy"
    const val TERMS_URL = WEBSITE_BASE_URL + "terms-and-conditions"


    //public static final String APP_BASE_URL1 = "http://sagedesigns.in/SportsAddaNew/";
    // login/signup url
    const val LOGIN_USER_METHOD = "get/login"
    const val SEND_VERIFICATION_CODE_METHOD = APP_BASE_URL + "forgot/password"
    const val CREATE_NEW_PASSWORD_METHOD = APP_BASE_URL + "change/password"
    const val SIGN_UP_METHOD = APP_BASE_URL + "add/user/details"
    const val CHECK_SOCIAL_USER_METHOD = APP_BASE_URL + "get/social/login"
    const val SOCIAL_LOGIN_METHOD = APP_BASE_URL + "add/social/user"

    // MAIN PAGE URL
    const val GET_HOME_PAGE_ITEMS = "get/homePage/items"

    // BANNER IMAGES URL
    const val GET_BANNER_IMAGES_URL = APP_BASE_URL + "get/slide/image"

    // CITY URL
    const val GET_CITIES_METHOD = "get/city"

    // category_url
    const val GET_CATEGORIES_URL = APP_BASE_URL + "get/city/categories"
    const val GET_PACKAGES_METHOD = APP_BASE_URL + "get/package"

    //products url
    const val GET_PRODUCTS_URL = "get/categories/product"
    const val GET_PRODUCT_DETAILS_URL = "get/product/details"

    // Address url
    const val GET_ADDRESS_METHOD = APP_BASE_URL + "get/user/address"
    const val DELETE_ADDRESS_METHOD = APP_BASE_URL + "delete/user/address"
    const val ADD_ADDRESS_METHOD = APP_BASE_URL + "add/user/address"
    const val UPDATE_ADDRESS_METHOD = APP_BASE_URL + "update/user/address"

    // payment urls
    const val APPLY_PROMOCODE_URL = APP_BASE_URL + "get/coupon/details"
    const val GET_PROMOCODES_URL = APP_BASE_URL + "get/couponList"
    const val GET_ORDER_ID_METHOD = APP_BASE_URL + "get/order_id"

    // ORDER URL
    const val GET_ORDERS_URL = APP_BASE_URL + "get/orderList"
    const val CANCEL_ORDER_URL = APP_BASE_URL + "cancel/order"
    const val PLACE_ORDER_URL = APP_BASE_URL + "order/place"

    // PAYMENT GATEWAY URLS
    const val GENERATE_HASH_URL = APP_BASE_URL + "payu.php"
    const val SUCCESS_URL = WEBSITE_BASE_URL + "success"
    const val FAILURE_URL = WEBSITE_BASE_URL + "failure"


    // WEB PAGE URL
    const val WEB_PAGE_URL = APP_BASE_URL + "get/page/details"


    // COMMON STRINGS
    const val TAG = "check"
    const val RESPONSE = "data"
    const val STATUS_CODE = "status_code"
    const val RESPONSE_MSG = "response_msg"
    const val MESSAGE = "message"
    private const val USER_PREFS = "user_prefs"
    const val INTENT_TEXT_EXTRA = "intent_text_extra"
    const val PACKAGE_NAME = "in.sportsadda.sportsadda"
    const val SPORTSADDA_CONTACT_NO = "8470088822"
    const val SPORTSADDA_EMAIL_ID = "info@sportsadda.in"
    const val API_TIME_OUT = 0
    const val INDIAN_TIME_ZONE = "GMT+5:30"
    const val SUCCESS_CODE = 201

    //Intent Request codes
    const val INTENT_REQUEST_CODE = 1

    // LOGIN STRINGS
    const val FACEBOOK_ID = "fb_id"
    const val GMAIL_ID = "google_id"
    const val SOCIAL_TYPE = "social_type" // 1- facebook, 2 -  gmail


    // USER STRINGS
    //    public static final String USER_ID = "user_id1";
    //    public static final String USER_NAME = "name1";
    //    public static final String USER_CONTACT = "contact_no1";
    //    public static final String USER_EMAIL = "email1";
    const val USER_ID = "user_id"
    const val USER_NAME = "name"
    const val USER_CONTACT = "contact_no"
    const val USER_EMAIL = "email"
    const val USER_PASSWORD = "password"
    const val USER_ADDRESS = "Address"
    const val USER_CITY = "City"
    const val USER_STATE = "State"
    const val USER_COUNTRY = "Country"
    const val USER_ZIP_CODE = "ZipCode"
    const val LANDMARK = "Landmark"
    const val VERIFICATION_CODE = "OTP"
    const val IS_USER_LOGGED_IN = "is_user_logged_in"


    // HOME PAGE STRINGS
    const val BANNERS = "image-slide"
    const val CATEGORIES = "categories"
    const val PACKAGES = "packages"

    // ADDRESS STRINGS
    const val ADDRESS_USER_ID = "User_ID"
    const val ADDRESS_ID = "user_address_id"
    const val ADDRESS = "address"
    const val ADDRESS_CITY = "city"
    const val ADDRESS_STATE = "state"
    const val ADDRESS_LANDMARK = "landmark"
    const val ADDRESS_PINCODE = "pincode"
    const val ADDRESS_COUNTRY = "country"
    const val ADDRESS_ALTERNATE_CONTACT = "alt_contact_no"


    // Banner Images Strings
    const val BANNER_IMAGE_ID = "slider_id"
    const val BANNER_IMAGE_URL = "slider_app_image"
    const val BANNER_TXT = "slider_text"


    // Categories String
    const val CATEGORY_POSITION = "category_pos"
    const val CATEGORY_ID = "manage_category_id"
    const val CATEGORY_TITLE = "category_name"
    const val CATEGORY_IMAGE = "category_image"
    const val CATEGORIES_OBJECT = "categories"

    // CITY Strings
    const val CITY_ID = "city_id"
    const val CITY_NAME = "city_name"
    const val CITY_IMAGE = "city_app_image"

    // PRODUCTS STRINGS
    const val PRODUCT_DETAILS_OBJECT = "PRODUCT_DETAILS"
    const val PRODUCT_ID = "product_id"
    const val PRODUCT_NAME = "product_name"
    const val PRODUCT_PRICE = "starting_price"
    const val PRODUCT_IMAGE = "product_app_image"
    const val PRODUCT_TYPE = "product_type"
    const val PRODUCT_TYPE_PACKAGE = "PACKAGE"
    const val PRODUCT_TYPE_PRODUCT = "PRODUCT"
    const val PRODUCT_DESC = "description"
    const val PRODUCT_REFUNDABLE_DEPOSIT = "refundable_amount"
    const val SIZES_AND_DIMENSIONS = "SIZES_AND_DIMENSIONS"
    const val FEATURES_AND_SPECS = "FEATURES_AND_SPECS"
    const val MATERIAL_AND_COLOR = "MATERIAL_AND_COLOR"
    const val TERMS_AND_CONDITIONS = "terms_and_conditions"
    const val RENT_TENURES = "RENT_TYPE_DETAILS"
    const val TENURE_ID = "tenure_id"
    const val TENURE_TYPE = "rent_type"
    const val TENURE_DURATION = "tenure_duration"
    const val TENURE_RENT = "tenure_amount"
    const val PRODUCT_SHIPPING_CHARGES = "shipping_charge"
    const val TOTAL_DISCOUNT = "total_discount"
    const val TOTAL_RENT_WITH_DISCOUNT = "rent_with_discount"
    const val GST = "gst"
    const val DELIVERY_DATE = "delivery_date"

    // COUPON CODE STRINGS
    const val COUPON_TYPE = "coupon_type"
    const val COUPON_TYPE_PERCENTAGE = "PERCENTAGE"
    const val COUPON_TYPE_DISCOUNT = "Discount Amount"
    const val COUPON_DISCOUNT_AMOUNT = "discount"
    const val COUPON_DISCOUNT_PERCENTAGE = "PERCENTAGE"
    const val COUPON_ID = "coupon_id"
    const val COUPON_CODE = "coupon_code"

    // PAYMENT GATEWAY STRINGS
    const val TOTAL_AMOUNT = "total_amount"
    const val SALT = "1sIvw3rekk"
    const val MERCHANT_KEY = "H5mbb1qw"
    const val TEST_KEY = "bewDvhHJ"
    const val TEST_KEY1 = "rjQUPktU"
    const val TEST_SALT = "e5iIg1jwi8"
    const val GATEWAY_MERCHANT_IDENTIFIER = "5779974"
    const val TEST_GATEWAY_MERCHANT_IDENTIFIER = "6239369"
    const val PAYMENT_SUCCESS_URL = "http://sportsadda.in/Pages/Success.aspx"
    const val PAYMENT_FAILURE_URL = "http://sportsadda.in/Pages/Failure.aspx"

    // ORDERS STRINGS
    const val ORDER_USER_ID =
        "USERID" // have to pass contact number in this to get the orders

    const val ORDER_ID = "order_id"
    const val PRODUCT_QTY = "qty"
    const val ORDER_TOTAL_AMOUNT = "total"
    const val MODE_OF_PAYMENT =
        "payment_mode" //  cash on delivery - Card on Delivery , payment gateway - Go With Payumoney

    const val CARD_ON_DELIVERY_MODE = "Card on Delivery"
    const val PAYUMONEY_MODE = "PayuMoney"
    const val PRODUCTS = "order_product"
    const val ORDER_STATUS = "order_status"
    const val PAYMENT_STATUS = "payment_status" // PENDING or DONE

    const val PAYMENT_STATUS_PENDING = "Pending"
    const val PAYMENT_STATUS_DONE = "Done"
    const val ORDER_DATE = "delivery_date"
    const val ORDER_PRODUCTS = "order_product"
    const val ORDER_STATUS_FAILED = "Failed"
    const val ORDER_STATUS_PLACED = "Order Placed"
    const val ORDER_STATUS_PROCESS = "Process"
    const val ORDER_STATUS_DISPATCH = "Dispatch"
    const val ORDER_STATUS_DELIVER = "Deliver"
    const val TXN_ID = "txn_id"


    // WEB PAGE STRINGS
    const val PAGE_TYPE = "page_type"
    const val TYPE_ABOUT = "ABOUT"
    const val TYPE_DOCS = "DOCS"
    const val TYPE_RENTAL = "RENTAL"
    const val TYPE_REFUND = "REFUND"
    const val TYPE_PRIVACY = "PRIVACY"
    const val TYPE_SHIPPING = "SHIPPING"
    const val PAGE_ID = "ABOUT_ID"
    const val PAGE_NAME = "DOCUMENTS_NAME"
    const val PAGE_DESC = "ABOUTUS"
}