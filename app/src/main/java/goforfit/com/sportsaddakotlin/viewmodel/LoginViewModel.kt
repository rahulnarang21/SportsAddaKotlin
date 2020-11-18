package goforfit.com.sportsaddakotlin.viewmodel

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.helper.Utility
import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.UserModel
import goforfit.com.sportsaddakotlin.models.UserModelResponse
import goforfit.com.sportsaddakotlin.repository.LoginRepository
import goforfit.com.sportsaddakotlin.requests.ApiClient
import goforfit.com.sportsaddakotlin.requests.ApiInterface
import goforfit.com.sportsaddakotlin.requests.LoginRequest
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class LoginViewModel :ViewModel(){
    var userContact= MutableLiveData<String>()
    var userPassword = MutableLiveData<String>()
    var context:Context? = null
    private var userData: MutableLiveData<UserModelResponse>? = null
    private var loginRepository: LoginRepository? = null
    var responseListener:ResponseListener? = null

    fun init(context: Context) {
        this.context = context
        loginRepository = LoginRepository(context,responseListener!!)
        loginRepository!!.isLoading.value = false
        if (userData != null) return
//        userData = loginRepository!!.getLogin()
        //isUpdating.setValue(false);
    }

    fun checkFields(){
        val userContactStr = userContact.value!!
        val userPassStr = userPassword.value!!
        if (Utility.checkField(userContact.value!!,context,"Contact",true)
            && Utility.checkField(userPassword.value!!,context,"Password")){
//            Toast.makeText(this,"Logged in", Toast.LENGTH_LONG).show()
//            val loginRequest = LoginRequest(context,userContact,userPassword,this)
//            loginRequest.hitLoginRequest()
            val loginResponse:LiveData<UserModelResponse> = loginRepository!!.getLogin(userContact.value!!,userPassword.value!!)

//            getLogin()

        }
    }

    fun getLogin(): LiveData<UserModelResponse> {
        return userData!!
    }
//fun getLogin():MutableLiveData<UserModelResponse>{
//    val userModel = MutableLiveData<UserModelResponse>()
//    val apiInterface: ApiInterface = ApiClient.buildService(ApiInterface::class.java)
//    val call: Call<UserModel> = apiInterface.loginUser(getRequestBody())
//    call.enqueue(object : Callback<UserModel> {
//        override fun onFailure(call: Call<UserModel>, t: Throwable) {
//            loginRepository!!.isLoading.value = false
//        }
//
//        override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
//            loginRepository!!.isLoading.value = false
//            val userModelResponse = response.body()
//            if (userModelResponse!!.statusCode == AppConfig.SUCCESS_CODE)
//                userModel.value = response.body()!!.userModelResponse
//            else
//                Toast.makeText(context,userModelResponse.message, Toast.LENGTH_LONG).show()
//        }
//
//    })
//    return userModel
//}

    fun getIsLoading(): LiveData<Boolean> {
        return loginRepository!!.isLoading
    }

    fun getRequestBody(userContact:String,userPassword:String): HashMap<String?, Any?> {
        val params = HashMap<String?, Any?>()
        params[AppConfig.USER_CONTACT] = userContact
        params[AppConfig.USER_PASSWORD] = userPassword
        Log.w(AppConfig.TAG, params.toString())
        return params
    }

}