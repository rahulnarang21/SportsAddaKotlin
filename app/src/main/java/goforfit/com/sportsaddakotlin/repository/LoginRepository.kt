package goforfit.com.sportsaddakotlin.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.UserModel
import goforfit.com.sportsaddakotlin.models.UserModelResponse
import goforfit.com.sportsaddakotlin.requests.ApiClient
import goforfit.com.sportsaddakotlin.requests.ApiInterface
import goforfit.com.sportsaddakotlin.viewmodel.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginRepository(var context: Context, var responseListener: ResponseListener) {
    private var apiInterface: ApiInterface? = null
    private var instance: LoginRepository?= null
    var isLoading = MutableLiveData<Boolean>()

//    fun getInstance(): LoginRepository? {
//        if (instance == null) {
//            instance = LoginRepository()
//        }
//        return instance
//    }

    fun getLogin(userContact:String,userPassword:String):MutableLiveData<UserModelResponse>{
        val userModel = MutableLiveData<UserModelResponse>()
        val apiInterface: ApiInterface = ApiClient.buildService(ApiInterface::class.java)
        val call: Call<UserModel> = apiInterface.loginUser(LoginViewModel().getRequestBody(userContact,userPassword))
        call.enqueue(object :Callback<UserModel>{
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                isLoading.value = false
                userModel.value = null
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                isLoading.value = false
                val userModelResponse = response.body()
                if (userModelResponse!!.statusCode == AppConfig.SUCCESS_CODE) {
                    userModel.value = response.body()!!.userModelResponse
                }
                else
                    Toast.makeText(context,userModelResponse.message,Toast.LENGTH_LONG).show()
                responseListener.onResponse(userModel.value)
            }

        })
        return userModel
    }


}