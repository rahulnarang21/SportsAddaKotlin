package goforfit.com.sportsaddakotlin.models

import com.google.gson.annotations.SerializedName
import goforfit.com.sportsaddakotlin.helper.AppConfig

data class ProductDetailsResponseModel(
    @SerializedName(AppConfig.STATUS_CODE) val statusCode: Int,
    @SerializedName(AppConfig.RESPONSE_MSG) val message: String,
    @SerializedName(AppConfig.RESPONSE) val productDetails: Product
)
