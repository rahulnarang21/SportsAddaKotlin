package goforfit.com.sportsaddakotlin.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.ui.adapters.FragmentsAdapter
import goforfit.com.sportsaddakotlin.ui.fragments.ProductsFragment
import goforfit.com.sportsaddakotlin.helper.AppConfig
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.json.JSONException
import org.json.JSONObject

class ProductsActivity : AppCompatActivity() {

    private var categoriesProductsFragmentPagerAdapter:FragmentsAdapter?=null
    var categories:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        categories = this.intent.getStringExtra(AppConfig.CATEGORIES_OBJECT)

        setUpViewPager()

        categories_tab_layout.setupWithViewPager(categories_product_fragment_pager)

        back_btn.setOnClickListener { super.onBackPressed() }
    }

    fun setUpViewPager(){
        categoriesProductsFragmentPagerAdapter = FragmentsAdapter(supportFragmentManager)
        try {
            val jsonObject = JSONObject(categories)
            val jsonArray = jsonObject.getJSONArray(AppConfig.RESPONSE)
            val totalCategories = jsonArray.length()
            for (i in 0 until totalCategories) {
                val categoriesObject: JSONObject = jsonArray.getJSONObject(i)
                val bundle = Bundle()
                val title = categoriesObject.getString(AppConfig.CATEGORY_TITLE)
                bundle.putString(
                    AppConfig.CATEGORY_ID,
                    categoriesObject.getString(AppConfig.CATEGORY_ID)
                )
                bundle.putString(AppConfig.CATEGORY_TITLE, title)
                val productsFragment = ProductsFragment()
                productsFragment.arguments = bundle
                categoriesProductsFragmentPagerAdapter!!.addFragment(productsFragment, title)

            }

            categories_product_fragment_pager.adapter = categoriesProductsFragmentPagerAdapter
            categories_product_fragment_pager.setCurrentItem(intent.extras!!.getInt(AppConfig.CATEGORY_POSITION))
            categories_product_fragment_pager.offscreenPageLimit = totalCategories
        }catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}
