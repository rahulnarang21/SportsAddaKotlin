package goforfit.com.sportsaddakotlin.ui.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.ui.adapters.CategoriesRecyclerAdapter
import goforfit.com.sportsaddakotlin.ui.adapters.PackagesRecyclerAdapter
import goforfit.com.sportsaddakotlin.ui.adapters.ViewPagerAdapter
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.helper.DatabaseManager
import goforfit.com.sportsaddakotlin.helper.FixedSpeedScroller
import goforfit.com.sportsaddakotlin.helper.Utility
import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.Category
import goforfit.com.sportsaddakotlin.models.HomePageResponseModel
import goforfit.com.sportsaddakotlin.models.Product
import goforfit.com.sportsaddakotlin.models.ViewPagerImageModel
import goforfit.com.sportsaddakotlin.requests.ApiClient
import goforfit.com.sportsaddakotlin.requests.ApiInterface
import goforfit.com.sportsaddakotlin.requests.HomePageApiRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_toolbar.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity(), ResponseListener, NavigationView.OnNavigationItemSelectedListener,
    SwipeRefreshLayout.OnRefreshListener, (View, Int) -> Unit {

    var bannersViewPagerAdapter: ViewPagerAdapter? = null
    var bannersImagesArrayList: ArrayList<ViewPagerImageModel> = ArrayList()
    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 500 //delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 3000 // time in milliseconds between successive task executions.
    var NUM_PAGES = 3
    var categoriesRecyclerAdapter: CategoriesRecyclerAdapter? = null
    var packagesRecyclerAdapter: PackagesRecyclerAdapter? = null
    var categoriesArrayList: ArrayList<Category> = ArrayList()
    var packagesArrayList: ArrayList<Product> = ArrayList()
    var cityNameItem: MenuItem? = null
    var sharedPreferences: SharedPreferences? = null
    var progressDialog: ProgressDialog? = null
    var databaseManager: DatabaseManager? = null
    var isBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = Utility.getSharedPrefs(this)
        databaseManager = DatabaseManager(this)

        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Loading")

        bannersViewPagerAdapter = ViewPagerAdapter(this,bannersImagesArrayList)
        banner_images_view_pager.adapter = bannersViewPagerAdapter

        HomePageApiRequest(this,this,progress_bar).hitRequest()

//        loadItems()


        tab_dots.setupWithViewPager(banner_images_view_pager, true)

        scrollSmooth()


        setCategoryRecyclerView()
        setPackagesRecyclerView()


        //loadCategories();
        //showProductsList();
        //showProductDetails();


        val toggle = ActionBarDrawerToggle(this,drawer_layout,
            toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        val headerView: View = nav_view.getHeaderView(0)

        nav_view.setNavigationItemSelectedListener(this)

        val userNameTxt = headerView.findViewById<TextView>(R.id.user_name)
        val userImageTxt = headerView.findViewById<TextView>(R.id.user_image)
        val userName = databaseManager?.userDetails!!.get(AppConfig.USER_NAME)
        userNameTxt.text= userName
        if (userName.equals(""))
            userImageTxt.text = userName!!.toUpperCase()[0].toString()


        val navigationMenu = nav_view.menu
        cityNameItem = navigationMenu.findItem(R.id.nav_select_city_btn)
        cityNameItem!!.setTitle(sharedPreferences!!.getString(AppConfig.CITY_NAME,"Select City"))

        refresh_layout.setOnRefreshListener(this)

        showCart()


    }


    private fun changeSliderImage() {
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            banner_images_view_pager.setCurrentItem(currentPage++, true)
        }
        timer = Timer() // This will create a new Thread
        timer!!.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(Update)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    fun scrollSmooth(){
        try{
            val interpolator = AccelerateInterpolator()
            val mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            val scroller = FixedSpeedScroller(banner_images_view_pager.context,interpolator)
            mScroller.set(banner_images_view_pager,scroller)
        } catch (e:NoSuchFieldException) {
        } catch (e:IllegalArgumentException) {
        } catch (e:IllegalAccessException) {
        }
    }

    fun setCategoryRecyclerView(){
        categoriesRecyclerAdapter = CategoriesRecyclerAdapter(this,categoriesArrayList,this)

        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        categories_recycler_view.layoutManager = layoutManager
        categories_recycler_view.isNestedScrollingEnabled = false
        categories_recycler_view.adapter = categoriesRecyclerAdapter

    }

    fun loadCategoriesObject():JSONObject{
        val jsonObject=JSONObject()
        val jsonArray=JSONArray()

        try {
            for(category in categoriesArrayList){
                val jsonObject1 = JSONObject()
                jsonObject1.put(AppConfig.CATEGORY_ID,category.categoryId)
                jsonObject1.put(AppConfig.CATEGORY_TITLE,category.categoryTitle)
                jsonArray.put(jsonObject1)

            }
            jsonObject.put(AppConfig.RESPONSE,jsonArray)
        }
        catch (e:JSONException){}
        return jsonObject

    }

    fun setPackagesRecyclerView(){
        packagesRecyclerAdapter = PackagesRecyclerAdapter(this,packagesArrayList,this)
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        packages_recycler_view.layoutManager = layoutManager
        packages_recycler_view.isNestedScrollingEnabled = false
        packages_recycler_view.adapter = packagesRecyclerAdapter

    }

    private fun showHideCartCountBadge(){
        val total_no:Int  = databaseManager!!.getTotalProducts()
        if (total_no>0){
            cart_count_badge.visibility = View.VISIBLE
            cart_count_badge.text = total_no.toString()
        }
        else
            cart_count_badge.visibility = View.GONE
    }

    private fun showCart(){
        cart_btn.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }
    }

