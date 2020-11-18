package goforfit.com.sportsaddakotlin.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.helper.Utility
import goforfit.com.sportsaddakotlin.models.Product

class ProductTenureListAdapter(val context: Context,val productsArrayList: ArrayList<Product>) :BaseAdapter(){

    inner class MyViewHolder(view: View){
        val tenure = view.findViewById<TextView>(R.id.list_item_title)
        val tenureSelectedIcon = view.findViewById<ImageView>(R.id.checked_image)
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val myViewHolder:MyViewHolder
        val rowView:View
        if (view == null){
            rowView = LayoutInflater.from(context).inflate(R.layout.list_view_dialog_child_layout,parent,false)
            myViewHolder = MyViewHolder(rowView)
            rowView.tag = myViewHolder

        }
        else{
            rowView = view
            myViewHolder = rowView.tag as MyViewHolder
        }

        val productTenure = productsArrayList.get(position)
        myViewHolder.tenure.setText(Utility.getTenureTitleText(productTenure))

        if (productTenure.isTenureSelected)
            myViewHolder.tenureSelectedIcon.setImageResource(R.drawable.tik)
        else
            myViewHolder.tenureSelectedIcon.setImageResource(R.drawable.circle)


        return rowView
    }

    override fun getItem(pos: Int): Any {
        return productsArrayList.get(pos)
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getCount(): Int {
        return productsArrayList.size
    }

}