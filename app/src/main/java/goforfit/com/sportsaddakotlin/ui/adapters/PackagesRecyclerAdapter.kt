package goforfit.com.sportsaddakotlin.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.models.Product
import kotlinx.android.synthetic.main.products_package_layout.view.*

class PackagesRecyclerAdapter(val context: Context,val packagesArrayList: ArrayList<Product>,
                              val itemClickListener:((View,Int)->Unit)):RecyclerView.Adapter<PackagesRecyclerAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun setData(position: Int){
            val product = packagesArrayList.get(position)
            itemView.product_name.text = product.productName
            itemView.product_starting_price.text = "Starting from र${product.productStartingPrice}"

            Glide.with(context).load(product.productImage)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.cart_product_image)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.products_package_layout,parent,false)
        return  MyViewHolder(view).listen(itemClickListener)
    }

    override fun getItemCount(): Int {
        return packagesArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(position)
    }

    fun <T:RecyclerView.ViewHolder> T.listen(event: (view:View,position:Int)->Unit):T{
        itemView.setOnClickListener {
            itemClickListener.invoke(itemView,adapterPosition)
        }
        return this
    }

}