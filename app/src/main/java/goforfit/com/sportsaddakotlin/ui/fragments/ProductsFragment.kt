package goforfit.com.sportsaddakotlin.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.ui.activity.ProductDetailsActivity
import goforfit.com.sportsaddakotlin.ui.adapters.ProductsListAdapter
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.Product
import goforfit.com.sportsaddakotlin.models.ProductsResponseModel
import goforfit.com.sportsaddakotlin.requests.ProductRequest
import kotlinx.android.synthetic.main.fragment_products.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ProductsFragment : Fragment(), ResponseListener {

    var productsListAdapter: ProductsListAdapter? = null
    var productArrayList: ArrayList<Product> = ArrayList()
    var isDataLoaded = false
    var categoryId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productsListAdapter = ProductsListAdapter(context!!,productArrayList)
        products_grid_view.adapter = productsListAdapter
        products_grid_view.setOnItemClickListener { adapterView, view, i, l ->
            loadProductDetails(i)
        }

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !isDataLoaded){
            val bundle = getArguments()
            categoryId = bundle!!.getString(AppConfig.CATEGORY_ID)
            val productRequest = ProductRequest(this)
            productRequest.hitRequest(categoryId!!)
            //loadProducts();
        }
    }

    override fun onResponse(obj: Any?) {
        progress_bar.visibility = View.GONE
        if (obj is ProductsResponseModel){
            if (obj.statusCode == AppConfig.SUCCESS_CODE){
                productArrayList.addAll(obj.products)
                productsListAdapter!!.notifyDataSetChanged()
            }
        }
    }

    fun loadProductDetails(position:Int){
        val product = productArrayList.get(position)
        val intent = Intent(context,ProductDetailsActivity::class.java)
        intent.putExtra(AppConfig.PRODUCT_ID,product.productId)
        intent.putExtra(AppConfig.PRODUCT_NAME,product.productName)
        intent.putExtra(AppConfig.PRODUCT_IMAGE,product.productImage)
        intent.putExtra(AppConfig.PRODUCT_TYPE,AppConfig.PRODUCT_TYPE_PRODUCT)
        startActivity(intent)

    }
}
