package goforfit.com.sportsaddakotlin.models

import com.google.gson.annotations.SerializedName
import goforfit.com.sportsaddakotlin.helper.AppConfig

data class UserModel(
    @SerializedName(AppConfig.STATUS_CODE)var statusCode:Int = 0,
    @SerializedName(AppConfig.RESPONSE_MSG)var message: String? = null,
    @SerializedName(AppConfig.RESPONSE)var userModelResponse: UserModelResponse? = null
)

data class UserModelResponse(
    @SerializedName(AppConfig.USER_ID) val userId: String? = null,
    @SerializedName(AppConfig.USER_NAME) val userName: String? = null,
    @SerializedName(AppConfig.USER_EMAIL) val userEmail: String? = null,
    @SerializedName(AppConfig.USER_CONTACT) val userContact: String? = null
)