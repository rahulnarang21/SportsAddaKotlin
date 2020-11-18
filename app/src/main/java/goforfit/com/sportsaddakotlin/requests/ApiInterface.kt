package goforfit.com.sportsaddakotlin.requests

import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

//    @POST(AppConfig.LOGIN_USER_METHOD)
//    fun loginUser(@Body loginDetails:HashMap<String,Any>):Call<UserModel>

    @POST(AppConfig.LOGIN_USER_METHOD)
    fun loginUser(@Body loginDetails: HashMap<String?, Any?>): Call<UserModel>

//    @GET(AppConfig.GET_CITIES_METHOD)
//    fun getCities(): Call<CityResponse>


    @GET(AppConfig.GET_CITIES_METHOD)
    suspend fun getCities(): Response<CityResponse>

    @FormUrlEncoded
    @POST(AppConfig.GET_HOME_PAGE_ITEMS)
    fun getHomePageItems(@Field(AppConfig.CITY_ID) cityId:String?):Call<HomePageResponseModel>

    @FormUrlEncoded
    @POST(AppConfig.GET_PRODUCTS_URL)
    fun getProducts(@Field(AppConfig.CATEGORY_ID) categoryId:String): Call<ProductsResponseModel>

    @POST(AppConfig.GET_PRODUCT_DETAILS_URL)
    fun getProductDetails(@Body productDetails: HashMap<String, Any>): Call<ProductDetailsResponseModel>

}