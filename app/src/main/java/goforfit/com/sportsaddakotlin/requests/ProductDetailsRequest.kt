package goforfit.com.sportsaddakotlin.requests

import android.view.View
import android.widget.ProgressBar
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.ProductDetailsResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsRequest(val progressBar: ProgressBar,val responseListener: ResponseListener) :
    Callback<ProductDetailsResponseModel> {

    fun hitRequest(productId:String, productType:String){
        val productDetails = HashMap<String,Any>()
        productDetails[AppConfig.PRODUCT_ID] = productId
        productDetails[AppConfig.PRODUCT_TYPE] = productType

        val apiInterface = ApiClient.buildService(ApiInterface::class.java)
        val apiCall = apiInterface.getProductDetails(productDetails)
        apiCall.enqueue(this)
    }

    override fun onFailure(call: Call<ProductDetailsResponseModel>, t: Throwable) {
        responseListener.onResponse(null)
        progressBar.visibility = View.GONE
    }

    override fun onResponse(call: Call<ProductDetailsResponseModel>, response: Response<ProductDetailsResponseModel>) {
        responseListener.onResponse(response.body())
        progressBar.visibility = View.GONE
    }
}