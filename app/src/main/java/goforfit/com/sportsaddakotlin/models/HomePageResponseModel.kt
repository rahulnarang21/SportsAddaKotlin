package goforfit.com.sportsaddakotlin.models

import com.google.gson.annotations.SerializedName
import goforfit.com.sportsaddakotlin.helper.AppConfig
import java.util.*
import kotlin.collections.ArrayList

data class HomePageResponseModel(
    @SerializedName(AppConfig.STATUS_CODE) var statusCode: Int,
    @SerializedName(AppConfig.RESPONSE_MSG) var message: String,
    @SerializedName(AppConfig.RESPONSE) var response: Response
)

data class Response(
    @SerializedName(AppConfig.BANNERS) var bannersList: ArrayList<ViewPagerImageModel>,
    @SerializedName(AppConfig.CATEGORIES) var categoryArrayList: ArrayList<Category>,
    @SerializedName(AppConfig.PACKAGES) var packagesArrayList: ArrayList<Product>
)