package goforfit.com.sportsaddakotlin.models

import com.google.gson.annotations.SerializedName
import goforfit.com.sportsaddakotlin.helper.AppConfig
import java.util.*

data class ProductsResponseModel(
    @SerializedName(AppConfig.STATUS_CODE) var statusCode: Int,
    @SerializedName(AppConfig.RESPONSE_MSG) val message: String,
    @SerializedName(AppConfig.RESPONSE) val products: ArrayList<Product>
)