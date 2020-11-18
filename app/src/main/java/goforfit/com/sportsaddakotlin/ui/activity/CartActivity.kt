package goforfit.com.sportsaddakotlin.ui.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import goforfit.com.sportsaddakotlin.CartPaymentDetailsActivity
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.ui.adapters.CartProductRecyclerAdapter
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.helper.DatabaseManager
import goforfit.com.sportsaddakotlin.models.Product
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*

class CartActivity : AppCompatActivity(), (View, Int) -> Unit {

    var totalProductsTxt:TextView? = null
    var totalRefundableDepositTxt:TextView? = null
    var cartProductRecyclerAdapter: CartProductRecyclerAdapter? = null
    var productArrayList: ArrayList<Product> = ArrayList<Product>()
    var databaseManager: DatabaseManager? = null
    var headerView: View? = null
    var calendar: Calendar? = null
    var minDateCalendar:Calendar? = null
    var maxDateCalendar:Calendar? = null
    var dateListener: OnDateSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        databaseManager = DatabaseManager(this)

        toolbar_title.text = getString(R.string.cart)
        back_btn.setOnClickListener {
            super.onBackPressed()
        }

//        headerView = getLayoutInflater().inflate(R.layout.cart_header_layout,null);
//        totalProductsTxt = headerView!!.findViewById(R.id.total_products);
//        totalRefundableDepositTxt = headerView.findViewById(R.id.total_refundable_deposit);
//
//        productsListView.addHeaderView(headerView);
//
//        headerView = layoutInflater.inflate(R.layout.cart_header_layout,null)

        cartProductRecyclerAdapter = CartProductRecyclerAdapter(this,productArrayList,this)
        products_recycler_view.layoutManager = LinearLayoutManager(this)
        products_recycler_view.adapter = cartProductRecyclerAdapter

        loadProducts()

        proceed_btn.setOnClickListener {
            selectDeliveryDate()
        }

        setDateListener()


    }

    private fun loadProducts(){
        val tempList = databaseManager!!.getCartProducts()
        productArrayList.clear()
        productArrayList.addAll(tempList)

        if (productArrayList.size>0){
//            headerView!!.visibility = View.VISIBLE
            proceed_btn.visibility = View.VISIBLE
            cartProductRecyclerAdapter!!.notifyDataSetChanged()
        }
        else{
            proceed_btn.visibility = View.GONE
            no_results_txt.visibility = View.GONE
        }
    }

    // for delivery date selection ---


    private fun setDateListener(){
        val timeZone = TimeZone.getTimeZone(AppConfig.INDIAN_TIME_ZONE)
        calendar = Calendar.getInstance(timeZone)
        minDateCalendar = Calendar.getInstance(timeZone)
        maxDateCalendar = Calendar.getInstance(timeZone)
        minDateCalendar!!.add(Calendar.DAY_OF_MONTH,1)
        maxDateCalendar!!.add(Calendar.MONTH,6)
        dateListener = OnDateSetListener { datePicker, year, month, day ->
            calendar!!.set(Calendar.YEAR,year)
            calendar!!.set(Calendar.MONTH,month)
            calendar!!.set(Calendar.DAY_OF_YEAR,day)

            val intent = Intent(this, CartPaymentDetailsActivity::class.java)
            intent.putExtra(AppConfig.DELIVERY_DATE,calendar!!.timeInMillis)
            startActivity(intent)

        }

    }

    private fun selectDeliveryDate(){
        val datePickerDialog = DatePickerDialog(this,dateListener,calendar!!.get(Calendar.YEAR),
            calendar!!.get(Calendar.MONTH),calendar!!.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.setTitle(getString(R.string.select_delivery_date))
        datePickerDialog.datePicker.minDate = minDateCalendar!!.timeInMillis
        datePickerDialog.datePicker.maxDate = maxDateCalendar!!.timeInMillis
        datePickerDialog.show()
    }


    // -- end of delivery date selection functions -->

    override fun invoke(view: View, position: Int) {
//        TODO("Not yet implemented")
        Log.d(AppConfig.TAG, view.id.toString())
        val product = productArrayList.get(position)
        val qty = product.productQty
        when(view.id){
            R.id.increment_products_btn->{
                Toast.makeText(this,"$qty increment" ,Toast.LENGTH_LONG).show()
            }
            R.id.decrement_products_btn->{
                Toast.makeText(this,"$qty decrement",Toast.LENGTH_LONG).show()
            }
        }
    }
}
