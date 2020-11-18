package goforfit.com.sportsaddakotlin.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.databinding.CityLayoutBinding
import goforfit.com.sportsaddakotlin.models.City
import goforfit.com.sportsaddakotlin.viewmodel.CityViewModel
import kotlinx.android.synthetic.main.city_layout.view.*

class SelectCityAdapter(val cityViewModel: CityViewModel,private val itemClickedListener: (Int,Int) -> Unit,val context:Context,var citiesArrayList: ArrayList<City>?):RecyclerView.Adapter<SelectCityAdapter.MyViewHolder>(){

    inner class MyViewHolder(val cityBinding:CityLayoutBinding):RecyclerView.ViewHolder(cityBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val cityLayoutBinding:CityLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.city_layout,parent,false)
        cityLayoutBinding.cityViewModel = cityViewModel
        return MyViewHolder(cityLayoutBinding)
//            .listen(itemClickedListener)
//            .listen{
//            position, type ->
//            val city = citiesArrayList!!.get(position)
//                Toast.makeText(context,city.cityName,Toast.LENGTH_LONG).show()
//                for (cityObj in citiesArrayList){
//                    cityObj.isCitySelected = cityObj.cityId == city.cityId
//                }
//                notifyDataSetChanged()
////                var selectedCityId = city.cityId
////                var selectedCityName = city.cityName
//
//
//        }
    }

    override fun getItemCount(): Int {
        return citiesArrayList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cityResponse = citiesArrayList!![position]
        holder.cityBinding.cityModel = cityResponse
//        holder.itemView.setOnClickListener{}
    }

//    fun <T:RecyclerView.ViewHolder> T.listen(event:(position:Int, type:Int)->Unit):T{
//        itemView.setOnClickListener{
//            //event.invoke(adapterPosition,itemViewType) // if we use this, we can call this click listener in the adapter only
//
//            itemClickedListener.invoke(adapterPosition,itemViewType) // in this, we are calling the invoke function of activity
//            // which we passed as a callback of our lambda extension function
//        }
//        return this
//    }

    fun setCityArrayList(citiesArrayList: ArrayList<City>){
        this.citiesArrayList = citiesArrayList
        notifyDataSetChanged()
    }



//        private void selectCity(){
//        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                City.CityResponse city = cityArrayList.get(i);
//                for (int j = 0; j < cityArrayList.size(); j++) {
//                if (i == j) {
//                    if (!city.isCitySelected()) {
//                        city.setCitySelected(true);
//                    }
//                } else {
//                    cityArrayList.get(j).setCitySelected(false);
//                }
//            }
//
//                selectedCityId = city.getCityId();
//                selectedCityName = city.getCityName();
//                selectCityAdapter.notifyDataSetChanged();
//                savePrefsAndPassIntent();
//            }
//        });
//    }

}