package goforfit.com.sportsaddakotlin.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.models.Category
import kotlinx.android.synthetic.main.category_layout.view.*

class CategoriesRecyclerAdapter(
    val context: Context, val categoryArrayList:ArrayList<Category>,
    val itemClickListener:((View, Int)->Unit)):RecyclerView.Adapter<CategoriesRecyclerAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun setData(position: Int){
            val category = categoryArrayList.get(position)
            itemView.category_title.setText(category.categoryTitle)
            Glide.with(context).load(category.categoryImage)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.category_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_layout,parent,false)
        return MyViewHolder(view).listen(itemClickListener)


    }

    override fun getItemCount(): Int {
        return categoryArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(position)
    }

//    fun <T:RecyclerView.ViewHolder> T.listen1(event:(position: Int)->Unit):T{
//        itemView.setOnClickListener {
//            itemClickListener.invoke(adapterPosition)
//        }
//        return this
//    }

    fun <T:RecyclerView.ViewHolder> T.listen(event:(view:View,position: Int)->Unit):T{
        itemView.setOnClickListener {
            itemClickListener.invoke(itemView,adapterPosition)
        }
        return this
    }


}