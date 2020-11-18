package goforfit.com.sportsaddakotlin.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.databinding.ActivityLoginBinding
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.helper.DatabaseManager
import goforfit.com.sportsaddakotlin.helper.Utility
import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.UserModelResponse
import goforfit.com.sportsaddakotlin.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity(),ResponseListener {

    var databaseManager: DatabaseManager? = null
    var sharedPreferences:SharedPreferences?=null
    var progressDialog:ProgressDialog?=null
    var userContact:String=""
    var userPassword:String=""

    lateinit var binding:ActivityLoginBinding
    lateinit var loginViewModel:LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this
        loginViewModel.responseListener = this

        databaseManager = DatabaseManager(this)
        sharedPreferences = Utility.getSharedPrefs(this)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Loading...")
        //progressDialog!!.show()

        loginViewModel.init(this)

        loginViewModel.getIsLoading().observe(this, Observer { aBoolean ->
                if (aBoolean)
                    progressDialog!!.show()
                else
                    progressDialog!!.hide()
                //placesRecyclerView.smoothScrollToPosition(placesActivityViewModel.getPlacesLiveData().getValue().size()-1);
            })

//        loginViewModel.getLogin().observe(this, Observer {
//
//        })

        onClick()
    }

    fun onClick(){
//        login_btn.setOnClickListener{
//            checkFields()
//        }
//
//        facebook_login_btn.setOnClickListener {  }
//
//        gmail_login_btn.setOnClickListener {  }
//
//        forget_password.setOnClickListener{}
//
//        sign_up_link.setOnClickListener {  }
    }

//    fun checkFields(){
//        userContact = user_contact.text.toString().trim()
//        userPassword = user_password.text.toString().trim()
//
//        if (Utility.checkField(userContact,this,"Contact",true)
//            && Utility.checkField(userPassword,this,"Password")){
////            Toast.makeText(this,"Logged in", Toast.LENGTH_LONG).show()
//            val loginRequest = LoginRequest(this,userContact,userPassword,this)
//            loginRequest.hitLoginRequest()
//
//        }
//    }
//
//    override fun onResponse(obj: Any?) {
//        if (obj is UserModel){
//            val userModel = obj
//            if (userModel.statusCode == AppConfig.SUCCESS_CODE){
//                addUser(userModel.userModelResponse!!)
//            }
//            else{
//                Toast.makeText(this,userModel.message,Toast.LENGTH_LONG).show()
//            }
//        }
//    }

    fun addUser(userModelResponse: UserModelResponse){
        var dataAdded = false
        dataAdded = databaseManager!!.addUser(userModelResponse.userId!!,userModelResponse.userName!!,userModelResponse.userEmail!!,
        userModelResponse.userContact!!)

        if (dataAdded){
            val intent:Intent
            if (sharedPreferences!!.contains(AppConfig.CITY_ID))
                intent = Intent(this,
                    MainActivity::class.java)
            else
                intent = Intent(this,SelectCityActivity::class.java)
            var editor:SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.putBoolean(AppConfig.IS_USER_LOGGED_IN,true)
            editor.apply()
            finish()
            startActivity(intent)

        }
        else{
            Toast.makeText(this, "Some error occured!", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onResponse(obj: Any?) {
        if (obj is UserModelResponse){
            addUser(obj)
        }
        else{
            Toast.makeText(this,"Something went wrong!",Toast.LENGTH_LONG).show()
        }
    }


}
