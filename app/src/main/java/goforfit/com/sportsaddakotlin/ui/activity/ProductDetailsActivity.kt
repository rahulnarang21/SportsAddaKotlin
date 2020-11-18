package goforfit.com.sportsaddakotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.ui.adapters.ProductTenureListAdapter
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.helper.DatabaseManager
import goforfit.com.sportsaddakotlin.helper.Utility
import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.Product
import goforfit.com.sportsaddakotlin.models.ProductDetailsResponseModel
import goforfit.com.sportsaddakotlin.requests.ProductDetailsRequest
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.product_layout.product_name
import kotlinx.android.synthetic.main.toolbar_layout.*

class ProductDetailsActivity : AppCompatActivity(), ResponseListener, View.OnClickListener {

    var tenuresListAdapter: ProductTenureListAdapter? = null
    var tenuresArrayList: ArrayList<Product> = ArrayList()
    var tenuresListAlertDialog: AlertDialog? = null
    var productId: String? = null
    var productName:String? = null
    var productImage:String? = null
    var productType:String? = null
    var refundableDeposit = 0
    var shippingCharges:Int = 0
    var selectedProductTenure: Product? = null
    var tenuresListView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        setFieldsFromIntent()
        ProductDetailsRequest(progress_bar,this).hitRequest(productId!!,productType!!)

        back_btn.setOnClickListener(this)
        select_tenure_btn.setOnClickListener(this)

    }


    fun setFieldsFromIntent(){
        intent = this.intent
        productId = intent.getIntExtra(AppConfig.PRODUCT_ID,0).toString()
        productName = intent.getStringExtra(AppConfig.PRODUCT_NAME)
        productImage = intent.getStringExtra(AppConfig.PRODUCT_IMAGE)
        productType = intent.getStringExtra(AppConfig.PRODUCT_TYPE)
        product_name.text = productName
        Glide.with(this).load(productImage)
            .thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(cart_product_image)

        toolbar_title.text = productName
    }

    fun loadTenureList(rentTenuresArrayList:ArrayList<Product>,tenureType:String){
        var isTenureSelected:Boolean
        for (i in 0 until rentTenuresArrayList.size){
            isTenureSelected = i==0
            val tenure = rentTenuresArrayList[i]
            val product = Product()
            product.tenureId = tenure.tenureId
            product.tenureDuration = tenure.tenureDuration
            product.tenureType = tenureType
            product.tenureRent = tenure.tenureRent
            product.isTenureSelected = isTenureSelected
            tenuresArrayList.add(product)
        }

        selectedProductTenure = tenuresArrayList[0]
        product_tenure.text = Utility.getTenureTitleText(selectedProductTenure!!)
    }


    private fun showTenureOptionsDialog(){
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.list_view_dialog_parent_layout,null)
        builder.setView(view)


        //Log.w(AppConfig.TAG,"tenures array list "+tenuresArrayList);
        val bookPlanBtn = view.findViewById<LinearLayout>(R.id.book_plan_btn)
        val refundableDepositEdTxt = view.findViewById<TextView>(R.id.refundable_deposit)
        refundableDepositEdTxt.text = "Refundable Deposit: $refundableDeposit"
        tenuresListView = view.findViewById<ListView>(R.id.list_view)

        tenuresListAdapter = ProductTenureListAdapter(this,tenuresArrayList)
        tenuresListView!!.adapter = tenuresListAdapter
        bookPlanBtn.setOnClickListener(this)

        tenuresListAlertDialog = builder.create()
        tenuresListAlertDialog!!.show()

        selectTenure()
    }

    fun selectTenure(){
        tenuresListView!!.setOnItemClickListener { adapterView, view, i, l ->
            for (j in 0 until tenuresArrayList.size){
                if (i==j){
                    if (!tenuresArrayList[i].isTenureSelected){
                        tenuresArrayList[i].isTenureSelected = true
                    }
                }
                else{
                    tenuresArrayList[j].isTenureSelected = false
                }
            }
            tenuresListAdapter!!.notifyDataSetChanged()
            selectedProductTenure = tenuresArrayList[i]
            product_tenure.text = Utility.getTenureTitleText(selectedProductTenure!!)
        }
    }

    private fun addCartProducts() {
        val databaseManager = DatabaseManager(this)
        val tenureId: Int = selectedProductTenure!!.tenureId
        val tenureNumber: Int = selectedProductTenure!!.tenureDuration
        val tenureType: String? = selectedProductTenure!!.tenureType
        val tenureRent: Int = selectedProductTenure!!.tenureRent
        if (!databaseManager.isProductExist(productId!!, tenureId.toString())) {
            val isDataAdded: Boolean = databaseManager.addToCart(productId!!, productName!!, productImage!!,
                refundableDeposit, tenureId, tenureNumber, tenureType!!, tenureRent, shippingCharges,1)
            if (isDataAdded) {
                bookPlan()
            }
        } else {
            val isDataUpdated: Boolean = databaseManager.updateCartProduct(tenureId, tenureNumber, tenureType!!, tenureRent)
            if (isDataUpdated) {
                bookPlan()
            }
        }
    }

    private fun bookPlan() {
        tenuresListAlertDialog!!.dismiss()
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    override fun onResponse(obj: Any?) {
        if (obj is ProductDetailsResponseModel){
            product_details_layout.visibility = View.VISIBLE
            select_tenure_btn.visibility = View.VISIBLE
            if (obj.statusCode == AppConfig.SUCCESS_CODE){
                val productDetails = obj.productDetails
                productDetails.productDesc?.let{
                    product_desc.text = Utility.htmlToString(productDetails.productDesc)
                }
                productDetails.termsAndConditions?.let{
                    terms_and_conditions.text = Utility.htmlToString(productDetails.termsAndConditions)
                }

                refundableDeposit = productDetails.refundableDeposit
                shippingCharges = productDetails.shippingCharges

                loadTenureList(productDetails.productTenuresArrayList!!,productDetails.tenureType!!)
            }
            else
                Toast.makeText(this, obj.message, Toast.LENGTH_SHORT).show()

        }
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.back_btn-> super.onBackPressed()
            R.id.select_tenure_btn->showTenureOptionsDialog()
            R.id.book_plan_btn->addCartProducts()
        }
    }


}