//    private fun loadItems(){
//        lifecycleScope.launch {
//            val apiInterface = ApiClient.buildService(ApiInterface::class.java)
//            val call = apiInterface.getHomePageItems(sharedPreferences?.getString(AppConfig.CITY_ID, ""))
//            progress_bar.visibility = View.GONE
//            if (call.isSuccessful) {
//                val obj = call.body()!!
//                if (obj.statusCode == AppConfig.SUCCESS_CODE) {
//                    progressDialog!!.dismiss()
//                    categories_and_packages_layout.visibility = View.VISIBLE
//                    bannersImagesArrayList.clear()
//                    categoriesArrayList.clear()
//                    packagesArrayList.clear()
//                    val response = obj.response
//                    bannersImagesArrayList.addAll(response.bannersList)
//                    NUM_PAGES = bannersImagesArrayList.size
//                    bannersViewPagerAdapter?.notifyDataSetChanged()
//                    changeSliderImage()
//
//                    categoriesArrayList.addAll(response.categoryArrayList)
//                    categoriesRecyclerAdapter?.notifyDataSetChanged()
//
//                    packagesArrayList.addAll(response.packagesArrayList)
//                    packagesRecyclerAdapter?.notifyDataSetChanged()
//
//                    refresh_layout.isRefreshing = false
//                }
//            }
//        }
//    }


    override fun onResponse(obj: Any?) {
        if (obj is HomePageResponseModel){
            if (obj.statusCode == AppConfig.SUCCESS_CODE){
                progressDialog!!.dismiss()
                categories_and_packages_layout.visibility = View.VISIBLE
                bannersImagesArrayList.clear()
                categoriesArrayList.clear()
                packagesArrayList.clear()
                val response = obj.response
                bannersImagesArrayList.addAll(response.bannersList)
                NUM_PAGES = bannersImagesArrayList.size
                bannersViewPagerAdapter?.notifyDataSetChanged()
                changeSliderImage()

                categoriesArrayList.addAll(response.categoryArrayList)
                categoriesRecyclerAdapter?.notifyDataSetChanged()

                packagesArrayList.addAll(response.packagesArrayList)
                packagesRecyclerAdapter?.notifyDataSetChanged()

                refresh_layout.isRefreshing = false
            }
        }
        else
            Toast.makeText(this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_select_city_btn->{
                startActivityForResult(Intent(this,SelectCityActivity::class.java),
                    AppConfig.INTENT_REQUEST_CODE)
            }
            R.id.my_orders->{
//                startActivity(Intent(this,))
            }
            R.id.my_addresses->{

            }
            R.id.nav_contact_us->{
                startNewImplicitActivity("tel:" + AppConfig.SPORTSADDA_CONTACT_NO)
            }
            R.id.nav_email_us->{
                startNewImplicitActivity("mailto:"+AppConfig.SPORTSADDA_EMAIL_ID)
            }
            R.id.nav_rate_us->{
                try{
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+AppConfig.PACKAGE_NAME)))
                } catch (e:ActivityNotFoundException){
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+AppConfig.PACKAGE_NAME)))
                }
            }
            R.id.nav_documents->{
                startNewImplicitActivity(AppConfig.DOCUMENTS_URL)
            }
            R.id.nav_shipping_policy->{
                startNewImplicitActivity(AppConfig.SHIPPING_POLICY_URL)
            }
            R.id.nav_terms_and_conditions->{
                startNewImplicitActivity(AppConfig.TERMS_URL)
            }
            R.id.nav_logout->{
                showLogoutConfirmationDialog()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true

    }


    private fun startNewImplicitActivity(uri: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

    override fun onRefresh() {
//        loadItems()
        HomePageApiRequest(this,this,progress_bar).hitRequest()
    }

    override fun onResume() {
        super.onResume()
        showHideCartCountBadge()
    }

    private fun showLogoutConfirmationDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.confirm))
        builder.setMessage(getString(R.string._sure_logout_confirm))
        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, i -> logoutUser() }
        builder.setNegativeButton(getString(R.string.cancel)) { dialogInterface, i -> }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun logoutUser(){
        progressDialog!!.setMessage(getString(R.string.logging_out))
        val sharedPreferences = Utility.getSharedPrefs(this)
        sharedPreferences!!.edit().clear().apply()
        databaseManager!!.deleteDatabase()
        val intent = Intent(this,LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        progressDialog!!.dismiss()
    }

//    override fun invoke(p1: Int, p2: Int) {
//        if (p2==0){ // for categories
//            val intent = Intent(this, ProductsActivity::class.java)
//            intent.putExtra(AppConfig.CATEGORIES_OBJECT, loadCategoriesObject().toString())
//            intent.putExtra(AppConfig.CATEGORY_POSITION, p1)
//            startActivity(intent)
//        }
//        else if (p2==1){ // for packages
//
//        }
//    }

    override fun invoke(view: View, position: Int) {
        if (view.id == R.id.category_layout){
            val intent = Intent(this, ProductsActivity::class.java)
            intent.putExtra(AppConfig.CATEGORIES_OBJECT, loadCategoriesObject().toString())
            intent.putExtra(AppConfig.CATEGORY_POSITION, position)
            startActivity(intent)
        }
        else if (view.id == R.id.package_layout){
            //progressDialog!!.dismiss()
            val product = packagesArrayList[position]
            val intent = Intent(this, ProductDetailsActivity::class.java)
            intent.putExtra(AppConfig.PRODUCT_ID, product.productId)
            intent.putExtra(AppConfig.PRODUCT_NAME, product.productName)
            intent.putExtra(AppConfig.PRODUCT_IMAGE, product.productImage)
            intent.putExtra(AppConfig.PRODUCT_TYPE, AppConfig.PRODUCT_TYPE_PACKAGE)
            startActivity(intent)
        }
    }

}
