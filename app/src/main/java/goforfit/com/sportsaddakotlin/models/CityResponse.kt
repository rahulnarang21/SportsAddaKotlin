package goforfit.com.sportsaddakotlin.models

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.annotations.SerializedName
import goforfit.com.sportsaddakotlin.helper.AppConfig
import kotlinx.android.synthetic.main.city_layout.view.*

data class CityResponse(
    @SerializedName(AppConfig.STATUS_CODE) var statusCode:Int,
    @SerializedName(AppConfig.RESPONSE_MSG) var message:String,
    @SerializedName(AppConfig.RESPONSE) var response:ArrayList<City>
)

class City {
    @SerializedName(AppConfig.CITY_ID)
    var cityId: String?=null
    @SerializedName(AppConfig.CITY_NAME)
    var cityName: String?=null
    @SerializedName(AppConfig.CITY_IMAGE)
    var cityImage: String?=null
    var isCitySelected: Boolean?=null

    companion object{

        @BindingAdapter("cityImage")
        @JvmStatic
        fun loadImage(imageView: ImageView,url:String){
            Glide.with(imageView.context).load(url)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)

        }
    }

    fun onClick(view:View,city:City){
        Log.e("check",city.cityName!!)
//        val city = cityArrayList!!.get(position)
        Toast.makeText(view.context,city.cityName, Toast.LENGTH_LONG).show()
        city.isCitySelected = true
//        for (cityObj in allCities.value!!){
//            cityObj.isCitySelected = cityObj.cityId == city.cityId
//        }
        //selectCityAdapter!!.notifyDataSetChanged()
        //selectedCityId = city.cityId
        //selectedCityName = city.cityName
        //savePrefsAndPassIntent()
    }
}





//public ArrayList<CityResponse> getResponse() {
//    return response;
//}
//
//public void setResponse(ArrayList<CityResponse> response) {
//    this.response = response;
//}
//
//public static class CityResponse{
//    @SerializedName(AppConfig.CITY_ID)
//    private String cityId;
//    @SerializedName(AppConfig.CITY_NAME)
//    private String cityName;
//    @SerializedName(AppConfig.CITY_IMAGE)
//    private String cityImage;
//    private boolean isCitySelected;

//    public CityResponse(String cityId, String cityName, String cityImage,boolean isCitySelected) {
//        this.cityId = cityId;
//        this.cityName = cityName;
//        this.cityImage = cityImage;
//        this.isCitySelected = isCitySelected;
//    }