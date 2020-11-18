package goforfit.com.sportsaddakotlin.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.helper.Utility
import goforfit.com.sportsaddakotlin.models.Product
import kotlinx.android.synthetic.main.cart_product_layout.view.*
import kotlinx.android.synthetic.main.products_package_layout.view.product_name

class CartProductRecyclerAdapter(val context: Context, val productArrayList: ArrayList<Product>,
                                 private val itemClickListener:((View, Int)->Unit)):RecyclerView.Adapter<CartProductRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        fun setData(position: Int){
            val product = productArrayList[position]
            val resources = context.resources
            val qty = product.productQty
            val totalPrice = qty * product.tenureRent
            val totalDeposit = qty * product.refundableDeposit

            itemView.product_name.text = product.productName
            val shipping = resources.getString(R.string.rs_string,product.shippingCharges)
            itemView.shipping_charges.text = resources.getString(R.string.shipping_charges,shipping)
            itemView.product_rent.text = resources.getString(R.string.rs_string,totalPrice)
            itemView.product_tenure.text = product.tenureDuration.toString() + " " + Utility.getTenureType(product.tenureType!!)
            itemView.product_qty.text = qty.toString()
            itemView.refundable_deposit.text = resources.getString(R.string.rs_string,totalDeposit)

            Glide.with(context).load(product.productImage)
                .thumbnail(0.5f)
                .placeholder(R.drawable.default_global_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.cart_product_image)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_product_layout,parent,false)
        return MyViewHolder(view).listen(itemClickListener)
    }

    override fun getItemCount(): Int {
        return productArrayList.size
    }

    override fun onBindViewHolder(holder: CartProductRecyclerAdapter.MyViewHolder, position: Int) {
        holder.setData(position)
    }

    fun <T:RecyclerView.ViewHolder> T.listen(event:((view:View,position:Int)->Unit)):T{
        itemView.increment_products_btn.setOnClickListener{
            itemClickListener.invoke(itemView.increment_products_btn,adapterPosition)
        }
        itemView.decrement_products_btn.setOnClickListener{
            itemClickListener.invoke(itemView.decrement_products_btn,adapterPosition)
        }
        return this
    }

    private fun incrementDecrementProducts(tenureId:Int,productQty:Int){

    }
//    private void incrementDecrementProducts(int tenureId,int productQty){
//        CartActivity cartActivity = (CartActivity) context;
//        if (productQty!=0) {
//            if (productQty != 11) {
//                cartActivity.incrementDecrementProducts(tenureId, productQty);
//            }
//            else {
//                Toast.makeText(cartActivity, "You have selected maximum no of products!", Toast.LENGTH_SHORT).show();
//            }
//        }
//        else {
//            cartActivity.showConfirmAndRemoveProduct(tenureId);
//        }
//    }
}
