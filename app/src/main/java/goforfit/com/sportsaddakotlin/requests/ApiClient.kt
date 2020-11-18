package goforfit.com.sportsaddakotlin.requests

import goforfit.com.sportsaddakotlin.helper.AppConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ApiClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor { chain->
            val request = chain.request().newBuilder().addHeader("Connection","close").build()
            chain.proceed(request)
        }
        .build()

    private val retrofitBuilder = Retrofit.Builder().baseUrl(AppConfig.APP_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)

    private val retrofit = retrofitBuilder.build()

    fun <T> buildService(serviceType:Class<T>):T{
        return retrofit.create(serviceType)
    }
}