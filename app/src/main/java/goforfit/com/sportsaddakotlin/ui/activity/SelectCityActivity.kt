package goforfit.com.sportsaddakotlin.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.ui.adapters.SelectCityAdapter
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.helper.DatabaseManager
import goforfit.com.sportsaddakotlin.helper.Utility
import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.CityResponse
import goforfit.com.sportsaddakotlin.models.City
import goforfit.com.sportsaddakotlin.requests.CityRequest
import goforfit.com.sportsaddakotlin.viewmodel.CityViewModel
import kotlinx.android.synthetic.main.activity_select_city.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SelectCityActivity : AppCompatActivity(), (Int, Int) -> Unit {

    var selectCityAdapter:SelectCityAdapter?=null
    val cityArrayList:ArrayList<City>?=ArrayList()
    var sharedPreferences:SharedPreferences?=null
    var selectedCityId:String?=null
    var selectedCityName:String?=null
    lateinit var cityViewModel:CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)


        cityViewModel = ViewModelProvider(this,CityViewModel.CityViewModelFactory(application,this)).get(CityViewModel::class.java)

        cityViewModel.init()
        setRecyclerView()
        sharedPreferences = Utility.getSharedPrefs(this)

//        val cityRequest = CityRequest(progress_bar,this)
//        cityRequest.hitRequest()


        cityViewModel.getCitiesLiveData.observe(this,
            Observer<List<City>> {
                selectCityAdapter!!.setCityArrayList(it as ArrayList<City>)
            })

        cityViewModel.getIsLoading().observe(this, Observer { aBoolean ->
            if (aBoolean)
                progress_bar!!.visibility = View.VISIBLE
            else
                progress_bar!!.visibility = View.GONE
            //placesRecyclerView.smoothScrollToPosition(placesActivityViewModel.getPlacesLiveData().getValue().size()-1);
        })

        toolbar_title.text = getString(R.string.select_your_city)
        back_btn.setOnClickListener {
//            super@SelectCityActivity.onBackPressed()
            super.onBackPressed()
        }

    }

    private fun setRecyclerView(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        city_recycler_view.layoutManager = layoutManager
        selectCityAdapter = SelectCityAdapter(cityViewModel,this,this,cityArrayList)
        city_recycler_view.adapter = selectCityAdapter
    }

//    override fun onResponse(obj: Any?) {
//        if (obj is CityResponse){
//            var cityId:String
//            val city = obj
//            for (cityResponse in city.response){
//                var isCitySelected = false
//                cityId = cityResponse.cityId
//                if (sharedPreferences!!.getString(AppConfig.CITY_ID,"").equals(cityId))
//                    isCitySelected = true
//                cityArrayList!!.add(City(cityId,cityResponse.cityName,cityResponse.cityImage,isCitySelected))
//            }
//            selectCityAdapter!!.notifyDataSetChanged()
//
//        }
//    }


    override fun invoke(position: Int, viewtype: Int) {
        val city = cityArrayList!!.get(position)
        Toast.makeText(this,city.cityName,Toast.LENGTH_LONG).show()
        for (cityObj in cityArrayList){
            cityObj.isCitySelected = cityObj.cityId == city.cityId
        }
        selectCityAdapter!!.notifyDataSetChanged()
        selectedCityId = city.cityId
        selectedCityName = city.cityName
        savePrefsAndPassIntent()
    }

    private fun saveCityPrefs(){
        val editor = sharedPreferences?.edit()
        editor?.putString(AppConfig.CITY_ID, selectedCityId)
        editor?.putString(AppConfig.CITY_NAME, selectedCityName)
        editor?.apply()
    }

    private fun savePrefsAndPassIntent(){
        // ? is the safe call which will not call null pointer but !! can call null pointer exception
        if (sharedPreferences!!.contains(AppConfig.CITY_ID)){
            DatabaseManager(this).emptyCart()
            saveCityPrefs()
            setResult(AppConfig.INTENT_REQUEST_CODE, Intent())
            finish()
        }
        else{
            saveCityPrefs()
            startActivity(Intent(this,
                MainActivity::class.java))
            finish()
        }
    }

}

