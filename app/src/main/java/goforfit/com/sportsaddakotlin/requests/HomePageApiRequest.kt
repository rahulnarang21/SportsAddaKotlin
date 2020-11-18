package goforfit.com.sportsaddakotlin.requests

import android.content.Context
import android.opengl.Visibility
import android.view.View
import android.widget.ProgressBar
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.helper.Utility
import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.HomePageResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePageApiRequest(val context: Context, val responseListener: ResponseListener,
                         val progressBar: ProgressBar): Callback<HomePageResponseModel> {


    fun hitRequest(){
        val apiInterface = ApiClient.buildService(ApiInterface::class.java)
//        val apiCall:Call<HomePageResponseModel> =
//            apiInterface.getHomePageItems(Utility.getSharedPrefs(context)?.getString(AppConfig.CITY_ID,""))
//        apiCall.enqueue(this)

    }

    override fun onResponse(call: Call<HomePageResponseModel>, response: Response<HomePageResponseModel>) {
       responseListener.onResponse(response.body())
        progressBar.visibility = View.GONE

    }


    override fun onFailure(call: Call<HomePageResponseModel>, t: Throwable) {
        responseListener.onResponse(null)
        progressBar.visibility = View.GONE

    }



}