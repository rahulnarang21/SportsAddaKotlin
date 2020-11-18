package goforfit.com.sportsaddakotlin.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.helper.DatabaseManager
import goforfit.com.sportsaddakotlin.helper.Utility
import goforfit.com.sportsaddakotlin.models.City
import goforfit.com.sportsaddakotlin.repository.SelectCityRepository
import goforfit.com.sportsaddakotlin.ui.activity.MainActivity
import kotlinx.coroutines.launch

class CityViewModel(application: Application):AndroidViewModel(application) {
    private val selectCityRepository = SelectCityRepository()
    private val citiesLiveData:MutableLiveData<List<City>> = MutableLiveData()
    private var sharedPreferences: SharedPreferences?=null
    private lateinit var selectedCity:String
    private lateinit var context:Context
    val getCitiesLiveData: LiveData<List<City>>
        get() = citiesLiveData

    fun init() {
        viewModelScope.launch {
            selectCityRepository.isLoading.value = true
            citiesLiveData.value = selectCityRepository.getCitiesLiveData()
            selectCityRepository.isLoading.value = false
        }
    }

//    fun init() {
//        selectCityRepository.isLoading.value = true
////        if (citiesLiveData!=null)
////            return
//
//        viewModelScope.launch {
//            citiesLiveData as MutableLiveData
//            citiesLiveData = selectCityRepository.getCitiesLiveData()
//        }
////        citiesLiveData = selectCityRepository.getCitiesLiveData()
//    }
//    val allCities:LiveData<List<City>> get() = citiesLiveData!!

    fun getIsLoading(): LiveData<Boolean> {
        return selectCityRepository.isLoading
    }

    fun onClick(view:View,city:City){
        Log.e("check",city.cityName!!)
//        val city = cityArrayList!!.get(position)
//        Toast.makeText(view.context,city.cityName, Toast.LENGTH_LONG).show()
        val cityArrayList = citiesLiveData!!.value!!
        for (cityObj in cityArrayList){
            cityObj.isCitySelected = cityObj.cityId == city.cityId
        }
        citiesLiveData!!.value = cityArrayList
        //selectedCityId = city.cityId
        //selectedCityName = city.cityName
        context = view.context
        savePrefsAndPassIntent(city)
    }

    private fun saveCityPrefs(context: Context,city: City){
        sharedPreferences = Utility.getSharedPrefs(context)
        val editor = sharedPreferences?.edit()
        editor?.putString(AppConfig.CITY_ID, city.cityId)
        editor?.putString(AppConfig.CITY_NAME, city.cityName)
        editor?.apply()
    }

    private fun savePrefsAndPassIntent(city: City){
        // ? is the safe call which will not call null pointer but !! can call null pointer exception
        sharedPreferences = Utility.getSharedPrefs(context)
        if (sharedPreferences!!.contains(AppConfig.CITY_ID)){
            DatabaseManager(context).emptyCart()
            saveCityPrefs(context,city)
            (context as Activity).setResult(AppConfig.INTENT_REQUEST_CODE, Intent())
            (context as Activity).finish()
        }
        else{
            saveCityPrefs(context,city)
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as Activity).finish()
        }
    }

    class CityViewModelFactory : ViewModelProvider.Factory {
        private var mApplication:Application?=null

        constructor()

        constructor(application: Application,context: Context){
            mApplication = application
        }

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CityViewModel(mApplication!!) as T
        }

        fun getApplication():Application{
            return mApplication!!
        }

    }
}