package goforfit.com.sportsaddakotlin.models

import com.google.gson.annotations.SerializedName
import goforfit.com.sportsaddakotlin.helper.AppConfig
import org.json.JSONArray
import java.util.*

data class Product(
    @SerializedName(AppConfig.ORDER_ID) var orderId: String? = null,
    @SerializedName(AppConfig.PRODUCT_NAME) var productName: String? = null,
    @SerializedName(AppConfig.PRODUCT_PRICE) var productStartingPrice: String? = null,
    @SerializedName(AppConfig.PRODUCT_IMAGE) var productImage: String? = null,
    @SerializedName(AppConfig.PRODUCT_DETAILS_OBJECT) var productDetailsObject: String? = null,
    @SerializedName(AppConfig.TENURE_TYPE) var tenureType: String? = null,
    @SerializedName(AppConfig.MODE_OF_PAYMENT) var modeOfPayment: String? = null,
    @SerializedName(AppConfig.DELIVERY_DATE) var deliveryDate: String? = null,
    @SerializedName(AppConfig.ORDER_STATUS) var orderStatus: String? = null,
    @SerializedName(AppConfig.PRODUCT_ID) var productId: Int = 0,
    @SerializedName(AppConfig.PRODUCT_QTY) var productQty: Int = 0,
    @SerializedName(AppConfig.PRODUCT_REFUNDABLE_DEPOSIT) var refundableDeposit: Int = 0,
    @SerializedName(AppConfig.TENURE_ID) var tenureId: Int = 0,
    @SerializedName(AppConfig.TENURE_DURATION) var tenureDuration: Int = 0,
    @SerializedName(AppConfig.TENURE_RENT) var tenureRent: Int = 0,
    @SerializedName(AppConfig.PRODUCT_SHIPPING_CHARGES) var shippingCharges: Int = 0,
    @SerializedName(AppConfig.TOTAL_AMOUNT) var totalAmount: Int = 0,
    @SerializedName(AppConfig.PRODUCT_DESC) var productDesc: String? = null,
    @SerializedName(AppConfig.TERMS_AND_CONDITIONS) var termsAndConditions: String? = null,

    var orderItems: Int = 0,
    var isTenureSelected: Boolean = false,
    @SerializedName(AppConfig.COUPON_ID) var couponCodeId: String? = null,
    @SerializedName(AppConfig.COUPON_CODE) var couponCode: String? = null,
    @SerializedName(AppConfig.COUPON_TYPE) var discountType: String? = null,
    @SerializedName(AppConfig.COUPON_DISCOUNT_AMOUNT) var couponCodeDiscount: Int = 0,
    @SerializedName(AppConfig.PRODUCTS) var productsArray: JSONArray? = null,
    @SerializedName(AppConfig.RENT_TENURES) var productTenuresArrayList: ArrayList<Product>? = null
)