package goforfit.com.sportsaddakotlin.requests

import android.view.View
import android.widget.ProgressBar
import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.CityResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityRequest(val progressBar: ProgressBar,val responseListener: ResponseListener): Callback<CityResponse> {
    fun hitRequest(){
//        val apiInterface = ApiClient.buildService(ApiInterface::class.java)
//        val apiCall:Call<CityResponse> = apiInterface.getCities()
//        apiCall.enqueue(this)
    }

    override fun onResponse(call: Call<CityResponse>, response: Response<CityResponse>) {
        responseListener.onResponse(response.body())
        progressBar.visibility = View.GONE
    }

    override fun onFailure(call: Call<CityResponse>, t: Throwable) {
        responseListener.onResponse(null)
        progressBar.visibility = View.GONE
    }


}