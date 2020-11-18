package goforfit.com.sportsaddakotlin.requests

import android.app.Activity
import android.app.ProgressDialog
import android.util.Log
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.listeners.ResponseListener
import goforfit.com.sportsaddakotlin.models.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRequest(val activity: Activity?, val userContact: String?, val userPassword: String,
                   val responseListener: ResponseListener?):Callback<UserModel>{
//    private var activity: Activity? = null
//    private var userContact: String? = null
//    private  var userPassword:String? = null
//    private var responseListener: ResponseListener? = null
    private var progressDialog: ProgressDialog? = null

//   init {
//       this.activity = activity
//       this.userContact = userContact
//       this.userPassword = userPassword
//       this.responseListener = responseListener
//       //this.progressDialog = progressDialog;
//   }

    fun hitLoginRequest() {
        val apiInterface: ApiInterface = ApiClient.buildService(ApiInterface::class.java)
        val call: Call<UserModel> = apiInterface.loginUser(getRequestBody())
        call.enqueue(this)
        progressDialog = ProgressDialog(activity)
        progressDialog!!.setMessage("Logging In..")
        progressDialog!!.show()
    }

    private fun getRequestBody(): HashMap<String?, Any?> {
        val params = HashMap<String?, Any?>()
        params[AppConfig.USER_CONTACT] = userContact
        params[AppConfig.USER_PASSWORD] = userPassword
        Log.w(AppConfig.TAG, params.toString())
        return params
    }

    override fun onResponse(call: Call<UserModel?>?, response: Response<UserModel?>) {
        progressDialog!!.dismiss()
        responseListener!!.onResponse(response.body())
    }

    override fun onFailure(call: Call<UserModel?>?, t: Throwable?) {
        progressDialog!!.dismiss()
        responseListener!!.onResponse(null)
    }
}