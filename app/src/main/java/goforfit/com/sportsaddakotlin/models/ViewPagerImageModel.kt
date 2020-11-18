package goforfit.com.sportsaddakotlin.models

import com.google.gson.annotations.SerializedName
import goforfit.com.sportsaddakotlin.helper.AppConfig

data class ViewPagerImageModel (
    @SerializedName(AppConfig.BANNER_IMAGE_ID) val imageId: String? = null,
    @SerializedName(AppConfig.BANNER_IMAGE_URL) val imageUrl: String? = null,
    @SerializedName(AppConfig.BANNER_TXT) val imageTxt: String? = null

)