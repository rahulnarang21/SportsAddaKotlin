package goforfit.com.sportsaddakotlin.repository

import androidx.lifecycle.MutableLiveData
import goforfit.com.sportsaddakotlin.models.City
import goforfit.com.sportsaddakotlin.models.CityResponse
import goforfit.com.sportsaddakotlin.requests.ApiClient
import goforfit.com.sportsaddakotlin.requests.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectCityRepository {
    private val citiesLiveData = MutableLiveData<List<City>>()
    var isLoading = MutableLiveData<Boolean>()

//    suspend fun getCitiesLiveData1():List<City> {
//        return ApiClient.buildService(ApiInterface::class.java).getCities1().body()!!.response
//
//    }

    suspend fun getCitiesLiveData(): List<City>{
        val apiInterface:ApiInterface = ApiClient.buildService(ApiInterface::class.java)
        val citiesResponse = apiInterface.getCities()
        var cityArrayList:ArrayList<City> = ArrayList()
        if (citiesResponse.isSuccessful){
            cityArrayList = citiesResponse.body()!!.response
            for (city in cityArrayList){
                city.isCitySelected = false
            }

//            citiesLiveData.value = cityArrayList
        }
//        val citiesCall: Call<CityResponse> = apiInterface.getCities()
//        citiesCall.enqueue(object :Callback<CityResponse>{
//            override fun onFailure(call: Call<CityResponse>, t: Throwable) {
//                isLoading.value = false
//            }
//
//            override fun onResponse(call: Call<CityResponse>, response: Response<CityResponse>) {
//                isLoading.value = false
//                if (response.isSuccessful){
//                    val citiesResponse = response.body()
//                    val cityArrayList:ArrayList<City> = citiesResponse!!.response
//                    for (city in cityArrayList){
//                        city.isCitySelected = false
//                    }
//                    citiesLiveData.value = cityArrayList
//                }
//            }
//
//        })
//        return citiesLiveData
        return cityArrayList
    }
}