package goforfit.com.sportsaddakotlin.models

import com.google.gson.annotations.SerializedName
import goforfit.com.sportsaddakotlin.helper.AppConfig

class Category(
    @field:SerializedName(AppConfig.CATEGORY_ID) val categoryId: String,
    @field:SerializedName(AppConfig.CATEGORY_TITLE) val categoryTitle: String,
    @field:SerializedName(AppConfig.CATEGORY_IMAGE) val categoryImage: String
)