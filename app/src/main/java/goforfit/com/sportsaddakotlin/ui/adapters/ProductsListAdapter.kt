package goforfit.com.sportsaddakotlin.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.models.Product

class ProductsListAdapter(val context: Context,val productArrayList: ArrayList<Product>) :BaseAdapter(){

    inner class ViewHolder(view: View){
        val productImage:ImageView = view.findViewById(R.id.cart_product_image)
        val productName:TextView = view.findViewById(R.id.product_name)
        val productStartingPrice:TextView = view.findViewById(R.id.product_starting_price)


    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewHolder:ViewHolder
        val rowView:View
        if (view == null) {
            rowView = layoutInflater.inflate(R.layout.product_layout, viewGroup, false)
            viewHolder = ViewHolder(rowView)
            rowView.tag = viewHolder
//            viewHolder.productImage = view.findViewById(R.id.product_image)
//            viewHolder.productName = view.findViewById(R.id.product_name)
//            viewHolder.productStartingPrice = view.findViewById(R.id.product_starting_price)
        } else {
            rowView = view
            viewHolder = rowView.tag as ViewHolder
        }

        val product = productArrayList[position]
        viewHolder.productName.text = product.productName
        val stringBuilder = context.resources.getString(R.string.starting_from_price)
        stringBuilder.plus(" ")
        stringBuilder.plus(context.resources.getString(R.string.rupees_symbol_label) + product.productStartingPrice)
        viewHolder.productStartingPrice.text = stringBuilder.toString()

        Glide.with(context).load(product.productImage)
            .thumbnail(0.5f)
            .placeholder(R.drawable.default_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(viewHolder.productImage)

        return rowView
    }

    override fun getItem(p0: Int): Any {
        return productArrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return productArrayList.size
    }

}