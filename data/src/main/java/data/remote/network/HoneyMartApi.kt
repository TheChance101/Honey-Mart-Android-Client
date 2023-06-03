package data.remote.network

import data.remote.HoneyMartService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HoneyMartApi {

    private const val BASE_URL = "https://honey-mart-server-oe345.ondigitalocean.app/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit: Retrofit by lazy {
        createRetrofit()
    }

    val honeyMartService: HoneyMartService by lazy {
        retrofit.create(HoneyMartService::class.java)
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)// Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}