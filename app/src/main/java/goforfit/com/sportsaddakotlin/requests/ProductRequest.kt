package goforfit.com.sportsaddakotlin.requests

import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.ProductsResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRequest(private val responseListener: ResponseListener): Callback<ProductsResponseModel> {

    fun hitRequest(categoryId:String){
        val apiInterface = ApiClient.buildService(ApiInterface::class.java)
        val apiCall:Call<ProductsResponseModel> = apiInterface.getProducts(categoryId)
        apiCall.enqueue(this)
    }

    override fun onResponse(call: Call<ProductsResponseModel>, response: Response<ProductsResponseModel>) {
        responseListener.onResponse(response.body())
    }

    override fun onFailure(call: Call<ProductsResponseModel>, t: Throwable) {
        responseListener.onResponse(null)
    }


}